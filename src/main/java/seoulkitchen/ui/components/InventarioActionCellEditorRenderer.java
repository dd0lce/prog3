package seoulkitchen.ui.components;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.dialogs.InventarioFormDialog;
import seoulkitchen.ui.panels.InventarioPanel;
import seoulkitchen.model.InventarioItem;
import seoulkitchen.dao.InventarioDAO;

public class InventarioActionCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel renderPanel;
    private final JPanel editPanel;
    private final JButton btnEditR, btnDeleteR;
    private final JButton btnEditE, btnDeleteE;
    private JTable table;
    private InventarioPanel parentPanel;
    private InventarioItem itemActual;

    public InventarioActionCellEditorRenderer(InventarioPanel parentPanel) {
        this.parentPanel = parentPanel;
        renderPanel = createActionPanel();
        btnEditR = createActionButton("✎", new Color(33, 150, 243)); 
        btnDeleteR = createActionButton("🗑", new Color(244, 67, 54));
        renderPanel.add(btnEditR);
        renderPanel.add(btnDeleteR);
        
        editPanel = createActionPanel();
        btnEditE = createActionButton("✎", new Color(33, 150, 243));
        btnDeleteE = createActionButton("🗑", new Color(244, 67, 54));
        editPanel.add(btnEditE);
        editPanel.add(btnDeleteE);
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                if (itemActual == null) return;
                
                if (e.getSource() == btnEditE) {
                    InventarioFormDialog formDialog = new InventarioFormDialog(
                        (Frame) SwingUtilities.getWindowAncestor(parentPanel), 
                        "Editar Inventario", 
                        parentPanel::cargarDatos
                    );
                    formDialog.loadDataForEdit(itemActual);
                    formDialog.setVisible(true);
                } else if (e.getSource() == btnDeleteE) {
                    int confirm = JOptionPane.showConfirmDialog(
                        parentPanel, 
                        "¿Estás seguro de eliminar el artículo " + itemActual.getNombre() + "?", 
                        "Confirmar Eliminación", 
                        JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        InventarioDAO dao = new InventarioDAO();
                        if(dao.eliminar(itemActual.getId())) {
                            parentPanel.cargarDatos();
                        } else {
                            JOptionPane.showMessageDialog(parentPanel, "Error al eliminar artículo", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        };
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
            renderPanel.setBackground(row % 2 == 0 ? Color.WHITE : new Color(253, 249, 241));
        }
        return renderPanel;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        if (value instanceof InventarioItem) {
            this.itemActual = (InventarioItem) value;
        }
        editPanel.setBackground(table.getSelectionBackground());
        return editPanel;
    }
    
    @Override
    public Object getCellEditorValue() {
        return itemActual;
    }
}
