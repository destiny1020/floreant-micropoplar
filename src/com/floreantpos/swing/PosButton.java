package com.floreantpos.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.floreantpos.actions.ActionCommand;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.config.UIConfig;

public class PosButton extends JButton {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static Border border = new LineBorder(Color.BLACK, 1);
  static Insets margin = new Insets(0, 0, 0, 0);

  static POSButtonUI ui = new POSButtonUI();

  static {
    UIManager.put("PosButtonUI", "com.floreantpos.swing.POSButtonUI");
  }

  public PosButton() {
    this("");
  }

  public PosButton(String text) {
    super(text);
    setFont(UIConfig.getButtonFont());

    setFocusPainted(false);
    setMargin(margin);
  }

  public PosButton(Action a) {
    super(a);

    setFont(UIConfig.getButtonFont());

    setFocusPainted(false);
    setMargin(margin);
  }

  public PosButton(ActionCommand command) {
    this(command.toString());

    setActionCommand(command.name());
  }

  public PosButton(ActionCommand command, ActionListener listener) {
    this(command.toString());

    setActionCommand(command.name());
    addActionListener(listener);
  }

  @Override
  public String getUIClassID() {
    return "PosButtonUI";
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension size = super.getPreferredSize();

    if (isPreferredSizeSet()) {
      return size;
    }

    if (ui != null) {
      size = ui.getPreferredSize(this);
    }

    if (size != null) {
      size.setSize(size.width + 20, TerminalConfig.getTouchScreenButtonHeight());
    }


    return (size != null) ? size : super.getPreferredSize();
  }
}
