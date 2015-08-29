package com.floreantpos.ui.views.order;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.border.EmptyBorder;

import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.views.CustomerView;
import com.floreantpos.ui.views.WaitingLoginScreen;

public class CustomerRootView extends TransparentPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CardLayout layout = new CardLayout();
	
	private WaitingLoginScreen waitingLoginScreen;
	private CustomerView customerView;
	
	private static CustomerRootView instance;
	
	private CustomerRootView() {
		setLayout(layout);
		setBorder(new EmptyBorder(3,3,3,3));
		
		waitingLoginScreen = new WaitingLoginScreen();
		addView(WaitingLoginScreen.VIEW_NAME, waitingLoginScreen);
		
		customerView = CustomerView.getInstance();
		addView(CustomerView.VIEW_NAME, customerView);
		
		// default view to waiting view
		showView(WaitingLoginScreen.VIEW_NAME);
	}
	
	public void addView(String viewName, Component view) {
		add(view, viewName);
	}
	
	public void showView(String viewName) {
		layout.show(this, viewName);
	}

	public WaitingLoginScreen getWaitingLoginScreen() {
		return waitingLoginScreen;
	}

	public CustomerView getCustomerView() {
		return customerView;
	}
	
	public synchronized static CustomerRootView getInstance() {
		if(instance == null) {
			instance = new CustomerRootView();
		}
		return instance;
	}
	
}
