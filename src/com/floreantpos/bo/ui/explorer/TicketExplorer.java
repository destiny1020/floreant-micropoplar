package com.floreantpos.bo.ui.explorer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketType;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.PosTableRenderer;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.util.UiUtil;

import net.miginfocom.swing.MigLayout;

public class TicketExplorer extends TransparentPanel {
  private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy, h:m a"); //$NON-NLS-1$
  DecimalFormat numberFormat = new DecimalFormat("0.00"); //$NON-NLS-1$

  private JXTable explorerTable;
  private List<Ticket> tickets;

  private JLabel lblStartTime;
  private JLabel lblEndTime;
  private JXDatePicker dpStartDate;
  private JXDatePicker dpEndDate;

  private JCheckBox chkAll;
  private JCheckBox chkPaid;
  private JCheckBox chkVoided;
  private JCheckBox chkRefunded;

  private JLabel lblTicketType;
  private JComboBox<String> cbTicketType;
  private JLabel lblMembership;
  private JComboBox<String> cbMembership;

  private TransparentPanel pnlFilters;

  private JButton btnLoad;

  public TicketExplorer() {
    initComponents();
  }

  private void initComponents() {
    setLayout(new BorderLayout());

    // filters area
    pnlFilters = new TransparentPanel();
    pnlFilters.setLayout(new MigLayout("", "[][][][][grow]", "[][][][]"));

    lblStartTime = new JLabel(POSConstants.START_DATE + POSConstants.COLON);
    pnlFilters.add(lblStartTime, "cell 0 0, alignx left, aligny center");

    dpStartDate = UiUtil.getCurrentMonthStart();
    pnlFilters.add(dpStartDate, "cell 1 0, alignx left, aligny center");

    lblEndTime = new JLabel(POSConstants.END_DATE + POSConstants.COLON);
    pnlFilters.add(lblEndTime, "cell 2 0, alignx left, aligny center");

    dpEndDate = UiUtil.getCurrentMonthEnd();
    pnlFilters.add(dpEndDate, "cell 3 0, alignx left, aligny center");

    chkAll = new JCheckBox(POSConstants.TICKET_EXPLORER_CHK_ALL);
    chkAll.setSelected(true);
    pnlFilters.add(chkAll, "cell 0 1, alignx left, aligny center");

    chkPaid = new JCheckBox(POSConstants.TICKET_EXPLORER_CHK_PAID);
    chkPaid.setSelected(false);
    chkPaid.setEnabled(false);
    pnlFilters.add(chkPaid, "cell 1 1, alignx left, aligny center");

    chkVoided = new JCheckBox(POSConstants.TICKET_EXPLORER_CHK_VOIDED);
    chkVoided.setSelected(false);
    chkVoided.setEnabled(false);
    pnlFilters.add(chkVoided, "cell 2 1, alignx left, aligny center");

    chkRefunded = new JCheckBox(POSConstants.TICKET_EXPLORER_CHK_REFUNDED);
    chkRefunded.setSelected(false);
    chkRefunded.setEnabled(false);
    pnlFilters.add(chkRefunded, "cell 3 1, alignx left, aligny center");

    lblTicketType = new JLabel(POSConstants.TICKET_EXPLORER_CB_TICKET_TYPE);
    pnlFilters.add(lblTicketType, "cell 0 2, alignx left, aligny center");

    cbTicketType = new JComboBox<>(
        new String[] {POSConstants.TICKET_EXPLORER_CB_OPTION_ALL, TicketType.DINE_IN.getValue(),
            TicketType.TAKE_OUT.getValue(), TicketType.HOME_DELIVERY.getValue()});
    cbTicketType.setSelectedIndex(0);
    pnlFilters.add(cbTicketType, "cell 1 2, alignx left, aligny center");

    lblMembership = new JLabel(POSConstants.TICKET_EXPLORER_CB_MEMBERSHIP);
    pnlFilters.add(lblMembership, "cell 2 2, alignx left, aligny center");

    cbMembership = new JComboBox<>(new String[] {POSConstants.TICKET_EXPLORER_CB_OPTION_ALL,
        POSConstants.TICKET_EXPLORER_CB_OPTION_MEMBER,
        POSConstants.TICKET_EXPLORER_CB_OPTION_NON_MEMBER});
    cbMembership.setSelectedIndex(0);
    pnlFilters.add(cbMembership, "cell 3 2, alignx left, aligny center");

    btnLoad = new JButton(POSConstants.LOAD);
    btnLoad.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        doLoadTickets(evt);
      }
    });

    pnlFilters.add(btnLoad, "cell 2 3 2 1, growx");

    add(pnlFilters, BorderLayout.NORTH);

    // table area
    explorerTable = new JXTable(new TicketExplorerTableModel()) {
      PosTableRenderer renderer = new PosTableRenderer();

      @Override
      public TableCellRenderer getCellRenderer(int row, int column) {
        return renderer;
      }
    };

    explorerTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    explorerTable.setColumnControlVisible(true);
    explorerTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    explorerTable.setHorizontalScrollEnabled(false);

    add(new JScrollPane(explorerTable), BorderLayout.CENTER);
  }

  private void doLoadTickets(ActionEvent evt) {
    Date startDate = dpStartDate.getDate();
    Date endDate = dpEndDate.getDate();

    if (startDate.after(endDate)) {
      POSMessageDialog.showError(BackOfficeWindow.getInstance(),
          com.floreantpos.POSConstants.FROM_DATE_CANNOT_BE_GREATER_THAN_TO_DATE_);
      return;
    }

    // load tickets within certain criteria
    TicketDAO dao = TicketDAO.getInstance();
    List<Ticket> tickets = dao.findTickets(startDate, endDate);
  }

  public void init() {
    try {
      TicketDAO dao = new TicketDAO();
      tickets = dao.findAll();
      explorerTable.packAll();
      explorerTable.repaint();
    } catch (Exception e) {
      BOMessageDialog.showError(e);
    }
  }

  class TicketExplorerTableModel extends AbstractTableModel {
    String[] columnNames = {POSConstants.TICKET_EXPLORER_TABLE_UNIQ_ID, POSConstants.CREATED,
        POSConstants.SETTLE_TIME, POSConstants.TICKET_EXPLORER_TABLE_TICKET_STATUS,
        POSConstants.TICKET_EXPLORER_TABLE_MEMBERSHIP,
        POSConstants.TICKET_EXPLORER_TABLE_TOTAL_BEFORE_DISCOUNT,
        POSConstants.TICKET_EXPLORER_TABLE_TOTAL_DISCOUNT,
        POSConstants.TICKET_EXPLORER_TABLE_TOTAL_AFTER_DISCOUNT,
        POSConstants.TICKET_EXPLORER_TABLE_OPERATION};

    public int getRowCount() {
      if (tickets == null) {
        return 0;
      }
      return tickets.size();
    }

    public int getColumnCount() {
      return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
      return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
      if (tickets == null)
        return ""; //$NON-NLS-1$

      Ticket ticket = tickets.get(rowIndex);

      switch (columnIndex) {
        case 0:
          return String.valueOf(ticket.getId());

        case 1:
          return ticket.getOwner().toString();

        case 2:
          return dateFormat.format(ticket.getCreateDate());

        case 3:
          if (ticket.getClosingDate() != null) {
            return dateFormat.format(ticket.getClosingDate());
          }
          return ""; //$NON-NLS-1$

        case 4:
          return Double.valueOf(ticket.getSubtotalAmount());

        case 5:
          return Double.valueOf(ticket.getDiscountAmount());

        case 6:
          return Double.valueOf(ticket.getTaxAmount());

        case 7:
          return Double.valueOf(ticket.getTotalAmount());

        case 8:
          return Boolean.valueOf(ticket.isPaid());

        case 9:
          return Boolean.valueOf(ticket.isVoided());
      }
      return null;
    }
  }
}
