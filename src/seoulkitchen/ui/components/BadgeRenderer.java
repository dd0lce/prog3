package seoulkitchen.ui.components;

import seoulkitchen.utils.StyleGuide;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class BadgeRenderer extends DefaultTableCellRenderer {
    
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2));
    private final JLabel label = new JLabel();

    public BadgeRenderer() {
        panel.setOpaque(true);
        label.setOpaque(true);
        label.setFont(StyleGuide.FONT_BOLD);
        label.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        panel.add(label);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            
        String status = value != null ? value.toString().toUpperCase() : "";
        label.setText(status);
        
        if (status.equals("ACTIVO") || status.equals("COMPLETADO")) {
            label.setBackground(StyleGuide.COLOR_BADGE_ACTIVE_BG);
            label.setForeground(StyleGuide.COLOR_BADGE_ACTIVE_TEXT);
        } else if (status.equals("PENDIENTE")) {
            label.setBackground(StyleGuide.COLOR_BADGE_PENDING_BG);
            label.setForeground(StyleGuide.COLOR_BADGE_PENDING_TEXT);
        } else if (status.equals("CANCELADA") || status.equals("AGOTADO") || status.equals("INACTIVO")) {
            label.setBackground(StyleGuide.COLOR_BADGE_CANCELLED_BG);
            label.setForeground(StyleGuide.COLOR_BADGE_CANCELLED_TEXT);
        } else {
            label.setBackground(Color.LIGHT_GRAY);
            label.setForeground(Color.BLACK);
        }
        
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
        
        return panel;
    }
}
