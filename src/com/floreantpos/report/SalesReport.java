package com.floreantpos.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdesktop.swingx.calendar.DateUtils;

import com.floreantpos.main.Application;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.model.util.DateUtil;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;

public class SalesReport extends Report {
  private SalesReportModel itemReportModel;
  private SalesReportModel modifierReportModel;

  public SalesReport() {
    super();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void refresh() throws Exception {
    createModels();

    SalesReportModel itemReportModel = this.itemReportModel;
    SalesReportModel modifierReportModel = this.modifierReportModel;

    JasperReport itemReport = (JasperReport) JRLoader.loadObject(SalesReportModelFactory.class
        .getResource("/com/floreantpos/report/template/sales_sub_report.jasper"));
    JasperReport modifierReport = (JasperReport) JRLoader.loadObject(SalesReportModelFactory.class
        .getResource("/com/floreantpos/report/template/sales_sub_report.jasper"));

    @SuppressWarnings("rawtypes")
    HashMap map = new HashMap();
    ReportUtil.populateRestaurantProperties(map);
    map.put("reportTitle", "销售报表");
    map.put("reportTime", DateUtil.getReportFullDate(new Date()));
    map.put("dateRange", DateUtil.getReportShortDate(getStartDate()) + "  到  "
        + DateUtil.getReportShortDate(getEndDate()));
    map.put("terminalName", com.floreantpos.POSConstants.ALL);
    map.put("itemDataSource", new JRTableModelDataSource(itemReportModel));
    map.put("modifierDataSource", new JRTableModelDataSource(modifierReportModel));
    map.put("currencySymbol", Application.getCurrencySymbol());
    map.put("itemGrandTotal", itemReportModel.getGrandTotalAsString());
    map.put("modifierGrandTotal", modifierReportModel.getGrandTotalAsString());
    map.put("itemReport", itemReport);
    map.put("modifierReport", modifierReport);

    JasperReport masterReport = (JasperReport) JRLoader.loadObject(
        SalesReport.class.getResource("/com/floreantpos/report/template/sales_report.jasper"));

    JasperPrint print = JasperFillManager.fillReport(masterReport, map, new JREmptyDataSource());
    viewer = new JRViewer(print);
  }

  @Override
  public boolean isDateRangeSupported() {
    return true;
  }

  @Override
  public boolean isTypeSupported() {
    return true;
  }

  public void createModels() {
    Date date1 = DateUtils.startOfDay(getStartDate());
    Date date2 = DateUtils.endOfDay(getEndDate());

    List<Ticket> tickets = TicketDAO.getInstance().findTickets(date1, date2);

    Map<String, ReportItem> itemMap = new HashMap<String, ReportItem>();
    Map<String, ReportItem> modifierMap = new HashMap<String, ReportItem>();

    for (Iterator<Ticket> iter = tickets.iterator(); iter.hasNext();) {
      Ticket t = (Ticket) iter.next();

      Ticket ticket = TicketDAO.getInstance().loadFullTicket(t.getId());

      List<TicketItem> ticketItems = ticket.getTicketItems();
      if (ticketItems == null)
        continue;

      String key = null;
      for (TicketItem ticketItem : ticketItems) {
        if (ticketItem.getItemId() == null) {
          key = ticketItem.getName();
        } else {
          key = ticketItem.getItemId().toString();
        }
        ReportItem reportItem = itemMap.get(key);

        if (reportItem == null) {
          reportItem = new ReportItem();
          reportItem.setId(key);
          reportItem.setPrice(ticketItem.getUnitPrice());
          reportItem.setName(ticketItem.getName());

          itemMap.put(key, reportItem);
        }
        reportItem.setQuantity(ticketItem.getItemCount() + reportItem.getQuantity());
        reportItem.setTotal(reportItem.getTotal() + ticketItem.getSubtotalAmount());
      }

      ticket = null;
      iter.remove();
    }
    itemReportModel = new SalesReportModel();
    itemReportModel.setItems(new ArrayList<ReportItem>(itemMap.values()));
    itemReportModel.calculateGrandTotal();

    modifierReportModel = new SalesReportModel();
    modifierReportModel.setItems(new ArrayList<ReportItem>(modifierMap.values()));
    modifierReportModel.calculateGrandTotal();
  }
}
