package seoulkitchen.ui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.panels.ClientesPanel;

public class ClienteActionCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    
    private final JPanel renderPanel;
    private final JPanel editPanel;
    private final JButton btnViewR, btnEditR;
    private final JButton btnViewE, btnEditE;
    private JTable table;
    private int currentRow;

    public ClienteActionCellEditorRenderer(ClientesPanel parentPanel) {
        renderPanel = createActionPanel();
        btnViewR = createActionButton("👁", StyleGuide.COLOR_PRIMARY);
        btnEditR = createActionButton("✎", new Color(33, 150, 243));
        renderPanel.add(btnViewR);
        renderPanel.add(btnEditR);

        editPanel = createActionPanel();
        btnViewE = createActionButton("👁", StyleGuide.COLOR_PRIMARY);
        btnEditE = createActionButton("✎", new Color(33, 150, 243));
        editPanel.add(btnViewE);
        editPanel.add(btnEditE);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                if (e.getSource() == btnViewE) {
                    parentPanel.showCard("Detalle");
                } else if (e.getSource() == btnEditE) {
                    seoulkitchen.ui.dialogs.ClienteFormDialog dialog = new seoulkitchen.ui.dialogs.ClienteFormDialog((Frame) SwingUtilities.getWindowAncestor(parentPanel), "Editar Cliente");
                    if (table != null) {
                        dialog.loadDataForEdit((javax.swing.table.DefaultTableModel) table.getModel(), currentRow);
                    }
                    dialog.setVisible(true);
                }
            }
        };

        btnViewE.addActionListener(actionListener);
        btnEditE.addActionListener(actionListener);
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        panel.setOpaque(true);
        return panel;
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Symbol", Font.BOLD, 12));
        button.setForeground(color);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            renderPanel.setBackground(table.getSelectionBackground());
        } else {
            renderPanel.setBackground(row % 2 == 0 ? Color.WHITE : new Color(253, 249, 241));
        }
        return renderPanel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        this.currentRow = row;
        editPanel.setBackground(table.getSelectionBackground());
        return editPanel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
