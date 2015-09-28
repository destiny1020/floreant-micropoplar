package com.micropolar.ui.model;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.floreantpos.POSConstants;
import com.floreantpos.model.MenuCategory;
import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.MenuItemShift;
import com.floreantpos.model.VirtualPrinter;
import com.floreantpos.model.dao.VirtualPrinterDAO;
import com.floreantpos.model.util.IllegalModelStateException;
import com.floreantpos.swing.DoubleTextField;
import com.floreantpos.swing.FixedLengthDocument;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.swing.IUpdatebleView;
import com.floreantpos.swing.MessageDialog;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.ui.model.MenuItemShiftDialog;
import com.floreantpos.ui.model.ShiftTableModel;
import com.micropoplar.model.dao.MenuItemSetDAO;
import com.micropoplar.pos.model.MenuItemSet;
import com.micropoplar.ui.model.MenuItemExplorerTableModel;

import net.miginfocom.swing.MigLayout;

public class MenuItemSetForm extends BeanEditor<MenuItemSet>
    implements ActionListener, ChangeListener {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // data
  private ShiftTableModel shiftTableModel;
  private MenuItemExplorerTableModel menuItemTableModel;

  // components
  private JTabbedPane tabbedPane;
  private JTable shiftTable;

  private JPanel pnlMainEditor;

  private JLabel lblName;
  private FixedLengthTextField tfName;
  private JLabel lblBarcode;
  private FixedLengthTextField tfBarcode;
  private JLabel lblPrice;
  private DoubleTextField tfPrice;
  private JLabel lblDiscountRate;
  private DoubleTextField tfDiscountRate;
  private JLabel lblMemberPrice;
  private DoubleTextField tfMemberPrice;
  private JLabel lblVisible;
  private JCheckBox chkVisible;
  private JLabel lblImagePreview;
  private JCheckBox cbShowTextWithImage;
  private JLabel lblVirtualPrinter;
  private JComboBox<VirtualPrinter> cbVirtualPrinter;

  private JComboBox<MenuCategory> cbCategory;
  private JComboBox<MenuGroup> cbGroup;

  public MenuItemSetForm(MenuItemSet menuItemSet) {
    initComponents();
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    Component selectedComponent = tabbedPane.getSelectedComponent();
    if (!(selectedComponent instanceof IUpdatebleView)) {
      return;
    }

    IUpdatebleView view = (IUpdatebleView) selectedComponent;

    MenuItemSet menuItem = (MenuItemSet) getBean();
    view.initView(menuItem);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    if (actionCommand.equals(com.floreantpos.POSConstants.ADD_SHIFT)) {
      addShift();
    } else if (actionCommand.equals(com.floreantpos.POSConstants.DELETE_SHIFT)) {
      deleteShift();
    }
  }

  @Override
  public boolean save() {
    try {
      if (!updateModel())
        return false;

      MenuItemSet menuItemSet = (MenuItemSet) getBean();
      MenuItemSetDAO menuItemSetDAO = new MenuItemSetDAO();
      menuItemSetDAO.saveOrUpdate(menuItemSet);
    } catch (Exception e) {
      MessageDialog.showError(this, POSConstants.ERROR_MESSAGE, e);
      return false;
    }
    return true;
  }

  @Override
  protected void updateView() {
    MenuItemSet menuItemSet = getBean();

    if (menuItemSet.getId() != null) {
      // initialize inner items
      MenuItemSetDAO dao = MenuItemSetDAO.getInstance();
      Session session = dao.getSession();
      menuItemSet = (MenuItemSet) session.merge(menuItemSet);
      Hibernate.initialize(menuItemSet.getItems());
      session.close();
    }

    tfName.setText(menuItemSet.getName());
    tfBarcode.setText(menuItemSet.getBarcode());
    tfPrice.setText(String.valueOf(menuItemSet.getPrice()));
    tfDiscountRate.setText(String.valueOf(menuItemSet.getDiscountRate()));
    chkVisible.setSelected(menuItemSet.getVisible());
    cbShowTextWithImage.setSelected(menuItemSet.getShowImageOnly());
    if (menuItemSet.getImage() != null) {
      ImageIcon imageIcon = new ImageIcon(new ImageIcon(menuItemSet.getImage()).getImage()
          .getScaledInstance(60, 60, Image.SCALE_SMOOTH));
      lblImagePreview.setIcon(imageIcon);
    }

    if (menuItemSet.getId() == null) {
      cbGroup.setSelectedIndex(0);
    } else {
      cbGroup.setSelectedItem(menuItemSet.getGroup());
    }

    cbVirtualPrinter.setSelectedItem(menuItemSet.getVirtualPrinter());
  }

  @Override
  protected boolean updateModel() throws IllegalModelStateException {
    String itemName = tfName.getText();
    if (StringUtils.isBlank(itemName)) {
      MessageDialog.showError(this, POSConstants.NAME_REQUIRED);
      return false;
    }

    MenuItemSet menuItem = getBean();
    menuItem.setName(itemName);
    menuItem.setBarcode(tfBarcode.getText());

    // TODO: 选择是在一级目录下还是在二级目录下
    // menuItem.setParent((MenuGroup) cbGroup.getSelectedItem());

    menuItem.setPrice(Double.valueOf(tfPrice.getText()));
    menuItem.setVisible(chkVisible.isSelected());
    menuItem.setShowImageOnly(cbShowTextWithImage.isSelected());

    try {
      menuItem.setDiscountRate(Double.parseDouble(tfDiscountRate.getText()));
    } catch (Exception x) {
      // TODO: 解析错误处理
    }

    menuItem.setShifts(shiftTableModel.getShifts());
    menuItem.setItems(menuItemTableModel.getItems());

    int tabCount = tabbedPane.getTabCount();
    for (int i = 0; i < tabCount; i++) {
      Component componentAt = tabbedPane.getComponent(i);
      if (componentAt instanceof IUpdatebleView) {
        IUpdatebleView view = (IUpdatebleView) componentAt;
        if (!view.updateModel(menuItem)) {
          return false;
        }
      }
    }

    menuItem.setVirtualPrinter((VirtualPrinter) cbVirtualPrinter.getSelectedItem());

    return true;
  }

  @Override
  public String getDisplayText() {
    MenuItemSet menuItemSet = (MenuItemSet) getBean();
    if (menuItemSet.getId() == null) {
      return POSConstants.NEW_MENU_ITEM;
    }
    return POSConstants.EDIT_MENU_ITEM;
  }

  private void addShift() {
    MenuItemShiftDialog dialog = new MenuItemShiftDialog((Dialog) this.getTopLevelAncestor());
    dialog.setSize(350, 220);
    dialog.open();

    if (!dialog.isCanceled()) {
      MenuItemShift menuItemShift = dialog.getMenuItemShift();
      shiftTableModel.add(menuItemShift);
    }
  }

  private void deleteShift() {
    int selectedRow = shiftTable.getSelectedRow();
    if (selectedRow >= 0) {
      shiftTableModel.remove(selectedRow);
    }
  }

  private void initComponents() {
    tabbedPane = new JTabbedPane();
    pnlMainEditor = new JPanel();
    shiftTable = new JTable();

    lblName = new JLabel();
    lblName.setHorizontalAlignment(SwingConstants.TRAILING);
    lblName.setText(POSConstants.EDITOR_NAME);
    tfName = new FixedLengthTextField();
    tfName.setDocument(new FixedLengthDocument(30));

    lblBarcode = new JLabel();
    lblBarcode.setHorizontalAlignment(SwingConstants.TRAILING);
    lblBarcode.setText(POSConstants.MENU_ITEM_SET_EDITOR_BARCODE);
    tfBarcode = new FixedLengthTextField(120);

    lblPrice = new JLabel();
    lblPrice.setHorizontalAlignment(SwingConstants.TRAILING);
    lblPrice.setText(POSConstants.MENU_ITEM_SET_EDITOR_PRICE);
    tfPrice = new DoubleTextField();
    tfPrice.setHorizontalAlignment(SwingConstants.TRAILING);

    lblVisible = new JLabel();
    lblVisible.setHorizontalAlignment(SwingConstants.TRAILING);
    lblVisible.setText(POSConstants.MENU_ITEM_SET_EDITOR_VISIBLE);
    chkVisible = new javax.swing.JCheckBox();
    chkVisible.setText(POSConstants.MENU_ITEM_SET_EDITOR_VISIBLE);

    lblImagePreview = new JLabel("");
    lblImagePreview.setHorizontalAlignment(JLabel.CENTER);
    lblImagePreview.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    lblImagePreview.setPreferredSize(new Dimension(60, 120));
    cbShowTextWithImage = new JCheckBox(POSConstants.EDITOR_IMAGE_ONLY);
    cbShowTextWithImage.setActionCommand("Show Text with Image");

    lblDiscountRate = new JLabel();
    lblDiscountRate.setHorizontalAlignment(SwingConstants.TRAILING);
    lblDiscountRate.setText(POSConstants.MENU_ITEM_SET_EDITOR_DISCOUNT_RATE);
    tfDiscountRate = new DoubleTextField();
    tfDiscountRate.setHorizontalAlignment(SwingConstants.TRAILING);

    lblMemberPrice = new JLabel();
    lblMemberPrice.setHorizontalAlignment(SwingConstants.TRAILING);
    lblMemberPrice.setText(POSConstants.MENU_ITEM_SET_EDITOR_MEMBER_PRICE);
    tfMemberPrice = new DoubleTextField();
    tfMemberPrice.setHorizontalAlignment(SwingConstants.TRAILING);

    lblVirtualPrinter = new JLabel();
    lblVirtualPrinter.setHorizontalAlignment(SwingConstants.TRAILING);
    lblVirtualPrinter.setText(POSConstants.EDITOR_V_PRINTER);
    cbVirtualPrinter = new JComboBox<VirtualPrinter>(new DefaultComboBoxModel<VirtualPrinter>(
        VirtualPrinterDAO.getInstance().findAll().toArray(new VirtualPrinter[0])));

    cbGroup = new JComboBox<>();
    cbCategory = new JComboBox<>();

    // layout
    pnlMainEditor.setLayout(new MigLayout("", "[104px][100px,grow][][49px]",
        "[19px][][25px][][19px][19px][][][25px][][15px]"));

    pnlMainEditor.add(lblBarcode, "cell 0 1,alignx leading");
    pnlMainEditor.add(tfBarcode, "cell 1 1,growx");

    tabbedPane.addTab(POSConstants.GENERAL, pnlMainEditor);
    tabbedPane.addChangeListener(this);
  }

}
