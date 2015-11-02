package com.floreantpos.bo.ui;

/**
 * Option in the combo box.
 * 
 * @author Destiny
 *
 */
public class ComboOption {

  private int value;
  private String label;

  public ComboOption(int value, String label) {
    super();
    this.value = value;
    this.label = label;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return label;
  }

}
