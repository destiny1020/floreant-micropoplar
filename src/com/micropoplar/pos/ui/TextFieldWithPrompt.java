package com.micropoplar.pos.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.FocusManager;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.floreantpos.config.UIConfig;

public class TextFieldWithPrompt extends JTextField {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String prompt;

  public TextFieldWithPrompt(String prompt) {
    this.prompt = prompt;
  }

  @Override
  protected void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);

    if (StringUtils.isBlank(getText())
        && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)) {
      setText("");

      Graphics2D g2 = (Graphics2D) g.create();
      g2.setBackground(Color.GRAY);
      g2.setColor(Color.GRAY);
      g2.setFont(UIConfig.getPromptFont());
      g2.drawString(prompt, 5, 15); //figure out x, y from font's FontMetrics and size of component.
      g2.dispose();
    }
  }

}
