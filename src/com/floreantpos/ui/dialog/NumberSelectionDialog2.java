package com.floreantpos.ui.dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.floreantpos.IconFactory;
import com.floreantpos.POSConstants;
import com.floreantpos.main.Application;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.TitlePanel;

import net.miginfocom.swing.MigLayout;

public class NumberSelectionDialog2 extends POSDialog implements ActionListener {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int defaultValue;
  private String defaultValueStr = "0";

  private TitlePanel titlePanel;
  private JTextField tfNumber;

  private boolean floatingPoint;
  private PosButton btnOK;
  private PosButton btnCancel;

  public NumberSelectionDialog2() {
    this(Application.getPosWindow());
  }

  public NumberSelectionDialog2(Frame parent) {
    super(parent, true);
    init();
  }

  public NumberSelectionDialog2(Dialog parent) {
    super(parent, true);
    init();
  }

  private void init() {
    setResizable(true);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        if (tfNumber.getText().trim().equals(defaultValueStr)
            || StringUtils.isBlank(tfNumber.getText())) {
          setCanceled(true);
        }
      }
    });

    Container contentPane = getContentPane();

    MigLayout layout = new MigLayout("fillx", "[60px,fill][60px,fill][60px,fill]", "[][][][][]");
    contentPane.setLayout(layout);

    titlePanel = new TitlePanel();
    contentPane.add(titlePanel, "spanx ,growy,height 60,wrap");

    tfNumber = new JTextField();
    tfNumber.setText(String.valueOf(defaultValue));
    tfNumber.setFont(tfNumber.getFont().deriveFont(Font.BOLD, 24));
    // tfNumber.setEditable(false);
    tfNumber.setFocusable(true);
    tfNumber.requestFocus();
    tfNumber.setBackground(Color.WHITE);
    // tfNumber.setHorizontalAlignment(JTextField.RIGHT);
    contentPane.add(tfNumber, "span 2, grow");

    // let number field respond to ENTER/ESC input
    tfNumber.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
          btnOK.doClick();
        }

        if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
          btnCancel.doClick();
        }
      }
    });

    PosButton posButton = new PosButton(POSConstants.CLEAR_ALL);
    posButton.setFocusable(false);
    posButton.setMinimumSize(new Dimension(25, 23));
    posButton.addActionListener(this);
    contentPane.add(posButton, "growy,height 55,wrap");

    String[][] numbers = {{"7", "8", "9"}, {"4", "5", "6"}, {"1", "2", "3"}, {".", "0", "清除"}};
    String[][] iconNames =
        new String[][] {{"7_32.png", "8_32.png", "9_32.png"}, {"4_32.png", "5_32.png", "6_32.png"},
            {"1_32.png", "2_32.png", "3_32.png"}, {"dot_32.png", "0_32.png", "clear_32.png"}};

    for (int i = 0; i < numbers.length; i++) {
      for (int j = 0; j < numbers[i].length; j++) {
        posButton = new PosButton();
        posButton.setFocusable(false);
        ImageIcon icon = IconFactory.getIcon(iconNames[i][j]);
        String buttonText = String.valueOf(numbers[i][j]);

        if (icon == null) {
          posButton.setText(buttonText);
        } else {
          posButton.setIcon(icon);
          if (POSConstants.CLEAR.equals(buttonText)) {
            posButton.setText(buttonText);
          }
        }

        posButton.setActionCommand(buttonText);
        posButton.addActionListener(this);
        String constraints = "grow, height 55";
        if (j == numbers[i].length - 1) {
          constraints += ", wrap";
        }
        contentPane.add(posButton, constraints);
      }
    }
    contentPane.add(new JSeparator(), "newline,spanx ,growy,gapy 20");

    btnOK = new PosButton(POSConstants.OK);
    btnOK.setFocusable(false);
    btnOK.addActionListener(this);
    contentPane.add(btnOK, "skip 1,grow");

    btnCancel = new PosButton(POSConstants.CANCEL);
    btnCancel.setFocusable(false);
    btnCancel.addActionListener(this);
    contentPane.add(btnCancel, "grow");
  }

  private void doOk() {
    if (!validate(tfNumber.getText())) {
      POSMessageDialog.showError(this, POSConstants.INVALID_NUMBER);
      return;
    }
    setCanceled(false);
    dispose();
  }

  private void doCancel() {
    setCanceled(true);
    dispose();
  }

  private void doClearAll() {
    tfNumber.setText(String.valueOf(defaultValue));
  }

  private void doClear() {
    String s = tfNumber.getText();
    if (s.length() > 1) {
      s = s.substring(0, s.length() - 1);
    } else {
      s = String.valueOf(defaultValue);
    }
    tfNumber.setText(s);
  }

  private void doInsertNumber(String number) {
    String s = tfNumber.getText();
    double d = 0;

    try {
      d = Double.parseDouble(s);
    } catch (Exception x) {
    }

    if (d == 0) {
      tfNumber.setText(number);
      return;
    }

    s = s + number;
    if (!validate(s)) {
      POSMessageDialog.showError(this, POSConstants.INVALID_NUMBER);
      return;
    }
    tfNumber.setText(s);
  }

  private void doInsertDot() {
    // if (isFloatingPoint() && tfNumber.getText().indexOf('.') < 0) {
    String string = tfNumber.getText() + ".";
    if (!validate(string)) {
      POSMessageDialog.showError(this, POSConstants.INVALID_NUMBER);
      return;
    }
    tfNumber.setText(string);
    // }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();

    if (POSConstants.CANCEL.equalsIgnoreCase(actionCommand)) {
      doCancel();
    } else if (POSConstants.OK.equalsIgnoreCase(actionCommand)) {
      doOk();
    } else if (actionCommand.equals(POSConstants.CLEAR_ALL)) {
      doClearAll();
    } else if (actionCommand.equals(POSConstants.CLEAR)) {
      doClear();
    } else if (actionCommand.equals(".")) {
      doInsertDot();
    } else {
      doInsertNumber(actionCommand);
    }

  }

  private boolean validate(String str) {
    if (isFloatingPoint()) {
      try {
        Double.parseDouble(str);
      } catch (Exception x) {
        return false;
      }
    } else {
      // input is for ticket id or item id
      // try {
      // Integer.parseInt(str);
      // } catch (Exception x) {
      // return false;
      // }
      return true;
    }
    return true;
  }

  @Override
  public void setTitle(String title) {
    titlePanel.setTitle(title);

    super.setTitle(title);
  }

  public void setDialogTitle(String title) {
    super.setTitle(title);
  }

  public String getValueString() {
    return tfNumber.getText();
  }

  public double getValue() {
    return Double.parseDouble(tfNumber.getText());
  }

  public void setValue(double value) {
    if (value == 0) {
      tfNumber.setText(defaultValueStr);
    } else if (isFloatingPoint()) {
      tfNumber.setText(String.valueOf(value));
    } else {
      tfNumber.setText(String.valueOf((int) value));
    }
  }

  public boolean isFloatingPoint() {
    return floatingPoint;
  }

  public void setFloatingPoint(boolean decimalAllowed) {
    this.floatingPoint = decimalAllowed;
  }

  public int getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(int defaultValue) {
    this.defaultValue = defaultValue;
    tfNumber.setText(String.valueOf(defaultValue));
  }

  private void setInitNumber(String initProvidedValue) {
    tfNumber.setText(initProvidedValue);
  }

  /**
   * Return the input result as String.
   * 
   * @param title
   * @return
   */
  public static String takeStringInput(String title) {
    return takeStringInput(title, "");
  }

  /**
   * Return the input result as String. Give a default value for the number TF.
   * 
   * @param title
   * @param initProvidedValue
   * @return
   */
  public static String takeStringInput(String title, String initProvidedValue) {
    return takeStringInput(title, title, initProvidedValue);
  }

  /**
   * Return the input result as String. Give a default value for the number TF.
   * Also provide parameter for the top panel title.
   * 
   * @param title
   * @param panelTitle
   * @param initProvidedValue
   * @return
   */
  public static String takeStringInput(String title, String panelTitle, String initProvidedValue) {
    NumberSelectionDialog2 dialog = new NumberSelectionDialog2();
    dialog.setTitle(panelTitle);
    dialog.setDialogTitle(title);

    if (StringUtils.isNotBlank(initProvidedValue)) {
      dialog.setInitNumber(initProvidedValue);
    }

    dialog.pack();
    dialog.setOptionSize(1.5);
    dialog.open();

    if (dialog.isCanceled()) {
      return "";
    }

    return dialog.getValueString();
  }

  public static int takeIntInput(String title) {
    NumberSelectionDialog2 dialog = new NumberSelectionDialog2();
    dialog.setTitle(title);
    dialog.pack();
    dialog.setOptionSize(1.5);
    dialog.open();

    if (dialog.isCanceled()) {
      return -1;
    }

    return (int) dialog.getValue();
  }

  private void setOptionSize(double d) {
    // expand for more space
    Dimension currentSize = getSize();
    setSize((int) (currentSize.width * d), currentSize.height);
  }

  public static double takeDoubleInput(String title, String dialogTitle, double initialAmount) {
    NumberSelectionDialog2 dialog = new NumberSelectionDialog2();
    dialog.setFloatingPoint(true);
    dialog.setValue(initialAmount);
    dialog.setTitle(title);
    dialog.setDialogTitle(dialogTitle);
    dialog.pack();
    dialog.open();

    if (dialog.isCanceled()) {
      return Double.NaN;
    }

    return dialog.getValue();
  }

  public static double show(Component parent, String title, double initialAmount) {
    NumberSelectionDialog2 dialog2 = new NumberSelectionDialog2();
    dialog2.setFloatingPoint(true);
    dialog2.setTitle(title);
    dialog2.pack();
    dialog2.setLocationRelativeTo(parent);
    dialog2.setValue(initialAmount);
    dialog2.setVisible(true);

    if (dialog2.isCanceled()) {
      return Double.NaN;
    }

    return dialog2.getValue();
  }
}
