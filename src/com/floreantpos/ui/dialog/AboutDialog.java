package com.floreantpos.ui.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.floreantpos.IconFactory;
import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.main.Application;

public class AboutDialog extends POSDialog {

  public AboutDialog() {
    super(BackOfficeWindow.getInstance(), true);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setTitle("关于");
  }

  @Override
  protected void initUI() {
    JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    JLabel logoLabel = new JLabel(IconFactory.getIcon("/icons/", "fp_logo128x128.png"));
    contentPanel.add(logoLabel, BorderLayout.WEST);

    JLabel l = new JLabel("<html><center><h1>" + POSConstants.MDS_POS + "</h1><br/><h2>Version "
        + Application.VERSION + "</h2></center></html>");
    contentPanel.add(l);

    JPanel buttonPanel = new JPanel();
    JButton btnOk = new JButton("OK");
    btnOk.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });

    buttonPanel.add(btnOk);
    contentPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(contentPanel);
  }
}
