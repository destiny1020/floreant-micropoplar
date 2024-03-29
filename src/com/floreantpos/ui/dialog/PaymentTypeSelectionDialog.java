/*
 * PaymentTypeSelectionDialog.java
 *
 * Created on August 25, 2006, 3:47 PM
 */

package com.floreantpos.ui.dialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import com.floreantpos.main.Application;
import com.floreantpos.model.PaymentType;
import com.floreantpos.swing.PosButton;

/**
 *
 * @author MShahriar
 */
public class PaymentTypeSelectionDialog extends POSDialog {
  PaymentType selectedPaymentType;
  private PaymentSelectionButton btnCash;
  // private PaymentSelectionButton btnGiftCert;

  /** Creates new form PaymentTypeSelectionDialog */
  public PaymentTypeSelectionDialog() {
    super(Application.getPosWindow(), true);
    setTitle("请选择支付类型");

    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {
    JPanel content = new JPanel(new MigLayout("gap 5px 20px, fill"));
    content.setBorder(new EmptyBorder(5, 5, 5, 5));

    JPanel genericPanel = new JPanel(new GridLayout(1, 0, 15, 15));
    btnCash = new PaymentSelectionButton(PaymentType.CASH);
    genericPanel.add(btnCash, "grow,wrap");
    // btnGiftCert = new PaymentSelectionButton(PaymentType.GIFT_CERTIFICATE);
    // genericPanel.add(btnGiftCert);
    content.add(genericPanel, "height 60px, wrap, growx");

    JPanel creditCardPanel = new JPanel(new GridLayout(1, 0, 10, 10));
    // creditCardPanel.add(new PaymentSelectionButton(PaymentType.CREDIT_VISA));
    // creditCardPanel.add(new PaymentSelectionButton(PaymentType.CREDIT_MASTER_CARD));
    // creditCardPanel.add(new PaymentSelectionButton(PaymentType.CREDIT_AMEX));
    // creditCardPanel.add(new PaymentSelectionButton(PaymentType.CREDIT_DISCOVERY));
    creditCardPanel.add(new PaymentSelectionButton(PaymentType.UNION_PAY));

    creditCardPanel
        .setBorder(new CompoundBorder(new TitledBorder("银行卡支付"), new EmptyBorder(10, 10, 10, 10)));
    content.add(creditCardPanel, "wrap, height 110px, growx");

    JPanel onlinePaymentPanel = new JPanel(new GridLayout(1, 0, 10, 10));
    // onlinePaymentPanel.add(new PaymentSelectionButton(PaymentType.DEBIT_MASTER_CARD));
    onlinePaymentPanel.add(new PaymentSelectionButton(PaymentType.ALIPAY));
    onlinePaymentPanel.add(new PaymentSelectionButton(PaymentType.WECHAT));

    onlinePaymentPanel
        .setBorder(new CompoundBorder(new TitledBorder("在线支付"), new EmptyBorder(10, 10, 10, 10)));
    content.add(onlinePaymentPanel, "wrap, height 110px, growx");

    // 外卖平台
    JPanel takeoutPaymentPanel = new JPanel(new GridLayout(1, 0, 10, 10));
    takeoutPaymentPanel.add(new PaymentSelectionButton(PaymentType.MEITUAN));
    takeoutPaymentPanel.add(new PaymentSelectionButton(PaymentType.ELEME));
    takeoutPaymentPanel.add(new PaymentSelectionButton(PaymentType.DAOJIA));
    takeoutPaymentPanel.add(new PaymentSelectionButton(PaymentType.LINEZERO));

    takeoutPaymentPanel
        .setBorder(new CompoundBorder(new TitledBorder("外卖平台"), new EmptyBorder(10, 10, 10, 10)));
    content.add(takeoutPaymentPanel, "wrap, height 110px, growx");

    PosButton cancel = new PosButton("取消");
    cancel.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        setCanceled(true);
        dispose();
      }
    });

    content.add(cancel, "alignx center, gaptop 20px");

    add(content);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  public PaymentType getSelectedPaymentType() {
    return selectedPaymentType;
  }

  // End of variables declaration//GEN-END:variables

  class PaymentSelectionButton extends PosButton implements ActionListener {
    PaymentType paymentType;

    public PaymentSelectionButton(PaymentType p) {
      paymentType = p;

      if (p.getImageFile() != null) {
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + p.getImageFile())));
      } else {
        setText(p.getDisplayString());
      }

      addActionListener(this);
      setEnabled(paymentType.isSupported());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      selectedPaymentType = paymentType;
      setCanceled(false);
      dispose();
    }
  }

  public void setCashButtonVisible(boolean visible) {
    btnCash.setVisible(visible);
    // btnGiftCert.setVisible(visible);
  }
}
