/*
 * OkCancelDialog.java
 *
 * Created on July 30, 2006, 10:36 PM
 */

package com.floreantpos.ui.dialog;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.floreantpos.swing.PosSmallButton;
import com.floreantpos.ui.BeanEditor;

/**
 *
 * @author MShahriar
 */
public class BeanEditorDialog extends javax.swing.JDialog implements WindowListener {
  protected BeanEditor beanEditor;
  private boolean canceled = false;
  private boolean okAndSelect = false;

  public BeanEditorDialog() {
    this(null, true);
  }

  /** Creates new form OkCancelDialog */
  public BeanEditorDialog(java.awt.Frame parent, boolean modal) {
    this(null, parent, modal);
  }

  public BeanEditorDialog(BeanEditor beanEditor, java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    init(beanEditor, false);
  }

  public BeanEditorDialog(BeanEditor beanEditor, java.awt.Frame parent, boolean modal,
      boolean createAndSelect) {
    super(parent, modal);
    okAndSelect = createAndSelect;
    init(beanEditor, createAndSelect);
  }

  private void init(BeanEditor beanEditor, boolean createAndSelect) {
    initComponents(createAndSelect);

    setBeanEditor(beanEditor);

    addWindowListener(this);
  }

  private void initComponents(boolean createAndSelect) {
    titlePanel = new com.floreantpos.ui.TitlePanel();
    jPanel1 = new com.floreantpos.swing.TransparentPanel();
    jSeparator1 = new javax.swing.JSeparator();
    jPanel2 = new com.floreantpos.swing.TransparentPanel();
    btnOk = new PosSmallButton();
    btnCancel = new PosSmallButton();
    beanEditorContainer = new com.floreantpos.swing.TransparentPanel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    getContentPane().add(titlePanel, java.awt.BorderLayout.NORTH);

    jPanel1.setLayout(new java.awt.BorderLayout());

    jPanel1.add(jSeparator1, java.awt.BorderLayout.NORTH);

    jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    if (createAndSelect) {
      btnOkAndSelect = new PosSmallButton();
      btnOkAndSelect.setText(com.floreantpos.POSConstants.OK_AND_SELECT);
      btnOkAndSelect.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
          performOk(evt);
        }
      });

      jPanel2.add(btnOkAndSelect);
    }

    btnOk.setText(com.floreantpos.POSConstants.OK);
    btnOk.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        performOk(evt);
      }
    });

    jPanel2.add(btnOk);

    btnCancel.setText(com.floreantpos.POSConstants.CANCEL);
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        performCancel(evt);
      }
    });

    jPanel2.add(btnCancel);

    jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

    getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

    beanEditorContainer.setLayout(new java.awt.BorderLayout());

    getContentPane().add(beanEditorContainer, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

  @Override
  public void dispose() {
    if (beanEditor != null) {
      beanEditor = null;
    }

    super.dispose();
  }

  private void performCancel(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_performCancel
    canceled = true;
    dispose();
  }// GEN-LAST:event_performCancel

  private void performOk(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_performOk
    if (beanEditor == null) {
      return;
    }

    boolean saved = beanEditor.save();
    if (saved) {
      dispose();
    }
  }// GEN-LAST:event_performOk

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.floreantpos.swing.TransparentPanel beanEditorContainer;
  private PosSmallButton btnCancel;
  private PosSmallButton btnOk;
  private PosSmallButton btnOkAndSelect;
  private com.floreantpos.swing.TransparentPanel jPanel1;
  private com.floreantpos.swing.TransparentPanel jPanel2;
  private javax.swing.JSeparator jSeparator1;
  private com.floreantpos.ui.TitlePanel titlePanel;

  // End of variables declaration//GEN-END:variables

  @Override
  public void setTitle(String title) {
    super.setTitle(title);

    titlePanel.setTitle(title);
  }

  public void open() {
    canceled = false;
    this.pack();
    this.setLocationRelativeTo(this.getOwner());
    super.setVisible(true);
  }

  public boolean isCanceled() {
    return canceled;
  }

  public Frame getParentFrame() {
    return (Frame) getOwner();
  }

  @Override
  public void windowOpened(WindowEvent e) {}

  @Override
  public void windowClosing(WindowEvent e) {
    performCancel(null);
  }

  @Override
  public void windowClosed(WindowEvent e) {}

  @Override
  public void windowIconified(WindowEvent e) {}

  @Override
  public void windowDeiconified(WindowEvent e) {}

  @Override
  public void windowActivated(WindowEvent e) {}

  @Override
  public void windowDeactivated(WindowEvent e) {}

  public BeanEditor getBeanEditor() {
    return beanEditor;
  }

  public Object getBean() {
    if (beanEditor != null) {
      return beanEditor.getBean();
    }
    return null;
  }

  public void setBean(Object bean) {
    if (beanEditor != null) {
      beanEditor.setBean(bean);
    }
  }

  public void setBeanEditor(BeanEditor beanEditor) {
    if (this.beanEditor != beanEditor) {
      this.beanEditor = beanEditor;
      beanEditorContainer.removeAll();

      if (beanEditor != null) {
        beanEditor.setEditorDialog(this);
        beanEditorContainer.add(beanEditor);
        setTitle(beanEditor.getDisplayText());
      }
      beanEditorContainer.revalidate();
    }
  }

  public boolean isOkAndSelect() {
    return okAndSelect;
  }


}
