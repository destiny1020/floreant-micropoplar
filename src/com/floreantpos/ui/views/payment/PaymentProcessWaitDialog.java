package com.floreantpos.ui.views.payment;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PaymentProcessWaitDialog extends JDialog {

	public PaymentProcessWaitDialog(JDialog parent) {
		super(parent, false);
		setTitle("正在处理中...");
		
		JLabel label = new JLabel("正在处理支付请求, 请稍等片刻...");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(label.getFont().deriveFont(24).deriveFont(Font.BOLD));
		add(label);
		
		setSize(500, 400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(parent);
	}

	public PaymentProcessWaitDialog(JFrame parent) {
		super(parent, false);
		setTitle("正在处理中...");
		
		JLabel label = new JLabel("正在处理支付请求, 请稍等片刻...");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(label.getFont().deriveFont(24).deriveFont(Font.BOLD));
		add(label);
		
		setSize(500, 400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(parent);
	}
}
