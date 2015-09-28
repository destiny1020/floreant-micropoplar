package com.floreantpos.ui.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.floreantpos.model.MenuItemShift;
import com.floreantpos.util.ShiftUtil;

public class ShiftTableModel extends AbstractTableModel {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  List<MenuItemShift> shifts;
  String[] cn = {com.floreantpos.POSConstants.START_TIME, com.floreantpos.POSConstants.END_TIME,
      com.floreantpos.POSConstants.PRICE};
  Calendar calendar = Calendar.getInstance();

  ShiftTableModel(List<MenuItemShift> shifts) {
    if (shifts == null) {
      this.shifts = new ArrayList<MenuItemShift>();
    } else {
      this.shifts = new ArrayList<MenuItemShift>(shifts);
    }
  }

  public MenuItemShift get(int index) {
    return shifts.get(index);
  }

  public void add(MenuItemShift group) {
    if (shifts == null) {
      shifts = new ArrayList<MenuItemShift>();
    }
    shifts.add(group);
    fireTableDataChanged();
  }

  public void remove(int index) {
    if (shifts == null) {
      return;
    }
    shifts.remove(index);
    fireTableDataChanged();
  }

  public void remove(MenuItemShift group) {
    if (shifts == null) {
      return;
    }
    shifts.remove(group);
    fireTableDataChanged();
  }

  public int getRowCount() {
    if (shifts == null)
      return 0;

    return shifts.size();

  }

  public int getColumnCount() {
    return cn.length;
  }

  @Override
  public String getColumnName(int column) {
    return cn[column];
  }

  public List<MenuItemShift> getShifts() {
    return shifts;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    MenuItemShift shift = shifts.get(rowIndex);

    switch (columnIndex) {
      case 0:
        return ShiftUtil.buildShiftTimeRepresentation(shift.getShift().getStartTime());

      case 1:
        return ShiftUtil.buildShiftTimeRepresentation(shift.getShift().getEndTime());

      case 2:
        return String.valueOf(shift.getShiftPrice());
    }
    return null;
  }
}
