package UI.Panels;

import Backend.*;
import UI.Components.SimpleDocumentListener;
import UI.Frames.MainFrame;
import UI.Theme;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PosPanel extends JPanel {
    MainFrame frame;
    List<CartItem> cart = new ArrayList<>();
    JPanel productGrid;
    DefaultTableModel cartTableModel;
    JLabel totalLabel, pointsLabel;

    JComboBox<Customer> customerCombo;
    JTextField customerSearchField;
    JTextField searchField;
    JComboBox<String> categoryCombo;

    public PosPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.BACKGROUND);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        leftPanel.setOpaque(false);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setOpaque(false);
        searchField = new JTextField(20);
        searchField.getDocument().addDocumentListener(new SimpleDocumentListener(e -> renderProducts()));

        categoryCombo = new JComboBox<>();
        categoryCombo.addActionListener(e -> renderProducts());

        filterPanel.add(new JLabel("Search Product: "));
        filterPanel.add(searchField);
        filterPanel.add(new JLabel(" Category: "));
        filterPanel.add(categoryCombo);
        leftPanel.add(filterPanel, BorderLayout.NORTH);

        productGrid = new JPanel(new GridLayout(0, 3, 10, 10));
        productGrid.setBackground(Theme.BACKGROUND);
        JScrollPane scrollPane = new JScrollPane(productGrid);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(Theme.CARD_BG);
        rightPanel.setBorder(new CompoundBorder(new LineBorder(Theme.BORDER), new EmptyBorder(15, 15, 15, 15)));
        rightPanel.setPreferredSize(new Dimension(400, 0));

        JPanel customerPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        customerPanel.setBackground(Theme.CARD_BG);
        customerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.PRIMARY), "Customer"));

        customerPanel.add(new JLabel("Search by ID:"));
        customerSearchField = new JTextField();
        customerPanel.add(customerSearchField);
        customerPanel.add(new JLabel("Select Customer:"));
        customerCombo = new JComboBox<>();
        customerPanel.add(customerCombo);
        customerSearchField.getDocument().addDocumentListener(new SimpleDocumentListener(e -> filterCustomers()));
        rightPanel.add(customerPanel, BorderLayout.NORTH);


        String[] cols = {"Item", "Qty", "Total", "X"};
        cartTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col){
                return col == 1 || col == 3;
            }
        };
        JTable cartTable = new JTable(cartTableModel);
        Theme.styleTable(cartTable);
        cartTable.getColumnModel().getColumn(1).setMaxWidth(50);
        cartTable.getColumnModel().getColumn(3).setMaxWidth(30);

        cartTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = cartTable.rowAtPoint(e.getPoint());
                int col = cartTable.columnAtPoint(e.getPoint());
                if (col == 3) removeFromCart(row);
            }
        });

        cartTableModel.addTableModelListener(e -> {
            if (e.getColumn() == 1 && e.getFirstRow() >= 0) {
                updateQty(e.getFirstRow(), (String) cartTableModel.getValueAt(e.getFirstRow(), 1));
            }
        });


        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setBorder(BorderFactory.createEmptyBorder());
        rightPanel.add(cartScrollPane, BorderLayout.CENTER);


        JPanel footer = new JPanel(new GridLayout(3, 1, 5, 5));
        footer.setOpaque(false);
        totalLabel = new JLabel("Total: ₱0.00", SwingConstants.RIGHT);
        totalLabel.setFont(Theme.FONT_HEADER);
        totalLabel.setForeground(Theme.PRIMARY);
        pointsLabel = new JLabel("Points to earn: 0", SwingConstants.RIGHT);
        pointsLabel.setFont(Theme.FONT_REGULAR);

        JButton checkoutBtn = Theme.createButton("COMPLETE SALE", Theme.SUCCESS, Color.WHITE);
        checkoutBtn.addActionListener(e -> checkout());

        footer.add(totalLabel);
        footer.add(pointsLabel);
        footer.add(checkoutBtn);
        rightPanel.add(footer, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        refresh();
    }

    public void refresh() {
        String currentCat = (String) categoryCombo.getSelectedItem();
        categoryCombo.removeAllItems();
        categoryCombo.addItem("All");
        Set<String> cats = frame.dataService.getProducts().stream().map(p -> p.category).collect(Collectors.toSet());
        cats.forEach(categoryCombo::addItem);
        if (currentCat != null) categoryCombo.setSelectedItem(currentCat);

        filterCustomers();
        renderProducts();
    }

    private void filterCustomers() {
        String query = customerSearchField.getText().toUpperCase().trim();
        customerCombo.removeAllItems();
        for (Customer c : frame.dataService.getCustomers()) {
            if (query.isEmpty() || c.customId.toUpperCase().contains(query) || c.name.toUpperCase().contains(query)) {
                customerCombo.addItem(c);
            }
        }
    }

    private void renderProducts() {
        productGrid.removeAll();
        String search = searchField.getText().toLowerCase();
        String cat = (String) categoryCombo.getSelectedItem();

        List<Product> filtered = frame.dataService.getProducts().stream()
                .filter(p -> (cat == null || cat.equals("All") || p.category.equals(cat)))
                .filter(p -> p.name.toLowerCase().contains(search) || p.sku.toLowerCase().contains(search))
                .collect(Collectors.toList());

        for (Product p : filtered) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Theme.CARD_BG);
            card.setBorder(new LineBorder(Theme.PRIMARY_SOFT, 1));
            card.setPreferredSize(new Dimension(150, 150));

            JLabel name = new JLabel("<html><center>" + p.name + "</center></html>", SwingConstants.CENTER);
            name.setFont(Theme.FONT_BOLD);
            JLabel price = new JLabel("₱" + String.format("%.2f", p.price), SwingConstants.CENTER);
            price.setForeground(Theme.PRIMARY);
            price.setFont(Theme.FONT_TITLE);
            JLabel stock = new JLabel("Stock: " + p.stock, SwingConstants.CENTER);
            stock.setFont(Theme.FONT_MONO);
            if(p.isLowStock()) { stock.setForeground(Theme.WARNING); stock.setText("Stock: " + p.stock + " (Low)"); }

            JButton addBtn = Theme.createButton("Add", Theme.SECONDARY, Color.WHITE);
            addBtn.addActionListener(e -> addToCart(p));
            if (p.stock <= 0) { addBtn.setEnabled(false); addBtn.setText("Out of Stock"); addBtn.setBackground(Color.GRAY); }

            JPanel info = new JPanel(new GridLayout(3, 1));
            info.setOpaque(false);
            info.add(name); info.add(price); info.add(stock);
            card.add(info, BorderLayout.CENTER);
            card.add(addBtn, BorderLayout.SOUTH);
            productGrid.add(card);
        }
        productGrid.revalidate();
        productGrid.repaint();
    }

    private void addToCart(Product p) {
        int inCart = cart.stream().filter(i -> i.product.id.equals(p.id)).mapToInt(i -> i.quantity).sum();
        if (inCart >= p.stock){
            JOptionPane.showMessageDialog(this, "Not enough stock!");
            return;
        }

        Optional<CartItem> existing = cart.stream().filter(i -> i.product.id.equals(p.id)).findFirst();
        if (existing.isPresent()){
            existing.get().quantity++;
        }else{
            cart.add(new CartItem(p, 1));
        }

        updateCartTable();
    }

    private void removeFromCart(int row) {
        cart.remove(row);
        updateCartTable();
    }

    private void updateQty(int row, String val) {
        try {
            int qty = Integer.parseInt(val);
            if (qty <= 0) cart.remove(row);
            else {
                CartItem item = cart.get(row);
                if (qty > item.product.stock){
                    JOptionPane.showMessageDialog(this, "Exceeds stock level!");
                }else{
                    item.quantity = qty;
                }
            }
            updateCartTable();
        } catch (NumberFormatException e){

        }
    }

    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        double total = 0;
        for (CartItem item : cart) {
            cartTableModel.addRow(new Object[]{item.product.name, String.valueOf(item.quantity), String.format("%.2f", item.getTotal()), "X"});
            total += item.getTotal();
        }
        totalLabel.setText("Total: ₱" + String.format("%.2f", total));
        pointsLabel.setText("Points to earn: " + (int)(total * 0.01));
    }

    private void checkout() {
        if (cart.isEmpty()) return;
        Customer cust = (Customer) customerCombo.getSelectedItem();
        if (cust == null){
            JOptionPane.showMessageDialog(this, "Please select a customer.");
            return;
        }

        double total = cart.stream().mapToDouble(CartItem::getTotal).sum();

        for (CartItem item : cart) {
            Product p = frame.dataService.getProducts().stream().filter(prod -> prod.id.equals(item.product.id)).findFirst().orElse(null);
            if (p != null) p.adjustStock(-item.quantity);
        }

        Customer c = frame.dataService.getCustomers().stream().filter(cus -> cus.id.equals(cust.id)).findFirst().orElse(null);
        if (c != null) c.addPurchase(total);

        Transaction t = new Transaction(UUID.randomUUID().toString(), new Date(), new ArrayList<>(cart), total, cust.id, cust.name);
        frame.dataService.getTransactions().add(t);

        JOptionPane.showMessageDialog(this, "Sale Completed! ₱" + String.format("%.2f", total));
        cart.clear();
        updateCartTable();
        frame.refreshAll();
    }
}