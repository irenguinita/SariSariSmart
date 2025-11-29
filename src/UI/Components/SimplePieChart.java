package UI.Components;

import UI.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.HashMap;
import java.util.Map;

public class SimplePieChart extends JPanel {
    private Map<String, Double> data = new HashMap<>();
    public void setData(Map<String, Double> data) { this.data = data; repaint(); }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data.isEmpty()) { g.drawString("No data available", getWidth()/2 - 40, getHeight()/2); return; }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double total = data.values().stream().mapToDouble(v -> v).sum();
        int diameter = Math.min(getWidth(), getHeight()) - 60;
        int startAngle = 0; int i = 0; int legendY = 20;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            double angle = (entry.getValue() / total) * 360;
            g2.setColor(Theme.CHART_COLORS[i % Theme.CHART_COLORS.length]);
            g2.fill(new Arc2D.Double(20, 20, diameter, diameter, startAngle, angle, Arc2D.PIE));
            g2.fillRect(getWidth() - 100, legendY, 10, 10);
            g2.setColor(Color.BLACK);
            g2.drawString(entry.getKey(), getWidth() - 85, legendY + 10);
            startAngle += angle; i++; legendY += 20;
        }
    }
}
