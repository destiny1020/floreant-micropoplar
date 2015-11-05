/*
 * FoodItemEditor.java
 *
 * Created on August 2, 2006, 10:34 PM
 */

package com.floreantpos.ui.model;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;

import com.floreantpos.POSConstants;
import com.floreantpos.extension.InventoryPlugin;
import com.floreantpos.main.Application;
import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.VirtualPrinter;
import com.floreantpos.model.dao.MenuGroupDAO;
import com.floreantpos.model.dao.MenuItemDAO;
import com.floreantpos.model.dao.VirtualPrinterDAO;
import com.floreantpos.swing.ComboBoxModel;
import com.floreantpos.swing.DoubleTextField;
import com.floreantpos.swing.FixedLengthDocument;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.swing.IUpdatebleView;
import com.floreantpos.swing.MessageDialog;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.util.POSUtil;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author MShahriar
 */
public class MenuItemForm extends BeanEditor<MenuItem>implements ChangeListener {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JButton btnNewGroup;
  private JComboBox<MenuGroup> cbGroup;
  private JCheckBox chkVisible;
  private JLabel lblName;
  private JLabel lblGroupName;
  private JPanel pnlMainEditor;
  private JTabbedPane tabbedPane;
  private FixedLengthTextField tfName;
  private JLabel lblPrice;
  private DoubleTextField tfPrice;
  private JLabel lblKitchenPrinter;
  private JComboBox<VirtualPrinter> cbPrinter;
  private JLabel lblBarcode;
  private FixedLengthTextField tfBarcode;

  private JLabel lblCode;
  private FixedLengthTextField tfCode;

  public MenuItemForm() throws Exception {
    this(new MenuItem());
  }

  protected void doSelectImageFile() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setMultiSelectionEnabled(false);
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    int option = fileChooser.showOpenDialog(null);

    if (option == JFileChooser.APPROVE_OPTION) {
      File imageFile = fileChooser.getSelectedFile();
      try {
        byte[] itemImage = FileUtils.readFileToByteArray(imageFile);
        int imageSize = itemImage.length / 1024;

        if (imageSize > 50) {
          POSMessageDialog.showMessage(POSConstants.MENU_ITEM_IMAGE_TOO_LARGE_ERROR);
          itemImage = null;
          return;
        }

        MenuItem menuItem = getBean();
        menuItem.setImage(itemImage);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  protected void doClearImage() {
    MenuItem menuItem = getBean();
    menuItem.setImage(null);
  }

  public MenuItemForm(MenuItem menuItem) throws Exception {
    initComponents();
    initData();
    initActions();

    setBean(menuItem);

    addRecepieExtension();
  }

  public void addRecepieExtension() {
    InventoryPlugin plugin = Application.getPluginManager().getPlugin(InventoryPlugin.class);
    if (plugin == null) {
      return;
    }

    plugin.addRecepieView(tabbedPane);
  }

  private void initActions() {
    btnNewGroup.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doCreateNewGroup(evt);
      }
    });
  }

  @SuppressWarnings("unchecked")
  private void initData() {
    MenuGroupDAO groupDAO = MenuGroupDAO.getInstance();
    List<MenuGroup> groups = groupDAO.findAll();
    cbGroup.setModel(new ComboBoxModel(groups));
  }

  private void initComponents() {
    setLayout(new BorderLayout(0, 0));

    tabbedPane = new JTabbedPane();
    pnlMainEditor = new JPanel();

    pnlMainEditor.setLayout(new MigLayout("", "[104px][100px,grow][][49px]",
        "[19px][][25px][][19px][19px][][][25px][][15px]"));

    lblCode = new JLabel();
    lblCode.setHorizontalAlignment(SwingConstants.TRAILING);
    lblCode.setText(POSConstants.EDITOR_CODE);
    tfCode = new FixedLengthTextField();
    tfCode.setDocument(new FixedLengthDocument(30));
    pnlMainEditor.add(lblCode, "cell 0 0, alignx left, aligny center");
    pnlMainEditor.add(tfCode, "cell 1 0 3 1, growx, aligny top");

    lblName = new JLabel();
    lblName.setHorizontalAlignment(SwingConstants.TRAILING);
    lblName.setText(POSConstants.EDITOR_NAME);
    tfName = new FixedLengthTextField();
    tfName.setDocument(new FixedLengthDocument(30));
    pnlMainEditor.add(lblName, "cell 0 1, alignx left, aligny center");
    pnlMainEditor.add(tfName, "cell 1 1 3 1, growx, aligny top");

    lblBarcode = new JLabel(POSConstants.MENU_ITEM_EDITOR_BARCODE);
    tfBarcode = new FixedLengthTextField(120);
    pnlMainEditor.add(lblBarcode, "cell 0 2, alignx leading");
    pnlMainEditor.add(tfBarcode, "cell 1 2 3 1, growx");

    lblGroupName = new JLabel();
    lblGroupName.setHorizontalAlignment(SwingConstants.TRAILING);
    lblGroupName.setText(POSConstants.MENU_ITEM_EDITOR_GROUP);
    cbGroup = new JComboBox<>();
    btnNewGroup = new JButton();
    btnNewGroup.setText(POSConstants.EDITOR_OMIT);
    pnlMainEditor.add(lblGroupName, "cell 0 3, alignx left, aligny center");
    pnlMainEditor.add(cbGroup, "cell 1 3, growx, aligny top");
    pnlMainEditor.add(btnNewGroup, "cell 3 3, growx, aligny top");

    lblPrice = new JLabel(POSConstants.EDITOR_PRICE);
    lblPrice.setHorizontalAlignment(SwingConstants.TRAILING);
    tfPrice = new DoubleTextField();
    tfPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    pnlMainEditor.add(lblPrice, "cell 0 4, alignx left, aligny center");
    pnlMainEditor.add(tfPrice, "cell 1 4, growx, aligny top");

    lblKitchenPrinter = new JLabel(POSConstants.EDITOR_V_PRINTER);
    cbPrinter = new JComboBox<VirtualPrinter>(new DefaultComboBoxModel<VirtualPrinter>(
        VirtualPrinterDAO.getInstance().findAll().toArray(new VirtualPrinter[0])));
    pnlMainEditor.add(lblKitchenPrinter, "cell 0 5");
    pnlMainEditor.add(cbPrinter, "cell 1 5, growx");

    chkVisible = new JCheckBox();
    chkVisible.setText(com.floreantpos.POSConstants.VISIBLE);
    chkVisible.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    chkVisible.setMargin(new java.awt.Insets(0, 0, 0, 0));
    pnlMainEditor.add(chkVisible, "cell 1 6, alignx left, aligny top");

    tabbedPane.addTab(POSConstants.GENERAL, pnlMainEditor);
    tabbedPane.addChangeListener(this);

    add(tabbedPane);
  }

  private void doCreateNewGroup(java.awt.event.ActionEvent evt) {
    MenuGroupForm editor = new MenuGroupForm();
    BeanEditorDialog dialog = new BeanEditorDialog(editor, getParentFrame(), true);
    dialog.open();
    if (!dialog.isCanceled()) {
      MenuGroup foodGroup = editor.getBean();
      ComboBoxModel model = (ComboBoxModel) cbGroup.getModel();
      model.addElement(foodGroup);
      model.setSelectedItem(foodGroup);
    }
  }

  @Override
  public boolean save() {
    try {
      if (!updateModel())
        return false;

      MenuItem menuItem = getBean();
      MenuItemDAO menuItemDAO = MenuItemDAO.getInstance();
      menuItemDAO.saveOrUpdate(menuItem);
    } catch (Exception e) {
      MessageDialog.showError(this, com.floreantpos.POSConstants.ERROR_MESSAGE, e);
      return false;
    }
    return true;
  }

  @Override
  protected void updateView() {
    MenuItem menuItem = getBean();

    if (menuItem.getId() != null) {
      MenuItemDAO dao = new MenuItemDAO();
      Session session = dao.getSession();
      menuItem = (MenuItem) session.merge(menuItem);
      session.close();
    }

    tfName.setText(menuItem.getName());
    tfBarcode.setText(menuItem.getBarcode());
    tfPrice.setText(String.valueOf(menuItem.getPrice()));
    chkVisible.setSelected(menuItem.isVisible());

    if (menuItem.getId() == null) {
      cbGroup.setSelectedIndex(0);
    } else {
      cbGroup.setSelectedItem(menuItem.getGroup());
    }

    cbPrinter.setSelectedItem(menuItem.getVirtualPrinter());
  }

  @Override
  protected boolean updateModel() {
    String itemName = tfName.getText();
    if (POSUtil.isBlankOrNull(itemName)) {
      MessageDialog.showError(this, com.floreantpos.POSConstants.NAME_REQUIRED);
      return false;
    }

    MenuItem menuItem = getBean();
    menuItem.setName(itemName);
    menuItem.setBarcode(tfBarcode.getText());
    menuItem.setGroup((MenuGroup) cbGroup.getSelectedItem());
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

    menuItem.setVirtualPrinter((VirtualPrinter) cbPrinter.getSelectedItem());

    return true;
  }

  @Override
  public String getDisplayText() {
    MenuItem foodItem = getBean();
    if (foodItem.getId() == null) {
      return com.floreantpos.POSConstants.NEW_MENU_ITEM;
    }
    return com.floreantpos.POSConstants.EDIT_MENU_ITEM;
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    Component selectedComponent = tabbedPane.getSelectedComponent();
    if (!(selectedComponent instanceof IUpdatebleView)) {
      return;
    }

    IUpdatebleView view = (IUpdatebleView) selectedComponent;

    MenuItem menuItem = getBean();
    view.initView(menuItem);
  }

}
