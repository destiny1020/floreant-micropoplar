package com.floreantpos.ui.views;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.floreantpos.IconFactory;
import com.floreantpos.swing.ImageComponent;

public class WaitingLoginScreen extends JPanel {
	public final static String VIEW_NAME = "WAITING_LOGIN_VIEW";
	
	public WaitingLoginScreen(){
		setLayout(new BorderLayout(5, 5));
		
		JLabel titleLabel = new JLabel(IconFactory.getIcon("title.png"));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(Color.WHITE);
		
		add(titleLabel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new CompoundBorder(new EtchedBorder(), new EmptyBorder(20, 20, 20, 20)));
		ImageIcon icon = IconFactory.getIcon("/", "logo.png");
		
		if(icon == null) {
			icon = IconFactory.getIcon("floreant-pos.png");
		}
		
		centerPanel.add(new ImageComponent(icon.getImage()));

		add(centerPanel);
	}
}
