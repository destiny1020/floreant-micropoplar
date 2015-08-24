package com.floreantpos.customer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;

import com.floreantpos.main.Application;
import com.floreantpos.model.Customer;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.dao.CustomerDAO;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.swing.POSTextField;
import com.floreantpos.swing.PosSmallButton;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.POSDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.forms.CustomerForm;

public class CustomerSelectionDialog extends POSDialog {

	private PosSmallButton btnCreateNewCustomer;

	private CustomerTable customerTable;
	private POSTextField tfPhone;
	private POSTextField tfEmail;
	private POSTextField tfName;

	protected Customer selectedCustomer;
	private PosSmallButton btnRemoveCustomer;
	
	private Ticket ticket;
	
	public CustomerSelectionDialog(Ticket ticket) {
		this.ticket = ticket;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setTitle("添加/编辑 客户");
		
		loadCustomerFromTicket();
	}

	@Override
	public void initUI() {
		setPreferredSize(new Dimension(690, 553));
		getContentPane().setLayout(new MigLayout("", "[549px,grow]", "[grow][][shrink 0,fill][grow][grow]"));

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel_4, "cell 0 0,grow");
		panel_4.setLayout(new MigLayout("", "[grow][][][]", "[grow][][][]"));

		JLabel lblNewLabel = new JLabel("");
		panel_4.add(lblNewLabel, "cell 0 0 1 3,grow");

		JLabel lblByPhone = new JLabel("电话号码");
		panel_4.add(lblByPhone, "cell 1 0");

		tfPhone = new POSTextField();
		panel_4.add(tfPhone, "cell 2 0");
		tfPhone.setColumns(16);

		PosSmallButton psmlbtnSearch = new PosSmallButton();
		panel_4.add(psmlbtnSearch, "cell 3 0 1 3,growy");
		psmlbtnSearch.setFocusable(false);
		psmlbtnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSearchCustomer();
			}
		});
		psmlbtnSearch.setText("搜索");

		JLabel lblByName = new JLabel("电子邮件");
		panel_4.add(lblByName, "cell 1 1,alignx trailing");

		tfEmail = new POSTextField();
		panel_4.add(tfEmail, "cell 2 1");
		tfEmail.setColumns(16);

		JLabel lblByEmail = new JLabel("顾客姓名");
		panel_4.add(lblByEmail, "cell 1 2,alignx trailing");

		tfName = new POSTextField();
		panel_4.add(tfName, "cell 2 2");
		tfName.setColumns(16);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(10, 0, 0, 0));
		panel_4.add(panel_2, "cell 0 3 4 1,growx");
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFocusable(false);
		panel_2.add(scrollPane, BorderLayout.CENTER);

		customerTable = new CustomerTable();
		customerTable.setModel(new CustomerListTableModel());
		customerTable.setFocusable(false);
		customerTable.setRowHeight(35);
		customerTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedCustomer = customerTable.getSelectedCustomer();
				if (selectedCustomer != null) {
//					btnInfo.setEnabled(true);
				}
				else {
//					btnInfo.setEnabled(false);
				}
			}
		});
		scrollPane.setViewportView(customerTable);

		JPanel panel = new JPanel();
		panel_2.add(panel, BorderLayout.SOUTH);

//		PosSmallButton btnInfo = new PosSmallButton();
//		btnInfo.setFocusable(false);
//		panel.add(btnInfo);
//		btnInfo.setEnabled(false);
//		btnInfo.setText("DETAIL");
//
//		PosSmallButton btnHistory = new PosSmallButton();
//		btnHistory.setEnabled(false);
//		btnHistory.setText("HISTORY");
//		panel.add(btnHistory);

		btnCreateNewCustomer = new PosSmallButton();
		btnCreateNewCustomer.setFocusable(false);
		panel.add(btnCreateNewCustomer);
		btnCreateNewCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCreateNewCustomer();
			}
		});
		btnCreateNewCustomer.setText("创建");
		
		btnRemoveCustomer = new PosSmallButton();
		btnRemoveCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doRemoveCustomerFromTicket();
			}
		});
		btnRemoveCustomer.setText("移除");
		panel.add(btnRemoveCustomer);

		PosSmallButton btnSelect = new PosSmallButton();
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Customer customer = customerTable.getSelectedCustomer();
				if(customer == null) {
					POSMessageDialog.showError("请选择一位顾客");
					return;
				}
				
				doSetCustomer(customer);
			}
		});
		btnSelect.setText("选择");
		panel.add(btnSelect);
		
				PosSmallButton btnCancel = new PosSmallButton();
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setCanceled(true);
						dispose();
					}
				});
				btnCancel.setText("取消");
				panel.add(btnCancel);

		JPanel panel_3 = new JPanel(new BorderLayout());
		getContentPane().add(panel_3, "cell 0 1,grow, gapright 2px");

		com.floreantpos.swing.QwertyKeyPad qwertyKeyPad = new com.floreantpos.swing.QwertyKeyPad();
		panel_3.add(qwertyKeyPad);
		tfName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSearchCustomer();
			}
		});
		tfEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSearchCustomer();
			}
		});
		tfPhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSearchCustomer();
			}
		});
	}

	private void loadCustomerFromTicket() {
		String customerIdString = ticket.getProperty(Ticket.CUSTOMER_ID);
		if(StringUtils.isNotEmpty(customerIdString)) {
			int customerId = Integer.parseInt(customerIdString);
			Customer customer = CustomerDAO.getInstance().get(customerId);
			
			List<Customer> list = new ArrayList<Customer>();
			list.add(customer);
			customerTable.setModel(new CustomerListTableModel(list));
		}
	}

	protected void doSetCustomer(Customer customer) {
		ticket.setCustomer(customer);
		TicketDAO.getInstance().saveOrUpdate(ticket);
		setCanceled(false);
		dispose();
	}

	protected void doRemoveCustomerFromTicket() {
		int option = JOptionPane.showOptionDialog(this, "从此订单中移除该顾客吗?", "确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if(option != JOptionPane.YES_OPTION) {
			return;
		}
		
		ticket.removeCustomer();
		TicketDAO.getInstance().saveOrUpdate(ticket);
		setCanceled(false);
		dispose();
	}

	protected void doSearchCustomer() {
		String phone = tfPhone.getText();
		String name = tfName.getText();
		String loyalty = tfEmail.getText();

		if (StringUtils.isEmpty(phone) && StringUtils.isEmpty(loyalty) && StringUtils.isEmpty(name)) {
			List<Customer> list = CustomerDAO.getInstance().findAll();
			customerTable.setModel(new CustomerListTableModel(list));
			return;
		}

		List<Customer> list = CustomerDAO.getInstance().findBy(phone, loyalty, name);
		customerTable.setModel(new CustomerListTableModel(list));
	}

	protected void doCreateNewCustomer() {
		CustomerForm form = new CustomerForm();
		BeanEditorDialog dialog = new BeanEditorDialog(form, Application.getPosWindow(), true);
		dialog.open();

		if (!dialog.isCanceled()) {
			selectedCustomer = (Customer) form.getBean();
			
			CustomerListTableModel model = (CustomerListTableModel) customerTable.getModel();
			model.addItem(selectedCustomer);
		}
	}
	
	@Override
	public String getName() {
		return "C";
	}
}
