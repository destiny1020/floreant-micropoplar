package com.micropoplar.pos.ui.model;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.VirtualPrinter;
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
import com.micropoplar.pos.bo.ui.dialog.MenuItemDialog;
import com.micropoplar.pos.model.MenuItemSet;
import com.micropoplar.pos.model.SetItem;
import com.micropoplar.pos.model.dao.MenuItemSetDAO;
import com.micropoplar.pos.ui.set.MenuItemSetTableModel;

import net.miginfocom.swing.MigLayout;

public class MenuItemSetForm extends BeanEditor<MenuItemSet>
    implements ActionListener, ChangeListener {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // data
  private MenuItemSet menuItemSet;
  private MenuItemSetTableModel menuItemSetTableModel;

  // components --- under general tab
  private JTabbedPane tabbedPane;

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
  private JLabel lblPriceHint;

  private JCheckBox chkVisible;

  private JLabel lblVirtualPrinter;
  private JComboBox<VirtualPrinter> cbVirtualPrinter;

  private JLabel lblGroup;
  private JComboBox<MenuGroup> cbGroup;

  // detail view
  private JLabel lblItemDetails;
  private JScrollPane spDetailTable;
  private JTable tableDetail;

  private JButton btnEditMenuItem;

  private JLabel lblTotalPrice;

  private boolean fromEditDialog;

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

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void stateChanged(ChangeEvent e) {
    Component selectedComponent = tabbedPane.getSelectedComponent();
    if (!(selectedComponent instanceof IUpdatebleView)) {
      return;
    }

    IUpdatebleView view = (IUpdatebleView) selectedComponent;

    MenuItemSet menuItem = getBean();
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

      MenuItemSet menuItemSet = getBean();
      MenuItemSetDAO menuItemSetDAO = MenuItemSetDAO.getInstance();
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

    tfCode.setText(menuItemSet.getCode());
    tfName.setText(menuItemSet.getName());
    tfBarcode.setText(menuItemSet.getBarcode());
    if (menuItemSet.getPrice() != null) {
      tfPrice.setText(String.valueOf(menuItemSet.getPrice()));
      lblTotalPrice.setVisible(true);
      lblTotalPrice.setText(POSConstants.MENU_ITEM_SET_EDITOR_TOTAL_AMOUNT + POSConstants.COLON
          + "   " + menuItemSet.getPrice());
    }

    chkVisible.setSelected(menuItemSet.isVisible());

    if (menuItemSet.getId() == null) {
      cbGroup.setSelectedIndex(0);
    } else {
      cbGroup.setSelectedItem(menuItemSet.getGroup());
    }

    cbVirtualPrinter.setSelectedItem(menuItemSet.getVirtualPrinter());

    // set items init
    menuItemSetTableModel.setMenuItemSet(menuItemSet);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  protected boolean updateModel() throws IllegalModelStateException {
    String code = tfCode.getText();
    if (StringUtils.isBlank(code)) {
      BOMessageDialog.showError(BackOfficeWindow.getInstance(), POSConstants.ERROR_CODE_REQUIRED);
      return false;
    }

    String itemName = tfName.getText();
    if (StringUtils.isBlank(itemName)) {
      BOMessageDialog.showError(BackOfficeWindow.getInstance(), POSConstants.NAME_REQUIRED);
      return false;
    }

    MenuGroup group = (MenuGroup) cbGroup.getSelectedItem();
    if (group == null) {
      BOMessageDialog.showError(BackOfficeWindow.getInstance(),
          POSConstants.MENU_ITEM_SET_EDITOR_GROUP_NEEDED);
      return false;
    }

    MenuItemSet menuItem = getBean();
    menuItem.setCode(code);
    menuItem.setName(itemName);
    menuItem.setBarcode(tfBarcode.getText());
    menuItem.setGroup(group);

    menuItem.setPrice(Double.valueOf(tfPrice.getText()));
    menuItem.setVisible(chkVisible.isSelected());

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
    MenuItemSet menuItemSet = getBean();
    if (menuItemSet.getId() == null) {
      return POSConstants.NEW_MENU_ITEM_SET;
    }
    return POSConstants.EDIT_MENU_ITEM_SET;
  }

  private void addMenuItem() {}

  private void editMenuItem() {
    MenuItemDialog dialog = new MenuItemDialog((Dialog) this.getTopLevelAncestor(), menuItemSet);

    // init the selecte menu items if necessary
    if (menuItemSetTableModel.getItemSet() != null
        && menuItemSetTableModel.getItemSet().getItems() != null) {
      dialog.initSelectedMenuItems(menuItemSetTableModel.getItemSet().getItems());
    }

    dialog.setSize(960, 680);
    dialog.open();

    if (!dialog.isCanceled()) {
      List<SetItem> selectedMenuItems = dialog.getSelectedSetItems();
      menuItemSet.setItems(selectedMenuItems);

      // set table model for the SetItem table
      fromEditDialog = true;
      menuItemSetTableModel.setMenuItemSet(menuItemSet);
      fromEditDialog = false;
    }
  }

  private void removeMenuItem() {}

  private void initComponents() {
    setLayout(new BorderLayout(0, 0));

    tabbedPane = new JTabbedPane();
    pnlMainEditor = new JPanel();
    pnlDetailEditor = new JPanel();

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
    lblPrice.setText(POSConstants.MENU_ITEM_SET_EDITOR_TOTAL_AMOUNT);
    tfPrice = new DoubleTextField();
    tfPrice.setHorizontalAlignment(SwingConstants.LEADING);
    tfPrice.setFocusable(false);
    lblPriceHint = new JLabel(POSConstants.EDITOR_PRICE_HINT);

    chkVisible = new JCheckBox();
    chkVisible.setText(POSConstants.EDITOR_VISIBLE);
    chkVisible.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    chkVisible.setMargin(new java.awt.Insets(0, 0, 0, 0));

    lblVirtualPrinter = new JLabel();
    lblVirtualPrinter.setHorizontalAlignment(SwingConstants.TRAILING);
    lblVirtualPrinter.setText(POSConstants.EDITOR_V_PRINTER);
    cbVirtualPrinter = new JComboBox<VirtualPrinter>(new DefaultComboBoxModel<VirtualPrinter>(
        VirtualPrinterDAO.getInstance().findAll().toArray(new VirtualPrinter[0])));

    lblGroup = new JLabel();
    lblGroup.setHorizontalAlignment(SwingConstants.TRAILING);
    lblGroup.setText(POSConstants.EDITOR_GROUP);
    cbGroup = new JComboBox<>();

    // layout for the general tab
    tabbedPane.addTab(POSConstants.GENERAL, pnlMainEditor);
    pnlMainEditor.setLayout(new MigLayout("", "[104px][100px,grow][][49px]",
        "[19px][][25px][][19px][][][][][][25px][][15px]"));

    // add components into container
    pnlMainEditor.add(lblCode, "cell 0 0, alignx left, aligny center");
    pnlMainEditor.add(tfCode, "cell 1 0 3 1, growx, aligny top");

    pnlMainEditor.add(lblName, "cell 0 1, alignx left, aligny center");
    pnlMainEditor.add(tfName, "cell 1 1 3 1, growx, aligny top");

    pnlMainEditor.add(lblBarcode, "cell 0 2, alignx leading");
    pnlMainEditor.add(tfBarcode, "cell 1 2 3 1, growx");

    pnlMainEditor.add(lblGroup, "cell 0 3, alignx leading");
    pnlMainEditor.add(cbGroup, "cell 1 3 3 1, growx");

    pnlMainEditor.add(lblPrice, "cell 0 4, alignx leading");
    pnlMainEditor.add(tfPrice, "cell 1 4 2 1, growx");
    pnlMainEditor.add(lblPriceHint, "cell 3 4, growx");

    pnlMainEditor.add(lblVirtualPrinter, "cell 0 5");
    pnlMainEditor.add(cbVirtualPrinter, "cell 1 5 3 1, growx");

    pnlMainEditor.add(chkVisible, "cell 1 6, alignx left, aligny top");

    // layout for the details tab
    tabbedPane.addTab(POSConstants.MENU_ITEM_SET_EDITOR_TAB_DETAIL, pnlDetailEditor);
    pnlDetailEditor.setLayout(new MigLayout("", "[]", "[][][][]"));

    spDetailTable = new JScrollPane();
    tableDetail = new JTable();
    menuItemSetTableModel = new MenuItemSetTableModel(tableDetail);
    tableDetail.setModel(menuItemSetTableModel);
    spDetailTable.setViewportView(tableDetail);
    menuItemSetTableModel.setItemCountEditor();

    // add components into container
    lblItemDetails = new JLabel();
    lblItemDetails.setHorizontalAlignment(SwingConstants.CENTER);
    lblItemDetails.setText(POSConstants.MENU_ITEM_SET_EDITOR_ITEMS);

    btnEditMenuItem = new JButton(POSConstants.MENU_ITEM_SET_EDITOR_EDIT_ITEM);
    btnEditMenuItem.addActionListener(this);

    JPanel pnlButtonGroup = new JPanel();
    pnlButtonGroup.setLayout(new MigLayout("", "[][][]", "[]"));
    pnlButtonGroup.add(btnEditMenuItem, "cell 0 0 3 1");

    lblTotalPrice = new JLabel();
    lblTotalPrice.setText(POSConstants.MENU_ITEM_SET_EDITOR_TOTAL_AMOUNT);
    lblTotalPrice.setVisible(false);

    pnlDetailEditor.add(lblItemDetails, "cell 0 0, alignx center, aligny center");
    pnlDetailEditor.add(spDetailTable, "cell 0 1, growx, aligny top");
    pnlDetailEditor.add(lblTotalPrice, "cell 0 2, alignx right, aligny center");
    pnlDetailEditor.add(pnlButtonGroup, "cell 0 3, alignx right, aligny top");

    tabbedPane.addChangeListener(this);
  }

  @SuppressWarnings("unchecked")
  private void initData() {
    MenuGroupDAO groupDAO = MenuGroupDAO.getInstance();
    List<MenuGroup> groups = groupDAO.findAll();
    cbGroup.setModel(new ComboBoxModel(groups));
  }

  private void initActions() {
    menuItemSetTableModel.addTableModelListener(new TableModelListener() {

      @Override
      public void tableChanged(TableModelEvent e) {
        boolean changed = false;
        // workaround for the data not sync problem
        // update the count number from the cache
        if (menuItemSet.getItems() != null && menuItemSet.getItems().size() > 0) {
          for (int idx = 0; idx < menuItemSet.getItems().size(); idx++) {
            SetItem setItem = MenuItemSetTableModel.getCacheCount(menuItemSet.getId(), idx);
            if (setItem != null) {
              menuItemSet.getItems().set(idx, setItem);
              changed = true;
            }
          }
        }

        if (changed || fromEditDialog) {
          // update the total price and member price
          menuItemSet.updatePrices();

          // For DEBUG use
          //        for (SetItem item : menuItemSet.getItems()) {
          //          System.out.println(String.format("count: %d ------ subtotal: %f", item.getItemCount(),
          //              item.getTotalAmount()));
          //        }

          // show the total price label
          if (menuItemSet.getItems() != null && menuItemSet.getItems().size() > 0) {
            lblTotalPrice.setVisible(true);
            lblTotalPrice.setText(POSConstants.MENU_ITEM_SET_EDITOR_TOTAL_AMOUNT
                + POSConstants.COLON + "   " + menuItemSet.getPrice());
            tfPrice.setText(String.valueOf(menuItemSet.getPrice()));
          } else {
            lblTotalPrice.setVisible(false);
            lblTotalPrice.setText(POSConstants.MENU_ITEM_SET_EDITOR_TOTAL_AMOUNT);
            tfPrice.setText("");
          }
        }
      }
    });
  }

}
