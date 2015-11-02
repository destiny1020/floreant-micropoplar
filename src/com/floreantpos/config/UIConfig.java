package com.floreantpos.config;

import java.awt.Font;

import javax.swing.UIManager;

public class UIConfig {
  public final static Font promptFont =
      UIManager.getFont("Prompt.font").deriveFont(Font.ITALIC, 12);
  public final static Font buttonFont = UIManager.getFont("Button.font").deriveFont(Font.BOLD, //$NON-NLS-1$
      TerminalConfig.getTouchScreenFontSize());
  public final static Font largeFont = UIManager.getFont("Button.font").deriveFont(Font.BOLD, 16); //$NON-NLS-1$

  public UIConfig() {
    super();
  }

  public static Font getButtonFont() {
    return buttonFont;
  }

  public static Font getPromptFont() {
    return promptFont;
  }

}
