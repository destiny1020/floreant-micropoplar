/*
 * MiscTicketItemDialog.java
 *
 * Created on September 8, 2006, 10:04 PM
 */

package com.floreantpos.ui.dialog;

import org.apache.commons.lang3.StringUtils;

import com.floreantpos.POSConstants;
import com.floreantpos.model.TicketItem;

/**
 *
 * @author MShahriar
 */
public class MiscTicketItemDialog extends POSDialog {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static int customIdx = 1;

  private String currentCustomItemName = POSConstants.OTHERS_VIEW_CUSTOM_ITEM_PREFIX + customIdx++;
  private TicketItem ticketItem;

  /** Creates new form MiscTicketItemDialog */
  public MiscTicketItemDialog(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();

    noteView1.setNoteLength(30);
    noteView1.setNote(currentCustomItemName);
    numberSelectionView1.setDecimalAllowed(true);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {
    titlePanel1 = new com.floreantpos.ui.TitlePanel();
    transparentPanel1 = new com.floreantpos.swing.TransparentPanel();
    posButton1 = new com.floreantpos.swing.PosButton();
    posButton2 = new com.floreantpos.swing.PosButton();
    transparentPanel2 = new com.floreantpos.swing.TransparentPanel();
    numberSelectionView1 = new com.floreantpos.ui.views.NumberSelectionView();
    noteView1 = new com.floreantpos.ui.views.NoteView();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    titlePanel1.setTitle(com.floreantpos.POSConstants.MISC_ITEM);
    getContentPane().add(titlePanel1, java.awt.BorderLayout.NORTH);

    posButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/finish_32.png")));
    posButton1.setText(com.floreantpos.POSConstants.FINISH);
    posButton1.setPreferredSize(new java.awt.Dimension(120, 50));
    posButton1.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doFinish(evt);
      }
    });

    transparentPanel1.add(posButton1);

    posButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel_32.png")));
    posButton2.setText(com.floreantpos.POSConstants.CANCEL);
    posButton2.setPreferredSize(new java.awt.Dimension(120, 50));
    posButton2.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doCancel(evt);
      }
    });

    transparentPanel1.add(posButton2);

    getContentPane().add(transparentPanel1, java.awt.BorderLayout.SOUTH);

    transparentPanel2.setLayout(new java.awt.BorderLayout());

    numberSelectionView1.setPreferredSize(new java.awt.Dimension(220, 392));
    numberSelectionView1.setTitle(com.floreantpos.POSConstants.PRICE);
    transparentPanel2.add(numberSelectionView1, java.awt.BorderLayout.WEST);

    noteView1.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
        com.floreantpos.POSConstants.ITEM_NAME, javax.swing.border.TitledBorder.CENTER,
        javax.swing.border.TitledBorder.DEFAULT_POSITION));
    transparentPanel2.add(noteView1, java.awt.BorderLayout.CENTER);

    getContentPane().add(transparentPanel2, java.awt.BorderLayout.CENTER);

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width - 786) / 2, (screenSize.height - 452) / 2, 786, 452);
  }// </editor-fold>//GEN-END:initComponents

  private void doCancel(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doCancel
    setCanceled(true);
    ticketItem = null;
    dispose();
  }// GEN-LAST:event_doCancel

  private void doFinish(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doFinish
    setCanceled(false);
    double amount = numberSelectionView1.getValue();
    String itemName = noteView1.getNote();
    if (StringUtils.isBlank(itemName)) {
      itemName = currentCustomItemName;
    }

    ticketItem = new TicketItem();
    ticketItem.setItemCount(1);
    ticketItem.setUnitPrice(amount);
    ticketItem.setName(itemName);
    ticketItem.setCategoryName(com.floreantpos.POSConstants.MISC);
    ticketItem.setGroupName(com.floreantpos.POSConstants.MISC);
    ticketItem.setShouldPrintToKitchen(true);

    dispose();
  }// GEN-LAST:event_doFinish

  public TicketItem getTicketItem() {
    return ticketItem;
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        new MiscTicketItemDialog(new javax.swing.JFrame(), true).setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.floreantpos.ui.views.NoteView noteView1;
  private com.floreantpos.ui.views.NumberSelectionView numberSelectionView1;
  private com.floreantpos.swing.PosButton posButton1;
  private com.floreantpos.swing.PosButton posButton2;
  private com.floreantpos.ui.TitlePanel titlePanel1;
  private com.floreantpos.swing.TransparentPanel transparentPanel1;
  private com.floreantpos.swing.TransparentPanel transparentPanel2;
  // End of variables declaration//GEN-END:variables

}
