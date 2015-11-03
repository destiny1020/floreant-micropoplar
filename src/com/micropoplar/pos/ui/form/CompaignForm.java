package com.micropoplar.pos.ui.form;

import com.floreantpos.POSConstants;
import com.floreantpos.model.util.IllegalModelStateException;
import com.floreantpos.ui.BeanEditor;
import com.micropoplar.pos.model.Compaign;

public class CompaignForm extends BeanEditor<Compaign> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private boolean addMode;

  public CompaignForm(boolean addMode) {
    this.addMode = addMode;
  }

  @Override
  public boolean save() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected void updateView() {
    // TODO Auto-generated method stub

  }

  @Override
  protected boolean updateModel() throws IllegalModelStateException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getDisplayText() {
    return addMode ? POSConstants.COMPAIGN_FORM_TITLE_ADD_MODE
        : POSConstants.COMPAIGN_FORM_TITLE_EDIT_MODE;
  }

  public boolean isAddMode() {
    return addMode;
  }

  public void setAddMode(boolean addMode) {
    this.addMode = addMode;
  }

}
