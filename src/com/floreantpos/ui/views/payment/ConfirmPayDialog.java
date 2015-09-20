package com.floreantpos.ui.views.payment;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.floreantpos.main.Application;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.TitlePanel;
import com.floreantpos.ui.dialog.POSDialog;
import javax.swing.border.EmptyBorder;

public class ConfirmPayDialog extends POSDialog {
	private JLabel lblInfo;
	private String title;

	public ConfirmPayDialog(String title) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		setTitle(title);
		this.title = title;

		createUI();
	}

	private void createUI() {
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JSeparator separator = new JSeparator();
		panel.add(separator, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);

		PosButton psbtnConfirm = new PosButton();
		psbtnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCanceled(false);
				dispose();
			}
		});
		psbtnConfirm.setText("确认");
		panel_1.add(psbtnConfirm);

		PosButton psbtnCancel = new PosButton();
		psbtnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCanceled(true);
				dispose();
			}
		});
		psbtnCancel.setText("取消");
		panel_1.add(psbtnCancel);

		TitlePanel titlePanel = new TitlePanel();
		titlePanel.setTitle(title);
		getContentPane().add(titlePanel, BorderLayout.NORTH);

		lblInfo = new JLabel("");
		lblInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
		lblInfo.setFont(new Font("微软雅黑", Font.BOLD, 16));
		getContentPane().add(lblInfo, BorderLayout.CENTER);
	}

	public void setMessage(String message) {
		lblInfo.setText(message);
	}

	public void setAmount(double amount) {
		lblInfo.setText("<html>请确认收到了来自顾客的  <b>" + Application.getCurrencySymbol() + amount
				+ "</b><br/><br/>如果确认无误, 请点击 <b>确认</b>, 否则请点击 <b>取消</b>.<br/><br/></html>");
	}
}
