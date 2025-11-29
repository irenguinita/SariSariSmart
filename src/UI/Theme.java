package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Theme {
    public static final Color BACKGROUND = new Color(245, 244, 235);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color SURFACE_ALT = new Color(238, 236, 225);

    public static final Color PRIMARY = new Color(103, 114, 57);
    public static final Color PRIMARY_SOFT = new Color(138, 150, 85);
    public static final Color PRIMARY_FG = new Color(245, 244, 235);

    public static final Color SECONDARY = new Color(194, 98, 55);
    public static final Color SECONDARY_SOFT = new Color(215, 125, 85);

    public static final Color ACCENT = new Color(249, 168, 86);

    public static final Color TEXT_MAIN = new Color(60, 45, 40);
    public static final Color TEXT_MUTED = new Color(120, 110, 105);

    public static final Color SUCCESS = new Color(132, 153, 79);
    public static final Color WARNING = new Color(234, 179, 8);
    public static final Color DANGER = new Color(220, 38, 38);

    public static final Color BORDER = new Color(220, 220, 210);

    public static final Color TEAL = new Color(45, 110, 120);
    public static final Color CLAY = new Color(160, 82, 45);
    public static final Color GOLD = new Color(218, 165, 32);
    public static final Color SAGE = new Color(143, 188, 143);
    public static final Color SLATE = new Color(112, 128, 144);

    public static final Color[] CHART_COLORS = {
            PRIMARY, SECONDARY, ACCENT, TEAL, GOLD, CLAY, SAGE, SLATE
    };

    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_MONO = new Font("Monospaced", Font.PLAIN, 13);

    public static void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER);
        table.setFont(FONT_REGULAR);
        table.setForeground(TEXT_MAIN);

        table.getTableHeader().setFont(FONT_BOLD);
        table.getTableHeader().setBackground(PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setOpaque(true);

        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(TEXT_MAIN);

        // a custom renderer that handles background colors and removes the cell focus border
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? CARD_BG : SURFACE_ALT);
                }

                ((JLabel) c).setBorder(new EmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
    }

    public static JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(bg.darker());
                } else {
                    g2.setColor(bg);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(FONT_BOLD);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }
}