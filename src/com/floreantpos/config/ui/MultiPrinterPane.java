package com.floreantpos.config.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.floreantpos.model.Printer;
import com.floreantpos.model.VirtualPrinter;
import com.floreantpos.ui.dialog.POSMessageDialog;

public class MultiPrinterPane extends JPanel {

  private JList<Printer> list;
  private List<Printer> printers;
  private DefaultListModel<Printer> listModel;

  public MultiPrinterPane() {

  }

  public MultiPrinterPane(String title, List<Printer> printers) {
    this.printers = printers;

    setBorder(BorderFactory.createTitledBorder(title));
    setLayout(new BorderLayout(10, 10));

    JPanel panel = new JPanel();
    add(panel, BorderLayout.SOUTH);

    JButton btnAdd = new JButton("添加");
    btnAdd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doAddPrinter();
      }
    });
    panel.add(btnAdd);

    JButton btnEdit = new JButton("编辑");
    btnEdit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        doEditPrinter();
      }
    });
    panel.add(btnEdit);

    JScrollPane scrollPane = new JScrollPane();
    add(scrollPane, BorderLayout.CENTER);

    listModel = new DefaultListModel<Printer>();
    list = new JList<Printer>(listModel);
    scrollPane.setViewportView(list);

    if (printers != null) {
      for (Printer printer : printers) {
        listModel.addElement(printer);
      }
    }

  }

  protected void doEditPrinter() {
    Printer printer = list.getSelectedValue();
    if (printer == null) {
      return;
    }

    AddPrinterDialog dialog = new AddPrinterDialog();
    dialog.setPrinter(printer);
    dialog.open();

    if (dialog.isCanceled()) {
      return;
    }

    Printer p = dialog.getPrinter();

    if (p.isDefaultPrinter()) {
      for (Printer printer2 : printers) {
        printer2.setDefaultPrinter(false);
      }
    }

    printer.setDefaultPrinter(true);
  }

  protected void doAddPrinter() {
    AddPrinterDialog dialog = new AddPrinterDialog();
    dialog.open();

    if (dialog.isCanceled()) {
      return;
    }

    Printer p = dialog.getPrinter();

    if (p.isDefaultPrinter()) {
      for (Printer printer : printers) {
        printer.setDefaultPrinter(false);
      }
    }

    VirtualPrinter virtualPrinter = p.getVirtualPrinter();

    for (Printer printer : printers) {
      if (virtualPrinter.equals(printer.getVirtualPrinter())) {
        // POSMessageDialog.showError(this.getParent(), "A printer with that virtual printer already
        // exists.该打印机已经存在于对应虚拟打印机中了.");
        POSMessageDialog.showError(this.getParent(), "该打印机已经存在于对应的虚拟打印机中了.");
        return;
      }
    }

    printers.add(p);
    listModel.addElement(p);
  }
}
