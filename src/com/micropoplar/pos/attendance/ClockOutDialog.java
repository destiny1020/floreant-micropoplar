package com.micropoplar.pos.attendance;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.lang3.StringUtils;

import com.floreantpos.POSConstants;
import com.floreantpos.swing.IntegerTextField;
import com.floreantpos.swing.PosButton;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.dialog.POSDialog;
import com.micropoplar.pos.ui.util.ControllerGenerator;

import net.miginfocom.swing.MigLayout;

public class ClockOutDialog extends POSDialog implements DocumentListener, FocusListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private TransparentPanel pnlLeft;

  private JLabel lblCashShould;
  private JLabel lblCashShouldContent;

  private JLabel lblCash100;
  private IntegerTextField tfCash100;

  private JLabel lblCash50;
  private IntegerTextField tfCash50;

  private JLabel lblCash20;
  private IntegerTextField tfCash20;

  private JLabel lblCash10;
  private IntegerTextField tfCash10;

  private JLabel lblCash5;
  private IntegerTextField tfCash5;

  private JLabel lblCash1;
  private IntegerTextField tfCash1;

  private JLabel lblCash5M;
  private IntegerTextField tfCash5M;

  private JLabel lblCash1M;
  private IntegerTextField tfCash1M;

  private JLabel lblCashActual;
  private JLabel lblCashActualContent;

  private JLabel lblAttendanceOperator;
  private JLabel lblAttendanceOperatorContent;

  private JLabel lblAttendanceInfo;
  private JLabel lblAttendanceInfoContent;

  private TransparentPanel pnlRight;

  private PosButton posButton1;
  private PosButton posButton10;
  private PosButton posButton11;
  private PosButton posButton12;
  private PosButton posButton2;
  private PosButton posButton3;
  private PosButton posButton4;
  private PosButton posButton5;
  private PosButton posButton6;
  private PosButton posButton7;
  private PosButton posButton8;
  private PosButton posButton9;

  private IntegerTextField tfTarget;

  private static final String ZERO = "0";
  private static final String ADD = "0";
  private static final String REMOVE = "1";

  public ClockOutDialog(Frame owner) {
    super(owner, true);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());

    initComponents();
    initRightPanel();
  }


  private void initRightPanel() {
    pnlRight = new TransparentPanel();

    posButton1 = new PosButton();
    posButton2 = new PosButton();
    posButton3 = new PosButton();
    posButton4 = new PosButton();
    posButton5 = new PosButton();
    posButton6 = new PosButton();
    posButton9 = new PosButton();
    posButton8 = new PosButton();
    posButton7 = new PosButton();
    posButton11 = new PosButton();
    posButton10 = new PosButton();
    posButton12 = new PosButton();

    pnlRight.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    pnlRight.setLayout(new GridLayout(0, 3, 5, 5));

    posButton1.setAction(calAction);
    posButton1.setIcon(new ImageIcon(getClass().getResource("/images/7_32.png")));
    posButton1.setActionCommand("7");
    posButton1.setFocusable(false);
    pnlRight.add(posButton1);

    posButton2.setAction(calAction);
    posButton2.setIcon(new ImageIcon(getClass().getResource("/images/8_32.png")));
    posButton2.setActionCommand("8");
    posButton2.setFocusable(false);
    pnlRight.add(posButton2);

    posButton3.setAction(calAction);
    posButton3.setIcon(new ImageIcon(getClass().getResource("/images/9_32.png")));
    posButton3.setActionCommand("9");
    posButton3.setFocusable(false);
    pnlRight.add(posButton3);

    posButton4.setAction(calAction);
    posButton4.setIcon(new ImageIcon(getClass().getResource("/images/4_32.png")));
    posButton4.setActionCommand("4");
    posButton4.setFocusable(false);
    pnlRight.add(posButton4);

    posButton5.setAction(calAction);
    posButton5.setIcon(new ImageIcon(getClass().getResource("/images/5_32.png")));
    posButton5.setActionCommand("5");
    posButton5.setFocusable(false);
    pnlRight.add(posButton5);

    posButton6.setAction(calAction);
    posButton6.setIcon(new ImageIcon(getClass().getResource("/images/6_32.png")));
    posButton6.setActionCommand("6");
    posButton6.setFocusable(false);
    pnlRight.add(posButton6);

    posButton9.setAction(calAction);
    posButton9.setIcon(new ImageIcon(getClass().getResource("/images/1_32.png")));
    posButton9.setActionCommand(REMOVE);
    posButton9.setFocusable(false);
    pnlRight.add(posButton9);

    posButton8.setAction(calAction);
    posButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/2_32.png")));
    posButton8.setActionCommand("2");
    posButton8.setFocusable(false);
    pnlRight.add(posButton8);

    posButton7.setAction(calAction);
    posButton7.setIcon(new ImageIcon(getClass().getResource("/images/3_32.png")));
    posButton7.setActionCommand("3");
    posButton7.setFocusable(false);
    pnlRight.add(posButton7);

    posButton11.setAction(calAction);
    posButton11.setIcon(new ImageIcon(getClass().getResource("/images/0_32.png")));
    posButton11.setActionCommand(ADD);
    posButton11.setFocusable(false);
    pnlRight.add(posButton11);

    posButton10.setAction(calAction);
    posButton10.setIcon(new ImageIcon(getClass().getResource("/images/previous_32.png")));
    posButton10.setActionCommand(".");
    posButton10.setFocusable(false);
    pnlRight.add(posButton10);

    posButton12.setAction(calAction);
    posButton12.setIcon(new ImageIcon(getClass().getResource("/images/clear_32.png")));
    posButton12.setText(POSConstants.CLEAR);
    posButton12.setActionCommand("CLEAR");
    posButton12.setFocusable(false);
    pnlRight.add(posButton12);

    add(pnlRight, BorderLayout.EAST);
  }


  private void initComponents() {
    pnlLeft = new TransparentPanel(new MigLayout());

    ControllerGenerator.addSeparator(pnlLeft, POSConstants.CLOCK_OUT_DLG_AREA_CASH);

    lblCashShould = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_SHOULD + POSConstants.COLON);
    lblCashShouldContent = new JLabel();
    pnlLeft.add(lblCashShould, "gap para");
    pnlLeft.add(lblCashShouldContent, "span, growx, wrap");

    ControllerGenerator.addSeparator(pnlLeft, POSConstants.CLOCK_OUT_DLG_AREA_CASH_STAT);

    Dimension dimension = new Dimension(100, 25);

    lblCash100 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_100 + POSConstants.COLON);
    tfCash100 = new IntegerTextField();
    tfCash100.setPreferredSize(dimension);
    tfCash100.getDocument().addDocumentListener(this);
    tfCash100.addFocusListener(this);
    pnlLeft.add(lblCash100, "gap para");
    pnlLeft.add(tfCash100, "span, growx, wrap");

    lblCash50 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_50 + POSConstants.COLON);
    tfCash50 = new IntegerTextField();
    tfCash50.setPreferredSize(dimension);
    tfCash50.getDocument().addDocumentListener(this);
    tfCash50.addFocusListener(this);
    pnlLeft.add(lblCash50, "gap para");
    pnlLeft.add(tfCash50, "span, growx, wrap");

    lblCash20 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_20 + POSConstants.COLON);
    tfCash20 = new IntegerTextField();
    tfCash20.setPreferredSize(dimension);
    tfCash20.getDocument().addDocumentListener(this);
    tfCash20.addFocusListener(this);
    pnlLeft.add(lblCash20, "gap para");
    pnlLeft.add(tfCash20, "span, growx, wrap");

    lblCash10 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_10 + POSConstants.COLON);
    tfCash10 = new IntegerTextField();
    tfCash10.setPreferredSize(dimension);
    tfCash10.getDocument().addDocumentListener(this);
    tfCash10.addFocusListener(this);
    pnlLeft.add(lblCash10, "gap para");
    pnlLeft.add(tfCash10, "span, growx, wrap");

    lblCash5 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_5 + POSConstants.COLON);
    tfCash5 = new IntegerTextField();
    tfCash5.setPreferredSize(dimension);
    tfCash5.getDocument().addDocumentListener(this);
    tfCash5.addFocusListener(this);
    pnlLeft.add(lblCash5, "gap para");
    pnlLeft.add(tfCash5, "span, growx, wrap");

    lblCash1 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_1 + POSConstants.COLON);
    tfCash1 = new IntegerTextField();
    tfCash1.setPreferredSize(dimension);
    tfCash1.getDocument().addDocumentListener(this);
    tfCash1.addFocusListener(this);
    pnlLeft.add(lblCash1, "gap para");
    pnlLeft.add(tfCash1, "span, growx, wrap");

    lblCash5M = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_5M + POSConstants.COLON);
    tfCash5M = new IntegerTextField();
    tfCash5M.setPreferredSize(dimension);
    tfCash5M.getDocument().addDocumentListener(this);
    tfCash5M.addFocusListener(this);
    pnlLeft.add(lblCash5M, "gap para");
    pnlLeft.add(tfCash5M, "span, growx, wrap");

    lblCash1M = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_1M + POSConstants.COLON);
    tfCash1M = new IntegerTextField();
    tfCash1M.setPreferredSize(dimension);
    tfCash1M.getDocument().addDocumentListener(this);
    tfCash1M.addFocusListener(this);
    pnlLeft.add(lblCash1M, "gap para");
    pnlLeft.add(tfCash1M, "span, growx, wrap");

    lblCashActual = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_ACTUAL + POSConstants.COLON);
    lblCashActualContent = new JLabel(ZERO);
    pnlLeft.add(lblCashActual, "gapleft para, gaptop 20px");
    pnlLeft.add(lblCashActualContent, "span, gaptop 20px, growx, wrap");

    ControllerGenerator.addSeparator(pnlLeft, POSConstants.CLOCK_OUT_DLG_AREA_ATTENDANCE);

    lblAttendanceOperator =
        new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_ATTENDANCE_OPERATOR + POSConstants.COLON);
    lblAttendanceOperatorContent = new JLabel();
    pnlLeft.add(lblAttendanceOperator, "gap para");
    pnlLeft.add(lblAttendanceOperatorContent, "span, growx, wrap");

    lblAttendanceInfo =
        new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_ATTENDANCE_INFO + POSConstants.COLON);
    lblAttendanceInfoContent = new JLabel();
    pnlLeft.add(lblAttendanceInfo, "gap para");
    pnlLeft.add(lblAttendanceInfoContent, "span, growx, wrap");

    add(pnlLeft, BorderLayout.WEST);
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    calculateActualAmount();
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    calculateActualAmount();
  }


  @Override
  public void changedUpdate(DocumentEvent e) {
    calculateActualAmount();
  }

  private void calculateActualAmount() {
    try {
      int cash100 = getCount(tfCash100);
      int cash50 = getCount(tfCash50);
      int cash20 = getCount(tfCash20);
      int cash10 = getCount(tfCash10);
      int cash5 = getCount(tfCash5);
      int cash1 = getCount(tfCash1);
      int cash5M = getCount(tfCash5M);
      int cash1M = getCount(tfCash1M);

      double total = cash100 * 100 + cash50 * 50 + cash20 * 20 + cash10 * 10 + cash5 * 5 + cash1 * 1
          + cash5M * 0.5 + cash1M * 0.1;

      lblCashActualContent.setText(String.valueOf(total));
    } catch (NumberFormatException nfe) {
      return;
    }
  }

  private int getCount(final IntegerTextField tf) {
    int cash;
    if (tf.getText().length() == 0) {
      cash = 0;
    } else {
      cash = Integer.parseInt(tf.getText());
    }

    return cash;
  }

  Action calAction = new AbstractAction() {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private boolean firstType = true;

    @Override
    public void actionPerformed(ActionEvent e) {
      JTextField textField = tfTarget;
      if (textField == null) {
        return;
      }

      if (firstType) {
        textField.setText(ADD);
        firstType = false;
      }

      PosButton button = (PosButton) e.getSource();
      String s = button.getActionCommand();
      if (s.equals("CLEAR")) {
        textField.setText(ADD);
      } else if (s.equals(".")) {
        String text = textField.getText();
        if (StringUtils.isNotBlank(text)) {
          text = text.substring(0, text.length() - 1);
          textField.setText(text);
        }
      } else {
        String string = textField.getText();
        int index = string.indexOf('.');
        if (index < 0) {
          double value = 0;
          try {
            value = Double.parseDouble(string);
          } catch (NumberFormatException x) {
            Toolkit.getDefaultToolkit().beep();
          }
          if (value == 0) {
            textField.setText(s);
          } else {
            textField.setText(string + s);
          }
        } else {
          textField.setText(string + s);
        }
      }
    }
  };

  @Override
  public void focusGained(FocusEvent e) {
    tfTarget = (IntegerTextField) e.getSource();
  }


  @Override
  public void focusLost(FocusEvent e) {
    tfTarget = null;
  }
}
