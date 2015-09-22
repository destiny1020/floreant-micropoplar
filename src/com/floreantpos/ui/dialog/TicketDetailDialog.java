/*
 * TicketDetailDialog.java
 *
 * Created on September 9, 2006, 12:43 AM
 */

package com.floreantpos.ui.dialog;

import com.floreantpos.model.Ticket;

/**
 *
 * @author MShahriar
 */
public class TicketDetailDialog extends POSDialog {

  /** Creates new form TicketDetailDialog */
  public TicketDetailDialog(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();

    setResizable(false);
    pack();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {

    titlePanel1 = new com.floreantpos.ui.TitlePanel();
    jSeparator1 = new javax.swing.JSeparator();
    btnFinish = new com.floreantpos.swing.PosButton();
    ticketDetailView = new com.floreantpos.ui.views.TicketDetailView();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(com.floreantpos.POSConstants.TICKET_DETAIL);

    titlePanel1.setTitle(com.floreantpos.POSConstants.TICKET_DETAIL);

    btnFinish.setText(com.floreantpos.POSConstants.OK);
    btnFinish.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doFinish(evt);
      }
    });

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup().addContainerGap()
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(titlePanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 428,
                            Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, ticketDetailView,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jSeparator1,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))))
            .add(layout.createSequentialGroup().add(140, 140, 140).add(btnFinish,
                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130,
                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap()));
    layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(layout.createSequentialGroup().addContainerGap()
            .add(titlePanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(ticketDetailView, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(btnFinish, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42,
                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void doFinish(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_doFinish
    setCanceled(false);

    dispose();
  }// GEN-LAST:event_doFinish

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.floreantpos.swing.PosButton btnFinish;
  private javax.swing.JSeparator jSeparator1;
  private com.floreantpos.ui.views.TicketDetailView ticketDetailView;
  private com.floreantpos.ui.TitlePanel titlePanel1;
  // End of variables declaration//GEN-END:variables

  public void setTicket(Ticket ticket) {
    ticketDetailView.setTicket(ticket);
  }


}
