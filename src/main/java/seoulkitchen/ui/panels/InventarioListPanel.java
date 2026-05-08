package seoulkitchen.ui.panels;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.InventarioActionCellEditorRenderer;
import seoulkitchen.ui.components.RoundedPanel;
import seoulkitchen.ui.components.BadgeRenderer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class InventarioListPanel extends JPanel {

    private InventarioPanel parentPanel;

    public InventarioListPanel(InventarioPanel parent) {
        this.parentPanel = parent;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(StyleGuide.COLOR_BG);

        // Header and Search
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setBackground(StyleGuide.COLOR_BG);

        JPanel topHeader = new JPanel(new BorderLayout());
        topHeader.setBackground(StyleGuide.COLOR_BG);
        topHeader.setBorder(new EmptyBorder(20, 20, 10, 20));

        JLabel titleLabel = new JLabel("Inventario");
        titleLabel.setFont(StyleGuide.FONT_TITLE);
        titleLabel.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        topHeader.add(titleLabel, BorderLayout.WEST);

        JTextField searchField = new JTextField(20);
        searchField.setBorder(StyleGuide.createRoundedBorder());
        searchField.setText("Buscar ingrediente...");
        searchField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Buscar ingrediente...")) {
                    searchField.setText("");
                    searchField.setForeground(StyleGuide.COLOR_TEXT_MAIN);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Buscar ingrediente...");
                    searchField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
                }
            }
        });
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actionPanel.setBackground(StyleGuide.COLOR_BG);
        seoulkitchen.ui.components.RoundedButton btnNew = new seoulkitchen.ui.components.RoundedButton("+ Nuevo Ingrediente");
        seoulkitchen.utils.StyleGuide.stylePrimaryButton(btnNew);
        btnNew.addActionListener(e -> {
            seoulkitchen.ui.dialogs.InventarioFormDialog dialog = new seoulkitchen.ui.dialogs.InventarioFormDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), "Crear Ingrediente");
            dialog.setVisible(true);
        });

        actionPanel.add(searchField);
        actionPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(10, 0)));
        actionPanel.add(btnNew);
        topHeader.add(actionPanel, BorderLayout.EAST);
        
        topContainer.add(topHeader, BorderLayout.NORTH);

        // Dashboard de Inventario (4 tarjetas)
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        cardsPanel.setBackground(StyleGuide.COLOR_BG);
        cardsPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        cardsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        cardsPanel.add(createSummaryCard("Total Artículos", "145", "📦", StyleGuide.COLOR_TEXT_MAIN));
        cardsPanel.add(createSummaryCard("En Stock", "120", "✅", StyleGuide.COLOR_BADGE_ACTIVE_TEXT));
        cardsPanel.add(createSummaryCard("Stock Bajo", "15", "⚠️", StyleGuide.COLOR_BADGE_PENDING_TEXT));
        cardsPanel.add(createSummaryCard("Sin Stock", "10", "❌", StyleGuide.COLOR_BADGE_CANCELLED_TEXT));

        topContainer.add(cardsPanel, BorderLayout.CENTER);
        add(topContainer, BorderLayout.NORTH);

        // Table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(StyleGuide.COLOR_BG);
        tableContainer.setBorder(new EmptyBorder(0, 20, 20, 20));

        String[] columnNames = {"Ingrediente", "Categoría", "Cantidad", "Stock Mínimo", "Estado", "Acciones"};
        Object[][] data = {
            {"Arroz Blanco", "Granos", "50 kg", "10 kg", "En Stock", ""},
            {"Gochujang", "Salsas", "2 kg", "5 kg", "Stock Bajo", ""},
            {"Aceite de Sésamo", "Aceites", "0 L", "2 L", "Sin Stock", ""},
            {"Carne de Res", "Carnes", "15 kg", "10 kg", "En Stock", ""}
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
        table.setShowGrid(false);

        // Alternating row colors
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

        JTableHeader header = table.getTableHeader();
        header.setBackground(StyleGuide.COLOR_SIDEBAR);
        header.setForeground(Color.WHITE);
        header.setFont(StyleGuide.FONT_BOLD);
        header.setPreferredSize(new Dimension(100, 40));

        // Use BadgeRenderer for Estado
        table.getColumnModel().getColumn(4).setCellRenderer(new BadgeRenderer());
        
        InventarioActionCellEditorRenderer actionRenderer = new InventarioActionCellEditorRenderer(parentPanel);
        table.getColumnModel().getColumn(5).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(5).setCellEditor(actionRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);
    }

    private JPanel createSummaryCard(String title, String value, String icon, Color valueColor) {
        RoundedPanel card = new RoundedPanel(new BorderLayout(), 15);
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font(StyleGuide.FONT_REGULAR.getName(), Font.BOLD, 12));
        lblTitle.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font(StyleGuide.FONT_TITLE.getName(), Font.BOLD, 24));
        lblValue.setForeground(valueColor);

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font(StyleGuide.FONT_TITLE.getName(), Font.PLAIN, 28));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        card.add(lblIcon, BorderLayout.EAST);

        return card;
    }
}
