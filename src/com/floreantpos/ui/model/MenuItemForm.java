/*
 * FoodItemEditor.java
 *
 * Created on August 2, 2006, 10:34 PM
 */

package com.floreantpos.ui.model;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
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

        ImageIcon imageIcon = new ImageIcon(
            new ImageIcon(itemImage).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        lblImagePreview.setIcon(imageIcon);

        MenuItem menuItem = (MenuItem) getBean();
        menuItem.setImage(itemImage);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  protected void doClearImage() {
    MenuItem menuItem = (MenuItem) getBean();
    menuItem.setImage(null);
    lblImagePreview.setIcon(null);
  }

  public MenuItemForm(MenuItem menuItem) throws Exception {
    initComponents();

    tfName.setDocument(new FixedLengthDocument(30));

    MenuGroupDAO foodGroupDAO = new MenuGroupDAO();
    List<MenuGroup> foodGroups = foodGroupDAO.findAll();
    cbGroup.setModel(new ComboBoxModel(foodGroups));

    pnlMainEditor.setLayout(new MigLayout("", "[104px][100px,grow][][49px]",
        "[19px][][25px][][19px][19px][][][25px][][15px]"));

    lblBarcode = new JLabel(POSConstants.MENU_ITEM_EDITOR_BARCODE); //$NON-NLS-1$
    pnlMainEditor.add(lblBarcode, "cell 0 1,alignx leading");

    tfBarcode = new FixedLengthTextField(120);
    pnlMainEditor.add(tfBarcode, "cell 1 1,growx");

    pnlMainEditor.add(lblGroupName, "cell 0 2,alignx left,aligny center");

    JLabel lblImage = new JLabel(POSConstants.EDITOR_IMAGE);
    lblImage.setHorizontalAlignment(SwingConstants.TRAILING);
    pnlMainEditor.add(lblImage, "cell 0 6,aligny center");
    setLayout(new BorderLayout(0, 0));

    lblImagePreview = new JLabel("");
    lblImagePreview.setHorizontalAlignment(JLabel.CENTER);
    lblImagePreview.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
    lblImagePreview.setPreferredSize(new Dimension(60, 120));
    pnlMainEditor.add(lblImagePreview, "cell 1 6,grow");

    JButton btnSelectImage = new JButton(POSConstants.EDITOR_OMIT);
    btnSelectImage.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doSelectImageFile();
      }
    });
    pnlMainEditor.add(btnSelectImage, "cell 2 6");

    btnClearImage = new JButton(POSConstants.EDITOR_CLEAR);
    btnClearImage.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doClearImage();
      }
    });
    pnlMainEditor.add(btnClearImage, "cell 3 6");

    cbShowTextWithImage = new JCheckBox(POSConstants.EDITOR_IMAGE_ONLY);
    cbShowTextWithImage.setActionCommand("Show Text with Image");
    pnlMainEditor.add(cbShowTextWithImage, "cell 1 7 3 1"); //$NON-NLS-1$
    pnlMainEditor.add(lblName, "cell 0 0,alignx left,aligny center"); //$NON-NLS-1$
    pnlMainEditor.add(tfName, "cell 1 0 3 1,growx,aligny top"); //$NON-NLS-1$
    pnlMainEditor.add(cbGroup, "cell 1 2,growx,aligny top"); //$NON-NLS-1$
    pnlMainEditor.add(btnNewGroup, "cell 3 2,growx,aligny top"); //$NON-NLS-1$
    pnlMainEditor.add(tfPrice, "cell 1 4,growx,aligny top"); //$NON-NLS-1$

    lblKitchenPrinter = new JLabel(POSConstants.EDITOR_V_PRINTER);
    pnlMainEditor.add(lblKitchenPrinter, "cell 0 9"); //$NON-NLS-1$

    cbPrinter = new JComboBox<VirtualPrinter>(new DefaultComboBoxModel<VirtualPrinter>(
        VirtualPrinterDAO.getInstance().findAll().toArray(new VirtualPrinter[0])));
    pnlMainEditor.add(cbPrinter, "cell 1 9,growx"); //$NON-NLS-1$
    pnlMainEditor.add(chkVisible, "cell 1 10,alignx left,aligny top"); //$NON-NLS-1$
    add(tabbedPane);

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

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {

    tabbedPane = new javax.swing.JTabbedPane();
    pnlMainEditor = new javax.swing.JPanel();
    lblName = new javax.swing.JLabel();
    lblName.setHorizontalAlignment(SwingConstants.TRAILING);
    tfName = new com.floreantpos.swing.FixedLengthTextField();
    lblGroupName = new javax.swing.JLabel();
    lblGroupName.setHorizontalAlignment(SwingConstants.TRAILING);
    cbGroup = new javax.swing.JComboBox();
    btnNewGroup = new javax.swing.JButton();
    tfPrice = new DoubleTextField();
    chkVisible = new javax.swing.JCheckBox();
    jScrollPane1 = new javax.swing.JScrollPane();
    jScrollPane2 = new javax.swing.JScrollPane();

    lblName.setText(POSConstants.EDITOR_NAME);
    lblGroupName.setText(POSConstants.MENU_ITEM_EDITOR_GROUP);

    btnNewGroup.setText(POSConstants.EDITOR_OMIT);
    btnNewGroup.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doCreateNewGroup(evt);
      }
    });

    tfPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

    chkVisible.setText(com.floreantpos.POSConstants.VISIBLE);
    chkVisible.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    chkVisible.setMargin(new java.awt.Insets(0, 0, 0, 0));

    tabbedPane.addTab(com.floreantpos.POSConstants.GENERAL, pnlMainEditor);

    tabbedPane.addChangeListener(this);
  }// </editor-fold>//GEN-END:initComponents

  private void doCreateNewGroup(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doCreateNewGroup
    MenuGroupForm editor = new MenuGroupForm();
    BeanEditorDialog dialog = new BeanEditorDialog(editor, getParentFrame(), true);
    dialog.open();
    if (!dialog.isCanceled()) {
      MenuGroup foodGroup = (MenuGroup) editor.getBean();
      ComboBoxModel model = (ComboBoxModel) cbGroup.getModel();
      model.addElement(foodGroup);
      model.setSelectedItem(foodGroup);
    }
  }// GEN-LAST:event_doCreateNewGroup

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnNewGroup;
  private javax.swing.JComboBox<MenuGroup> cbGroup;
  private javax.swing.JCheckBox chkVisible;
  private javax.swing.JLabel lblName;
  private javax.swing.JLabel lblGroupName;
  private javax.swing.JPanel pnlMainEditor;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTabbedPane tabbedPane;
  private com.floreantpos.swing.FixedLengthTextField tfName;
  private DoubleTextField tfPrice;
  private JLabel lblImagePreview;
  private JButton btnClearImage;
  private JCheckBox cbShowTextWithImage;
  private JLabel lblKitchenPrinter;
  private JComboBox<VirtualPrinter> cbPrinter;
  private JLabel lblBarcode;
  private FixedLengthTextField tfBarcode;

  @Override
  public boolean save() {
    try {
      if (!updateModel())
        return false;

      MenuItem menuItem = (MenuItem) getBean();
      MenuItemDAO menuItemDAO = new MenuItemDAO();
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
    cbShowTextWithImage.setSelected(menuItem.isShowImageOnly());
    if (menuItem.getImage() != null) {
      ImageIcon imageIcon = new ImageIcon(new ImageIcon(menuItem.getImage()).getImage()
          .getScaledInstance(60, 60, Image.SCALE_SMOOTH));
      lblImagePreview.setIcon(imageIcon);
    }

    if (menuItem.getId() == null) {
      cbGroup.setSelectedIndex(0);
    } else {
      cbGroup.setSelectedItem(menuItem.getCategory());
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
    menuItem.setShowImageOnly(cbShowTextWithImage.isSelected());

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

  public String getDisplayText() {
    MenuItem foodItem = (MenuItem) getBean();
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

    MenuItem menuItem = (MenuItem) getBean();
    view.initView(menuItem);
  }

}
