package com.micropoplar.pos.bo.ui.explorer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXTable;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.bo.ui.explorer.ListTableModel;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.PosTableRenderer;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.ConfirmDeleteDialog;
import com.micropoplar.pos.dao.CompaignDAO;
import com.micropoplar.pos.model.Compaign;
import com.micropoplar.pos.ui.form.CompaignForm;

public class CompaignExplorer extends TransparentPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JXTable compaignTable;
  private List<Compaign> compaigns;

  public CompaignExplorer() {
    initComponents();
  }

  private void initComponents() {
    setLayout(new BorderLayout());

    // table area
    compaignTable = new JXTable(new CompaignExplorerTableModel());
    compaignTable.setDefaultRenderer(Object.class, new PosTableRenderer());
    compaignTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    compaignTable.setColumnControlVisible(true);
    compaignTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    compaignTable.setHorizontalScrollEnabled(false);

    add(new JScrollPane(compaignTable), BorderLayout.CENTER);

    // button controls area
    JButton addButton = new JButton(POSConstants.ADD);
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
        } catch (Exception x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }

    });

    final JButton editButton = new JButton(POSConstants.EDIT);
    editButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int index = compaignTable.getSelectedRow();
          if (index < 0)
            return;

          Compaign compaign = compaigns.get(index);
          CompaignForm editor = new CompaignForm();
          editor.setBean(compaign);
          BeanEditorDialog dialog =
              new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
          dialog.open();
          if (dialog.isCanceled())
            return;

          compaignTable.repaint();
        } catch (Throwable x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }
    });
    compaignTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
          editButton.doClick();
        }
      }
    });

    JButton deleteButton = new JButton(POSConstants.DELETE);
    deleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int index = compaignTable.getSelectedRow();
          if (index < 0)
            return;

          if (ConfirmDeleteDialog.showMessage(CompaignExplorer.this, POSConstants.CONFIRM_DELETE,
              POSConstants.DELETE) == ConfirmDeleteDialog.YES) {
            Compaign compaign = compaigns.get(index);

            CompaignDAO dao = CompaignDAO.getInstance();
            dao.delete(compaign);
            compaignTable.remove(index);
          }
        } catch (Exception x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }

    });

    TransparentPanel panel = new TransparentPanel();
    panel.add(addButton);
    panel.add(editButton);
    panel.add(deleteButton);
    add(panel, BorderLayout.SOUTH);
  }

  class CompaignExplorerTableModel extends ListTableModel<Compaign> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // @formatter:off
    private String[] columnNames = {
        POSConstants.COMPAIGN_EXPLORER_TABLE_NAME,
        POSConstants.COMPAIGN_EXPLORER_TABLE_STATUS, 
        POSConstants.COMPAIGN_EXPLORER_TABLE_TYPE,
        POSConstants.COMPAIGN_EXPLORER_TABLE_EXCLUSIVE,
        POSConstants.COMPAIGN_EXPLORER_TABLE_PRIORITY, 
        POSConstants.COMPAIGN_EXPLORER_TABLE_GLOBAL,
        POSConstants.COMPAIGN_EXPLORER_TABLE_PER_TICKET,
        POSConstants.COMPAIGN_EXPLORER_TABLE_MEMBERSHIP,
        POSConstants.COMPAIGN_EXPLORER_TABLE_TARGET, 
        POSConstants.COMPAIGN_EXPLORER_TABLE_PERIOD
    };
    // @formatter:on

    @Override
    public int getRowCount() {
      if (compaigns == null) {
        return 0;
      }
      return compaigns.size();
    }

    @Override
    public int getColumnCount() {
      return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
      return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      if (compaigns == null)
        return "";

      Compaign compaign = compaigns.get(rowIndex);
      if (compaign == null) {
        return "";
      }

      switch (columnIndex) {
        case 0:
          return compaign.getName();

        case 1:
          Boolean enabled = compaign.getEnabled();
          if (enabled == null || !enabled) {
            return POSConstants.COMPAIGN_EXPLORER_TABLE_STATUS_OFF;
          } else {
            return POSConstants.COMPAIGN_EXPLORER_TABLE_STATUS_ON;
          }

        case 2:
          return compaign.getCompaignType().getDescription();

        case 3:
          Boolean exclusive = compaign.getExclusive();
          if (exclusive == null || !exclusive) {
            return POSConstants.COMMON_NO;
          } else {
            return POSConstants.COMMON_YES;
          }

        case 4:
          return compaign.getPriority();

        case 5:
          Boolean global = compaign.getGlobal();
          if (global == null || !global) {
            return POSConstants.COMMON_NO;
          } else {
            return POSConstants.COMMON_YES;
          }

        case 6:
          Boolean perTicket = compaign.getPerTicket();
          if (perTicket == null || !perTicket) {
            return POSConstants.COMMON_NO;
          } else {
            return POSConstants.COMMON_YES;
          }

        case 7:
          Boolean membership = compaign.getMembership();
          if (membership == null || !membership) {
            return POSConstants.COMMON_NO;
          } else {
            return POSConstants.COMMON_YES;
          }

        case 8:
          return compaign.omittedTargets();

        case 9:
          return compaign.omittedPeriods();
      }

      return null;
    }
  }
}
