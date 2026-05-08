package seoulkitchen.ui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.panels.OrdenesPanel;
import seoulkitchen.ui.panels.OrdenesFormPanel;

public class OrdenesActionCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    
    private final JPanel renderPanel;
    private final JPanel editPanel;
    private final JButton btnEditR;
    private final JButton btnEditE;
    private JTable table;
    private int currentRow;

    public OrdenesActionCellEditorRenderer(OrdenesPanel parentPanel, OrdenesFormPanel formPanel) {
        renderPanel = createActionPanel();
        btnEditR = createActionButton("✎", new Color(33, 150, 243));
        renderPanel.add(btnEditR);

        editPanel = createActionPanel();
        btnEditE = createActionButton("✎", new Color(33, 150, 243));
        editPanel.add(btnEditE);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                if (e.getSource() == btnEditE) {
                    if (table != null) {
                        String estado = table.getValueAt(currentRow, 4).toString();
                        if (estado.equalsIgnoreCase("Completada")) {
                            JOptionPane.showMessageDialog(parentPanel, "No se puede editar una orden Completada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        formPanel.loadOrderForEdit((DefaultTableModel) table.getModel(), currentRow);
                        parentPanel.showCard("Form");
                    }
                }
            }
        };

        btnEditE.addActionListener(actionListener);
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        panel.setOpaque(true);
        return panel;
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
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
