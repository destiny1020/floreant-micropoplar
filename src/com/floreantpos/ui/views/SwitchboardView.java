/*
 * SwitchboardView.java
 *
 * Created on August 14, 2006, 11:45 PM
 */

package com.floreantpos.ui.views;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.LogFactory;

import com.floreantpos.ITicketList;
import com.floreantpos.POSConstants;
import com.floreantpos.PosException;
import com.floreantpos.actions.AuthorizeTicketAction;
import com.floreantpos.actions.NewBarTabAction;
import com.floreantpos.actions.OpenKitchenDisplayAction;
import com.floreantpos.actions.RefundAction;
import com.floreantpos.actions.SettleTicketAction;
import com.floreantpos.actions.ShutDownAction;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.demo.KitchenDisplay;
import com.floreantpos.extension.FloorLayoutPlugin;
import com.floreantpos.extension.OrderServiceExtension;
import com.floreantpos.main.Application;
import com.floreantpos.model.AttendenceHistory;
import com.floreantpos.model.Shift;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketType;
import com.floreantpos.model.User;
import com.floreantpos.model.UserPermission;
import com.floreantpos.model.UserType;
import com.floreantpos.model.dao.AttendenceHistoryDAO;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.model.dao.UserDAO;
import com.floreantpos.model.util.TicketUniqIdGenerator;
import com.floreantpos.services.TicketService;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.dialog.ManagerDialog;
import com.floreantpos.ui.dialog.NumberSelectionDialog2;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.dialog.PayoutDialog;
import com.floreantpos.ui.dialog.VoidTicketDialog;
import com.floreantpos.ui.views.order.DefaultOrderServiceExtension;
import com.floreantpos.ui.views.order.OrderView;
import com.floreantpos.ui.views.order.RootView;
import com.floreantpos.ui.views.payment.SettleTicketDialog;
import com.floreantpos.util.POSUtil;
import com.floreantpos.util.PosGuiUtil;
import com.floreantpos.util.TicketAlreadyExistsException;

/**
 * 
 * @author MShahriar
 */
public class SwitchboardView extends JPanel implements ActionListener, ITicketList {
	private final AutoLogoffHandler logoffHandler = new AutoLogoffHandler();

	public final static String VIEW_NAME = com.floreantpos.POSConstants.SWITCHBOARD;

	private OrderServiceExtension orderServiceExtension;

	private static SwitchboardView instance;
	
	private Timer autoLogoffTimer = new Timer(1000, logoffHandler);

	//	private Timer ticketListUpdater;

	/** Creates new form SwitchboardView */
	public SwitchboardView() {
		initComponents();

		btnBackOffice.addActionListener(this);
		btnClockOut.addActionListener(this);
		btnEditTicket.addActionListener(this);
		btnGroupSettle.addActionListener(this);
		btnLogout.addActionListener(this);
		btnManager.addActionListener(this);
		btnNewTicket.addActionListener(this);
		btnPayout.addActionListener(this);
		btnOrderInfo.addActionListener(this);
		btnReopenTicket.addActionListener(this);
		btnSettleTicket.addActionListener(this);
		btnSplitTicket.addActionListener(this);
		btnTakeout.addActionListener(this);
		btnVoidTicket.addActionListener(this);

		orderServiceExtension = Application.getPluginManager().getPlugin(OrderServiceExtension.class);

		if (orderServiceExtension == null) {
			btnHomeDelivery.setEnabled(false);
			btnPickup.setEnabled(false);
			btnDriveThrough.setEnabled(false);
			btnAssignDriver.setEnabled(false);

			orderServiceExtension = new DefaultOrderServiceExtension();
		}
		//		ticketListUpdater = new Timer(30 * 1000, new TicketListUpdaterTask());

		applyComponentOrientation(ComponentOrientation.getOrientation(Locale.getDefault()));

		instance = this;
	}
	
	public static SwitchboardView getInstance() {
		return instance;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code
	// <editor-fold defaultstate="collapsed"
	// desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {

		
		lblUserName = new javax.swing.JLabel();
		javax.swing.JPanel bottomPanel = new javax.swing.JPanel();
		javax.swing.JPanel bottomLeftPanel = new javax.swing.JPanel();
		openTicketList = new com.floreantpos.ui.TicketListView();
		javax.swing.JPanel activityPanel = new javax.swing.JPanel();
		btnNewTicket = new com.floreantpos.swing.PosButton();
		btnEditTicket = new com.floreantpos.swing.PosButton();
		btnVoidTicket = new com.floreantpos.swing.PosButton();
		btnRefundTicket = new com.floreantpos.swing.PosButton(new RefundAction(this));
		btnPayout = new com.floreantpos.swing.PosButton();
		btnOrderInfo = new com.floreantpos.swing.PosButton();
		javax.swing.JPanel bottomRightPanel = new javax.swing.JPanel();
		btnShutdown = new com.floreantpos.swing.PosButton(new ShutDownAction());
		btnLogout = new com.floreantpos.swing.PosButton();
		btnBackOffice = new com.floreantpos.swing.PosButton();
		btnManager = new com.floreantpos.swing.PosButton();
		btnAuthorize = new PosButton(new AuthorizeTicketAction());
		btnKitchenDisplay = new PosButton(new OpenKitchenDisplayAction());
		btnClockOut = new com.floreantpos.swing.PosButton();

		setLayout(new java.awt.BorderLayout(10, 10));

		createHeaderPanel();

		bottomPanel.setLayout(new java.awt.BorderLayout(5, 5));

		bottomLeftPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, POSConstants.OPEN_TICKETS_AND_ACTIVITY,
				javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
		bottomLeftPanel.setLayout(new java.awt.BorderLayout(5, 5));
		bottomLeftPanel.add(openTicketList, java.awt.BorderLayout.CENTER);

		activityPanel.setLayout(new java.awt.GridLayout(3, 0, 5, 5));

		btnNewTicket.setText("堂食");
		btnTakeout = new com.floreantpos.swing.PosButton();
		btnPickup = new PosButton();
		btnHomeDelivery = new PosButton();
		btnDriveThrough = new PosButton();
		btnBarTab = new PosButton("BAR TAB");

		if (TerminalConfig.isDineInEnable()) {
			activityPanel.add(btnNewTicket);
		}

		if (TerminalConfig.isTakeOutEnable()) {
			btnTakeout.setText(POSConstants.CAPITAL_TAKE_OUT);
			activityPanel.add(btnTakeout);
		}

		if (TerminalConfig.isPickupEnable()) {
			btnPickup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doHomeDelivery(TicketType.PICKUP);
				}
			});
			btnPickup.setText("PICKUP");
			activityPanel.add(btnPickup);
		}

		if (TerminalConfig.isHomeDeliveryEnable()) {
			btnHomeDelivery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doHomeDelivery(TicketType.HOME_DELIVERY);
				}
			});
			btnHomeDelivery.setText("HOME DELIVERY");
			activityPanel.add(btnHomeDelivery);
		}

		if (TerminalConfig.isDriveThruEnable()) {
			btnDriveThrough.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doTakeout(TicketType.DRIVE_THRU);
				}
			});
			btnDriveThrough.setText("DRIVE THRU");
			activityPanel.add(btnDriveThrough);
		}

		if (TerminalConfig.isBarTabEnable()) {
			btnBarTab.setAction(new NewBarTabAction(this));
			activityPanel.add(btnBarTab);
		}
		
		btnEditTicket.setText(POSConstants.CAPITAL_EDIT);
		activityPanel.add(btnEditTicket);

		btnSettleTicket = new com.floreantpos.swing.PosButton();
		btnSettleTicket.setText(POSConstants.CAPITAL_SETTLE);
		activityPanel.add(btnSettleTicket);
		btnGroupSettle = new com.floreantpos.swing.PosButton();

		btnGroupSettle.setText(POSConstants.CAPITAL_GROUP + POSConstants.CAPITAL_SETTLE);
		activityPanel.add(btnGroupSettle);
		btnSplitTicket = new com.floreantpos.swing.PosButton();

		btnSplitTicket.setText(POSConstants.CAPITAL_SPLIT);
		activityPanel.add(btnSplitTicket);
		btnReopenTicket = new com.floreantpos.swing.PosButton();

		btnReopenTicket.setText(POSConstants.CAPITAL_RE_OPEN);
		activityPanel.add(btnReopenTicket);

		btnVoidTicket.setText(POSConstants.CAPITAL_VOID);
		activityPanel.add(btnVoidTicket);
		
		btnRefundTicket.setText("订单退款");
		activityPanel.add(btnRefundTicket);

		btnPayout.setText(POSConstants.CAPITAL_PAY_OUT);
		activityPanel.add(btnPayout);

		btnOrderInfo.setText(POSConstants.ORDER_INFO);
		activityPanel.add(btnOrderInfo);

		bottomLeftPanel.add(activityPanel, java.awt.BorderLayout.SOUTH);

		btnAssignDriver = new PosButton();
		btnAssignDriver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAssignDriver();
			}
		});
		btnAssignDriver.setText("<html>ASSIGN<br/>DRIVER</html>");
//		activityPanel.add(btnAssignDriver);

		btnCloseOrder = new PosButton();
		btnCloseOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCloseOrder();
			}
		});
		btnCloseOrder.setText("关闭订单");
		activityPanel.add(btnCloseOrder);

		bottomPanel.add(bottomLeftPanel, java.awt.BorderLayout.CENTER);

		bottomRightPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "OTHERS", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION));

		btnLogout.setText(POSConstants.CAPITAL_LOGOUT);

		btnBackOffice.setText(POSConstants.CAPITAL_BACK_OFFICE);

		btnManager.setText(POSConstants.CAPITAL_MANAGER);

		btnClockOut.setText(POSConstants.CAPITAL_CLOCK_OUT);
		
		bottomPanel.add(bottomRightPanel, java.awt.BorderLayout.EAST);
		bottomRightPanel.setLayout(new MigLayout("aligny bottom, insets 1 2 1 2, gapy 10", "[140px]", "[][][][][]"));
		
		final FloorLayoutPlugin floorLayoutPlugin = Application.getPluginManager().getPlugin(FloorLayoutPlugin.class);
		if(floorLayoutPlugin != null) {
			PosButton btnTicketsAndTables = new PosButton("TICKETS & TABLES");
			btnTicketsAndTables.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					floorLayoutPlugin.openTicketsAndTablesDisplay();
				}
			});
			
			bottomRightPanel.add(btnTicketsAndTables, "height pref!,grow,wrap");
		}

		bottomRightPanel.add(btnAuthorize, "height pref!,grow,wrap");
		bottomRightPanel.add(btnKitchenDisplay, "height pref!,grow,wrap");
		bottomRightPanel.add(btnManager, "height pref!,grow,wrap");
		bottomRightPanel.add(btnBackOffice, "height pref!,grow,wrap");
		//bottomRightPanel.add(new PosButton(new TicketImportAction(bottomRightPanel)), "height pref!,grow,wrap");
		bottomRightPanel.add(btnClockOut, "height pref!,grow,wrap");
		bottomRightPanel.add(btnLogout, "height pref!,grow,wrap");
		bottomRightPanel.add(btnShutdown, "height pref!,grow,wrap");

		add(bottomPanel, java.awt.BorderLayout.CENTER);
	}// </editor-fold>//GEN-END:initComponents

	private void createHeaderPanel() {
		JPanel statusPanel = new JPanel(new MigLayout("fill", "[fill, grow 100][]", ""));
		statusPanel.setPreferredSize(new Dimension(80, 40));
		java.awt.Font headerFont = new java.awt.Font("微软雅黑", Font.BOLD, 12);
		
		lblUserName.setFont(headerFont);
		statusPanel.add(lblUserName);
		
		timerLabel.setHorizontalAlignment(JLabel.RIGHT);
		timerLabel.setFont(headerFont);
		statusPanel.add(timerLabel);

		add(statusPanel, java.awt.BorderLayout.NORTH);
	}

	protected void doCloseOrder() {
		Ticket ticket = getFirstSelectedTicket();

		int due = (int) POSUtil.getDouble(ticket.getDueAmount());
		if (due != 0) {
			POSMessageDialog.showError("Ticket is not fully paid");
			return;
		}

		int option = JOptionPane.showOptionDialog(Application.getPosWindow(), "Ticket# " + ticket.getId() + " will be closed.", "Confirm",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

		if (option != JOptionPane.OK_OPTION) {
			return;
		}

		ticket.setClosed(true);
		TicketDAO.getInstance().saveOrUpdate(ticket);

		User driver = ticket.getAssignedDriver();
		if (driver != null) {
			driver.setAvailableForDelivery(true);
			UserDAO.getInstance().saveOrUpdate(driver);
		}
		
		updateTicketList();
	}

	protected void doAssignDriver() {
		try {

			Ticket ticket = getFirstSelectedTicket();

			if (ticket == null) {
				return;
			}

			if (ticket.getType() != TicketType.HOME_DELIVERY) {
				POSMessageDialog.showError("Driver can be assigned only for Home Delivery");
				return;
			}

			User assignedDriver = ticket.getAssignedDriver();
			if (assignedDriver != null) {
				int option = JOptionPane.showOptionDialog(Application.getPosWindow(), "Driver already assigned. Do you want to reassign?", "Confirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

				if (option != JOptionPane.YES_OPTION) {
					return;
				}
			}

			orderServiceExtension.assignDriver(ticket.getId());
		} catch (Exception e) {
			e.printStackTrace();
			POSMessageDialog.showError(e.getMessage());
			LogFactory.getLog(SwitchboardView.class).error(e);
		}
	}

	private void doReopenTicket() {
		try {

			int ticketId = NumberSelectionDialog2.takeIntInput("请输入或者扫描订单号");

			if (ticketId == -1) {
				return;
			}

			Ticket ticket = TicketDAO.getInstance().loadFullTicket(ticketId);

			if (ticket == null) {
				throw new PosException(POSConstants.NO_TICKET_WITH_ID + " " + ticketId + " " + POSConstants.FOUND);
			}

			if (!ticket.isClosed()) {
				throw new PosException(POSConstants.TICKET_IS_NOT_CLOSED);
			}
			
			if(ticket.isVoided()) {
				throw new PosException("废弃订单不能重新打开");
			}

			ticket.setClosed(false);
			ticket.setClosingDate(null);
			ticket.setReOpened(true);
			
			TicketDAO.getInstance().saveOrUpdate(ticket);
			
			OrderInfoView view = new OrderInfoView(Arrays.asList(ticket));
			OrderInfoDialog dialog = new OrderInfoDialog(view);
			dialog.setSize(400, 600);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(Application.getPosWindow());
			dialog.setVisible(true);
			
			updateTicketList();

//			String ticketTotalAmount = Application.getCurrencySymbol() + NumberUtil.formatNumber(ticket.getTotalAmount());
//			String amountMessage = "<span style='color: red; font-weight: bold;'>" + ticketTotalAmount + "</span>";
//			String message = "<html><body>Ticket amount is " + ticketTotalAmount
//					+ ". To reopen ticket, you need to refund that amount to system.<br/>Please press <b>OK</b> after you refund amount " + amountMessage
//					+ "</body></html>";
//
//			int option = JOptionPane.showOptionDialog(this, message, "Alert!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
//			if (option != JOptionPane.OK_OPTION) {
//				return;
//			}

		} catch (PosException e) {
			POSMessageDialog.showError(this, e.getLocalizedMessage());
		} catch (Exception e) {
			POSMessageDialog.showError(this, POSConstants.ERROR_MESSAGE, e);
		}
	}

	private void doClockOut() {
		int option = JOptionPane.showOptionDialog(this, POSConstants.CONFIRM_CLOCK_OUT, POSConstants.CONFIRM, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (option != JOptionPane.YES_OPTION) {
			return;
		}

		User user = Application.getCurrentUser();
		AttendenceHistoryDAO attendenceHistoryDAO = new AttendenceHistoryDAO();
		AttendenceHistory attendenceHistory = attendenceHistoryDAO.findHistoryByClockedInTime(user);
		if (attendenceHistory == null) {
			attendenceHistory = new AttendenceHistory();
			Date lastClockInTime = user.getLastClockInTime();
			Calendar c = Calendar.getInstance();
			c.setTime(lastClockInTime);
			attendenceHistory.setClockInTime(lastClockInTime);
			attendenceHistory.setClockInHour(Short.valueOf((short) c.get(Calendar.HOUR)));
			attendenceHistory.setUser(user);
			attendenceHistory.setTerminal(Application.getInstance().getTerminal());
			attendenceHistory.setShift(user.getCurrentShift());
		}

		Shift shift = user.getCurrentShift();
		Calendar calendar = Calendar.getInstance();

		user.doClockOut(attendenceHistory, shift, calendar);

		Application.getInstance().logout();
	}

	private synchronized void doShowBackoffice() {
		BackOfficeWindow window = BackOfficeWindow.getInstance();
		if (window == null) {
			window = new BackOfficeWindow();
			Application.getInstance().setBackOfficeWindow(window);
		}
		window.setVisible(true);
		window.toFront();
	}

	private void doLogout() {
		BackOfficeWindow.getInstance().dispose();
		KitchenDisplay.instance.dispose();
		Application.getInstance().logout();
	}

	private void doSettleTicket() {
		try {
			Ticket ticket = null;

			List<Ticket> selectedTickets = openTicketList.getSelectedTickets();

			if (selectedTickets.size() > 0) {
				ticket = selectedTickets.get(0);
			}
			else {
				int ticketId = NumberSelectionDialog2.takeIntInput("请输入或者扫描订单号");
				ticket = TicketService.getTicket(ticketId);
			}

			new SettleTicketAction(ticket.getId()).execute();

			updateTicketList();

		} catch (PosException e) {
			POSMessageDialog.showError(this, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			POSMessageDialog.showError(POSConstants.ERROR_MESSAGE, e);
		}
	}
	
	private void doShowOrderInfo() {
		doShowOrderInfo(openTicketList.getSelectedTickets());
	}

	private void doShowOrderInfo(List<Ticket> tickets) {
		try {
			
			if (tickets.size() == 0) {
				POSMessageDialog.showMessage(POSConstants.SELECT_ONE_TICKET_TO_PRINT);
				return;
			}

			List<Ticket> ticketsToShow = new ArrayList<Ticket>();

			for (int i = 0; i < tickets.size(); i++) {
				Ticket ticket = tickets.get(i);
				ticketsToShow.add(TicketDAO.getInstance().loadFullTicket(ticket.getId()));
			}

			OrderInfoView view = new OrderInfoView(ticketsToShow);
			OrderInfoDialog dialog = new OrderInfoDialog(view);
			dialog.setSize(400, 600);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(Application.getPosWindow());
			dialog.setVisible(true);

		} catch (Exception e) {
			POSMessageDialog.showError(POSConstants.ERROR_MESSAGE, e);
		}
	}

	private void doVoidTicket() {
		try {
			Ticket ticket = null;

			List<Ticket> selectedTickets = openTicketList.getSelectedTickets();

			if (selectedTickets.size() > 0) {
				ticket = selectedTickets.get(0);
			}
			else {
				int ticketId = NumberSelectionDialog2.takeIntInput("请输入或者扫描订单号");
				ticket = TicketService.getTicket(ticketId);
			}

			Ticket ticketToVoid = TicketDAO.getInstance().loadFullTicket(ticket.getId());

			VoidTicketDialog voidTicketDialog = new VoidTicketDialog(Application.getPosWindow(), true);
			voidTicketDialog.setTicket(ticketToVoid);
			voidTicketDialog.open();

			if (!voidTicketDialog.isCanceled()) {
				updateView();
			}
		} catch (PosException e) {
			POSMessageDialog.showError(this, e.getMessage());
		} catch (Exception e) {
			POSMessageDialog.showError(POSConstants.ERROR_MESSAGE, e);
		}
	}

	private void doSplitTicket() {
		try {
			Ticket selectedTicket = getFirstSelectedTicket();

			if (selectedTicket == null) {
				return;
			}

			//			if (selectedTicket.getTotalAmount() != selectedTicket.getDueAmount()) {
			//				POSMessageDialog.showMessage(POSConstants.PARTIAL_PAID_VOID_ERROR);
			//				return;
			//			}

			// initialize the ticket.
			Ticket ticket = TicketDAO.getInstance().loadFullTicket(selectedTicket.getId());

			SplitTicketDialog dialog = new SplitTicketDialog();
			dialog.setTicket(ticket);
			dialog.open();

			updateView();
		} catch (Exception e) {
			POSMessageDialog.showError(POSConstants.ERROR_MESSAGE, e);
		}
	}

	private void doEditTicket() {
		try {
			Ticket ticket = null;

			List<Ticket> selectedTickets = openTicketList.getSelectedTickets();

			if (selectedTickets.size() > 0) {
				ticket = selectedTickets.get(0);
			}
			else {
				int ticketId = NumberSelectionDialog2.takeIntInput("请输入或者扫描订单号");
				ticket = TicketService.getTicket(ticketId);
			}

			editTicket(ticket);
		} catch (PosException e) {
			POSMessageDialog.showError(this, e.getMessage());
		} catch (Exception e) {
			POSMessageDialog.showError(this, e.getMessage(), e);
		}
	}

	private void editTicket(Ticket ticket) {
		if (ticket.isPaid()) {
			POSMessageDialog.showMessage("已支付的订单无法编辑");
			return;
		}

		Ticket ticketToEdit = TicketDAO.getInstance().loadFullTicket(ticket.getId());
		OrderView.getInstance().setCurrentTicket(ticketToEdit);

		RootView.getInstance().showView(OrderView.VIEW_NAME);
	}

	private void doCreateNewTicket(final TicketType ticketType) {
		try {
			OrderServiceExtension orderService = new DefaultOrderServiceExtension();
			orderService.createNewTicket(ticketType);

		} catch (TicketAlreadyExistsException e) {

			int option = JOptionPane.showOptionDialog(Application.getPosWindow(), POSConstants.EDIT_TICKET_CONFIRMATION, POSConstants.CONFIRM,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (option == JOptionPane.YES_OPTION) {
				editTicket(e.getTicket());
				return;
			}
		}
	}

	protected void doHomeDelivery(TicketType ticketType) {
		try {

			orderServiceExtension.createNewTicket(ticketType);

		} catch (TicketAlreadyExistsException e) {

			int option = JOptionPane.showOptionDialog(Application.getPosWindow(), POSConstants.EDIT_TICKET_CONFIRMATION, POSConstants.CONFIRM,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (option == JOptionPane.YES_OPTION) {
				editTicket(e.getTicket());
				return;
			}
		}
	}

	private void doTakeout(TicketType titcketType) {
		Application application = Application.getInstance();

		Ticket ticket = new Ticket();
		ticket.setPriceIncludesTax(application.isPriceIncludesTax());
		//ticket.setTableNumber(-1);
		ticket.setType(titcketType);
		ticket.setTerminal(application.getTerminal());
		ticket.setOwner(Application.getCurrentUser());
		ticket.setShift(application.getCurrentShift());

		Calendar currentTime = Calendar.getInstance();
		ticket.setCreateDate(currentTime.getTime());
		ticket.setCreationHour(currentTime.get(Calendar.HOUR_OF_DAY));

		OrderView.getInstance().setCurrentTicket(ticket);
		RootView.getInstance().showView(OrderView.VIEW_NAME);
	}

	private void doPayout() {
		PayoutDialog dialog = new PayoutDialog(Application.getPosWindow(), true);
		dialog.open();
	}

	private void doShowManagerWindow() {
		ManagerDialog dialog = new ManagerDialog();
		dialog.open();

		updateTicketList();
	}

	private void doGroupSettle() {
		List<Ticket> selectedTickets = openTicketList.getSelectedTickets();
		if (selectedTickets == null) {
			return;
		}

		for (int i = 0; i < selectedTickets.size(); i++) {
			Ticket ticket = selectedTickets.get(i);

			Ticket fullTicket = TicketDAO.getInstance().loadFullTicket(ticket.getId());

			SettleTicketDialog posDialog = new SettleTicketDialog();
			posDialog.setTicket(fullTicket);
			posDialog.setSize(800, 700);
			posDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			posDialog.open();
		}

		updateTicketList();
	}

	public void updateView() {
		User user = Application.getCurrentUser();
		UserType userType = user.getType();
		if (userType != null) {
			Set<UserPermission> permissions = userType.getPermissions();
			if (permissions != null) {
				btnNewTicket.setEnabled(false);
				btnBackOffice.setEnabled(false);
				btnEditTicket.setEnabled(false);
				btnGroupSettle.setEnabled(false);
				btnManager.setEnabled(false);
				btnPayout.setEnabled(false);
				btnReopenTicket.setEnabled(false);
				btnSettleTicket.setEnabled(false);
				btnSplitTicket.setEnabled(false);
				btnTakeout.setEnabled(false);
				btnVoidTicket.setEnabled(false);

				for (UserPermission permission : permissions) {
					if (permission.equals(UserPermission.VOID_TICKET)) {
						btnVoidTicket.setEnabled(true);
					}
					else if (permission.equals(UserPermission.PAY_OUT)) {
						btnPayout.setEnabled(true);
					}
					else if (permission.equals(UserPermission.SETTLE_TICKET)) {
						btnSettleTicket.setEnabled(true);
						btnGroupSettle.setEnabled(true);
					}
					else if (permission.equals(UserPermission.REOPEN_TICKET)) {
						btnReopenTicket.setEnabled(true);
					}
					else if (permission.equals(UserPermission.PERFORM_MANAGER_TASK)) {
						btnManager.setEnabled(true);
					}
					else if (permission.equals(UserPermission.SPLIT_TICKET)) {
						btnSplitTicket.setEnabled(true);
					}
					else if (permission.equals(UserPermission.TAKE_OUT)) {
						btnTakeout.setEnabled(true);
					}
					else if (permission.equals(UserPermission.VIEW_BACK_OFFICE)) {
						btnBackOffice.setEnabled(true);
					}
					else if (permission.equals(UserPermission.PAY_OUT)) {
						btnPayout.setEnabled(true);
					}
					else if (permission.equals(UserPermission.EDIT_TICKET)) {
						btnEditTicket.setEnabled(true);
					}
					else if (permission.equals(UserPermission.CREATE_TICKET)) {
						btnNewTicket.setEnabled(true);
					}
				}
			}
		}

		updateTicketList();
	}

	public void updateTicketList() {
		User user = Application.getCurrentUser();

		TicketDAO dao = TicketDAO.getInstance();
		List<Ticket> openTickets = null;

		boolean showAllOpenTicket = false;
		if (user.getType() != null) {
			Set<UserPermission> permissions = user.getType().getPermissions();
			if (permissions != null) {
				for (UserPermission permission : permissions) {
					if (permission.equals(UserPermission.VIEW_ALL_OPEN_TICKETS)) {
						showAllOpenTicket = true;
						break;
					}
				}
			}
		}

		if (showAllOpenTicket) {
			openTickets = dao.findOpenTickets();
		}
		else {
			openTickets = dao.findOpenTicketsForUser(user);
		}
		openTicketList.setTickets(openTickets);

		lblUserName.setText(POSConstants.WELCOME + " " + user.toString() + "." + POSConstants.YOU + POSConstants.HAVE + " " + openTickets.size() + "个 "
				+ POSConstants.OPEN.toLowerCase() + "的" + POSConstants.TICKETS + ".");
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.floreantpos.swing.PosButton btnBackOffice;
	private com.floreantpos.swing.PosButton btnClockOut;
	private com.floreantpos.swing.PosButton btnEditTicket;
	private com.floreantpos.swing.PosButton btnGroupSettle;
	private com.floreantpos.swing.PosButton btnLogout;
	private com.floreantpos.swing.PosButton btnManager;
	private com.floreantpos.swing.PosButton btnAuthorize;
	private com.floreantpos.swing.PosButton btnKitchenDisplay;
	private com.floreantpos.swing.PosButton btnNewTicket;
	private com.floreantpos.swing.PosButton btnPayout;
	private com.floreantpos.swing.PosButton btnOrderInfo;
	private com.floreantpos.swing.PosButton btnReopenTicket;
	private com.floreantpos.swing.PosButton btnSettleTicket;
	private com.floreantpos.swing.PosButton btnShutdown;
	private com.floreantpos.swing.PosButton btnSplitTicket;
	private com.floreantpos.swing.PosButton btnTakeout;
	private com.floreantpos.swing.PosButton btnVoidTicket;
	private com.floreantpos.swing.PosButton btnRefundTicket;
	private com.floreantpos.swing.PosButton btnBarTab;
	private javax.swing.JLabel lblUserName;
	private com.floreantpos.ui.TicketListView openTicketList;
	private PosButton btnPickup;
	private PosButton btnHomeDelivery;
	private PosButton btnDriveThrough;
	private PosButton btnAssignDriver;
	private PosButton btnCloseOrder;
	private JLabel timerLabel = new JLabel();

	// End of variables declaration//GEN-END:variables

	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);

		if (aFlag) {
			updateView();
			
			logoffHandler.reset();
			if(TerminalConfig.isAutoLogoffEnable()) {
				autoLogoffTimer.start();
			}
		}
		else {
			autoLogoffTimer.stop();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == btnBackOffice) {
			doShowBackoffice();
		}
		else if (source == btnClockOut) {
			doClockOut();
		}
		else if (source == btnEditTicket) {
			doEditTicket();
		}
		else if (source == btnGroupSettle) {
			doGroupSettle();
		}
		else if (source == btnLogout) {
			doLogout();
		}
		else if (source == btnManager) {
			doShowManagerWindow();
		}
		else if (source == btnNewTicket) {
			doCreateNewTicket(TicketType.DINE_IN);
		}
		else if (source == btnPayout) {
			doPayout();
		}
		else if (source == btnOrderInfo) {
			doShowOrderInfo();
		}
		else if (source == btnReopenTicket) {
			doReopenTicket();
		}
		else if (source == btnSettleTicket) {
			doSettleTicket();
		}
		else if (source == btnSplitTicket) {
			doSplitTicket();
		}
		else if (source == btnTakeout) {
			doTakeout(TicketType.TAKE_OUT);
		}
		else if (source == btnVoidTicket) {
			doVoidTicket();
		}
	}

	public Ticket getFirstSelectedTicket() {
		List<Ticket> selectedTickets = openTicketList.getSelectedTickets();

		if (selectedTickets.size() == 0 || selectedTickets.size() > 1) {
			POSMessageDialog.showMessage("请选择一个订单");
			return null;
		}

		Ticket ticket = selectedTickets.get(0);

		return ticket;
	}
	
	public Ticket getSelectedTicket() {
		List<Ticket> selectedTickets = openTicketList.getSelectedTickets();
		
		if (selectedTickets.size() == 0 || selectedTickets.size() > 1) {
			return null;
		}
		
		Ticket ticket = selectedTickets.get(0);
		
		return ticket;
	}

	//	private class TicketListUpdaterTask implements ActionListener {
	//
	//		public void actionPerformed(ActionEvent e) {
	//			updateTicketList();
	//		}
	//
	//	}
	
	private class AutoLogoffHandler implements ActionListener {
		int countDown = TerminalConfig.getAutoLogoffTime();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(PosGuiUtil.isModalDialogShowing()) {
				reset();
				return;
			}
			
			--countDown;
			int min = countDown / 60;
			int sec = countDown % 60;
			
			timerLabel.setText("在 " + min + ":" + sec + " 以后自动登出");
			
			if(countDown == 0) {
				doLogout();
			}
		}
		
		public void reset() {
			timerLabel.setText("");
			countDown = TerminalConfig.getAutoLogoffTime();
		}
		
	}
}
