package com.mircopoplar.pos.ui.set;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.floreantpos.model.ITicketItem;
import com.floreantpos.ui.ticket.TicketViewerTableModel;

public class MenuItemSetTableCellRenderer extends DefaultTableCellRenderer {

  private static final long serialVersionUID = 1L;
  private static final DecimalFormat numberFormat = new DecimalFormat("0.00");

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    Component rendererComponent =
        super.getTableCellRendererComponent(table, value, isSelected, false, row, column);

    TicketViewerTableModel model = (TicketViewerTableModel) table.getModel();
    Object object = model.get(row);

    if (isSelected) {
      return rendererComponent;
    }

    rendererComponent.setBackground(Color.WHITE);

    if (object instanceof ITicketItem) {
      ITicketItem ticketItem = (ITicketItem) object;
      if (ticketItem.isPrintedToKitchen()) {
        rendererComponent.setBackground(Color.YELLOW);
      }
    }

    return rendererComponent;
  }

  @Override
  protected void setValue(Object value) {
    if (value == null) {
      setText("");
      return;
    }

    String text = value.toString();

    if (value instanceof Double || value instanceof Float) {
      text = numberFormat.format(((java.lang.Number) value).doubleValue());
      setHorizontalAlignment(SwingConstants.RIGHT);
    } else if (value instanceof Integer) {
      setHorizontalAlignment(SwingConstants.RIGHT);
    } else {
      setHorizontalAlignment(SwingConstants.LEFT);
    }

    setText(text);
  }

}
