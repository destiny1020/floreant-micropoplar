/*
 * ReportViewer.java
 *
 * Created on September 17, 2006, 11:38 PM
 */

package com.floreantpos.report;

import java.util.Date;

import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.swing.MessageDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.util.UiUtil;

/**
 *
 * @author  MShahriar
 */
public class ReportViewer extends javax.swing.JPanel {
    private Report report;
    
    /** Creates new form ReportViewer */
    public ReportViewer() {
        initComponents();
    }

    public ReportViewer(Report report) {
    	initComponents();
    	
    	setReport(report);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        reportConstraintPanel = new com.floreantpos.swing.TransparentPanel();
        jLabel1 = new javax.swing.JLabel();
        cbReportType = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        dpStartDate = UiUtil.getCurrentMonthStart();
        dpEndDate = UiUtil.getCurrentMonthEnd();
        jLabel3 = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        reportPanel = new com.floreantpos.swing.TransparentPanel();

        setLayout(new java.awt.BorderLayout(5, 5));

        jLabel1.setText("报告类型" + ":");

        cbReportType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { com.floreantpos.POSConstants.PREVIOUS_SALE_AFTER_DRAWER_RESET_, com.floreantpos.POSConstants.SALE_BEFORE_DRAWER_RESET }));

        jLabel2.setText(com.floreantpos.POSConstants.START_DATE + ":");

        jLabel3.setText(com.floreantpos.POSConstants.END_DATE + ":");

        btnRefresh.setText(com.floreantpos.POSConstants.REFRESH);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doRefreshReport(evt);
            }
        });

        org.jdesktop.layout.GroupLayout reportConstraintPanelLayout = new org.jdesktop.layout.GroupLayout(reportConstraintPanel);
        reportConstraintPanel.setLayout(reportConstraintPanelLayout);
        reportConstraintPanelLayout.setHorizontalGroup(
            reportConstraintPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(reportConstraintPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(reportConstraintPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(reportConstraintPanelLayout.createSequentialGroup()
                        .add(reportConstraintPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jLabel1))
                        .add(15, 15, 15)
                        .add(reportConstraintPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(cbReportType, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(dpStartDate, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(dpEndDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, btnRefresh))
                .addContainerGap())
        );
        reportConstraintPanelLayout.setVerticalGroup(
            reportConstraintPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(reportConstraintPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(reportConstraintPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(cbReportType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(reportConstraintPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(dpEndDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(reportConstraintPanelLayout.createSequentialGroup()
                        .add(reportConstraintPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(dpStartDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnRefresh)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        reportConstraintPanelLayout.linkSize(new java.awt.Component[] {dpEndDate, dpStartDate, jLabel2, jLabel3}, org.jdesktop.layout.GroupLayout.VERTICAL);

        add(reportConstraintPanel, java.awt.BorderLayout.NORTH);

        reportPanel.setLayout(new java.awt.BorderLayout());

        add(reportPanel, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents

    private void doRefreshReport(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doRefreshReport
    	Date fromDate = dpStartDate.getDate();
		Date toDate = dpEndDate.getDate();
		
		if(fromDate.after(toDate)) {
			POSMessageDialog.showError(BackOfficeWindow.getInstance(), com.floreantpos.POSConstants.FROM_DATE_CANNOT_BE_GREATER_THAN_TO_DATE_);
			return;
		}
		
    	try {
    		reportPanel.removeAll();
    		reportPanel.revalidate();
    		
			if (report != null) {
				int reportType = cbReportType.getSelectedIndex();

				report.setReportType(reportType);
				report.setStartDate(fromDate);
				report.setEndDate(toDate);

				report.refresh();
				
				if (report != null && report.getViewer() != null) {
					reportPanel.add(report.getViewer());
					reportPanel.revalidate();
				}
			}
			
		} catch (Exception e) {
			MessageDialog.showError(this, com.floreantpos.POSConstants.ERROR_MESSAGE, e);
		}
    }//GEN-LAST:event_doRefreshReport
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox cbReportType;
    private org.jdesktop.swingx.JXDatePicker dpEndDate;
    private org.jdesktop.swingx.JXDatePicker dpStartDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private com.floreantpos.swing.TransparentPanel reportConstraintPanel;
    private com.floreantpos.swing.TransparentPanel reportPanel;
    // End of variables declaration//GEN-END:variables

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
		
//		if(report != null) {
//			cbReportType.setEnabled(report.isTypeSupported());
//			this.dpStartDate.setEnabled(report.isDateRangeSupported());
//			this.dpEndDate.setEnabled(report.isDateRangeSupported());
//		}
	}
    
}
