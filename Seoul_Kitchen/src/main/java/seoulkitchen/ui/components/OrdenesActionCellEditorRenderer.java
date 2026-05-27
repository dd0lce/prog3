package seoulkitchen.ui.components;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.panels.OrdenesPanel;
import seoulkitchen.ui.panels.OrdenesFormPanel;
import seoulkitchen.ui.panels.OrdenesListPanel;
import seoulkitchen.model.Orden;
import seoulkitchen.dao.OrdenDAO;

public class OrdenesActionCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel renderPanel;
    private final JPanel editPanel;
    private final JButton btnEditR, btnDeleteR;
    private final JButton btnEditE, btnDeleteE;
    private JTable table;
    private OrdenesListPanel parentPanel;
    private Orden ordenActual;

    public OrdenesActionCellEditorRenderer(OrdenesListPanel parentPanel) {
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
                if (ordenActual == null) return;
                
                if (e.getSource() == btnEditE) {
                    if (ordenActual.getEstado().equalsIgnoreCase("Completada")) {
                        JOptionPane.showMessageDialog(parentPanel, "No se puede editar una orden Completada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    OrdenesPanel mainPanel = (OrdenesPanel) parentPanel.getParent();
                    OrdenesFormPanel formPanel = mainPanel.getFormPanel();
                    formPanel.loadOrderForEdit(ordenActual);
                    mainPanel.showCard("Form");
                } else if (e.getSource() == btnDeleteE) {
                    int confirm = JOptionPane.showConfirmDialog(
                        parentPanel, 
                        "¿Estás seguro de eliminar la orden " + ordenActual.getId() + "?", 
                        "Confirmar Eliminación", 
                        JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        OrdenDAO dao = new OrdenDAO();
                        if(dao.eliminar(ordenActual.getId())) {
                            parentPanel.cargarDatos();
                        } else {
                            JOptionPane.showMessageDialog(parentPanel, "Error al eliminar orden", "Error", JOptionPane.ERROR_MESSAGE);
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
        if (value instanceof Orden) {
            this.ordenActual = (Orden) value;
        }
        editPanel.setBackground(table.getSelectionBackground());
        return editPanel;
    }
    
    @Override
    public Object getCellEditorValue() {
        return ordenActual;
    }
}
