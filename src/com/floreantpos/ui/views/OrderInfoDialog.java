package com.floreantpos.ui.views;

import com.floreantpos.ui.dialog.POSDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import com.floreantpos.swing.PosButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrderInfoDialog extends POSDialog {
  OrderInfoView view;

  public OrderInfoDialog(OrderInfoView view) {
    this.view = view;
    setTitle("订单预览");

    createUI();
  }

  private void createUI() {
    add(view);

    JPanel panel = new JPanel();
    getContentPane().add(panel, BorderLayout.SOUTH);

    PosButton btnPrint = new PosButton();
    btnPrint.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doPrint();
      }
    });
    btnPrint.setText("打印");
    panel.add(btnPrint);

    PosButton btnClose = new PosButton();
    btnClose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    btnClose.setText("关闭");
    panel.add(btnClose);
  }

  protected void doPrint() {
    try {
      view.print();
    } catch (Exception e) {
      POSMessageDialog.showError(e.getMessage());
    }
  }

}
