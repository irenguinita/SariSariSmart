package UI.Panels;

import Backend.Product;
import Database.CustomException.InvalidInputException;
import UI.Components.ActionButtonRenderer;
import UI.Frames.MainFrame;
import UI.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InventoryPanel extends JPanel {
    MainFrame frame;
    DefaultTableModel tableModel;

    public InventoryPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Theme.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Inventory Management");
        title.setFont(Theme.FONT_HEADER);
        JButton addBtn = Theme.createButton("+ Add Product", Theme.PRIMARY, Theme.PRIMARY_FG);
        addBtn.addActionListener(e -> showProductDialog(null));
        header.add(title, BorderLayout.WEST);
        header.add(addBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);


        String[] cols = {"SKU", "Name", "Category", "Price", "Stock", "Reorder Pt", "Edit"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(tableModel);
        Theme.styleTable(table);

        table.getTableHeader().setReorderingAllowed(false);

        table.getColumnModel().getColumn(6).setMaxWidth(80);
        table.getColumnModel().getColumn(6).setCellRenderer(new ActionButtonRenderer());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (row >= 0 && col == 6) {
                    String sku = (String) table.getValueAt(row, 0);
                    Product p = frame.dataService.getProducts().stream()
                            .filter(pr -> pr.sku.equals(sku))
                            .findFirst()
                            .orElse(null);
                    if (p != null) showProductDialog(p);
                }
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Product p : frame.dataService.getProducts()) {
            tableModel.addRow(new Object[]{p.sku, p.name, p.category, p.price, p.stock, p.reorderPoint, ""});
        }
    }

    private void showProductDialog(Product p) {
        JDialog d = new JDialog(frame, (p == null ? "Add" : "Edit") + " Product", true);
        d.setLayout(new GridBagLayout());
        d.setSize(400, 500);
        d.setLocationRelativeTo(frame);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        JTextField txtSku = new JTextField();
        txtSku.setEditable(false);
        if (p == null) txtSku.setText(Product.generateSKU());
        else txtSku.setText(p.sku);

        JTextField txtName = new JTextField(p != null ? p.name : "");
        JTextField txtCat = new JTextField(p != null ? p.category : "");
        JTextField txtPrice = new JTextField(p != null ? String.valueOf(p.price) : "");
        JTextField txtStock = new JTextField(p != null ? String.valueOf(p.stock) : "");
        JTextField txtReorder = new JTextField(p != null ? String.valueOf(p.reorderPoint) : "10");

        addField(d, "SKU (Auto):", txtSku, gbc);
        addField(d, "Name:", txtName, gbc);
        addField(d, "Category:", txtCat, gbc);
        addField(d, "Price:", txtPrice, gbc);
        addField(d, "Stock:", txtStock, gbc);
        addField(d, "Reorder Point:", txtReorder, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton save = Theme.createButton("Save", Theme.PRIMARY, Color.WHITE);

        save.addActionListener(e -> {
            try {
                String sku = txtSku.getText().trim();
                String name = txtName.getText().trim();
                String category = txtCat.getText().trim();
                double price = Double.parseDouble(txtPrice.getText().trim());
                int stock = Integer.parseInt(txtStock.getText().trim());
                int reorder = Integer.parseInt(txtReorder.getText().trim());

                if (name.isEmpty() || category.isEmpty()){
                    JOptionPane.showMessageDialog(d, "Name, and Category cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    throw new InvalidInputException("Fields are empty.");


                }

                frame.dataService.getInventoryManager().saveProducts(sku,
                        name,
                        category,
                        price,
                        stock,
                        reorder);

                JOptionPane.showMessageDialog(d, "Product added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.refreshAll();
                d.dispose();
            }
            catch (NumberFormatException ex) { JOptionPane.showMessageDialog(d, "Price, Stock, and Reorder must be valid numbers", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(d, "An unexpected error occurred " + ex.getMessage()); }
        });
        btnPanel.add(save);

        if (p != null) {
            JButton delete = Theme.createButton("Delete", Theme.DANGER, Color.WHITE);
            delete.addActionListener(ex -> {
                int confirm = JOptionPane.showConfirmDialog(d, "Are you sure you want to delete '" + p.name + "'?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean success = frame.dataService.getInventoryManager().deleteProductsBySku(p.sku);

                        if (success){
                            JOptionPane.showMessageDialog(d, "Product removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(d, "Product removal failed", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (RuntimeException e) {
                        JOptionPane.showMessageDialog(d, "Error deleting product or saving product to file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                    }
                    refresh();

                    d.dispose();
                }
            });
            btnPanel.add(delete);
        }

        gbc.gridy++; gbc.gridwidth = 2;
        d.add(btnPanel, gbc);
        d.setVisible(true);
    }

    private void addField(JDialog d, String label, JTextField field, GridBagConstraints gbc) {
        gbc.gridwidth = 1; gbc.gridx = 0; d.add(new JLabel(label), gbc);
        gbc.gridx = 1; d.add(field, gbc); gbc.gridy++;
    }
}