package com.floreantpos.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;


public class QwertyKeyPad extends JPanel implements ActionListener, ChangeListener {
  Font buttonFont = getFont().deriveFont(Font.BOLD, 24);

  String[] s1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
  String[] s2 = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p"};
  String[] s3 = {"a", "s", "d", "f", "g", "h", "j", "k", "l", ";"};
  String[] s4 = {"z", "x", "c", "v", "b", "n", "m", "-", ",", "."};

  private ArrayList<PosButton> buttons = new ArrayList<PosButton>();
  Dimension pSize = new Dimension(50, 50);

  public QwertyKeyPad() {
    createUI();

    Dimension size = new Dimension(700, 200);
    setMinimumSize(size);
    setPreferredSize(size);
  }

  private void createUI() {
    setLayout(new BorderLayout(0, 0));


    TransparentPanel centerPanel = new TransparentPanel(new GridLayout(0, 1, 2, 2));
    centerPanel.add(addButtonsToPanel(s1));
    centerPanel.add(addButtonsToPanel(s2));
    centerPanel.add(addButtonsToPanel(s3));
    centerPanel.add(addButtonsToPanel(s4));
    add(centerPanel, BorderLayout.CENTER);

    JPanel eastPanel = new JPanel(new GridLayout(0, 1, 2, 2));
    PosButton button = new PosButton();
    button.setText("空格");
    button.setFocusable(false);
    button.addActionListener(this);
    eastPanel.add(button);

    POSToggleButton toggleButton = new POSToggleButton();
    toggleButton.setText("大小写");
    toggleButton.setFocusable(false);
    toggleButton.addChangeListener(this);
    eastPanel.add(toggleButton);

    button = new PosButton();
    button.setText(com.floreantpos.POSConstants.CLEAR);
    button.setFocusable(false);
    button.addActionListener(this);
    eastPanel.add(button);

    button = new PosButton();
    button.setFocusable(false);
    button.setText(com.floreantpos.POSConstants.CLEAR_ALL);
    button.addActionListener(this);
    eastPanel.add(button);

    eastPanel.setPreferredSize(new Dimension(90, 50));
    add(eastPanel, BorderLayout.EAST);
  }

  private TransparentPanel addButtonsToPanel(String[] buttonText) {
    TransparentPanel panel = new TransparentPanel(new GridLayout(0, s1.length, 2, 2));
    for (int i = 0; i < buttonText.length; i++) {
      String s = buttonText[i];
      PosButton button = new PosButton();
      button.setText(s);
      button.setPreferredSize(pSize);
      button.addActionListener(this);
      button.setFont(buttonFont);
      button.setFocusable(false);
      buttons.add(button);
      panel.add(button);
    }
    return panel;
  }

  public void actionPerformed(ActionEvent e) {
    Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

    JTextComponent note = null;

    if (!(focusOwner instanceof JTextComponent)) {
      return;
    }

    note = (JTextComponent) focusOwner;

    String s = e.getActionCommand();
    if (s.equals(com.floreantpos.POSConstants.CLEAR)) {
      String str = note.getText();
      if (str.length() > 0) {
        str = str.substring(0, str.length() - 1);
      }
      note.setText(str);
    } else if (s.equals(com.floreantpos.POSConstants.CLEAR_ALL)) {
      note.setText("");
    } else if (s.equals("SPACE")) {
      String str = note.getText();
      if (str == null) {
        str = "";
      }
      note.setText(str + " ");
    } else {
      String str = note.getText();
      if (str == null) {
        str = "";
      }
      note.setText(str + s);
    }
  }

  public void stateChanged(ChangeEvent e) {
    JToggleButton b = (JToggleButton) e.getSource();

    if (b.isSelected()) {
      for (PosButton button : buttons) {
        button.setText(button.getText().toUpperCase());
      }
    } else {
      for (PosButton button : buttons) {
        button.setText(button.getText().toLowerCase());
      }
    }
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    final QwertyKeyPad comp = new QwertyKeyPad();
    frame.add(comp);
    comp.addComponentListener(new ComponentListener() {

      @Override
      public void componentShown(ComponentEvent e) {


      }

      @Override
      public void componentResized(ComponentEvent e) {
        // System.out.println(comp.getSize());

      }

      @Override
      public void componentMoved(ComponentEvent e) {


      }

      @Override
      public void componentHidden(ComponentEvent e) {


      }
    });

    frame.pack();
    frame.setVisible(true);
  }

}
