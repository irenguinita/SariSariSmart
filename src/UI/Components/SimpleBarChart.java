package UI.Components;

import UI.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SimpleBarChart extends JPanel {
    private Map<String, Double> data = new HashMap<>();
    public void setData(Map<String, Double> data) { this.data = new TreeMap<>(data); repaint(); }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data.isEmpty()) { g.drawString("No data available", getWidth()/2 - 40, getHeight()/2); return; }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int pad = 40; int h = getHeight() - 2 * pad;
        double maxVal = data.values().stream().mapToDouble(v -> v).max().orElse(1);
        int barWidth = Math.max(10, (getWidth() - 2*pad) / data.size() - 10); int x = pad;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            int barHeight = (int) ((entry.getValue() / maxVal) * h);
            g2.setColor(Theme.PRIMARY);
            g2.fillRoundRect(x, getHeight() - pad - barHeight, barWidth, barHeight, 5, 5);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            g2.drawString(entry.getKey(), x, getHeight() - pad + 15);
            x += barWidth + 10;
        }
    }
}
