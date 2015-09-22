package com.floreantpos.ui.views.payment;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.floreantpos.model.WeChatPaymentType;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.TitlePanel;
import com.floreantpos.ui.dialog.POSDialog;

import net.miginfocom.swing.MigLayout;

public class WeChatDialog extends POSDialog implements CardInputter {

  private static final long serialVersionUID = 1L;
  private WeChatPaymentType selectedPaymentType;
  private CardInputListener cardInputListener;

  public WeChatDialog(CardInputListener cardInputListener) {
    this.cardInputListener = cardInputListener;

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setResizable(false);

    TitlePanel titlePanel = new TitlePanel();
    titlePanel.setTitle("请选择微信支付方式");
    getContentPane().add(titlePanel, BorderLayout.NORTH);

    JPanel content = new JPanel(new MigLayout("gap 5px 20px, fill"));
    content.setBorder(new EmptyBorder(5, 5, 5, 5));

    JPanel paymentPanel = new JPanel(new GridLayout(1, 0, 10, 10));
    paymentPanel.add(new WeChatPaymentSelectionButton(WeChatPaymentType.PAYMENT_BAR));
    paymentPanel.add(new WeChatPaymentSelectionButton(WeChatPaymentType.QR_CODE));
    paymentPanel
        .setBorder(new CompoundBorder(new TitledBorder("可选支付方式"), new EmptyBorder(10, 10, 10, 10)));
    content.add(paymentPanel, "wrap, height 110px, growx");

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
  }

  public WeChatPaymentType getSelectedPaymentType() {
    return selectedPaymentType;
  }

  class WeChatPaymentSelectionButton extends PosButton implements ActionListener {
    private static final long serialVersionUID = 1L;
    WeChatPaymentType paymentType;

    public WeChatPaymentSelectionButton(WeChatPaymentType p) {
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

      cardInputListener.cardInputted(WeChatDialog.this);
    }
  }

}
