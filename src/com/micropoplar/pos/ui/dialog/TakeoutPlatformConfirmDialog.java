package com.micropoplar.pos.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.floreantpos.POSConstants;
import com.floreantpos.model.PaymentType;
import com.floreantpos.swing.PosButton;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.TitlePanel;
import com.floreantpos.ui.dialog.POSDialog;
import com.floreantpos.util.NumberUtil;
import com.micropoplar.pos.payment.config.TakeoutPlatformConfig;
import com.micropoplar.pos.ui.util.ControllerGenerator;

import net.miginfocom.swing.MigLayout;

public class TakeoutPlatformConfirmDialog extends POSDialog {

  private static final long serialVersionUID = 1L;
  private PaymentType takeoutPaymentType;
  private final double tenderAmount;
  private double modifiedTenderAmount;
  private double actualDiscountRate;

  private JTextField tfModifiedTenderedAmount;

  public TakeoutPlatformConfirmDialog(double tenderAmount, PaymentType paymentType) {
    this.takeoutPaymentType = paymentType;
    this.tenderAmount = tenderAmount;
    this.modifiedTenderAmount = tenderAmount;

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setResizable(false);

    TitlePanel titlePanel = new TitlePanel();
    titlePanel.setTitle("外卖平台: " + paymentType.getDisplayString());
    getContentPane().add(titlePanel, BorderLayout.NORTH);

    JPanel content = new JPanel(new MigLayout("gap 5px 20px, fill"));
    content.setBorder(new EmptyBorder(5, 5, 5, 5));

    JPanel paymentPanel = new JPanel(new GridLayout(3, 2, 10, 10));

    // 第一行：折扣前金额
    JLabel lblTenderedAmount = new JLabel("折扣前金额: ");
    paymentPanel.add(lblTenderedAmount, "cell 0 0");

    JTextField tfTenderedAmount = new JTextField(String.valueOf(tenderAmount));
    tfTenderedAmount.setEditable(false);
    tfTenderedAmount.setFont(new Font(POSConstants.DEFAULT_FONT_NAME, 1, 20));
    tfTenderedAmount.setHorizontalAlignment(JTextField.TRAILING);
    paymentPanel.add(tfTenderedAmount, "cell 1 0, alignx trailing");

    // 第二行：折扣率
    JLabel lblCurrentDiscount = new JLabel("平台折扣率: ");
    paymentPanel.add(lblCurrentDiscount, "cell 0 1");

    this.actualDiscountRate = TakeoutPlatformConfig.getPaymentDiscount(paymentType);
    JSpinner spinCurrentDiscount = ControllerGenerator.getDiscountSpinner(actualDiscountRate);

    // 设置监听器
    spinCurrentDiscount.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        JSpinner target = (JSpinner) e.getSource();
        Double discount = (Double) target.getValue();

        double modifiedTenderAmount = TakeoutPlatformConfirmDialog.this
            .calculateAmount(TakeoutPlatformConfirmDialog.this.tenderAmount, discount);
        TakeoutPlatformConfirmDialog.this.modifiedTenderAmount = modifiedTenderAmount;
        TakeoutPlatformConfirmDialog.this.actualDiscountRate = discount;

        tfModifiedTenderedAmount.setText(String.valueOf(modifiedTenderAmount));
      }
    });

    paymentPanel.add(spinCurrentDiscount, "cell 1 1, alignx leading");

    // 第三行：折扣后金额
    JLabel lblModifiedTenderedAmount = new JLabel("折扣后金额: ");
    paymentPanel.add(lblModifiedTenderedAmount, "cell 0 2");

    this.modifiedTenderAmount = calculateAmount(tenderAmount, actualDiscountRate);
    tfModifiedTenderedAmount = new JTextField(String.valueOf(modifiedTenderAmount));
    tfModifiedTenderedAmount.setEditable(false);
    tfModifiedTenderedAmount.setFont(new Font(POSConstants.DEFAULT_FONT_NAME, 1, 20));
    tfModifiedTenderedAmount.setHorizontalAlignment(JTextField.TRAILING);
    paymentPanel.add(tfModifiedTenderedAmount, "cell 1 2, alignx trailing");

    paymentPanel
        .setBorder(new CompoundBorder(new TitledBorder("支付信息"), new EmptyBorder(10, 10, 10, 10)));
    content.add(paymentPanel, "wrap, height 110px, growx");

    JPanel controlPanel = new TransparentPanel();
    controlPanel.setLayout(new MigLayout("wrap 2"));

    JLabel lblConfirm = new JLabel("请确认金额信息无误后点击下方确定按钮");
    lblConfirm.setForeground(new java.awt.Color(204, 102, 0));
    lblConfirm.setFont(new Font(POSConstants.DEFAULT_FONT_NAME, 1, 20));
    controlPanel.add(lblConfirm, "span 2");

    PosButton confirm = new PosButton("确定");
    confirm.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        setCanceled(false);
        dispose();
      }
    });
    controlPanel.add(confirm, "growx");

    PosButton cancel = new PosButton("取消");
    cancel.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        setCanceled(true);
        dispose();
      }
    });
    controlPanel.add(cancel, "growx");

    content.add(controlPanel, "wrap, height 110px");

    add(content);
  }

  private double calculateAmount(double amount, double discount) {
    return NumberUtil.roundToTwoDigit(amount * discount / 10);
  }

  public PaymentType getTakeoutPaymentType() {
    return takeoutPaymentType;
  }

  public double getModifiedTenderAmount() {
    return modifiedTenderAmount;
  }

  public double getActualDiscountRate() {
    return actualDiscountRate;
  }

}
