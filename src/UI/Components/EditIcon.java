package UI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class EditIcon implements Icon {
    private int size;
    private Color color;

    public EditIcon(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1.5f));

        // drawing the pencil
        g2.drawLine(x + 12, y + 2, x + 14, y + 4);
        g2.drawLine(x + 10, y + 4, x + 12, y + 6);

        Path2D p = new Path2D.Double();
        p.moveTo(x + 10, y + 4);
        p.lineTo(x + 4, y + 10);
        p.lineTo(x + 6, y + 12);
        p.lineTo(x + 12, y + 6);
        p.closePath();
        g2.draw(p);

        g2.drawLine(x + 4, y + 10, x + 2, y + 14);
        g2.drawLine(x + 2, y + 14, x + 6, y + 12);

        g2.dispose();
    }

    @Override
    public int getIconWidth() { return size; }

    @Override
    public int getIconHeight() { return size; }
}
