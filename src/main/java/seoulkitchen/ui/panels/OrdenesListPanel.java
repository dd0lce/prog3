package seoulkitchen.ui.panels;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.BadgeRenderer;
import seoulkitchen.ui.components.RoundedButton;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class OrdenesListPanel extends JPanel {

    private OrdenesPanel parentPanel;

    public OrdenesListPanel(OrdenesPanel parent) {
        this.parentPanel = parent;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(StyleGuide.COLOR_BG);

        // Header and Actions
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(StyleGuide.COLOR_BG);
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Órdenes");
        titleLabel.setFont(StyleGuide.FONT_TITLE);
        titleLabel.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setBackground(StyleGuide.COLOR_BG);

        RoundedButton btnNew = new RoundedButton("+ Nueva Orden");
        StyleGuide.stylePrimaryButton(btnNew);
        btnNew.addActionListener(e -> parentPanel.showCard("Form"));

        actionPanel.add(btnNew);
        topPanel.add(actionPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(StyleGuide.COLOR_BG);
        tableContainer.setBorder(new EmptyBorder(0, 20, 20, 20));

        String[] columnNames = {"Orden#", "Cliente", "Fecha", "Total", "Estado", "Acciones"};
        Object[][] data = {
            {"ORD-001", "Juan Pérez", "2026-05-08", "$305.00", "Completada", ""},
            {"ORD-002", "María García", "2026-05-08", "$120.00", "Pendiente", ""},
            {"ORD-003", "Carlos López", "2026-05-08", "$450.00", "Cancelada", ""}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Solo acciones
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(StyleGuide.FONT_REGULAR);
        table.setSelectionBackground(new Color(230, 230, 230));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Filas alternadas en crema
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(253, 249, 241));
                }
                return c;
            }
        });

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(StyleGuide.COLOR_SIDEBAR);
        header.setForeground(Color.WHITE);
        header.setFont(StyleGuide.FONT_BOLD);
        header.setPreferredSize(new Dimension(100, 40));

        // Badge Renderer for Status
        table.getColumnModel().getColumn(4).setCellRenderer(new BadgeRenderer());

        seoulkitchen.ui.components.OrdenesActionCellEditorRenderer actionRenderer = new seoulkitchen.ui.components.OrdenesActionCellEditorRenderer(parentPanel, parentPanel.getFormPanel());
        table.getColumnModel().getColumn(5).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(5).setCellEditor(actionRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);
    }
}
