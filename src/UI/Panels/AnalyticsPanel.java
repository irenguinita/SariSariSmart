package UI.Panels;

import Backend.CartItem;
import Backend.Transaction;
import UI.Components.SimpleBarChart;
import UI.Components.SimplePieChart;
import UI.Frames.MainFrame;
import UI.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsPanel extends JPanel {
    MainFrame frame;
    JLabel lblSales, lblTrans, lblAvg, lblItems;
    SimpleBarChart barChart;
    SimplePieChart pieChart;

    public AnalyticsPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Theme.BACKGROUND);

        JLabel title = new JLabel("Sales Analytics");
        title.setFont(Theme.FONT_HEADER);
        add(title, BorderLayout.NORTH);

        JPanel kpiPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        kpiPanel.setOpaque(false);
        kpiPanel.setPreferredSize(new Dimension(0, 100));

        lblSales = createKpiCard("Total Sales", Theme.SUCCESS);
        lblTrans = createKpiCard("Transactions", Theme.PRIMARY);
        lblAvg = createKpiCard("Avg Transaction", Theme.ACCENT);
        lblItems = createKpiCard("Products Sold", Theme.SECONDARY);

        kpiPanel.add(formatKpiPanel(lblSales, "Total Revenue", Theme.SUCCESS));
        kpiPanel.add(formatKpiPanel(lblTrans, "Completed Sales", Theme.PRIMARY));
        kpiPanel.add(formatKpiPanel(lblAvg, "Per Sale Avg", Theme.ACCENT));
        kpiPanel.add(formatKpiPanel(lblItems, "Items Sold", Theme.SECONDARY));
        add(kpiPanel, BorderLayout.CENTER);

        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsPanel.setOpaque(false);
        chartsPanel.setPreferredSize(new Dimension(0, 400));

        barChart = new SimpleBarChart();
        barChart.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.PRIMARY_SOFT), "Sales by Date"));
        barChart.setBackground(Theme.CARD_BG);

        pieChart = new SimplePieChart();
        pieChart.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.PRIMARY_SOFT), "Revenue by Category"));
        pieChart.setBackground(Theme.CARD_BG);

        chartsPanel.add(barChart);
        chartsPanel.add(pieChart);

        JPanel centerContainer = new JPanel(new BorderLayout(0, 20));
        centerContainer.setOpaque(false);
        centerContainer.add(kpiPanel, BorderLayout.NORTH);
        centerContainer.add(chartsPanel, BorderLayout.CENTER);
        add(centerContainer, BorderLayout.CENTER);
        refresh();
    }

    private JLabel createKpiCard(String title, Color color) {
        JLabel lbl = new JLabel("0");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(color);
        return lbl;
    }

    private JPanel formatKpiPanel(JLabel valueLabel, String subText, Color sideColor) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.CARD_BG);
        JPanel colorStrip = new JPanel();
        colorStrip.setBackground(sideColor);
        colorStrip.setPreferredSize(new Dimension(5, 0));
        p.add(colorStrip, BorderLayout.WEST);
        p.setBorder(new LineBorder(Theme.BORDER));

        JPanel content = new JPanel(new GridLayout(2, 1));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(15, 15, 15, 15));
        valueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        content.add(valueLabel);
        JLabel sub = new JLabel(subText);
        sub.setForeground(Theme.TEXT_MUTED);
        content.add(sub);
        p.add(content, BorderLayout.CENTER);
        return p;
    }

    public void refresh() {
        List<Transaction> trans = frame.dataService.getTransactions();
        double totalSales = trans.stream().mapToDouble(t -> t.total).sum();
        int count = trans.size();
        double avg = count > 0 ? totalSales / count : 0;
        int items = trans.stream().flatMap(t -> t.items.stream()).mapToInt(i -> i.quantity).sum();

        lblSales.setText("₱" + String.format("%.2f", totalSales));
        lblTrans.setText(String.valueOf(count));
        lblAvg.setText("₱" + String.format("%.2f", avg));
        lblItems.setText(String.valueOf(items));

        Map<String, Double> salesByDate = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        for (Transaction t : trans) {
            String date = sdf.format(t.date);
            salesByDate.put(date, salesByDate.getOrDefault(date, 0.0) + t.total);
        }
        barChart.setData(salesByDate);

        Map<String, Double> salesByCat = new HashMap<>();
        for (Transaction t : trans) {
            for (CartItem item : t.items) {
                salesByCat.put(item.product.category, salesByCat.getOrDefault(item.product.category, 0.0) + item.getTotal());
            }
        }
        pieChart.setData(salesByCat);
        repaint();
    }
}
