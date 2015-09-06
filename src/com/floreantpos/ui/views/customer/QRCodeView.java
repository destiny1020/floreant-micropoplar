package com.floreantpos.ui.views.customer;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QRCodeView extends JPanel {
public final static String VIEW_NAME = "QR_CODE_VIEW";
	
	private JLabel picLabel;

	public QRCodeView() {
		setLayout(new BorderLayout());
	}
	
	public boolean renderQrCode(String qrCodeDest) {
		if(picLabel != null) {
			remove(picLabel);
		}
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File(qrCodeDest));
			picLabel = new JLabel(new ImageIcon(myPicture));
			add(picLabel, BorderLayout.CENTER);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
