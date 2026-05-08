package seoulkitchen.ui.panels;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.ui.components.RoundedPanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class InventarioDetallePanel extends JPanel {

    private InventarioPanel parentPanel;

    public InventarioDetallePanel(InventarioPanel parent) {
        this.parentPanel = parent;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(StyleGuide.COLOR_BG);

        // Top Navigation
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(StyleGuide.COLOR_BG);
        topPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        JLabel titleLabel = new JLabel("Ver Ingrediente: Gochujang");
        titleLabel.setFont(StyleGuide.FONT_TITLE);
        titleLabel.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        topPanel.add(titleLabel, BorderLayout.WEST);

        RoundedButton btnBack = new RoundedButton("⬅ Regresar");
        btnBack.setFont(StyleGuide.FONT_BOLD);
        btnBack.setBackground(Color.WHITE);
        btnBack.addActionListener(e -> parentPanel.showCard("List"));
        topPanel.add(btnBack, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(StyleGuide.COLOR_BG);
        contentPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        // Info Panel
        contentPanel.add(createInfoPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // History Panel (Entradas / Salidas)
        contentPanel.add(createHistoryPanel());

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createInfoPanel() {
        RoundedPanel panel = new RoundedPanel(new GridBagLayout(), 20);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 20);
        gbc.weightx = 1.0;

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.33;
        panel.add(createDataField("Ingrediente", "Gochujang"), gbc);
        
        gbc.gridx = 1;
        panel.add(createDataField("Categoría", "Salsas y Condimentos"), gbc);

        gbc.gridx = 2;
        panel.add(createDataField("Proveedor", "K-Food Suppliers S.A."), gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createDataField("Cantidad Actual", "2 kg"), gbc);

        gbc.gridx = 1;
        panel.add(createDataField("Stock Mínimo", "5 kg"), gbc);

        gbc.gridx = 2;
        panel.add(createDataField("Estado", "Stock Bajo (Crítico)"), gbc);

        return panel;
    }

    private JPanel createHistoryPanel() {
        RoundedPanel panel = new RoundedPanel(new BorderLayout(0, 10), 20);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel headerLabel = new JLabel("Historial de Movimientos");
        headerLabel.setFont(StyleGuide.FONT_HEADING);
        headerLabel.setForeground(StyleGuide.COLOR_SIDEBAR);
        panel.add(headerLabel, BorderLayout.NORTH);

        String[] columns = {"Fecha", "Tipo de Movimiento", "Cantidad", "Motivo/Origen", "Usuario"};
        Object[][] data = {
            {"2026-05-08", "🔴 Salida", "-0.5 kg", "Uso en Cocina (Orden #001)", "Chef Lee"},
            {"2026-05-06", "🔴 Salida", "-1.2 kg", "Uso en Cocina (Turno Tarde)", "Chef Park"},
            {"2026-05-01", "🟢 Entrada", "+5.0 kg", "Compra a Proveedor (Fac. #A210)", "Admin"},
            {"2026-04-28", "🔴 Salida", "-0.8 kg", "Uso en Cocina (Orden #112)", "Chef Lee"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(StyleGuide.FONT_REGULAR);
        table.setShowGrid(false);

        JTableHeader header = table.getTableHeader();
        header.setBackground(StyleGuide.COLOR_SIDEBAR);
        header.setForeground(Color.WHITE);
        header.setFont(StyleGuide.FONT_BOLD);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDataField(String labelText, String valueText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font(StyleGuide.FONT_SMALL.getName(), Font.BOLD, 12));
        lbl.setForeground(StyleGuide.COLOR_TEXT_SECONDARY); // #757575

        JLabel val = new JLabel(valueText);
        val.setFont(StyleGuide.FONT_REGULAR);
        val.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        
        // Custom styling for "Stock Bajo (Crítico)" to be red
        if (valueText.contains("Crítico") || valueText.contains("Stock Bajo")) {
            val.setForeground(StyleGuide.COLOR_BADGE_CANCELLED_TEXT);
            val.setFont(new Font(StyleGuide.FONT_REGULAR.getName(), Font.BOLD, 14));
        }

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(val, BorderLayout.CENTER);
        return panel;
    }
}
