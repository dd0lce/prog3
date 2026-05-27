package seoulkitchen.ui.panels;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.BadgeRenderer;
import seoulkitchen.ui.components.PlatilloActionCellEditorRenderer;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.ui.dialogs.PlatilloFormDialog;
import seoulkitchen.dao.PlatilloDAO;
import seoulkitchen.model.Platillo;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PlatillosPanel extends JPanel {
    private DefaultTableModel model;
    private PlatilloDAO platilloDAO;
    private JTable table;

    public PlatillosPanel() {
        this.platilloDAO = new PlatilloDAO();
        setupUI();
        cargarDatos();
    }
    
    public void cargarDatos() {
        model.setRowCount(0); // Limpiar tabla
        List<Platillo> platillos = platilloDAO.obtenerTodos();
        for (Platillo p : platillos) {
            // El ActionCellEditorRenderer necesitará el objeto completo o el ID. 
            // Podemos guardar el Platillo en la primera columna o simplemente su ID.
            // Para simplicidad, guardaremos el Platillo en la primera columna o usaremos los datos de las columnas.
            // Es mejor guardar el ID real.
            model.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getCategoria(),
                "$" + String.format("%.2f", p.getPrecio()),
                p.getEstado(),
                p // Pasamos el objeto platillo completo a la celda de acciones para poder editarlo/borrarlo
            });
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(StyleGuide.COLOR_BG);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(StyleGuide.COLOR_BG);
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("Platillos");
        titleLabel.setFont(StyleGuide.FONT_TITLE);
        titleLabel.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        topPanel.add(titleLabel, BorderLayout.WEST);
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionPanel.setBackground(StyleGuide.COLOR_BG);
        JTextField searchField = new JTextField(20);
        searchField.setBorder(StyleGuide.createRoundedBorder());
        searchField.setText("Buscar platillo...");
        searchField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Buscar platillo...")) {
                    searchField.setText("");
                    searchField.setForeground(StyleGuide.COLOR_TEXT_MAIN);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Buscar platillo...");
                    searchField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
                }
            }
        });
        RoundedButton btnNew = new RoundedButton("+ Nuevo Platillo");
        StyleGuide.stylePrimaryButton(btnNew);
        btnNew.addActionListener(e -> {
            PlatilloFormDialog formDialog = new PlatilloFormDialog(
                (Frame) SwingUtilities.getWindowAncestor(this), 
                "Crear Platillo", 
                this::cargarDatos
            );
            formDialog.setVisible(true);
        });
        actionPanel.add(searchField);
        actionPanel.add(btnNew);
        topPanel.add(actionPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(StyleGuide.COLOR_BG);
        tableContainer.setBorder(new EmptyBorder(0, 20, 20, 20));
        String[] columnNames = {"ID", "Nombre", "Categoría", "Precio", "Estado", "Acciones"};
        
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
        
        PlatilloActionCellEditorRenderer actionRenderer = new PlatilloActionCellEditorRenderer(this);
        table.getColumnModel().getColumn(5).setCellRenderer(actionRenderer);
        table.getColumnModel().getColumn(5).setCellEditor(actionRenderer);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);
    }
}
