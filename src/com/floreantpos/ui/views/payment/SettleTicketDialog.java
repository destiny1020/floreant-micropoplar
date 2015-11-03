package com.floreantpos.ui.views.payment;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.floreantpos.POSConstants;
import com.floreantpos.main.Application;
import com.floreantpos.model.CashTransaction;
import com.floreantpos.model.Coupon;
import com.floreantpos.model.PaymentType;
import com.floreantpos.model.PosTransaction;
import com.floreantpos.model.TakeoutTransaction;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketCoupon;
import com.floreantpos.model.UnionPayTransaction;
import com.floreantpos.model.UserPermission;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.report.JReportPrintService;
import com.floreantpos.services.PosTransactionService;
import com.floreantpos.ui.dialog.CouponDialog;
import com.floreantpos.ui.dialog.DiscountListDialog;
import com.floreantpos.ui.dialog.POSDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.dialog.PaymentTypeSelectionDialog;
import com.floreantpos.ui.dialog.TransactionCompletionDialog;
import com.floreantpos.ui.views.SwitchboardView;
import com.floreantpos.ui.views.TicketDetailView;
import com.floreantpos.ui.views.customer.AdvertisementView;
import com.floreantpos.ui.views.customer.MultipleUsageView;
import com.floreantpos.ui.views.order.CustomerRootView;
import com.floreantpos.ui.views.order.OrderController;
import com.micropoplar.pos.payment.PaymentResult;
import com.micropoplar.pos.payment.PreorderBusiness;
import com.micropoplar.pos.payment.QueryBusiness;
import com.micropoplar.pos.payment.config.TakeoutPlatformConfig;
import com.micropoplar.pos.payment.config.WeChatConfig;
import com.micropoplar.pos.ui.dialog.TakeoutPlatformConfirmDialog;

public class SettleTicketDialog extends POSDialog implements CardInputListener {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static final String LOYALTY_DISCOUNT_PERCENTAGE = "loyalty_discount_percentage";
  public static final String LOYALTY_POINT = "loyalty_point";
  public static final String LOYALTY_COUPON = "loyalty_coupon";
  public static final String LOYALTY_DISCOUNT = "loyalty_discount";
  public static final String LOYALTY_ID = "loyalty_id";

  public final static String VIEW_NAME = "PAYMENT_VIEW";

  private String previousViewName = SwitchboardView.VIEW_NAME;

  private com.floreantpos.swing.TransparentPanel leftPanel =
      new com.floreantpos.swing.TransparentPanel(new BorderLayout());
  private com.floreantpos.swing.TransparentPanel rightPanel =
      new com.floreantpos.swing.TransparentPanel(new BorderLayout());

  private TicketDetailView ticketDetailView;
  private PaymentView paymentView;

  private Ticket ticket;

  private double tenderAmount;
  private PaymentType paymentType;

  public SettleTicketDialog() {
    super(Application.getPosWindow(), true);
    setTitle("处理订单");

    getContentPane().setLayout(new BorderLayout(5, 5));

    ticketDetailView = new TicketDetailView();
    paymentView = new PaymentView(this);

    leftPanel.add(ticketDetailView);
    rightPanel.add(paymentView);

    getContentPane().add(leftPanel, BorderLayout.CENTER);
    getContentPane().add(rightPanel, BorderLayout.EAST);
  }

  private void updateModel() {
    if (ticket == null) {
      return;
    }

    ticket.calculatePrice();
  }

  public void doApplyCoupon() {// GEN-FIRST:event_btnApplyCoupondoApplyCoupon
    try {
      if (ticket == null)
        return;

      if (!Application.getCurrentUser().hasPermission(UserPermission.ADD_DISCOUNT)) {
        POSMessageDialog.showError("您没有权限进行折扣的添加");
        return;
      }

      if (ticket.getCoupons() != null && ticket.getCoupons().size() > 0) {
        POSMessageDialog.showError(com.floreantpos.POSConstants.DISCOUNT_COUPON_LIMIT_);
        return;
      }

      CouponDialog dialog = new CouponDialog();
      dialog.setTicket(ticket);
      dialog.initData();
      dialog.open();
      if (!dialog.isCanceled()) {
        TicketCoupon coupon = dialog.getSelectedCoupon();
        ticket.addToCoupons(coupon);

        updateModel();

        OrderController.saveOrder(ticket);
        ticketDetailView.updateView();
        paymentView.updateView();
      }
    } catch (Exception e) {
      POSMessageDialog.showError(this, com.floreantpos.POSConstants.ERROR_MESSAGE, e);
    }
  }// GEN-LAST:event_btnApplyCoupondoApplyCoupon

  public void doTaxExempt(boolean taxExempt) {// GEN-FIRST:event_doTaxExempt
    if (ticket == null)
      return;

    boolean setTaxExempt = taxExempt;
    if (setTaxExempt) {
      int option = JOptionPane.showOptionDialog(this, POSConstants.CONFIRM_SET_TAX_EXEMPT,
          POSConstants.CONFIRM, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
          null);
      if (option != JOptionPane.YES_OPTION) {
        return;
      }

      ticket.calculatePrice();
      TicketDAO.getInstance().saveOrUpdate(ticket);
    } else {
      ticket.calculatePrice();
      TicketDAO.getInstance().saveOrUpdate(ticket);
    }

    ticketDetailView.updateView();
    paymentView.updateView();
  }// GEN-LAST:event_doTaxExempt

  public void doViewDiscounts() {// GEN-FIRST:event_btnViewDiscountsdoViewDiscounts
    try {

      if (ticket == null)
        return;

      DiscountListDialog dialog = new DiscountListDialog(Arrays.asList(ticket));
      dialog.open();

      if (!dialog.isCanceled() && dialog.isModified()) {
        updateModel();

        TicketDAO.getInstance().saveOrUpdate(ticket);

        ticketDetailView.updateView();
        paymentView.updateView();
      }

    } catch (Exception e) {
      POSMessageDialog.showError(this, com.floreantpos.POSConstants.ERROR_MESSAGE, e);
    }
  }// GEN-LAST:event_btnViewDiscountsdoViewDiscounts

  public void doSettle() {
    try {
      if (ticket == null)
        return;

      tenderAmount = paymentView.getTenderedAmount();

      // if (ticket.getType() == TicketType.BAR_TAB) {
      // doSettleBarTabTicket(ticket);
      // return;
      // }

      PaymentTypeSelectionDialog dialog = new PaymentTypeSelectionDialog();
      dialog.setResizable(false);
      dialog.pack();
      dialog.open();
      if (dialog.isCanceled()) {
        return;
      }

      paymentType = dialog.getSelectedPaymentType();
      PosTransaction transaction = null;

      ticket.setPaymentType(paymentType.getDisplayString());

      switch (paymentType) {
        case CASH:
          ConfirmPayDialog confirmPayDialog = new ConfirmPayDialog(POSConstants.CASH_CONFIRM_TITLE);
          confirmPayDialog.setAmount(tenderAmount);
          confirmPayDialog.open();

          if (confirmPayDialog.isCanceled()) {
            return;
          }

          transaction = new CashTransaction();
          transaction.setTenderAmount(tenderAmount);
          transaction.setPaymentType(paymentType.getType());
          transaction.setTicket(ticket);
          transaction.setCaptured(true);
          setTransactionAmounts(transaction);

          settleTicket(transaction);
          break;
        case WECHAT:
          payUsingWechat(tenderAmount);
          break;
        case ALIPAY:
          break;
        case UNION_PAY:
          payUsingUnionPay(transaction, tenderAmount, paymentType);
          break;
        case MEITUAN:
        case ELEME:
        case DAOJIA:
        case LINEZERO:
          payUsingTakeoutPlatform(transaction, tenderAmount, paymentType);
          break;

        default:
          break;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void payUsingUnionPay(PosTransaction transaction, double tenderAmount,
      PaymentType paymentType) {
    ConfirmPayDialog confirmPayDialog = new ConfirmPayDialog(POSConstants.UNION_PAY_CONFIRM_TITLE);
    confirmPayDialog.setAmount(tenderAmount);
    confirmPayDialog.open();

    if (confirmPayDialog.isCanceled()) {
      return;
    }

    transaction = new UnionPayTransaction();
    transaction.setTenderAmount(tenderAmount);
    transaction.setTicket(ticket);
    setTransactionAmounts(transaction);

    settleTicket(transaction);
  }

  private void payUsingTakeoutPlatform(PosTransaction transaction, double tenderAmount,
      PaymentType paymentType) {
    if (!TakeoutPlatformConfig.isPaymentEnabled(paymentType)) {
      POSMessageDialog
          .showError(paymentType.getDisplayString() + POSConstants.TAKEOUT_PLATFORM_NOT_ENABLED);
      return;
    }

    TakeoutPlatformConfirmDialog confirmDialog =
        new TakeoutPlatformConfirmDialog(tenderAmount, paymentType);
    confirmDialog.pack();
    confirmDialog.open();

    if (confirmDialog.isCanceled()) {
      return;
    }

    transaction = new TakeoutTransaction();
    transaction.setAmount(tenderAmount);
    transaction.setTenderAmount(confirmDialog.getModifiedTenderAmount());
    transaction.setDiscountRate(confirmDialog.getActualDiscountRate());
    transaction.setTicket(ticket);

    settleTicket(transaction);
  }

  public void settleTicket(PosTransaction transaction) {
    try {
      final double dueAmount = ticket.getDueAmount();

      PosTransactionService transactionService = PosTransactionService.getInstance();
      transactionService.settleTicket(ticket, transaction);

      // FIXME
      if (transaction instanceof TakeoutTransaction) {
        // 外卖订单对于Ticket的特别处理
      } else {
        printTicket(ticket, transaction);
      }

      // remove ticket information in customer view
      CustomerRootView.getInstance().getCustomerView().transactionCompleted();

      showTransactionCompleteMsg(dueAmount, transaction.getTenderAmount(), ticket, transaction);

      // TODO: fix partial payment problem
      if (ticket.getDueAmount() > 0.0) {
        int option = JOptionPane.showConfirmDialog(Application.getPosWindow(),
            POSConstants.CONFIRM_PARTIAL_PAYMENT, POSConstants.MDS_POS, JOptionPane.YES_NO_OPTION);

        if (option != JOptionPane.YES_OPTION) {
          setCanceled(false);
          dispose();
        }

        setTicket(ticket);
      } else {
        setCanceled(false);
        dispose();
      }
    } catch (UnknownHostException e) {
      POSMessageDialog.showError("My Kala discount server connection error");
    } catch (Exception e) {
      POSMessageDialog.showError(this, POSConstants.ERROR_MESSAGE, e);
    }
  }

  private void showTransactionCompleteMsg(final double dueAmount, final double tenderedAmount,
      Ticket ticket, PosTransaction transaction) {
    TransactionCompletionDialog dialog =
        new TransactionCompletionDialog(Application.getPosWindow(), transaction);
    dialog.setCompletedTransaction(transaction);
    dialog.setTenderedAmount(tenderedAmount);
    dialog.setTotalAmount(dueAmount);
    dialog.setPaidAmount(transaction.getAmount());
    dialog.setDueAmount(ticket.getDueAmount());

    if (tenderedAmount > transaction.getAmount()) {
      dialog.setChangeAmount(tenderedAmount - transaction.getAmount());
    } else {
      dialog.setChangeAmount(0);
    }

    // dialog.setGratuityAmount(gratuityAmount);
    dialog.updateView();
    dialog.pack();
    dialog.open();
  }

  private void printTicket(Ticket ticket, PosTransaction transaction) {
    try {
      if (ticket.needsKitchenPrint()) {
        JReportPrintService.printTicketToKitchen(ticket);
      }

      // JReportPrintService.printTransaction(transaction);
    } catch (Exception ee) {
      POSMessageDialog.showError(Application.getPosWindow(),
          com.floreantpos.POSConstants.PRINT_ERROR, ee);
    }
  }

  private void payUsingWechat(final double tenderedAmount) throws Exception {
    if (!WeChatConfig.isWeChatSupported()) {
      POSMessageDialog.showError(POSConstants.WECHAT_NOT_SUPPORTED);
      return;
    } else {
      // check whether wechat config is valid
      if (!WeChatConfig.isWeChatPaymentValid()) {
        POSMessageDialog.showError(POSConstants.WECHAT_NOT_VALID);
        return;
      }
    }

    WeChatDialog weChatDialog = new WeChatDialog(this);
    weChatDialog.pack();
    weChatDialog.open();
  }

  public void updatePaymentView() {
    paymentView.updateView();
  }

  public String getPreviousViewName() {
    return previousViewName;
  }

  public void setPreviousViewName(String previousViewName) {
    this.previousViewName = previousViewName;
  }

  public TicketDetailView getTicketDetailView() {
    return ticketDetailView;
  }

  @Override
  public void open() {
    super.open();
  }

  @Override
  public void cardInputted(CardInputter inputter) {
    // authorize only, do not capture
    PaymentProcessWaitDialog waitDialog = new PaymentProcessWaitDialog(this);

    try {
      waitDialog.setVisible(true);

      PosTransaction transaction = paymentType.createTransaction();
      transaction.setTicket(ticket);

      if (inputter instanceof WeChatDialog) {
        // WeChat processing
        WeChatDialog dialog = (WeChatDialog) inputter;
        switch (dialog.getSelectedPaymentType()) {
          case PAYMENT_BAR:
            handleBarCode(transaction);
            break;
          case QR_CODE:
            handleQrCode(transaction);
            break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      POSMessageDialog.showError(e.getMessage());
    } finally {
      waitDialog.setVisible(false);
    }
  }

  private void handleBarCode(PosTransaction transaction) {
    // TODO
    setTransactionAmounts(transaction);
    settleTicket(transaction);
  }

  private void handleQrCode(final PosTransaction transaction) {
    setTransactionAmounts(transaction);

    // show customer the qr code
    final PaymentProcessWaitDialog waitDialog = new PaymentProcessWaitDialog(this);
    try {
      String qrCodeLocation =
          PreorderBusiness.run(ticket, (int) (transaction.getTenderAmount() * 100));
      if (qrCodeLocation.equals(PreorderBusiness.QRCODE_GEN_FAILED)) {
        POSMessageDialog.showError(POSConstants.WECHAT_QR_GEN_ERROR);
        return;
      } else {
        final MultipleUsageView view =
            CustomerRootView.getInstance().getCustomerView().getCustomerMultipleUsageView();
        boolean successfullyShown = view.switchToQRCode(qrCodeLocation);
        if (successfullyShown) {
          EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
              // show waiting dialog and query for the wechat
              // transaction
              waitDialog.setVisible(true);
              // polling for the payment status
              PaymentResult result = checkWhetherPaid();
              if (result.isSuccessful()) {
                POSMessageDialog.showMessage(POSConstants.WECHAT_PAID_SUCCESSFUL);
                if (StringUtils.isNotEmpty(result.getTransactionId())) {
                  transaction.setOuterTransactionId(result.getTransactionId());
                }
                settleTicket(transaction);
                // show normal view
                view.showView(AdvertisementView.VIEW_NAME);
              } else {
                POSMessageDialog.showError(POSConstants.WECHAT_PAID_UNSUCCESSFUL);
              }
              waitDialog.setVisible(false);
            }
          });

          // save the payment type to wechat into properties
          Map<String, String> props = ticket.getProperties();
          String paymentType = ticket.getProperty(Ticket.PAYMENT_TYPE);
          if (StringUtils.isBlank(paymentType)
              || !StringUtils.equals(paymentType, PaymentType.WECHAT.name())) {
            TicketDAO.getInstance().refresh(ticket);
            props.put(Ticket.PAYMENT_TYPE, PaymentType.WECHAT.name());
            ticket.setProperties(props);
            TicketDAO.getInstance().saveOrUpdate(ticket);
          }
        } else {
          POSMessageDialog.showError(POSConstants.WECHAT_QR_GEN_ERROR);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      waitDialog.setVisible(false);
      POSMessageDialog.showError(POSConstants.WECHAT_QR_GEN_ERROR);
    }
  }

  private PaymentResult checkWhetherPaid() {
    try {
      return QueryBusiness.queryQrCodePay(ticket);
    } catch (Exception e) {
      e.printStackTrace();
      return PaymentResult.FAIL_RESULT;
    }
  }

  private void setTransactionAmounts(PosTransaction transaction) {
    transaction.setTenderAmount(tenderAmount);

    if (tenderAmount >= ticket.getDueAmount()) {
      transaction.setAmount(ticket.getDueAmount());
    } else {
      transaction.setAmount(tenderAmount);
    }
  }

  public boolean hasMyKalaId() {
    if (ticket == null)
      return false;

    if (ticket.hasProperty(LOYALTY_ID)) {
      return true;
    }

    return false;
  }

  @SuppressWarnings("unused")
  private void addCoupon(Ticket ticket, JsonObject jsonObject) {
    Set<String> keys = jsonObject.keySet();
    for (String key : keys) {
      JsonNumber jsonNumber = jsonObject.getJsonNumber(key);
      double doubleValue = jsonNumber.doubleValue();

      TicketCoupon coupon = new TicketCoupon();
      coupon.setName(key);
      coupon.setType(Coupon.FIXED_PER_ORDER);
      coupon.setValue(doubleValue);

      ticket.addToCoupons(coupon);
    }
  }

  public Ticket getTicket() {
    return ticket;
  }

  public void setTicket(Ticket ticket) {
    this.ticket = ticket;

    ticketDetailView.setTickets(Arrays.asList(ticket));
    paymentView.updateView();
  }
}
