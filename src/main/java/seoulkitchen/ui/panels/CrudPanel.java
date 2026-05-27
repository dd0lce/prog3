package seoulkitchen.ui.panels;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.BadgeRenderer;
import seoulkitchen.ui.components.ActionCellEditorRenderer;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.ui.dialogs.FormDialog;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
public class CrudPanel extends JPanel {
    private final String title;
    public CrudPanel(String title) {
        this.title = title;
        setupUI();
    }
    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(StyleGuide.COLOR_BG);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(StyleGuide.COLOR_BG);
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(StyleGuide.FONT_TITLE);
        titleLabel.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        topPanel.add(titleLabel, BorderLayout.WEST);
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setBackground(StyleGuide.COLOR_BG);
        JTextField searchField = new JTextField(20);
        searchField.setBorder(StyleGuide.createRoundedBorder());
        searchField.setText("Buscar...");
        searchField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Buscar...")) {
                    searchField.setText("");
                    searchField.setForeground(StyleGuide.COLOR_TEXT_MAIN);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Buscar...");
                    searchField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
                }
            }
        });
        RoundedButton btnNew = new RoundedButton("+ Nuevo");
        StyleGuide.stylePrimaryButton(btnNew);
        btnNew.addActionListener(e -> {
            FormDialog formDialog = new FormDialog((Frame) SwingUtilities.getWindowAncestor(this), "Crear " + title);
            formDialog.setVisible(true);
        });
        actionPanel.add(searchField);
        actionPanel.add(btnNew);
        topPanel.add(actionPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(StyleGuide.COLOR_BG);
        tableContainer.setBorder(new EmptyBorder(0, 20, 20, 20));
        String[] columnNames = {"ID", "Nombre", "Precio / Info", "Estado", "Acciones"};
        Object[][] data = {
            {"001", "Bibimbap Especial", "$185.00", "Activo", ""},
            {"002", "Tteokbokki", "$120.00", "Agotado", ""},
            {"003", "Kimbap de Atún", "$95.00", "Activo", ""}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; 
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(StyleGuide.FONT_REGULAR);
        table.setSelectionBackground(new Color(230, 230, 230));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        JTableHeader header = table.getTableHeader();
        header.setBackground(StyleGuide.COLOR_SIDEBAR);
        header.setForeground(Color.WHITE);
        header.setFont(StyleGuide.FONT_BOLD);
        header.setPreferredSize(new Dimension(100, 40));
        table.getColumnModel().getColumn(3).setCellRenderer(new BadgeRenderer());
        ActionCellEditorRenderer actionRenderer = new ActionCellEditorRenderer(this);
        table.getColumnModel().getColumn(4).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(4).setCellEditor(actionRenderer);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);
    }
}
