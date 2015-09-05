package com.floreantpos.ui.views.customer;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MultipleUsageView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CardLayout layout = new CardLayout();

	private static MultipleUsageView instance;

	private AdvertisementView advertisementView;
	private QRCodeView qrCodeView;

	public MultipleUsageView() {
		setLayout(layout);
		setBorder(new EmptyBorder(3, 3, 3, 3));

		advertisementView = new AdvertisementView();
		addView(AdvertisementView.VIEW_NAME, advertisementView);

		qrCodeView = new QRCodeView();
		addView(QRCodeView.VIEW_NAME, qrCodeView);
	}

	public void addView(String viewName, Component view) {
		add(view, viewName);
	}

	public void showView(String viewName) {
		layout.show(this, viewName);
	}

	public synchronized static MultipleUsageView getInstance() {
		if (instance == null) {
			instance = new MultipleUsageView();
		}
		return instance;
	}

	public AdvertisementView getAdvertisementView() {
		return advertisementView;
	}

	public QRCodeView getQrCodeView() {
		return qrCodeView;
	}

	/**
	 * Used to show the generated qr code to customer
	 * 
	 * @param qrCodeLocation
	 */
	public void switchToQRCode(String qrCodeLocation) {
		qrCodeView.renderQrCode(qrCodeLocation);
		showView(QRCodeView.VIEW_NAME);
	}
}
