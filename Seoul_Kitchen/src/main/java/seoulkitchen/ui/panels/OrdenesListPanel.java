package seoulkitchen.ui.panels;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.BadgeRenderer;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.dao.OrdenDAO;
import seoulkitchen.model.Orden;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class OrdenesListPanel extends JPanel {
    private OrdenesPanel parentPanel;
    private DefaultTableModel model;
    private OrdenDAO ordenDAO;
    private JTable table;

    public OrdenesListPanel(OrdenesPanel parent) {
        this.parentPanel = parent;
        this.ordenDAO = new OrdenDAO();
        setupUI();
        cargarDatos();
    }
    
    public void cargarDatos() {
        model.setRowCount(0); // Limpiar tabla
        List<Orden> ordenes = ordenDAO.obtenerTodas();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        for (Orden o : ordenes) {
            String fechaStr = o.getCreadoEn() != null ? sdf.format(o.getCreadoEn()) : "";
            String cliente = o.getCliente() != null ? o.getCliente().toString() : "Cliente ID " + o.getIdCliente();
            
            model.addRow(new Object[]{
                "ORD-" + String.format("%03d", o.getId()),
                cliente,
                fechaStr,
                "$" + String.format("%.2f", o.getTotal()),
                o.getEstado(),
                o
            });
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(StyleGuide.COLOR_BG);
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
        btnNew.addActionListener(e -> {
            parentPanel.getFormPanel().clearForm();
            parentPanel.showCard("Form");
        });
        actionPanel.add(btnNew);
        topPanel.add(actionPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(StyleGuide.COLOR_BG);
        tableContainer.setBorder(new EmptyBorder(0, 20, 20, 20));
        String[] columnNames = {"Orden#", "Cliente", "Fecha", "Total", "Estado", "Acciones"};
        
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; 
            }
        };
        table = new JTable(model);
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
        table.getColumnModel().getColumn(4).setCellRenderer(new BadgeRenderer());
        
        seoulkitchen.ui.components.OrdenesActionCellEditorRenderer actionRenderer = 
            new seoulkitchen.ui.components.OrdenesActionCellEditorRenderer(this);
        table.getColumnModel().getColumn(5).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(5).setCellEditor(actionRenderer);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);
    }
}
