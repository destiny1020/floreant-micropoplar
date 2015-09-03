package com.floreantpos.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.explorer.ListTableModel;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketStatus;
import com.floreantpos.model.TicketType;
import com.floreantpos.model.User;
import com.floreantpos.model.util.DateUtil;
import com.floreantpos.ui.dialog.POSMessageDialog;

public class TicketListView extends JPanel {
	private JXTable table;
	private TicketListTableModel tableModel;

	public TicketListView() {
		table = new TicketListTable();
		table.setSortable(false);
		table.setModel(tableModel = new TicketListTableModel());
		table.setRowHeight(60);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setDefaultRenderer(Object.class, new PosTableRenderer());
		table.setGridColor(Color.LIGHT_GRAY);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(120);
//		columnModel.getColumn(1).setPreferredWidth(20);
		columnModel.getColumn(1).setPreferredWidth(50);
		columnModel.getColumn(2).setPreferredWidth(120);
		columnModel.getColumn(3).setPreferredWidth(100);
		columnModel.getColumn(4).setPreferredWidth(120);
		columnModel.getColumn(5).setPreferredWidth(60);
		columnModel.getColumn(6).setPreferredWidth(60);

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
		scrollBar.setPreferredSize(new Dimension(30, 60));

		setLayout(new BorderLayout());

		add(scrollPane);
	}

	public void setTickets(List<Ticket> tickets) {
		tableModel.setRows(tickets);
	}

	public void addTicket(Ticket ticket) {
		tableModel.addItem(ticket);
	}

	public Ticket getSelectedTicket() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow < 0) {
			return null;
		}

		return (Ticket) tableModel.getRowData(selectedRow);
	}

	public List<Ticket> getSelectedTickets() {
		int[] selectedRows = table.getSelectedRows();

		ArrayList<Ticket> tickets = new ArrayList<Ticket>(selectedRows.length);

		for (int i = 0; i < selectedRows.length; i++) {
			Ticket ticket = (Ticket) tableModel.getRowData(selectedRows[i]);
			tickets.add(ticket);
		}
		
		return tickets;
	}

	// public void removeTicket(Ticket ticket) {
	// tableModel.
	// }

	private class TicketListTable extends JXTable {
		
		public TicketListTable() {
			setColumnControlVisible(true);
		}
		
		@Override
		public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
			ListSelectionModel selectionModel = getSelectionModel();
			boolean selected = selectionModel.isSelectedIndex(rowIndex);
			if (selected) {
				selectionModel.removeSelectionInterval(rowIndex, rowIndex);
			}
			else {
				selectionModel.addSelectionInterval(rowIndex, rowIndex);
			}
		}
	}

	private class TicketListTableModel extends ListTableModel {
		public TicketListTableModel() {
//			super(new String[] { POSConstants.ID, "餐桌号", POSConstants.SERVER, POSConstants.CREATED, POSConstants.CUSTOMER,
//					POSConstants.TICKET_DELIVERY_DATE, POSConstants.TICKET_TYPE, "订单状态", POSConstants.TOTAL, POSConstants.DUE });
			super(new String[] { POSConstants.ID, POSConstants.SERVER, POSConstants.CREATED, POSConstants.CUSTOMER,
					POSConstants.TICKET_DELIVERY_DATE, POSConstants.TICKET_TYPE, "订单状态", POSConstants.TOTAL, POSConstants.DUE });
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			Ticket ticket = (Ticket) rows.get(rowIndex);

			switch (columnIndex) {
			case 0:
				String uniqId = ticket.getUniqId();
				return StringUtils.isBlank(uniqId) ? Integer.valueOf(ticket.getId()) : uniqId;

//			case 1:
//				return ticket.getTableNumbers();

			case 1:
					User owner = ticket.getOwner();
					return owner.toString();

			case 2:
				return DateUtil.getTicketViewDate(ticket.getCreateDate());

			case 3:
				String customerPhone = ticket.getProperty(Ticket.CUSTOMER_PHONE);
				
				if (customerPhone != null) {
					return customerPhone;
				}

				return "非会员";

			case 4:
				return DateUtil.getTicketViewDate(ticket.getDeliveryDate());

			case 5:
				return ticket.getType().getValue();
				
			case 6:
				if(ticket.getType() == TicketType.PICKUP) {
					return "等待自取";
				}
				else if(ticket.getType() == TicketType.HOME_DELIVERY) {
					if(ticket.getAssignedDriver() == null) {
						return "未安排送货";
					}
					return "已安排送货";
				}
				else if(ticket.getType() == TicketType.DRIVE_THRU) {
					return "未穿越";
				}
				
				if(ticket.isPaid()) {
					if(ticket.getStatus() != null) {
						return TicketStatus.valueOf(ticket.getStatus()).toString();
					}
					return "已支付";
				}
				
				return "进行中";

			case 7:
				return ticket.getTotalAmount();

			case 8:
				return ticket.getDueAmount();

			}

			return null;
		}

	}
	
	public Ticket getFirstSelectedTicket() {
		List<Ticket> selectedTickets = getSelectedTickets();

		if (selectedTickets.size() == 0 || selectedTickets.size() > 1) {
			POSMessageDialog.showMessage("请选择一个订单");
			return null;
		}

		Ticket ticket = selectedTickets.get(0);

		return ticket;
	}
	
	public int getFirstSelectedTicketId() {
		Ticket ticket = getFirstSelectedTicket();
		if(ticket == null) {
			return -1;
		}
		
		return ticket.getId();
	}

	public JXTable getTable() {
		return table;
	}
}
