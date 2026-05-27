package seoulkitchen.ui.panels;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.ui.components.RoundedPanel;
import seoulkitchen.dao.ClienteDAO;
import seoulkitchen.dao.PlatilloDAO;
import seoulkitchen.dao.OrdenDAO;
import seoulkitchen.model.Cliente;
import seoulkitchen.model.Platillo;
import seoulkitchen.model.Orden;
import seoulkitchen.model.OrdenDetalle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class OrdenesFormPanel extends JPanel {
    private OrdenesPanel parentPanel;
    private DefaultTableModel detailModel;
    private JLabel totalLabel;
    private double totalAmount = 0.0;
    private JComboBox<Cliente> comboClient;
    private JComboBox<Platillo> comboPlatillo;
    private JTextField txtCantidad;
    private JComboBox<String> comboEstado;
    
    private List<OrdenDetalle> detallesActuales;
    private Orden ordenActualEdit;

    public OrdenesFormPanel(OrdenesPanel parent) {
        this.parentPanel = parent;
        this.detallesActuales = new ArrayList<>();
        setupUI();
    }

    public void clearForm() {
        this.ordenActualEdit = null;
        cargarCatalogos();
        detallesActuales.clear();
        detailModel.setRowCount(0);
        totalAmount = 0.0;
        totalLabel.setText("Total: $0.00");
        comboEstado.setSelectedIndex(0);
        txtCantidad.setText("1");
    }

    public void loadOrderForEdit(Orden orden) {
        clearForm();
        this.ordenActualEdit = orden;
        // Seleccionar cliente
        for (int i = 0; i < comboClient.getItemCount(); i++) {
            if (comboClient.getItemAt(i).getId() == orden.getIdCliente()) {
                comboClient.setSelectedIndex(i);
                break;
            }
        }
        comboEstado.setSelectedItem(orden.getEstado());
        
        // En un caso real también cargaríamos los detalles de la base de datos (SELECT * FROM orden_detalle)
        // Para simplificar esta demo y porque OrdenDAO.obtenerTodas() no los carga profundamente,
        // solo mostramos el total, no podemos editar los platillos de una orden existente fácilmente sin cargar los detalles.
        totalAmount = orden.getTotal();
        totalLabel.setText(String.format("Total: $%.2f", totalAmount));
    }

    private void cargarCatalogos() {
        comboClient.removeAllItems();
        ClienteDAO clienteDAO = new ClienteDAO();
        for (Cliente c : clienteDAO.obtenerTodos()) {
            if (c.isActivo()) {
                comboClient.addItem(c);
            }
        }

        comboPlatillo.removeAllItems();
        PlatilloDAO platilloDAO = new PlatilloDAO();
        for (Platillo p : platilloDAO.obtenerTodos()) {
            if (p.getEstado().equals("Activo")) {
                comboPlatillo.addItem(p);
            }
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(StyleGuide.COLOR_BG);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(StyleGuide.COLOR_BG);
        topPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        JLabel titleLabel = new JLabel("Crear/Editar Orden");
        titleLabel.setFont(StyleGuide.FONT_TITLE);
        titleLabel.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        topPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel headerRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        headerRightPanel.setBackground(StyleGuide.COLOR_BG);
        comboEstado = new JComboBox<>(new String[]{"Pendiente", "Completada", "Cancelada"});
        comboEstado.setFont(StyleGuide.FONT_REGULAR);
        headerRightPanel.add(comboEstado);
        
        RoundedButton btnBack = new RoundedButton("⬅ Regresar");
        btnBack.setFont(StyleGuide.FONT_BOLD);
        btnBack.setBackground(Color.WHITE);
        btnBack.addActionListener(e -> {
            // Refrescar listado al regresar
            ((OrdenesListPanel) parentPanel.getComponent(0)).cargarDatos();
            parentPanel.showCard("List");
        });
        headerRightPanel.add(btnBack);
        topPanel.add(headerRightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(StyleGuide.COLOR_BG);
        contentPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        
        RoundedPanel formContainer = new RoundedPanel(new GridBagLayout(), 20);
        formContainer.setBackground(Color.WHITE);
        formContainer.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formContainer.add(createLabel("Cliente:"), gbc);
        comboClient = new JComboBox<>();
        comboClient.setFont(StyleGuide.FONT_REGULAR);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formContainer.add(comboClient, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        formContainer.add(createLabel("Platillo:"), gbc);
        comboPlatillo = new JComboBox<>();
        comboPlatillo.setFont(StyleGuide.FONT_REGULAR);
        gbc.gridx = 1; gbc.weightx = 0.7;
        formContainer.add(comboPlatillo, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0.0;
        formContainer.add(createLabel("Cant:"), gbc);
        txtCantidad = new JTextField("1");
        txtCantidad.setFont(StyleGuide.FONT_REGULAR);
        txtCantidad.setBorder(StyleGuide.createRoundedBorder());
        gbc.gridx = 3; gbc.weightx = 0.1;
        formContainer.add(txtCantidad, gbc);
        
        RoundedButton btnAdd = new RoundedButton("Agregar");
        btnAdd.setBackground(StyleGuide.COLOR_SIDEBAR);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(StyleGuide.FONT_BOLD);
        btnAdd.addActionListener(e -> addPlatillo());
        gbc.gridx = 4; gbc.weightx = 0.2;
        formContainer.add(btnAdd, gbc);
        
        contentPanel.add(formContainer, BorderLayout.NORTH);
        
        String[] columns = {"Platillo", "Precio Unitario", "Cantidad", "Subtotal"};
        detailModel = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable detailTable = new JTable(detailModel);
        detailTable.setRowHeight(35);
        detailTable.setFont(StyleGuide.FONT_REGULAR);
        detailTable.setShowGrid(false);
        detailTable.setIntercellSpacing(new Dimension(0, 0));
        JTableHeader header = detailTable.getTableHeader();
        header.setBackground(StyleGuide.COLOR_SIDEBAR);
        header.setForeground(Color.WHITE);
        header.setFont(StyleGuide.FONT_BOLD);
        JScrollPane scrollPane = new JScrollPane(detailTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(StyleGuide.COLOR_BG);
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font(StyleGuide.FONT_TITLE.getName(), Font.BOLD, 22));
        totalLabel.setForeground(StyleGuide.COLOR_PRIMARY);
        bottomPanel.add(totalLabel, BorderLayout.WEST);
        
        RoundedButton btnSave = new RoundedButton("Guardar Orden");
        StyleGuide.stylePrimaryButton(btnSave);
        btnSave.addActionListener(e -> saveOrder());
        bottomPanel.add(btnSave, BorderLayout.EAST);
        
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);
        
        cargarCatalogos();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(StyleGuide.FONT_BOLD);
        label.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        return label;
    }

    private void addPlatillo() {
        if (ordenActualEdit != null) {
            JOptionPane.showMessageDialog(this, "En esta versión no se pueden editar los platillos de una orden existente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Platillo selected = (Platillo) comboPlatillo.getSelectedItem();
        if (selected == null) return;
        
        int cant;
        try {
            cant = Integer.parseInt(txtCantidad.getText().trim());
            if (cant <= 0) throw new NumberFormatException();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        OrdenDetalle detalle = new OrdenDetalle(selected.getId(), cant, selected.getPrecio());
        detallesActuales.add(detalle);
        
        double subtotal = selected.getPrecio() * cant;
        detailModel.addRow(new Object[]{
            selected.getNombre(), 
            String.format("$%.2f", selected.getPrecio()), 
            cant, 
            String.format("$%.2f", subtotal)
        });
        
        totalAmount += subtotal;
        totalLabel.setText(String.format("Total: $%.2f", totalAmount));
    }

    private void saveOrder() {
        OrdenDAO dao = new OrdenDAO();
        
        if (ordenActualEdit != null) {
            // Solo actualizamos el estado
            if (dao.actualizarEstado(ordenActualEdit.getId(), comboEstado.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Estado de orden actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar estado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Es una nueva orden
            if (detallesActuales.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay platillos en la orden.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Cliente cliente = (Cliente) comboClient.getSelectedItem();
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Orden orden = new Orden(cliente.getId(), totalAmount, comboEstado.getSelectedItem().toString());
            orden.setDetalles(detallesActuales);
            
            if (dao.agregar(orden)) {
                JOptionPane.showMessageDialog(this, "Orden guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar orden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        clearForm();
        ((OrdenesListPanel) parentPanel.getComponent(0)).cargarDatos();
        parentPanel.showCard("List");
    }
}
