/*
 * CategoryBeanEditor.java
 *
 * Created on July 30, 2006, 11:20 PM
 */

package com.floreantpos.ui.model;

import com.floreantpos.model.MenuCategory;
import com.floreantpos.model.dao.MenuCategoryDAO;
import com.floreantpos.swing.FixedLengthDocument;
import com.floreantpos.swing.MessageDialog;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.util.POSUtil;

/**
 *
 * @author  MShahriar
 */
public class MenuCategoryForm extends BeanEditor {
    
	/** Creates new form CategoryBeanEditor */
    public MenuCategoryForm() throws Exception {
    	this(new MenuCategory());
    }
    
    public MenuCategoryForm(MenuCategory category) throws Exception {
    	initComponents();
    	
    	tfName.setDocument(new FixedLengthDocument(20));
    	setBean(category);
    }
    
    public String getDisplayText() {
    	MenuCategory foodCategory = (MenuCategory) getBean();
    	if(foodCategory.getId() == null) {
    		return com.floreantpos.POSConstants.NEW_MENU_CATEGORY;
    	}
    	return com.floreantpos.POSConstants.EDIT_MENU_CATEGORY;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        chkVisible = new javax.swing.JCheckBox();
        tfName = new com.floreantpos.swing.FixedLengthTextField();
        chkBeverage = new javax.swing.JCheckBox();

        jLabel1.setText(com.floreantpos.POSConstants.NAME + ":");

        chkVisible.setText(com.floreantpos.POSConstants.VISIBLE);
        chkVisible.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        chkVisible.setMargin(new java.awt.Insets(0, 0, 0, 0));

        chkBeverage.setText(com.floreantpos.POSConstants.BEVERAGE);
        chkBeverage.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        chkBeverage.setMargin(new java.awt.Insets(0, 0, 0, 0));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(chkVisible)
                        .addContainerGap())
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(chkBeverage)
                        .add(layout.createSequentialGroup()
                            .add(tfName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                            .addContainerGap()))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(tfName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(chkBeverage)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(chkVisible)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    protected void updateView() {
    	MenuCategory foodCategory = (MenuCategory) getBean();
    	if(foodCategory == null) {
    		tfName.setText("");
    		chkVisible.setSelected(false);
    		return;
    	}
    	tfName.setText(foodCategory.getName());
    	chkBeverage.setSelected(foodCategory.isBeverage());
    	if(foodCategory.getId() == null) {
    		chkVisible.setSelected(true);
		}
    	else {
    		chkVisible.setSelected(foodCategory.isVisible());
    	}
    }
    
    protected boolean updateModel() {
    	MenuCategory foodCategory = (MenuCategory) getBean();
    	if(foodCategory == null) {
    		return false;
    	}
    	
    	
    	String categoryName = tfName.getText();
    	if(POSUtil.isBlankOrNull(categoryName)) {
    		MessageDialog.showError("需要输入名字");
    		return false;
    	}
    	
		foodCategory.setName(categoryName);
		foodCategory.setBeverage(chkBeverage.isSelected());
    	foodCategory.setVisible(chkVisible.isSelected());
    	
    	return true;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkBeverage;
    private javax.swing.JCheckBox chkVisible;
    private javax.swing.JLabel jLabel1;
    private com.floreantpos.swing.FixedLengthTextField tfName;
    // End of variables declaration//GEN-END:variables
	@Override
	public boolean save() {
		try {
			if(!updateModel()) return false;
			
			MenuCategory foodCategory = (MenuCategory) getBean();
			MenuCategoryDAO foodCategoryDAO = new MenuCategoryDAO();
			foodCategoryDAO.saveOrUpdate(foodCategory);
			return true;
		} catch (Exception x) {
			MessageDialog.showError(x);
			return false;
		}
	}
}
