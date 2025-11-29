package UI.Panels;

import Backend.Customer;
import UI.Frames.MainFrame;
import UI.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.UUID;

public class CustomerPanel extends JPanel {
    MainFrame frame;
    DefaultTableModel tableModel;

    public CustomerPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Theme.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Customer Management");
        title.setFont(Theme.FONT_HEADER);
        JButton addBtn = Theme.createButton("+ Add Customer", Theme.PRIMARY, Theme.PRIMARY_FG);
        addBtn.addActionListener(e -> showCustomerDialog());

        header.add(title, BorderLayout.WEST);
        header.add(addBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        String[] cols = {"Customer ID", "Name", "Phone", "Loyalty Pts", "Total Purchases"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(tableModel);
        Theme.styleTable(table);

        table.getTableHeader().setReorderingAllowed(false);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Customer c : frame.dataService.getCustomers()) {
            tableModel.addRow(new Object[]{c.customId, c.name, c.phone, c.loyaltyPoints, String.format("₱%.2f", c.totalPurchases)});
        }
    }

    private void showCustomerDialog() {
        JDialog d = new JDialog(frame, "Add Customer", true);
        d.setLayout(new GridLayout(4, 2, 10, 10));
        d.setSize(300, 200);
        d.setLocationRelativeTo(frame);

        JTextField txtName = new JTextField();
        JTextField txtPhone = new JTextField();
        d.add(new JLabel(" Name:")); d.add(txtName);
        d.add(new JLabel(" Phone:")); d.add(txtPhone);
        d.add(new JLabel(" ID:")); d.add(new JLabel("ID will be generated"));

        JButton save = new JButton("Save");
        save.addActionListener(e -> {

            String newId = Customer.generateDisplayId();
            Customer c = new Customer(UUID.randomUUID().toString(), newId, txtName.getText(), txtPhone.getText(), 0, 0);
            frame.dataService.getCustomers().add(c);
            frame.refreshAll();
            JOptionPane.showMessageDialog(frame, "Customer Added! ID: " + newId);
            d.dispose();
        });
        d.add(new JLabel("")); d.add(save);
        d.setVisible(true);
    }
}
