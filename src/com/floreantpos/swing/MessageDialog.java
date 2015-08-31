package com.floreantpos.swing;

import java.awt.Component;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.floreantpos.main.Application;

public class MessageDialog {
	private static Logger logger = Logger.getLogger(Application.class);
	
	public static void showError(Component parent, String errorMessage) {
		if(parent == null) {
			parent = Application.getPosWindow();
		}
		JOptionPane.showMessageDialog(parent, errorMessage, "错误", JOptionPane.ERROR_MESSAGE);
	}

	public static void showError(Component parent, String errorMessage, Throwable t) {
		if(parent == null) {
			parent = Application.getPosWindow();
		}
		logger.error(errorMessage, t);
		JOptionPane.showMessageDialog(parent, errorMessage, "错误", JOptionPane.ERROR_MESSAGE);
	}

	public static void showError(Component parent, Throwable t) {
		if(parent == null) {
			parent = Application.getPosWindow();
		}
		logger.error("Error", t);
		JOptionPane.showMessageDialog(parent, "发生了一个异常, 您或许需要重启应用.", "错误", JOptionPane.ERROR_MESSAGE);
	}
	
}
