package com.floreantpos.report;

import java.util.Date;

import net.sf.jasperreports.view.JRViewer;

public abstract class Report {
  private Date startDate;
  private Date endDate;
  protected JRViewer viewer;

  public abstract void refresh() throws Exception;

  public abstract boolean isDateRangeSupported();

  public abstract boolean isTypeSupported();

  public JRViewer getViewer() {
    return viewer;
  }

  public Date getEndDate() {
    if (endDate == null) {
      return new Date();
    }
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Date getStartDate() {
    if (startDate == null) {
      return new Date();
    }
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

}
