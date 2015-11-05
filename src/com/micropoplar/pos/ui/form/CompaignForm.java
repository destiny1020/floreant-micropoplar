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

  public CompaignForm() {
    this(new Compaign());
  }

  public CompaignForm(Compaign compaign) {
    initComponents();
  }

  private void initComponents() {
    // TODO Auto-generated method stub

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
    return editMode ? POSConstants.COMPAIGN_FORM_TITLE_ADD_MODE
        : POSConstants.COMPAIGN_FORM_TITLE_EDIT_MODE;
  }

}
