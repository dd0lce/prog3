package seoulkitchen.ui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.dialogs.DetailDialog;
import seoulkitchen.ui.dialogs.FormDialog;

public class ActionCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    
    private final JPanel renderPanel;
    private final JPanel editPanel;
    private final JButton btnViewR, btnEditR, btnDeleteR;
    private final JButton btnViewE, btnEditE, btnDeleteE;
    private JTable table;
    private int currentRow;

    public ActionCellEditorRenderer(Component parentComponent) {
        renderPanel = createActionPanel();
        btnViewR = createActionButton("👁", StyleGuide.COLOR_PRIMARY); 
        btnEditR = createActionButton("✎", new Color(33, 150, 243)); 
        btnDeleteR = createActionButton("🗑", StyleGuide.COLOR_BADGE_CANCELLED_TEXT); 
        renderPanel.add(btnViewR);
        renderPanel.add(btnEditR);
        renderPanel.add(btnDeleteR);

        editPanel = createActionPanel();
        btnViewE = createActionButton("👁", StyleGuide.COLOR_PRIMARY);
        btnEditE = createActionButton("✎", new Color(33, 150, 243));
        btnDeleteE = createActionButton("🗑", StyleGuide.COLOR_BADGE_CANCELLED_TEXT);
        editPanel.add(btnViewE);
        editPanel.add(btnEditE);
        editPanel.add(btnDeleteE);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                if (e.getSource() == btnViewE) {
                    DetailDialog detailDialog = new DetailDialog((Frame) SwingUtilities.getWindowAncestor(parentComponent), "Detalle de Registro");
                    detailDialog.setVisible(true);
                } else if (e.getSource() == btnEditE) {
                    FormDialog formDialog = new FormDialog((Frame) SwingUtilities.getWindowAncestor(parentComponent), "Editar Registro");
                    formDialog.setVisible(true);
                } else if (e.getSource() == btnDeleteE) {
                    JOptionPane.showMessageDialog(parentComponent, "Registro eliminado exitosamente.", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };

        btnViewE.addActionListener(actionListener);
        btnEditE.addActionListener(actionListener);
        btnDeleteE.addActionListener(actionListener);
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        panel.setOpaque(true);
        return panel;
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
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
            renderPanel.setBackground(table.getBackground());
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
