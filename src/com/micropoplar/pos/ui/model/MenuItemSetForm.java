package com.micropoplar.pos.ui.model;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.MenuItemShift;
import com.floreantpos.model.VirtualPrinter;
import com.floreantpos.model.dao.MenuCategoryDAO;
import com.floreantpos.model.dao.MenuGroupDAO;
import com.floreantpos.model.dao.VirtualPrinterDAO;
import com.floreantpos.model.util.IllegalModelStateException;
import com.floreantpos.swing.ComboBoxModel;
import com.floreantpos.swing.DoubleTextField;
import com.floreantpos.swing.FixedLengthDocument;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.swing.IUpdatebleView;
import com.floreantpos.swing.MessageDialog;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.ui.model.MenuItemShiftDialog;
import com.floreantpos.ui.model.ShiftTableModel;
import com.floreantpos.ui.ticket.TicketViewerTable;
import com.micropoplar.pos.bo.ui.dialog.MenuItemDialog;
import com.micropoplar.pos.model.MenuItemSet;
import com.micropoplar.pos.model.SetItem;
import com.micropoplar.pos.model.dao.MenuItemSetDAO;
import com.mircopoplar.pos.ui.set.MenuItemSetTableModel;

import net.miginfocom.swing.MigLayout;

public class MenuItemSetForm extends BeanEditor<MenuItemSet> implements ActionListener,
    ChangeListener {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // data
  private MenuItemSet menuItemSet;
  private ShiftTableModel shiftTableModel;
  private MenuItemExplorerTableModel menuItemTableModel;
  private MenuItemSetTableModel menuItemSetTableModel;

  // components --- under general tab
  private JTabbedPane tabbedPane;
  private JTable shiftTable;

  private JPanel pnlMainEditor;
  private JPanel pnlDetailEditor;

  private JLabel lblCode;
  private FixedLengthTextField tfCode;
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
  private JCheckBox chkVisible;

  private JLabel lblImage;
  private JLabel lblImagePreview;
  private JButton btnImageSelect;
  private JButton btnImageClear;

  private JCheckBox chkShowTextWithImage;
  private JLabel lblVirtualPrinter;
  private JComboBox<VirtualPrinter> cbVirtualPrinter;

  private JLabel lblCategory;
  private JComboBox<MenuCategory> cbCategory;
  private JLabel lblGroup;
  private JComboBox<MenuGroup> cbGroup;

  private JLabel lblItems;
  private TicketViewerTable ticketViewerTable;

  // detail view
  private JLabel lblItemDetails;
  private JScrollPane spDetailTable;
  private JTable tableDetail;

  private JButton btnAddMenuItem;
  private JButton btnEditMenuItem;
  private JButton btnRemoveMenuItem;

  public MenuItemSetForm() throws Exception {
    this(new MenuItemSet());
  }

  public MenuItemSetForm(MenuItemSet menuItemSet) {
    this.menuItemSet = menuItemSet;

    initComponents();
    initData();
    initActions();

    add(tabbedPane);
    setBean(menuItemSet);
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
    if (actionCommand.equals(POSConstants.MENU_ITEM_SET_EDITOR_ADD_ITEM)) {
      addMenuItem();
    } else if (actionCommand.equals(POSConstants.MENU_ITEM_SET_EDITOR_EDIT_ITEM)) {
      editMenuItem();
    } else if (actionCommand.equals(POSConstants.MENU_ITEM_SET_EDITOR_REMOVE_ITEM)) {
      removeMenuItem();
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
    if (menuItemSet.getPrice() != null) {
      tfPrice.setText(String.valueOf(menuItemSet.getPrice()));
    }
    if (menuItemSet.getDiscountRate() != null) {
      tfDiscountRate.setText(String.valueOf(menuItemSet.getDiscountRate()));
    }
    chkVisible.setSelected(menuItemSet.getVisible());
    chkShowTextWithImage.setSelected(menuItemSet.getShowImageOnly());
    if (menuItemSet.getImage() != null) {
      ImageIcon imageIcon =
          new ImageIcon(new ImageIcon(menuItemSet.getImage()).getImage().getScaledInstance(60, 60,
              Image.SCALE_SMOOTH));
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
    menuItem.setShowImageOnly(chkShowTextWithImage.isSelected());

    try {
      menuItem.setDiscountRate(Double.parseDouble(tfDiscountRate.getText()));
    } catch (Exception x) {
      // TODO: 解析错误处理
    }

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
      return POSConstants.NEW_MENU_ITEM_SET;
    }
    return POSConstants.EDIT_MENU_ITEM_SET;
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

  private void addMenuItem() {}

  private void editMenuItem() {
    MenuItemDialog dialog = new MenuItemDialog((Dialog) this.getTopLevelAncestor());

    dialog.setSize(1024, 768);
    dialog.open();

    if (!dialog.isCanceled()) {
      List<MenuItem> selectedMenuItems = dialog.getSelectedMenuItems();

      // convert list of MenuItem to list of SetItem
      List<SetItem> selectedSetItems = new ArrayList<>(selectedMenuItems.size());
      for (MenuItem item : selectedMenuItems) {
        selectedSetItems.add(new SetItem(item, menuItemSet));
      }
      menuItemSet.setItems(selectedSetItems);

      // set table model for the SetItem table
      menuItemSetTableModel.setMenuItemSet(menuItemSet);
    }
  }

  private void removeMenuItem() {}

  private void initComponents() {
    setLayout(new BorderLayout(0, 0));

    tabbedPane = new JTabbedPane();
    pnlMainEditor = new JPanel();
    pnlDetailEditor = new JPanel();
    shiftTable = new JTable();

    lblCode = new JLabel();
    lblCode.setHorizontalAlignment(SwingConstants.TRAILING);
    lblCode.setText(POSConstants.EDITOR_CODE);
    tfCode = new FixedLengthTextField();
    tfCode.setDocument(new FixedLengthDocument(30));

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

    chkVisible = new JCheckBox();
    chkVisible.setText(POSConstants.EDITOR_VISIBLE);
    chkVisible.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    chkVisible.setMargin(new java.awt.Insets(0, 0, 0, 0));

    lblImage = new JLabel();
    lblImage.setHorizontalAlignment(SwingConstants.TRAILING);
    lblImage.setText(POSConstants.EDITOR_IMAGE);
    lblImagePreview = new JLabel("");
    lblImagePreview.setHorizontalAlignment(JLabel.CENTER);
    lblImagePreview.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    lblImagePreview.setPreferredSize(new Dimension(60, 120));
    btnImageSelect = new JButton(POSConstants.EDITOR_OMIT);
    btnImageClear = new JButton(POSConstants.EDITOR_CLEAR);

    chkShowTextWithImage = new JCheckBox(POSConstants.EDITOR_IMAGE_ONLY);
    chkShowTextWithImage.setActionCommand("Show Text with Image");

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
    cbVirtualPrinter =
        new JComboBox<VirtualPrinter>(new DefaultComboBoxModel<VirtualPrinter>(VirtualPrinterDAO
            .getInstance().findAll().toArray(new VirtualPrinter[0])));

    lblCategory = new JLabel();
    lblCategory.setHorizontalAlignment(SwingConstants.TRAILING);
    lblCategory.setText(POSConstants.EDITOR_CATEGORY);
    cbGroup = new JComboBox<>();

    lblGroup = new JLabel();
    lblGroup.setHorizontalAlignment(SwingConstants.TRAILING);
    lblGroup.setText(POSConstants.EDITOR_GROUP);
    cbCategory = new JComboBox<>();

    // layout for the general tab
    tabbedPane.addTab(POSConstants.GENERAL, pnlMainEditor);
    pnlMainEditor.setLayout(new MigLayout("", "[104px][100px,grow][][49px]",
        "[19px][][25px][][19px][][][][25px][][15px]"));

    // add components into container
    pnlMainEditor.add(lblCode, "cell 0 0, alignx left, aligny center");
    pnlMainEditor.add(tfCode, "cell 1 0 3 1, growx, aligny top");

    pnlMainEditor.add(lblName, "cell 0 1, alignx left, aligny center");
    pnlMainEditor.add(tfName, "cell 1 1 3 1, growx, aligny top");

    pnlMainEditor.add(lblBarcode, "cell 0 2, alignx leading");
    pnlMainEditor.add(tfBarcode, "cell 1 2 3 1, growx");

    pnlMainEditor.add(lblCategory, "cell 0 3, alignx leading");
    pnlMainEditor.add(cbCategory, "cell 1 3 3 1, growx");

    pnlMainEditor.add(lblGroup, "cell 0 4, alignx leading");
    pnlMainEditor.add(cbGroup, "cell 1 4 3 1, growx");

    pnlMainEditor.add(lblImage, "cell 0 5, aligny center");
    pnlMainEditor.add(lblImagePreview, "cell 1 5, grow");
    pnlMainEditor.add(btnImageSelect, "cell 2 5");
    pnlMainEditor.add(btnImageClear, "cell 3 5");

    pnlMainEditor.add(chkShowTextWithImage, "cell 1 6 3 1");

    pnlMainEditor.add(lblVirtualPrinter, "cell 0 7");
    pnlMainEditor.add(cbVirtualPrinter, "cell 1 7, growx");

    pnlMainEditor.add(chkVisible, "cell 1 8, alignx left, aligny top");

    // layout for the details tab
    tabbedPane.addTab(POSConstants.MENU_ITEM_SET_EDITOR_TAB_DETAIL, pnlDetailEditor);
    pnlDetailEditor.setLayout(new MigLayout("", "[]", "[][][]"));

    spDetailTable = new JScrollPane();
    tableDetail = new JTable();
    menuItemSetTableModel = new MenuItemSetTableModel(tableDetail);
    tableDetail.setModel(menuItemSetTableModel);
    spDetailTable.setViewportView(tableDetail);

    // add components into container
    lblItemDetails = new JLabel();
    lblItemDetails.setHorizontalAlignment(SwingConstants.CENTER);
    lblItemDetails.setText(POSConstants.MENU_ITEM_SET_EDITOR_ITEMS);

    // btnAddMenuItem = new JButton(POSConstants.MENU_ITEM_SET_EDITOR_ADD_ITEM);
    // btnAddMenuItem.addActionListener(this);
    btnEditMenuItem = new JButton(POSConstants.MENU_ITEM_SET_EDITOR_EDIT_ITEM);
    btnEditMenuItem.addActionListener(this);
    // btnRemoveMenuItem = new JButton(POSConstants.MENU_ITEM_SET_EDITOR_REMOVE_ITEM);
    // btnRemoveMenuItem.addActionListener(this);

    JPanel pnlButtonGroup = new JPanel();
    pnlButtonGroup.setLayout(new MigLayout("", "[][][]", "[]"));
    // pnlButtonGroup.add(btnAddMenuItem, "cell 0 0");
    pnlButtonGroup.add(btnEditMenuItem, "cell 0 0 3 1");
    // pnlButtonGroup.add(btnRemoveMenuItem, "cell 2 0");

    pnlDetailEditor.add(lblItemDetails, "cell 0 0, alignx center, aligny center");
    pnlDetailEditor.add(spDetailTable, "cell 0 1, growx, aligny top");
    pnlDetailEditor.add(pnlButtonGroup, "cell 0 2, alignx right, aligny top");

    tabbedPane.addChangeListener(this);
  }

  private void initData() {
    MenuGroupDAO groupDAO = MenuGroupDAO.getInstance();
    List<MenuGroup> groups = groupDAO.findAll();
    cbGroup.setModel(new ComboBoxModel(groups));

    MenuCategoryDAO categoryDAO = MenuCategoryDAO.getInstance();
    List<MenuCategory> categories = categoryDAO.findAll();
    cbCategory.setModel(new ComboBoxModel(categories));
  }

  private void initActions() {
    btnImageSelect.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doSelectImageFile();
      }
    });

    btnImageClear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doClearImage();
      }

    });
  }

  private void doSelectImageFile() {
    // TODO Auto-generated method stub

  }

  private void doClearImage() {
    // TODO Auto-generated method stub

  }
}
