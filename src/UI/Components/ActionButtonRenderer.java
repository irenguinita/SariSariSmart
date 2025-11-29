package UI.Components;

import UI.Theme;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ActionButtonRenderer extends DefaultTableCellRenderer {
    private final JLabel iconLabel;

    public ActionButtonRenderer() {
        iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setIcon(new EditIcon(16, Theme.TEXT_MAIN));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (!isSelected) {
            iconLabel.setBackground(row % 2 == 0 ? Theme.CARD_BG : Theme.SURFACE_ALT);
        } else {
            iconLabel.setBackground(table.getSelectionBackground());
        }
        iconLabel.setOpaque(true);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        return iconLabel;
    }
}