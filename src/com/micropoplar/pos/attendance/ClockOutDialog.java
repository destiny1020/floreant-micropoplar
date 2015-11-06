package com.micropoplar.pos.attendance;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.floreantpos.POSConstants;
import com.floreantpos.swing.IntegerTextField;
import com.floreantpos.ui.dialog.POSDialog;
import com.micropoplar.pos.ui.util.ControllerGenerator;

import net.miginfocom.swing.MigLayout;

public class ClockOutDialog extends POSDialog implements DocumentListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

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

  private static final String ZERO = "0";

  public ClockOutDialog(Frame owner) {
    super(owner, true);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    initComponents();
  }


  private void initComponents() {
    setLayout(new BorderLayout());

    JPanel pnlMain = new JPanel(new MigLayout());

    ControllerGenerator.addSeparator(pnlMain, POSConstants.CLOCK_OUT_DLG_AREA_CASH);

    lblCashShould = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_SHOULD + POSConstants.COLON);
    lblCashShouldContent = new JLabel();
    pnlMain.add(lblCashShould, "gap para");
    pnlMain.add(lblCashShouldContent, "span, growx, wrap");

    ControllerGenerator.addSeparator(pnlMain, POSConstants.CLOCK_OUT_DLG_AREA_CASH_STAT);

    Dimension dimension = new Dimension(100, 25);

    lblCash100 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_100 + POSConstants.COLON);
    tfCash100 = new IntegerTextField();
    tfCash100.setPreferredSize(dimension);
    tfCash100.getDocument().addDocumentListener(this);
    pnlMain.add(lblCash100, "gap para");
    pnlMain.add(tfCash100, "span, growx, wrap");

    lblCash50 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_50 + POSConstants.COLON);
    tfCash50 = new IntegerTextField();
    tfCash50.setPreferredSize(dimension);
    tfCash50.getDocument().addDocumentListener(this);
    pnlMain.add(lblCash50, "gap para");
    pnlMain.add(tfCash50, "span, growx, wrap");

    lblCash20 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_20 + POSConstants.COLON);
    tfCash20 = new IntegerTextField();
    tfCash20.setPreferredSize(dimension);
    tfCash20.getDocument().addDocumentListener(this);
    pnlMain.add(lblCash20, "gap para");
    pnlMain.add(tfCash20, "span, growx, wrap");

    lblCash10 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_10 + POSConstants.COLON);
    tfCash10 = new IntegerTextField();
    tfCash10.setPreferredSize(dimension);
    tfCash10.getDocument().addDocumentListener(this);
    pnlMain.add(lblCash10, "gap para");
    pnlMain.add(tfCash10, "span, growx, wrap");

    lblCash5 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_5 + POSConstants.COLON);
    tfCash5 = new IntegerTextField();
    tfCash5.setPreferredSize(dimension);
    tfCash5.getDocument().addDocumentListener(this);
    pnlMain.add(lblCash5, "gap para");
    pnlMain.add(tfCash5, "span, growx, wrap");

    lblCash1 = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_1 + POSConstants.COLON);
    tfCash1 = new IntegerTextField();
    tfCash1.setPreferredSize(dimension);
    tfCash1.getDocument().addDocumentListener(this);
    pnlMain.add(lblCash1, "gap para");
    pnlMain.add(tfCash1, "span, growx, wrap");

    lblCash5M = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_5M + POSConstants.COLON);
    tfCash5M = new IntegerTextField();
    tfCash5M.setPreferredSize(dimension);
    tfCash5M.getDocument().addDocumentListener(this);
    pnlMain.add(lblCash5M, "gap para");
    pnlMain.add(tfCash5M, "span, growx, wrap");

    lblCash1M = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_1M + POSConstants.COLON);
    tfCash1M = new IntegerTextField();
    tfCash1M.setPreferredSize(dimension);
    tfCash1M.getDocument().addDocumentListener(this);
    pnlMain.add(lblCash1M, "gap para");
    pnlMain.add(tfCash1M, "span, growx, wrap");

    lblCashActual = new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_CASH_ACTUAL + POSConstants.COLON);
    lblCashActualContent = new JLabel(ZERO);
    pnlMain.add(lblCashActual, "gapleft para, gaptop 20px");
    pnlMain.add(lblCashActualContent, "span, gaptop 20px, growx, wrap");

    ControllerGenerator.addSeparator(pnlMain, POSConstants.CLOCK_OUT_DLG_AREA_ATTENDANCE);

    lblAttendanceOperator =
        new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_ATTENDANCE_OPERATOR + POSConstants.COLON);
    lblAttendanceOperatorContent = new JLabel();
    pnlMain.add(lblAttendanceOperator, "gap para");
    pnlMain.add(lblAttendanceOperatorContent, "span, growx, wrap");

    lblAttendanceInfo =
        new JLabel(POSConstants.CLOCK_OUT_DLG_AREA_ATTENDANCE_INFO + POSConstants.COLON);
    lblAttendanceInfoContent = new JLabel();
    pnlMain.add(lblAttendanceInfo, "gap para");
    pnlMain.add(lblAttendanceInfoContent, "span, growx, wrap");

    add(pnlMain, BorderLayout.CENTER);
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
}
