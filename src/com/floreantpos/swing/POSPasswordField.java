package com.floreantpos.swing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
import javax.swing.text.Document;

public class POSPasswordField extends JPasswordField implements FocusListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public POSPasswordField() {
    addFocusListener(this);
  }

  public POSPasswordField(String text) {
    super(text);
    addFocusListener(this);
  }

  public POSPasswordField(int columns) {
    super(columns);
    addFocusListener(this);
  }

  public POSPasswordField(String text, int columns) {
    super(text, columns);
    addFocusListener(this);
  }

  public POSPasswordField(Document doc, String txt, int columns) {
    super(doc, txt, columns);
    addFocusListener(this);
  }

  @Override
  public void focusGained(FocusEvent e) {
    selectAll();
  }

  @Override
  public void focusLost(FocusEvent e) {}

}
