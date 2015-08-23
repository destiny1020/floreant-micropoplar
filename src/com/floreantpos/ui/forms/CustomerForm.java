package com.floreantpos.ui.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.hibernate.StaleObjectStateException;

import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.model.Customer;
import com.floreantpos.model.dao.CustomerDAO;
import com.floreantpos.model.util.IllegalModelStateException;
import com.floreantpos.swing.DoubleTextField;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.swing.QwertyKeyPad;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.util.PosGuiUtil;

public class CustomerForm extends BeanEditor<Customer> {
	private static final SimpleDateFormat DOB_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	
	private FixedLengthTextField tfPhone;
	private FixedLengthTextField tfName;
	private FixedLengthTextField tfDoB;
	private FixedLengthTextField tfEmail;
	private FixedLengthTextField tfAddress;
//	private FixedLengthTextField tfCity;
//	private FixedLengthTextField tfLoyaltyNo;
//	private FixedLengthTextField tfState;
//	private FixedLengthTextField tfCountry;
//	private DoubleTextField tfCreditLimit;
//	private JCheckBox cbVip;
	private JLabel lblDob;
	private JPanel panel;
	private QwertyKeyPad qwertyKeyPad;
	
	public CustomerForm() {
		setLayout(new MigLayout("", "[][grow][][][][grow]", "[19px][][][][][][][grow]"));
		
		// Phone
		JLabel lblPhone = new JLabel("电话");
		add(lblPhone, "cell 0 0,alignx trailing");
		
		tfPhone = new FixedLengthTextField(30);
		tfPhone.setLength(30);
		add(tfPhone, "cell 1 0 5 1,growx");
		
		// Name
		JLabel lblName = new JLabel("姓名");
		add(lblName, "cell 0 1,alignx trailing");
		
		tfName = new FixedLengthTextField(60);
		tfName.setLength(60);
		add(tfName, "cell 1 1 5 1,growx");
		
		// DoB
		lblDob = new JLabel("生日 (年-月-日，例如:1987-10-20)");
		add(lblDob, "cell 0 2,alignx trailing");
		
		tfDoB = new FixedLengthTextField();
		tfDoB.setLength(16);
		add(tfDoB, "cell 1 2 5 1,growx");
		
		// E-Mail
		JLabel lblEmail = new JLabel("电子邮箱");
		add(lblEmail, "cell 0 3,alignx trailing");
		
		tfEmail = new FixedLengthTextField(40);
		tfEmail.setLength(40);
		add(tfEmail, "cell 1 3 5 1,growx");
		
		// Address
		JLabel lblAddress = new JLabel("地址");
		add(lblAddress, "cell 0 4,alignx trailing");
		
		tfAddress = new FixedLengthTextField(120);
		tfAddress.setLength(120);
		add(tfAddress, "cell 1 4 5 1,growx");
		
//		JLabel lblLoyaltyNo = new JLabel("Loyalty No");
//		add(lblLoyaltyNo, "cell 0 2,alignx trailing");
		
//		tfLoyaltyNo = new FixedLengthTextField(30);
//		tfLoyaltyNo.setLength(30);
//		add(tfLoyaltyNo, "cell 1 2,growx");
		
//		JLabel lblCitytown = new JLabel("City");
//		add(lblCitytown, "cell 0 4,alignx trailing");
		
//		tfCity = new FixedLengthTextField(30);
//		tfCity.setLength(30);
//		add(tfCity, "flowx,cell 1 4,growx");
//		
//		JLabel lblStatecountry = new JLabel("State");
//		add(lblStatecountry, "flowx,cell 2 4,alignx trailing");
//		
//		tfState = new FixedLengthTextField(30);
//		tfState.setColumns(2);
//		tfState.setLength(30);
//		add(tfState, "cell 3 4");
//		
//		JLabel lblCountry = new JLabel("Country");
//		add(lblCountry, "cell 4 4,alignx trailing");
//		
//		tfCountry = new FixedLengthTextField(30);
//		tfCountry.setText("USA");
//		tfCountry.setLength(30);
//		add(tfCountry, "cell 5 4,growx");
//		
//		JLabel lblCreditLimit = new JLabel("Credit Limit ($)");
//		add(lblCreditLimit, "cell 0 5,alignx trailing");
//		
//		tfCreditLimit = new DoubleTextField();
//		tfCreditLimit.setText("500.00");
//		tfCreditLimit.setColumns(10);
//		add(tfCreditLimit, "cell 1 5,growx");
//		
//		cbVip = new JCheckBox("VIP");
//		cbVip.setFocusable(false);
//		add(cbVip, "cell 1 6");
		
		panel = new JPanel();
		add(panel, "cell 0 7 6 1,grow");
		
		qwertyKeyPad = new QwertyKeyPad();
		panel.add(qwertyKeyPad);
	}
	
	public void setFieldsEditable(boolean editable) {
		tfName.setEditable(editable);
		tfPhone.setEditable(editable);
		tfEmail.setEditable(editable);
//		tfLoyaltyNo.setEditable(editable);
		tfAddress.setEditable(editable);
//		tfCity.setEditable(editable);
//		tfCreditLimit.setEditable(editable);
//		tfState.setEditable(editable);
//		tfCountry.setEditable(editable);
//		cbVip.setEnabled(editable);
		tfDoB.setEditable(editable);
	}

	@Override
	public boolean save() {
		 try {
			if (!updateModel())
				return false;

			Customer customer = (Customer) getBean();
			CustomerDAO.getInstance().saveOrUpdate(customer);
			return true;
		} catch (IllegalModelStateException e) {
		} catch (StaleObjectStateException e) {
			BOMessageDialog.showError(this, "It seems this Customer is modified by some other person or terminal. Save failed.");
		}
		
		return false;
	}

	@Override
	protected void updateView() {
		Customer customer = (Customer) getBean();
		
		if(customer != null) {
			tfName.setText(customer.getName());
			if(customer.getDob() != null) {
				tfDoB.setText(DOB_FORMATTER.format(customer.getDob()));
			}
			tfAddress.setText(customer.getAddress());
//			tfCity.setText(customer.getCity());
//			tfCountry.setText(customer.getCountry());
//			tfCreditLimit.setText(String.valueOf(customer.getCreditLimit()));
			tfEmail.setText(customer.getEmail());
//			tfLoyaltyNo.setText(customer.getLoyaltyNo());
			tfPhone.setText(customer.getTelephoneNo());
//			tfState.setText(customer.getState());
//			cbVip.setSelected(customer.isVip());
		}
		else {
			tfName.setText("");
			tfDoB.setText("");
			tfAddress.setText("");
//			tfCity.setText("");
//			tfCountry.setText("");
//			tfCreditLimit.setText("");
			tfEmail.setText("");
//			tfLoyaltyNo.setText("");
			tfPhone.setText("");
//			tfState.setText("");
//			cbVip.setSelected(false);
		}
	}

	@Override
	protected boolean updateModel() throws IllegalModelStateException {
		String phoneString = tfPhone.getText();
		
		if(StringUtils.isEmpty(phoneString)) {
			POSMessageDialog.showError("请提供顾客的电话号码");
			return false;
		}
		
		Customer customer = (Customer) getBean();
		
		if(customer == null) {
			customer = new Customer();
			setBean(customer, false);
		}
		
		customer.setName(tfName.getText());
		
		Date dob = null;
		String dobStr = tfDoB.getText();
		if(org.apache.commons.lang3.StringUtils.isNotBlank(dobStr)) {
			try {
				dob = DOB_FORMATTER.parse(dobStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		customer.setDob(dob);
		
		customer.setAddress(tfAddress.getText());
//		customer.setCity(tfCity.getText());
//		customer.setCountry(tfCountry.getText());
//		customer.setCreditLimit(PosGuiUtil.parseDouble(tfCreditLimit));
		customer.setEmail(tfEmail.getText());
//		customer.setLoyaltyNo(tfLoyaltyNo.getText());
		customer.setTelephoneNo(tfPhone.getText());
//		customer.setState(tfState.getText());
//		customer.setVip(cbVip.isSelected());
		
		return true;
	}

	@Override
	public String getDisplayText() {
		if(editMode) {
			return "编辑客户";
		}
		return "创建客户";
	}
}
