/*
 * UserForm2.java
 *
 * Created on February 8, 2008, 6:08 PM
 */

package com.floreantpos.ui.forms;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import org.apache.commons.lang3.StringUtils;

import net.miginfocom.swing.MigLayout;

import com.floreantpos.POSConstants;
import com.floreantpos.PosException;
import com.floreantpos.model.User;
import com.floreantpos.model.UserType;
import com.floreantpos.model.dao.UserDAO;
import com.floreantpos.model.dao.UserTypeDAO;
import com.floreantpos.model.util.IllegalModelStateException;
import com.floreantpos.swing.DoubleTextField;
import com.floreantpos.swing.FixedLengthDocument;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.util.POSUtil;

/**
 * 
 * @author rodaya
 */
public class UserForm extends BeanEditor {

  /** Creates new form UserForm2 */
  public UserForm() {
    initComponents();

    UserTypeDAO dao = new UserTypeDAO();
    List<UserType> userTypes = dao.findAll();

    cbUserType.setModel(new DefaultComboBoxModel(userTypes.toArray()));

    // chkDriver = new JCheckBox("Driver");
    // add(chkDriver, "cell 1 9");
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    jLabel1 = new javax.swing.JLabel();
    // jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();
    tfPassword1 = new javax.swing.JPasswordField(new FixedLengthDocument(4), "", 10);
    tfPassword1.setColumns(16);
    tfPassword2 = new javax.swing.JPasswordField(new FixedLengthDocument(4), "", 10);
    tfPassword2.setColumns(16);
    tfId = new FixedLengthTextField();
    // tfSsn = new FixedLengthTextField();
    // tfSsn.setLength(30);
    // tfSsn.setColumns(30);
    tfFirstName = new FixedLengthTextField();
    tfFirstName.setColumns(30);
    tfFirstName.setLength(30);
    tfLastName = new FixedLengthTextField();
    tfLastName.setLength(30);
    tfLastName.setColumns(30);
    // jLabel5 = new javax.swing.JLabel();
    // tfCostPerHour = new DoubleTextField();
    jLabel6 = new javax.swing.JLabel();
    cbUserType = new javax.swing.JComboBox();
    setLayout(new MigLayout("", "[134px][204px,grow]",
        "[19px][][19px][19px][19px][19px][19px][19px][24px][]"));

    jLabel1.setText(POSConstants.USER_ID + POSConstants.COLON);
    add(jLabel1, "cell 0 0,alignx trailing,aligny center");
    add(tfId, "cell 1 0,growx,aligny center");

    lblUserIdFormat = new JLabel(POSConstants.USER_ID_FORMAT);
    add(lblUserIdFormat, "cell 1 1,alignx leading,aligny center");

    jLabel9.setText(POSConstants.ENTER_YOUR_PASSWORD);
    add(jLabel9, "cell 0 2,alignx trailing,aligny center");
    add(tfPassword1, "cell 1 2,growx,aligny center");

    jLabel10.setText(POSConstants.USER_PASS_CONFIRM + POSConstants.COLON);
    add(jLabel10, "cell 0 3,alignx trailing,aligny center");
    add(tfPassword2, "cell 1 3,growx,aligny center");

    lblPasswordFormat = new JLabel(POSConstants.USER_PASS_FORMAT);
    add(lblPasswordFormat, "cell 1 4,alignx leading,aligny center");

    jLabel4.setText("姓氏");
    add(jLabel4, "cell 0 5,alignx trailing,aligny center");
    add(tfLastName, "cell 1 5,growx,aligny center");

    jLabel3.setText("名字");
    add(jLabel3, "cell 0 6,alignx trailing,aligny center");
    add(tfFirstName, "cell 1 6,growx,aligny center");

    jLabel6.setText(POSConstants.USER_TYPE);
    add(jLabel6, "cell 0 7,alignx trailing,aligny center");
    cbUserType.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"经理", "收银员", "服务员"}));
    add(cbUserType, "cell 1 7,growx,aligny center");

    lblPhone = new JLabel(POSConstants.TELEPHONE + POSConstants.COLON);
    add(lblPhone, "cell 0 8,alignx trailing");
    tfPhone = new FixedLengthTextField();
    tfPhone.setLength(11);
    tfPhone.setColumns(20);
    add(tfPhone, "cell 1 8,growx");
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox cbUserType;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;

  // private javax.swing.JLabel jLabel2;
  // private FixedLengthTextField tfSsn;
  // private javax.swing.JLabel jLabel5;
  // private DoubleTextField tfCostPerHour;

  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel9;
  private FixedLengthTextField tfFirstName;
  private FixedLengthTextField tfId;
  private FixedLengthTextField tfLastName;
  private javax.swing.JPasswordField tfPassword1;
  private javax.swing.JPasswordField tfPassword2;

  private JLabel lblUserIdFormat;
  private JLabel lblPasswordFormat;

  // End of variables declaration//GEN-END:variables

  @Override
  public String getDisplayText() {
    if (isEditMode())
      return "编辑用户";

    return "添加新用户";
  }

  private boolean editMode;
  private JLabel lblPhone;
  private FixedLengthTextField tfPhone;
  // private JCheckBox chkDriver;

  @Override
  public boolean save() {
    try {
      updateModel();
    } catch (IllegalModelStateException e) {
      POSMessageDialog.showError(this, e.getMessage());
      return false;
    }

    User user = (User) getBean();
    UserDAO userDAO = UserDAO.getInstance();

    if (!editMode) {
      if (userDAO.isUserExist(user.getUserId())) {
        POSMessageDialog.showError(this, "用户ID: " + user.getUserId() + " 已经存在了.");
        return false;
      }
    }

    try {
      userDAO.saveOrUpdate(user, editMode);
    } catch (PosException x) {
      POSMessageDialog.showError(this, x.getMessage(), x);
      x.printStackTrace();
      return false;
    } catch (Exception x) {
      POSMessageDialog.showError(this, "无法保存用户", x);
      x.printStackTrace();
      return false;
    }

    return true;
  }

  @Override
  protected boolean updateModel() throws IllegalModelStateException {
    User user = null;
    if (!(getBean() instanceof User)) {
      user = new User();
    } else {
      user = (User) getBean();
    }

    String id = tfId.getText();
    String firstName = tfFirstName.getText();
    String lastName = tfLastName.getText();
    String secretKey1 = new String(tfPassword1.getPassword());
    String secretKey2 = new String(tfPassword2.getPassword());

    if (StringUtils.isBlank(id)) {
      throw new IllegalModelStateException(POSConstants.USER_ID_NEEDED);
    }
    if (!id.matches("[0-9A-Za-z]*") || id.length() > 16) {
      throw new IllegalModelStateException(POSConstants.USER_ID_FORMAT);
    }

    if (POSUtil.isBlankOrNull(secretKey1)) {
      throw new IllegalModelStateException("密码不能为空");
    }
    if (POSUtil.isBlankOrNull(secretKey2)) {
      throw new IllegalModelStateException("确认密码不能为空");
    }

    if (secretKey1.length() < 4 || secretKey1.length() > 12 || !secretKey1.matches("[0-9]*")) {
      throw new IllegalModelStateException(POSConstants.USER_PASS_FORMAT);
    }

    // final confirmation for the password
    if (!secretKey1.equals(secretKey2)) {
      throw new IllegalModelStateException("两次输入的密码不一致");
    }

    if (POSUtil.isBlankOrNull(lastName)) {
      throw new IllegalModelStateException("姓氏不能为空");
    }
    if (POSUtil.isBlankOrNull(firstName)) {
      throw new IllegalModelStateException("名字不能为空");
    }

    // TODO: to add user id when login
    if (!isEditMode()) {
      if (UserDAO.getInstance().isUserExist(id)) {
        throw new IllegalModelStateException(POSConstants.USER_ID_DUPLICATED);
      }
    }

    user.setType((UserType) cbUserType.getSelectedItem());

    user.setUserId(id);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setPhoneNo(tfPhone.getText());
    user.setPassword(secretKey1);

    setBean(user);
    return true;
  }

  @Override
  protected void updateView() {
    if (!(getBean() instanceof User)) {
      return;
    }
    User user = (User) getBean();
    setData(user);
  }

  private void setData(User data) {
    if (data.getUserId() != null) {
      tfId.setText(String.valueOf(data.getUserId()));
    } else {
      tfId.setText("");
    }
    tfFirstName.setText(data.getFirstName());
    tfLastName.setText(data.getLastName());
    tfPassword1.setText(data.getPassword());
    tfPassword2.setText(data.getPassword());
    tfPhone.setText(data.getPhoneNo());
    cbUserType.setSelectedItem(data.getType());
  }

  public boolean isEditMode() {
    return editMode;
  }

  public void setEditMode(boolean editMode) {
    this.editMode = editMode;
    if (editMode) {
      tfId.setEditable(false);
    } else {
      tfId.setEditable(true);
    }
  }

  public void setId(String id) {
    if (StringUtils.isNotBlank(id)) {
      tfId.setText(id);
    }
  }
}
