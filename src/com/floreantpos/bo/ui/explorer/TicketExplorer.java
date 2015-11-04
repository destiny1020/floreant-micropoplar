package com.floreantpos.bo.ui.explorer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.bo.ui.ComboOption;
import com.floreantpos.bo.ui.explorer.search.TicketSearchDto;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketType;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.model.util.DateUtil;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.PosTableRenderer;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.util.UiUtil;
import com.micropoplar.pos.ui.TextFieldWithPrompt;
import com.micropoplar.pos.ui.model.TicketForm;
import com.micropoplar.pos.ui.util.ControllerGenerator;

import net.miginfocom.swing.MigLayout;

public class TicketExplorer extends TransparentPanel implements ItemListener {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JXTable explorerTable;
  private List<Ticket> tickets;

  private JLabel lblSearch;
  private TextFieldWithPrompt tfSearch;

  private JLabel lblStartTime;
  private JLabel lblEndTime;
  private JXDatePicker dpStartDate;
  private JXDatePicker dpEndDate;

  private JCheckBox chkAll;
  private JCheckBox chkPaid;
  private JCheckBox chkVoided;
  private JCheckBox chkRefunded;

  private JLabel lblTicketType;
  private JComboBox<ComboOption> cbTicketType;
  private JLabel lblMembership;
  private JComboBox<ComboOption> cbMembership;
  private JLabel lblPaymentType;
  private JComboBox<ComboOption> cbPaymentType;

  private TransparentPanel pnlFilters;

  private JButton btnLoad;

  private JButton btnCheckDetails;

  public TicketExplorer() {
    initComponents();
  }

  private void initComponents() {
    setLayout(new BorderLayout());

    // filters area
    pnlFilters = new TransparentPanel();
    pnlFilters.setLayout(new MigLayout("", "[][][][][][][grow]", "[][][][][]"));

    lblSearch = new JLabel(POSConstants.TICKET_EXPLORER_SEARCH + POSConstants.COLON);
    pnlFilters.add(lblSearch, "cell 0 0, alignx left, aligny center");

    tfSearch = new TextFieldWithPrompt(POSConstants.TICKET_EXPLORER_SEARCH_PROMPT);
    pnlFilters.add(tfSearch, "cell 1 0 3 1, grow");

    lblStartTime = new JLabel(POSConstants.START_DATE + POSConstants.COLON);
    pnlFilters.add(lblStartTime, "cell 0 1, alignx left, aligny center");

    dpStartDate = UiUtil.getCurrentMonthStart();
    pnlFilters.add(dpStartDate, "cell 1 1, alignx left, aligny center");

    lblEndTime = new JLabel(POSConstants.END_DATE + POSConstants.COLON);
    pnlFilters.add(lblEndTime, "cell 2 1, alignx left, aligny center");

    dpEndDate = UiUtil.getCurrentMonthEnd();
    pnlFilters.add(dpEndDate, "cell 3 1, alignx left, aligny center");

    chkAll = new JCheckBox(POSConstants.TICKET_EXPLORER_CHK_ALL);
    chkAll.setSelected(true);
    chkAll.addItemListener(this);
    pnlFilters.add(chkAll, "cell 0 2, alignx left, aligny center");

    chkPaid = new JCheckBox(POSConstants.TICKET_EXPLORER_CHK_PAID);
    chkPaid.setSelected(false);
    chkPaid.setEnabled(false);
    chkPaid.addItemListener(this);
    pnlFilters.add(chkPaid, "cell 1 2, alignx left, aligny center");

    chkVoided = new JCheckBox(POSConstants.TICKET_EXPLORER_CHK_VOIDED);
    chkVoided.setSelected(false);
    chkVoided.setEnabled(false);
    chkVoided.addItemListener(this);
    pnlFilters.add(chkVoided, "cell 2 2, alignx left, aligny center");

    chkRefunded = new JCheckBox(POSConstants.TICKET_EXPLORER_CHK_REFUNDED);
    chkRefunded.setSelected(false);
    chkRefunded.setEnabled(false);
    chkRefunded.addItemListener(this);
    pnlFilters.add(chkRefunded, "cell 3 2, alignx left, aligny center");

    lblTicketType = new JLabel(POSConstants.TICKET_EXPLORER_CB_TICKET_TYPE + POSConstants.COLON);
    pnlFilters.add(lblTicketType, "cell 0 3, alignx left, aligny center");

    cbTicketType = new JComboBox<>(
        new ComboOption[] {new ComboOption(0, POSConstants.TICKET_EXPLORER_CB_OPTION_ALL),
            new ComboOption(1, TicketType.DINE_IN.getValue()),
            new ComboOption(2, TicketType.TAKE_OUT.getValue()),
            new ComboOption(3, TicketType.HOME_DELIVERY.getValue())});
    cbTicketType.setSelectedIndex(0);
    pnlFilters.add(cbTicketType, "cell 1 3, alignx left, aligny center");

    lblMembership = new JLabel(POSConstants.TICKET_EXPLORER_CB_MEMBERSHIP + POSConstants.COLON);
    pnlFilters.add(lblMembership, "cell 2 3, alignx left, aligny center");

    cbMembership = new JComboBox<>(
        new ComboOption[] {new ComboOption(0, POSConstants.TICKET_EXPLORER_CB_OPTION_ALL),
            new ComboOption(1, POSConstants.TICKET_EXPLORER_CB_OPTION_MEMBER),
            new ComboOption(2, POSConstants.TICKET_EXPLORER_CB_OPTION_NON_MEMBER)});
    cbMembership.setSelectedIndex(0);
    pnlFilters.add(cbMembership, "cell 3 3, alignx left, aligny center");

    lblPaymentType = new JLabel(POSConstants.TICKET_EXPLORER_CB_PAYMENT_TYPE + POSConstants.COLON);
    pnlFilters.add(lblPaymentType, "cell 4 3, alignx left, aligny center");

    cbPaymentType = ControllerGenerator.getPaymentTypeComboBox();
    pnlFilters.add(cbPaymentType, "cell 5 3, alignx left, aligny center");

    btnLoad = new JButton(POSConstants.LOAD);
    btnLoad.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        doLoadTickets(evt);
      }
    });

    pnlFilters.add(btnLoad, "cell 2 4 2 1, growx");

    add(pnlFilters, BorderLayout.NORTH);

    // table area
    explorerTable = new JXTable(new TicketExplorerTableModel());
    explorerTable.setDefaultRenderer(Object.class, new PosTableRenderer());
    explorerTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    explorerTable.setColumnControlVisible(true);
    explorerTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    explorerTable.setHorizontalScrollEnabled(false);

    add(new JScrollPane(explorerTable), BorderLayout.CENTER);

    // button controls area
    btnCheckDetails = new JButton(POSConstants.TICKET_EXPLORER_CTRL_CHECK_DETAIL);
    btnCheckDetails.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int index = explorerTable.getSelectedRow();
          if (index < 0)
            return;

          Ticket ticket = tickets.get(index);
          // TODO: avoid load one ticket again and again
          ticket = TicketDAO.getInstance().loadFullTicket(ticket.getUniqId());

          TicketForm editor = new TicketForm(ticket);
          BeanEditorDialog dialog =
              new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
          dialog.open();
          if (dialog.isCanceled())
            return;

          explorerTable.repaint();
        } catch (Throwable x) {
          BOMessageDialog.showError(POSConstants.ERROR_MESSAGE, x);
        }
      }
    });
    explorerTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
          btnCheckDetails.doClick();
        }
      }
    });

    TransparentPanel panel = new TransparentPanel();
    panel.add(btnCheckDetails);
    add(panel, BorderLayout.SOUTH);
  }

  private void doLoadTickets(ActionEvent evt) {
    Date startDate = dpStartDate.getDate();
    Date endDate = dpEndDate.getDate();

    if (startDate.after(endDate)) {
      POSMessageDialog.showError(BackOfficeWindow.getInstance(),
          com.floreantpos.POSConstants.FROM_DATE_CANNOT_BE_GREATER_THAN_TO_DATE_);
      return;
    }

    TicketSearchDto searchDto = prepareSearchDto();

    // load tickets within certain criteria
    TicketDAO dao = TicketDAO.getInstance();
    tickets = dao.findTickets(searchDto);

    @SuppressWarnings("unchecked")
    ListTableModel<Ticket> tableModel = (ListTableModel<Ticket>) explorerTable.getModel();
    tableModel.setRows(tickets);
  }

  private TicketSearchDto prepareSearchDto() {
    TicketSearchDto searchDto = new TicketSearchDto();

    searchDto.setUniqIdOrPhone(tfSearch.getText().trim());
    searchDto.setStartDate(dpStartDate.getDate());
    searchDto.setEndDate(dpEndDate.getDate());
    searchDto.setAllTickets(chkAll.isSelected());
    searchDto.setPaidTickets(chkPaid.isSelected());
    searchDto.setVoidedTickets(chkVoided.isSelected());
    searchDto.setRefundedTickets(chkRefunded.isSelected());
    searchDto.setTicketType((ComboOption) cbTicketType.getSelectedItem());
    searchDto.setMembershipType((ComboOption) cbMembership.getSelectedItem());
    searchDto.setPaymentType((ComboOption) cbPaymentType.getSelectedItem());

    return searchDto;
  }

  class TicketExplorerTableModel extends ListTableModel<Ticket> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // @formatter:off
    String[] columnNames = {
        POSConstants.TICKET_EXPLORER_TABLE_UNIQ_ID, 
        POSConstants.CREATED,
        POSConstants.SETTLE_TIME, 
        POSConstants.TICKET_EXPLORER_TABLE_TICKET_STATUS,
        POSConstants.TICKET_EXPLORER_TABLE_TYPE, 
        POSConstants.TICKET_EXPLORER_TABLE_PAYMENT_TYPE, 
        POSConstants.TICKET_EXPLORER_TABLE_MEMBERSHIP,
        POSConstants.TICKET_EXPLORER_TABLE_TOTAL_BEFORE_DISCOUNT,
        POSConstants.TICKET_EXPLORER_TABLE_TOTAL_DISCOUNT,
        POSConstants.TICKET_EXPLORER_TABLE_TOTAL_AFTER_DISCOUNT
    };
    // @formatter:on

    @Override
    public int getRowCount() {
      if (tickets == null) {
        return 0;
      }
      return tickets.size();
    }

    @Override
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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      if (tickets == null)
        return "";

      Ticket ticket = tickets.get(rowIndex);

      switch (columnIndex) {
        case 0:
          return ticket.getUniqId();

        case 1:
          return DateUtil.getTicketViewDate(ticket.getCreateDate());

        case 2:
          return DateUtil.getTicketViewDate(ticket.getClosingDate());

        case 3:
          return ticket.getTicketStatus();

        case 4:
          return TicketType.valueOf(ticket.getTicketType()).getValue();

        case 5:
          return ticket.getPaymentType();

        case 6:
          if (ticket.getCustomer() != null) {
            return ticket.getCustomerPhone();
          } else {
            return POSConstants.TICKET_EXPLORER_TABLE_NON_MEMBERSHIP;
          }

        case 7:
          return Double.valueOf(ticket.getSubtotalAmount());

        case 8:
          return Double.valueOf(ticket.getDiscountAmount());

        case 9:
          return Double.valueOf(ticket.getTotalAmount());
      }
      return null;
    }
  }

  /**
   * Listener for the checkboxes.
   */
  @Override
  public void itemStateChanged(ItemEvent e) {
    Object source = e.getItemSelectable();
    if (source == chkAll) {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        allTicketsEnabled(true);
      } else if (e.getStateChange() == ItemEvent.DESELECTED) {
        allTicketsEnabled(false);
      }
    }
  }

  private void allTicketsEnabled(boolean allTickets) {
    if (allTickets) {
      chkPaid.setSelected(false);
      chkPaid.setEnabled(false);
      chkVoided.setSelected(false);
      chkVoided.setEnabled(false);
      chkRefunded.setSelected(false);
      chkRefunded.setEnabled(false);
    } else {
      chkPaid.setSelected(true);
      chkPaid.setEnabled(true);
      chkVoided.setSelected(false);
      chkVoided.setEnabled(true);
      chkRefunded.setSelected(false);
      chkRefunded.setEnabled(true);
    }
  }
}
