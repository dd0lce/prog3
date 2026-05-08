package seoulkitchen.ui.panels;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.ui.components.RoundedPanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ClienteDetallePanel extends JPanel {

    private ClientesPanel parentPanel;

    public ClienteDetallePanel(ClientesPanel parent) {
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

        JLabel titleLabel = new JLabel("Detalle del Cliente");
        titleLabel.setFont(StyleGuide.FONT_TITLE);
        titleLabel.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        topPanel.add(titleLabel, BorderLayout.WEST);

        RoundedButton btnBack = new RoundedButton("⬅ Regresar");
        btnBack.setFont(StyleGuide.FONT_BOLD);
        btnBack.setBackground(Color.WHITE);
        btnBack.addActionListener(e -> parentPanel.showCard("List"));
        topPanel.add(btnBack, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Content Scrollable
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(StyleGuide.COLOR_BG);
        contentPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        // 1. Información Personal
        contentPanel.add(createInfoPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // 2. Estadísticas
        contentPanel.add(createStatsPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // 3. Historial de Órdenes
        contentPanel.add(createHistoryPanel());

        JScrollPane mainScroll = new JScrollPane(contentPanel);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(mainScroll, BorderLayout.CENTER);
    }

    private JPanel createInfoPanel() {
        RoundedPanel panel = new RoundedPanel(new GridBagLayout(), 20);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 20);
        gbc.weightx = 1.0;

        // Título del subpanel
        JLabel headerLabel = new JLabel("Información Personal");
        headerLabel.setFont(StyleGuide.FONT_HEADING);
        headerLabel.setForeground(StyleGuide.COLOR_SIDEBAR);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(headerLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1; gbc.weightx = 0.5;
        panel.add(createDataField("Nombre Completo", "Juan Pérez"), gbc);
        
        gbc.gridx = 1;
        panel.add(createDataField("ID Cliente", "CLI-001"), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createDataField("Email", "juan.perez@email.com"), gbc);

        gbc.gridx = 1;
        panel.add(createDataField("Teléfono", "555-0101"), gbc);

        return panel;
    }

    private JPanel createStatsPanel() {
        RoundedPanel panel = new RoundedPanel(new GridLayout(1, 2, 20, 0), 20);
        panel.setBackground(StyleGuide.COLOR_BG);
        panel.setOpaque(false); // Transparente para que se vean los bordes redondeados de los hijos sin conflicto
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        panel.add(createStatCard("Total Gastado", "$875.00", "💰"));
        panel.add(createStatCard("Órdenes Realizadas", "14", "📦"));

        return panel;
    }

    private JPanel createStatCard(String title, String value, String icon) {
        RoundedPanel card = new RoundedPanel(new BorderLayout(), 15);
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(StyleGuide.FONT_REGULAR);
        lblTitle.setForeground(StyleGuide.COLOR_TEXT_SECONDARY); // #757575

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font(StyleGuide.FONT_TITLE.getName(), Font.BOLD, 28));
        lblValue.setForeground(StyleGuide.COLOR_PRIMARY);

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font(StyleGuide.FONT_TITLE.getName(), Font.PLAIN, 36));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        card.add(lblIcon, BorderLayout.EAST);

        return card;
    }

    private JPanel createHistoryPanel() {
        RoundedPanel panel = new RoundedPanel(new BorderLayout(0, 10), 20);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel headerLabel = new JLabel("Historial de Órdenes");
        headerLabel.setFont(StyleGuide.FONT_HEADING);
        headerLabel.setForeground(StyleGuide.COLOR_SIDEBAR);
        panel.add(headerLabel, BorderLayout.NORTH);

        String[] columns = {"Orden#", "Fecha", "Total", "Estado"};
        Object[][] data = {
            {"ORD-001", "2026-05-08", "$305.00", "Completada"},
            {"ORD-014", "2026-04-12", "$120.00", "Completada"},
            {"ORD-032", "2026-03-05", "$450.00", "Cancelada"}
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
        scroll.setPreferredSize(new Dimension(100, 150));

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

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(val, BorderLayout.CENTER);
        return panel;
    }
}
