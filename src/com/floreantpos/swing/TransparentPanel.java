package com.floreantpos.swing;

import java.awt.LayoutManager;

import javax.swing.JPanel;



public class TransparentPanel extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public TransparentPanel(LayoutManager layout, boolean isDoubleBuffered) {
    super(layout, isDoubleBuffered);

    setOpaque(false);
  }

  public TransparentPanel(LayoutManager layout) {
    super(layout);

    setOpaque(false);
  }

  public TransparentPanel(boolean isDoubleBuffered) {
    super(isDoubleBuffered);

    setOpaque(false);
  }

  public TransparentPanel() {
    super();
    setOpaque(false);
  }
}
