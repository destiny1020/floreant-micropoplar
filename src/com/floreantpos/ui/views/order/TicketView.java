package com.floreantpos.ui.views.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.StaleObjectStateException;

import com.floreantpos.POSConstants;
import com.floreantpos.PosException;
import com.floreantpos.main.Application;
import com.floreantpos.model.CookingInstruction;
import com.floreantpos.model.ITicketItem;
import com.floreantpos.model.MenuCategory;
import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.TicketItemCookingInstruction;
import com.floreantpos.model.TicketItemModifier;
import com.floreantpos.model.TicketType;
import com.floreantpos.model.dao.CookingInstructionDAO;
import com.floreantpos.model.dao.MenuItemDAO;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.model.util.TicketUniqIdGenerator;
import com.floreantpos.report.JReportPrintService;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.views.CookingInstructionSelectionView;
import com.floreantpos.ui.views.CustomerView;
import com.floreantpos.ui.views.SwitchboardView;
import com.floreantpos.ui.views.order.actions.OrderListener;
import com.floreantpos.util.NumberUtil;
import com.micropoplar.pos.ui.ITicketTypeSelectionListener;
import com.micropoplar.pos.ui.TicketTypeButton;
import com.micropoplar.pos.util.PatternChecker;

import net.miginfocom.swing.MigLayout;

public class TicketView extends JPanel implements ActionListener {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private java.util.Vector<OrderListener> orderListeners = new java.util.Vector<OrderListener>();
  private List<ITicketTypeSelectionListener> ticketTypeListeners = new LinkedList<>();
  private Ticket ticket;

  public final static String VIEW_NAME = "TICKET_VIEW";

  public TicketView() {
    initComponents();

    btnAddCookingInstruction.setEnabled(false);
    btnIncreaseAmount.setEnabled(false);
    btnDecreaseAmount.setEnabled(false);

    ticketViewerTable.setRowHeight(35);
    ticketViewerTable.getRenderer().setInTicketScreen(true);
    ticketViewerTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          updateSelectionView();
        }
      }

    });

    ticketViewerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {

        Object selected = ticketViewerTable.getSelected();
        if (!(selected instanceof ITicketItem)) {
          return;
        }

        ITicketItem item = (ITicketItem) selected;

        Boolean printedToKitchen = item.isPrintedToKitchen();

        btnAddCookingInstruction.setEnabled(item.canAddCookingInstruction());
        btnIncreaseAmount.setEnabled(!printedToKitchen);
        btnDecreaseAmount.setEnabled(!printedToKitchen);
        btnDelete.setEnabled(!printedToKitchen);
      }

    });
  }

  private void initComponents() {
    pnlControls = new com.floreantpos.swing.TransparentPanel();
    pnlTicketInfo = new com.floreantpos.swing.TransparentPanel();
    controlPanel = new com.floreantpos.swing.TransparentPanel();
    controlPanel.setOpaque(true);
    btnPay = new com.floreantpos.swing.PosButton();
    btnCancel = new com.floreantpos.swing.PosButton();
    btnFinish = new com.floreantpos.swing.PosButton();
    scrollerPanel = new com.floreantpos.swing.TransparentPanel();
    btnIncreaseAmount = new com.floreantpos.swing.PosButton();
    btnDecreaseAmount = new com.floreantpos.swing.PosButton();
    btnScrollUp = new com.floreantpos.swing.PosButton();
    btnScrollDown = new com.floreantpos.swing.PosButton();
    pnlTableContainer = new com.floreantpos.swing.TransparentPanel();
    ticketViewerTable = new com.floreantpos.ui.ticket.TicketViewerTable();
    scrollPaneTable = new javax.swing.JScrollPane(ticketViewerTable);

    setBorder(javax.swing.BorderFactory.createTitledBorder(null,
        com.floreantpos.POSConstants.TICKET, javax.swing.border.TitledBorder.CENTER,
        javax.swing.border.TitledBorder.DEFAULT_POSITION));
    setPreferredSize(new java.awt.Dimension(420, 463));
    setLayout(new java.awt.BorderLayout(5, 5));
    pnlControls.setLayout(new BorderLayout(5, 5));
    pnlTicketInfo.setLayout(new MigLayout("alignx trailing,fill", "[grow][]", "[][][][][][][][]"));

    // customer phone
    lblCustomerPhone = new JLabel();
    lblCustomerPhone.setFont(new java.awt.Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    lblCustomerPhone.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    lblCustomerPhone.setText(com.floreantpos.POSConstants.CUSTOMER_PHONE + POSConstants.COLON);
    pnlTicketInfo.add(lblCustomerPhone, "cell 0 0,growx,aligny center");

    tfCustomerPhone = new javax.swing.JTextField();
    tfCustomerPhone.setHorizontalAlignment(SwingConstants.TRAILING);
    tfCustomerPhone.setEditable(false);
    tfCustomerPhone.setFocusable(false);
    tfCustomerPhone.setFont(new java.awt.Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    tfCustomerPhone.setPreferredSize(new Dimension(200, 25));
    pnlTicketInfo.add(tfCustomerPhone, "cell 1 0,growx,aligny center");

    lblSubtotal = new javax.swing.JLabel();
    lblSubtotal.setFont(new java.awt.Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    lblSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    lblSubtotal.setText(com.floreantpos.POSConstants.SUBTOTAL + POSConstants.COLON);
    pnlTicketInfo.add(lblSubtotal, "cell 0 1,growx,aligny center");
    tfSubtotal = new javax.swing.JTextField();
    tfSubtotal.setHorizontalAlignment(SwingConstants.TRAILING);
    tfSubtotal.setEditable(false);
    tfSubtotal.setFocusable(false);
    tfSubtotal.setFont(new java.awt.Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    tfSubtotal.setPreferredSize(new Dimension(200, 25));
    pnlTicketInfo.add(tfSubtotal, "cell 1 1,growx,aligny center");

    lblDiscount = new javax.swing.JLabel();
    lblDiscount.setFont(new java.awt.Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    lblDiscount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    lblDiscount.setText(com.floreantpos.POSConstants.DISCOUNT + POSConstants.COLON);
    pnlTicketInfo.add(lblDiscount, "cell 0 2,growx,aligny center");
    tfDiscount = new javax.swing.JTextField();
    tfDiscount.setHorizontalAlignment(SwingConstants.TRAILING);
    tfDiscount.setEditable(false);
    tfDiscount.setFocusable(false);
    tfDiscount.setFont(new java.awt.Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    pnlTicketInfo.add(tfDiscount, "cell 1 2,growx,aligny center");

    lblTotal = new javax.swing.JLabel();
    lblTotal.setFont(new java.awt.Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    lblTotal.setText(com.floreantpos.POSConstants.TOTAL + POSConstants.COLON);
    pnlTicketInfo.add(lblTotal, "cell 0 3,growx,aligny center");
    tfTotal = new javax.swing.JTextField();
    tfTotal.setHorizontalAlignment(SwingConstants.TRAILING);
    tfTotal.setEditable(false);
    tfTotal.setFocusable(false);
    tfTotal.setFont(new java.awt.Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    pnlTicketInfo.add(tfTotal, "cell 1 3,growx,aligny center");

    btnPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pay_32.png")));
    btnPay.setText(com.floreantpos.POSConstants.PAY_NOW);
    btnPay.setPreferredSize(new java.awt.Dimension(76, 45));
    btnPay.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doPayNow(evt);
      }
    });
    controlPanel.setLayout(new MigLayout("insets 0", "[202px,grow][202px,grow]", "[45px][45px]"));
    controlPanel.add(btnPay, "cell 0 0 2 1,grow");

    btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel_32.png")));
    btnCancel.setText(com.floreantpos.POSConstants.CANCEL);
    btnCancel.setPreferredSize(new java.awt.Dimension(76, 45));
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doCancelOrder(evt);
      }
    });
    controlPanel.add(btnCancel, "cell 0 1,grow");

    btnFinish.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/finish_32.png")));
    btnFinish.setText(com.floreantpos.POSConstants.FINISH);
    btnFinish.setPreferredSize(new java.awt.Dimension(76, 45));
    btnFinish.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doFinishOrder(evt);
      }
    });
    controlPanel.add(btnFinish, "cell 1 1,grow");

    btnIncreaseAmount
        .setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_user_32.png")));
    btnIncreaseAmount.setPreferredSize(new java.awt.Dimension(76, 45));
    btnIncreaseAmount.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doIncreaseAmount(evt);
      }
    });
    scrollerPanel.setLayout(
        new MigLayout("insets 0", "[133px,grow][133px,grow][133px,grow]", "[45px][45px][45px]"));
    scrollerPanel.add(btnIncreaseAmount, "cell 0 0,grow");

    btnDecreaseAmount
        .setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minus_32.png")));
    btnDecreaseAmount.setPreferredSize(new java.awt.Dimension(76, 45));
    btnDecreaseAmount.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doDecreaseAmount(evt);
      }
    });
    scrollerPanel.add(btnDecreaseAmount, "cell 1 0,grow");

    btnScrollUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/up_32.png")));
    btnScrollUp.setPreferredSize(new java.awt.Dimension(76, 45));
    btnScrollUp.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doScrollUp(evt);
      }
    });
    scrollerPanel.add(btnScrollUp, "cell 2 0,grow");

    btnScrollDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/down_32.png")));
    btnScrollDown.setPreferredSize(new java.awt.Dimension(76, 45));
    btnScrollDown.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doScrollDown(evt);
      }
    });
    btnDelete = new com.floreantpos.swing.PosButton();

    btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_32.png")));
    btnDelete.setText(com.floreantpos.POSConstants.DELETE);
    btnDelete.setPreferredSize(new java.awt.Dimension(80, 17));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doDeleteSelection(evt);
      }
    });

    btnAddCookingInstruction = new PosButton();
    btnAddCookingInstruction.setEnabled(false);
    btnAddCookingInstruction.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        doAddCookingInstruction();
      }
    });
    btnAddCookingInstruction.setText(POSConstants.ADD_COOKING_INSTRUCTION);
    scrollerPanel.add(btnAddCookingInstruction, "cell 0 1,grow");
    scrollerPanel.add(btnDelete, "cell 1 1,grow");
    scrollerPanel.add(btnScrollDown, "cell 2 1,grow");

    // ticket type toggle button group
    ticketTypeButtonGroup = new ButtonGroup();

    btnTicketTypeDineIn = new TicketTypeButton(TicketType.DINE_IN, this);
    btnTicketTypeDineIn.addActionListener(this);
    ticketTypeButtonGroup.add(btnTicketTypeDineIn);
    btnTicketTypeTakeOut = new TicketTypeButton(TicketType.TAKE_OUT, this);
    btnTicketTypeTakeOut.addActionListener(this);
    btnTicketTypeTakeOut.setSelected(true);
    ticketTypeButtonGroup.add(btnTicketTypeTakeOut);
    btnTicketTypeHomeDelivery = new TicketTypeButton(TicketType.HOME_DELIVERY, this);
    btnTicketTypeHomeDelivery.addActionListener(this);
    ticketTypeButtonGroup.add(btnTicketTypeHomeDelivery);

    tfDineInNumber = new FixedLengthTextField(3);
    tfDineInNumber.setMaximumSize(new Dimension(60, 25));

    scrollerPanel.add(btnTicketTypeDineIn, "cell 0 2,grow");
    scrollerPanel.add(btnTicketTypeTakeOut, "cell 1 2,grow");
    scrollerPanel.add(btnTicketTypeHomeDelivery, "cell 2 2,grow");

    JPanel amountPanelContainer = new JPanel(new BorderLayout(5, 5));
    amountPanelContainer.add(scrollerPanel, BorderLayout.SOUTH);

    pnlControls.add(amountPanelContainer);
    pnlControls.add(controlPanel, BorderLayout.SOUTH);

    add(pnlTicketInfo, BorderLayout.NORTH);
    add(pnlControls, java.awt.BorderLayout.SOUTH);

    pnlTableContainer.setLayout(new java.awt.BorderLayout());

    // jScrollPane1.setBorder(null);
    scrollPaneTable
        .setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneTable
        .setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPaneTable.setPreferredSize(new java.awt.Dimension(180, 200));
    // jScrollPane1.setViewportView(ticketViewerTable);

    pnlTableContainer.add(scrollPaneTable, java.awt.BorderLayout.CENTER);

    add(pnlTableContainer, java.awt.BorderLayout.CENTER);

  }

  protected void doAddCookingInstruction() {

    try {
      Object object = ticketViewerTable.getSelected();
      if (!(object instanceof TicketItem)) {
        POSMessageDialog.showError("请选择商品");
        return;
      }

      TicketItem ticketItem = (TicketItem) object;

      if (ticketItem.isPrintedToKitchen()) {
        POSMessageDialog.showError("已经打印给厨房的商品不能添加烹饪说明");
        return;
      }

      List<CookingInstruction> list = CookingInstructionDAO.getInstance().findAll();
      CookingInstructionSelectionView cookingInstructionSelectionView =
          new CookingInstructionSelectionView();
      BeanEditorDialog dialog =
          new BeanEditorDialog(cookingInstructionSelectionView, Application.getPosWindow(), true);
      dialog.setBean(list);
      dialog.setSize(450, 300);
      dialog.setLocationRelativeTo(Application.getPosWindow());
      dialog.setVisible(true);

      if (dialog.isCanceled()) {
        return;
      }

      List<TicketItemCookingInstruction> instructions =
          cookingInstructionSelectionView.getTicketItemCookingInstructions();
      ticketItem.addCookingInstructions(instructions);

      ticketViewerTable.updateView();
    } catch (Exception e) {
      e.printStackTrace();
      POSMessageDialog.showError(e.getMessage());
    }
  }

  private synchronized void doFinishOrder(java.awt.event.ActionEvent evt) {
    try {

      updateModel();

      if (ticket.getType() != TicketType.TAKE_OUT) {
        OrderController.saveOrder(ticket);
      }

      if (ticket.needsKitchenPrint()) {
        JReportPrintService.printTicketToKitchen(ticket);
      }

      ticket.clearDeletedItems();
      OrderController.saveOrder(ticket);

      RootView.getInstance().showView(SwitchboardView.VIEW_NAME);

      // remove the current ticket in customer ticket view
      CustomerView.getInstance().getCustomerTicketView().setTicket(null);

    } catch (StaleObjectStateException e) {
      POSMessageDialog.showError("当前订单似乎已经被其他人员或者终端修改, 修改失败.");
      return;
    } catch (PosException x) {
      POSMessageDialog.showError(x.getMessage());
    } catch (Exception e) {
      POSMessageDialog.showError(Application.getPosWindow(), POSConstants.ERROR_MESSAGE, e);
    }
  }

  private void doCancelOrder(java.awt.event.ActionEvent evt) {
    RootView.getInstance().showView(SwitchboardView.VIEW_NAME);

    // remove the current ticket in customer ticket view
    CustomerView.getInstance().getCustomerTicketView().setTicket(null);
  }

  private synchronized void updateModel() {
    if (StringUtils.isBlank(ticket.getUniqId())
        && (ticket.getTicketItems() == null || ticket.getTicketItems().size() == 0)) {
      throw new PosException(com.floreantpos.POSConstants.TICKET_IS_EMPTY_);
    }

    // set dine in number if necessary
    if (ticket.getTicketType().equals(TicketType.DINE_IN.name())) {
      String dineInNumber = tfDineInNumber.getText();
      if (StringUtils.isNotBlank(dineInNumber)) {
        if (PatternChecker.isNumber(dineInNumber, 1, 999)) {
          ticket.setDineInNumber(Integer.parseInt(dineInNumber));
        } else {
          throw new PosException(String.format(POSConstants.ERROR_DINE_IN_NUMBER, 1, 999));
        }
      }
    }

    // generate uniq id if necessary
    if (StringUtils.isBlank(ticket.getUniqId())) {
      ticket.setUniqId(TicketUniqIdGenerator.generate());
      TicketDAO.getInstance().saveOrUpdate(ticket);
      TicketDAO.getInstance().refresh(ticket);
    }

    ticket = TicketDAO.getInstance().loadFullTicket(ticket.getUniqId());
    ticket.calculatePrice();
  }

  private void doPayNow(java.awt.event.ActionEvent evt) {
    try {
      updateModel();

      OrderController.saveOrder(ticket);

      firePayOrderSelected();
    } catch (PosException e) {
      POSMessageDialog.showError(e.getMessage());
    }
  }

  private void doDeleteSelection(java.awt.event.ActionEvent evt) {
    Object object = ticketViewerTable.deleteSelectedItem();
    if (object != null) {
      updateView();
    }

  }

  private void doIncreaseAmount(java.awt.event.ActionEvent evt) {
    if (ticketViewerTable.increaseItemAmount()) {
      updateView();
    }

  }

  private void doDecreaseAmount(java.awt.event.ActionEvent evt) {
    if (ticketViewerTable.decreaseItemAmount()) {
      updateView();
    }
  }

  private void doScrollDown(java.awt.event.ActionEvent evt) {
    ticketViewerTable.scrollDown();
  }

  private void doScrollUp(java.awt.event.ActionEvent evt) {
    ticketViewerTable.scrollUp();
  }

  private com.floreantpos.swing.TransparentPanel controlPanel;
  private com.floreantpos.swing.PosButton btnCancel;
  private com.floreantpos.swing.PosButton btnDecreaseAmount;
  private com.floreantpos.swing.PosButton btnDelete;
  private com.floreantpos.swing.PosButton btnFinish;
  private com.floreantpos.swing.PosButton btnIncreaseAmount;
  private com.floreantpos.swing.PosButton btnPay;
  private com.floreantpos.swing.PosButton btnScrollDown;
  private com.floreantpos.swing.PosButton btnScrollUp;

  private ButtonGroup ticketTypeButtonGroup;
  private TicketTypeButton btnTicketTypeDineIn;
  private TicketTypeButton btnTicketTypeTakeOut;
  private TicketTypeButton btnTicketTypeHomeDelivery;
  private FixedLengthTextField tfDineInNumber;

  private javax.swing.JLabel lblDiscount;
  private javax.swing.JLabel lblSubtotal;
  private javax.swing.JLabel lblTotal;
  private com.floreantpos.swing.TransparentPanel pnlControls;
  private com.floreantpos.swing.TransparentPanel pnlTableContainer;
  private com.floreantpos.swing.TransparentPanel pnlTicketInfo;
  private com.floreantpos.swing.TransparentPanel scrollerPanel;
  private javax.swing.JScrollPane scrollPaneTable;
  private javax.swing.JTextField tfDiscount;
  private javax.swing.JTextField tfSubtotal;

  // for customer information
  private JLabel lblCustomerPhone;
  private JTextField tfCustomerPhone;

  private javax.swing.JTextField tfTotal;
  private com.floreantpos.ui.ticket.TicketViewerTable ticketViewerTable;
  private PosButton btnAddCookingInstruction;

  public Ticket getTicket() {
    return ticket;
  }

  public void setTicket(Ticket _ticket) {
    this.ticket = _ticket;

    ticketViewerTable.setTicket(_ticket);

    updateView();
  }

  public void addTicketItem(TicketItem ticketItem) {
    ticketViewerTable.addTicketItem(ticketItem);
    updateView();
  }

  public void removeModifier(TicketItem parent, TicketItemModifier modifier) {
    ticketViewerTable.removeModifier(parent, modifier);
  }

  public void updateAllView() {
    ticketViewerTable.updateView();
    updateView();
  }

  public void selectRow(int rowIndex) {
    ticketViewerTable.selectRow(rowIndex);
  }

  public void updateView() {
    if (ticket == null) {
      tfSubtotal.setText("");
      tfDiscount.setText("");
      tfTotal.setText("");

      setBorder(BorderFactory.createTitledBorder(null, POSConstants.TICKET_VIEW_BORDER_TITLE_NEW,
          TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));

      return;
    }

    ticket.calculatePrice();

    tfSubtotal.setText(NumberUtil.formatNumber(ticket.getSubtotalAmount()));
    tfDiscount.setText(NumberUtil.formatNumber(ticket.getDiscountAmount()));

    // update customer cellphone
    String cellphone = ticket.getCustomerPhone();
    if (StringUtils.isNotBlank(cellphone)) {
      tfCustomerPhone.setText(cellphone);
    }

    tfTotal.setText(NumberUtil.formatNumber(ticket.getTotalAmount()));

    if (ticket.getId() == null) {
      setBorder(BorderFactory.createTitledBorder(null, POSConstants.TICKET_VIEW_BORDER_TITLE_NEW,
          TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
    } else {
      setBorder(BorderFactory.createTitledBorder(null,
          String.format(POSConstants.TICKET_VIEW_BORDER_TITLE_WITH_ID, ticket.getUniqId()),
          TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
    }

    // update ticket type
    selectTicketTypeButton(ticket.getType());
    fireTicketTypeSelected(ticket.getType());
  }

  private void selectTicketTypeButton(TicketType type) {
    switch (type) {
      case DINE_IN:
        btnTicketTypeDineIn.setSelected(true);
        break;
      case TAKE_OUT:
        btnTicketTypeTakeOut.setSelected(true);
        break;
      case HOME_DELIVERY:
        btnTicketTypeHomeDelivery.setSelected(true);
        break;
      default:
        POSMessageDialog.showError(this, POSConstants.ERROR_INVALID_TICKET_TYPE);
        break;
    }
  }

  public void addOrderListener(OrderListener listenre) {
    orderListeners.add(listenre);
  }

  public void removeOrderListener(OrderListener listenre) {
    orderListeners.remove(listenre);
  }

  public void firePayOrderSelected() {
    for (OrderListener listener : orderListeners) {
      listener.payOrderSelected(getTicket());
    }
  }

  public void setControlsVisible(boolean visible) {
    if (visible) {
      controlPanel.setVisible(true);
      btnIncreaseAmount.setEnabled(true);
      btnDecreaseAmount.setEnabled(true);
      btnDelete.setEnabled(true);
    } else {
      controlPanel.setVisible(false);
      btnIncreaseAmount.setEnabled(false);
      btnDecreaseAmount.setEnabled(false);
      btnDelete.setEnabled(false);
    }
  }

  private void updateSelectionView() {
    Object selectedObject = ticketViewerTable.getSelected();

    OrderView orderView = OrderView.getInstance();

    TicketItem selectedItem = null;
    if (selectedObject instanceof TicketItem) {
      selectedItem = (TicketItem) selectedObject;
      MenuItemDAO dao = new MenuItemDAO();
      MenuItem menuItem = dao.get(selectedItem.getItemId());
      MenuGroup menuGroup = menuItem.getGroup();
      MenuItemAndMenuItemSetView itemView = OrderView.getInstance().getItemView();
      if (!menuGroup.equals(itemView.getMenuGroup())) {
        itemView.setMenuGroup(menuGroup);
      }
      orderView.showView(MenuItemAndMenuItemSetView.VIEW_NAME);

      MenuCategory menuCategory = menuGroup.getCategory();
      orderView.getCategoryView().setSelectedCategory(menuCategory);
      return;
    } else if (selectedObject instanceof TicketItemModifier) {
      selectedItem = ((TicketItemModifier) selectedObject).getParent().getParent();
    }
    if (selectedItem == null)
      return;
  }

  public void updateTicketUniqIdAndCustomer() {
    setBorder(BorderFactory.createTitledBorder(null,
        String.format(POSConstants.TICKET_VIEW_BORDER_TITLE_WITH_ID, ticket.getUniqId()),
        TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
    tfCustomerPhone.setText(ticket.getCustomerPhone());

    // recalculate the ticket items prices information
    ticket.calculatePrice();
    updateView();
  }

  /**
   * dealing with ticket type selection
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    TicketTypeButton button = (TicketTypeButton) e.getSource();
    if (button.isSelected()) {
      fireTicketTypeSelected(button.getTicketType());
    }
  }

  public void addTicketTypeSelectionListener(ITicketTypeSelectionListener listener) {
    ticketTypeListeners.add(listener);
  }

  public void removeTicketTypeSelectionListener(ITicketTypeSelectionListener listener) {
    ticketTypeListeners.remove(listener);
  }

  private void fireTicketTypeSelected(TicketType ticketType) {
    for (ITicketTypeSelectionListener listener : ticketTypeListeners) {
      listener.ticketTypeSelected(ticketType);
    }
  }

  public void toggleDineInLayout(TicketType ticketType) {
    if (ticketType == TicketType.DINE_IN) {
      scrollerPanel.remove(btnTicketTypeDineIn);
      scrollerPanel.add(btnTicketTypeDineIn, "cell 0 2");
      scrollerPanel.add(tfDineInNumber, "cell 0 2, grow");
    } else {
      tfDineInNumber.setText("");
      scrollerPanel.remove(btnTicketTypeDineIn);
      scrollerPanel.remove(tfDineInNumber);
      scrollerPanel.add(btnTicketTypeDineIn, "cell 0 2, grow");
    }

    scrollerPanel.validate();
    scrollerPanel.repaint(50L);
  }
}
