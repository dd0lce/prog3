package seoulkitchen.ui.panels;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.ClienteActionCellEditorRenderer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ClientesListPanel extends JPanel {

    private ClientesPanel parentPanel;

    public ClientesListPanel(ClientesPanel parent) {
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

        JLabel titleLabel = new JLabel("Clientes");
        titleLabel.setFont(StyleGuide.FONT_TITLE);
        titleLabel.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setBackground(StyleGuide.COLOR_BG);

        JTextField searchField = new JTextField(20);
        searchField.setBorder(StyleGuide.createRoundedBorder());
        searchField.setText("Buscar cliente...");
        searchField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Buscar cliente...")) {
                    searchField.setText("");
                    searchField.setForeground(StyleGuide.COLOR_TEXT_MAIN);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Buscar cliente...");
                    searchField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
                }
            }
        });

        seoulkitchen.ui.components.RoundedButton btnNew = new seoulkitchen.ui.components.RoundedButton("+ Nuevo Cliente");
        seoulkitchen.utils.StyleGuide.stylePrimaryButton(btnNew);
        btnNew.addActionListener(e -> {
            seoulkitchen.ui.dialogs.ClienteFormDialog dialog = new seoulkitchen.ui.dialogs.ClienteFormDialog((java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this), "Crear Cliente");
            dialog.setVisible(true);
        });

        actionPanel.add(searchField);
        actionPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(10, 0)));
        actionPanel.add(btnNew);
        topPanel.add(actionPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(StyleGuide.COLOR_BG);
        tableContainer.setBorder(new EmptyBorder(0, 20, 20, 20));

        String[] columnNames = {"ID", "Nombre", "Email", "Teléfono", "Estado", "Acciones"};
        Object[][] data = {
            {"CLI-001", "Juan Pérez", "juan.perez@email.com", "555-0101", "Activo", ""},
            {"CLI-002", "María García", "maria.g@email.com", "555-0202", "Activo", ""},
            {"CLI-003", "Carlos López", "carlos.lopez@email.com", "555-0303", "Inactivo", ""}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Acciones editable
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(StyleGuide.FONT_REGULAR);
        table.setSelectionBackground(new Color(230, 230, 230));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

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

        table.getColumnModel().getColumn(4).setCellRenderer(new seoulkitchen.ui.components.BadgeRenderer());

        ClienteActionCellEditorRenderer actionRenderer = new ClienteActionCellEditorRenderer(parentPanel);
        table.getColumnModel().getColumn(5).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(5).setCellEditor(actionRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);
    }
}
