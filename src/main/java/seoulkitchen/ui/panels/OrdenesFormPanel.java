package seoulkitchen.ui.panels;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.ui.components.RoundedPanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class OrdenesFormPanel extends JPanel {

    private OrdenesPanel parentPanel;
    private DefaultTableModel detailModel;
    private JLabel totalLabel;
    private double totalAmount = 0.0;
    
    private JComboBox<String> comboClient;
    private JComboBox<String> comboPlatillo;
    private JTextField txtCantidad;
    private JComboBox<String> comboEstado;

    private int editRow = -1;
    private DefaultTableModel mainModel = null;

    public OrdenesFormPanel(OrdenesPanel parent) {
        this.parentPanel = parent;
        setupUI();
    }

    public void loadOrderForEdit(DefaultTableModel model, int row) {
        this.mainModel = model;
        this.editRow = row;
        
        
        String client = model.getValueAt(row, 1).toString();
        comboClient.setSelectedItem(client);
        
        String estado = model.getValueAt(row, 4).toString();
        comboEstado.setSelectedItem(estado);
        
        
        detailModel.setRowCount(0);
        totalAmount = 0.0;
        
        detailModel.addRow(new Object[]{"Bibimbap Especial", "$185.00", 1, "$185.00"});
        totalAmount = 185.0;
        totalLabel.setText(String.format("Total: $%.2f", totalAmount));
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
            editRow = -1; 
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
        
        comboClient = new JComboBox<>(new String[]{"Consumidor Final", "Juan Pérez", "María García", "Carlos López"});
        comboClient.setFont(StyleGuide.FONT_REGULAR);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formContainer.add(comboClient, gbc);

        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        formContainer.add(createLabel("Platillo:"), gbc);

        comboPlatillo = new JComboBox<>(new String[]{"Bibimbap Especial ($185.00)", "Tteokbokki ($120.00)", "Kimbap de Atún ($95.00)"});
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
            public boolean isCellEditable(int row, int column) {
                return false;
            }
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
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(StyleGuide.FONT_BOLD);
        label.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        return label;
    }

    private void addPlatillo() {
        String selected = (String) comboPlatillo.getSelectedItem();
        int cant;
        try {
            cant = Integer.parseInt(txtCantidad.getText().trim());
            if (cant <= 0) throw new NumberFormatException();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        double price = 0;
        String name = selected;
        if (selected != null && selected.contains("($")) {
            int start = selected.indexOf("($") + 2;
            int end = selected.indexOf(")", start);
            price = Double.parseDouble(selected.substring(start, end));
            name = selected.substring(0, selected.indexOf("($")).trim();
        }

        double subtotal = price * cant;
        detailModel.addRow(new Object[]{name, String.format("$%.2f", price), cant, String.format("$%.2f", subtotal)});
        
        totalAmount += subtotal;
        totalLabel.setText(String.format("Total: $%.2f", totalAmount));
    }

    private void saveOrder() {
        if (detailModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay platillos en la orden.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (editRow != -1 && mainModel != null) {
            
            mainModel.setValueAt(comboClient.getSelectedItem().toString(), editRow, 1);
            mainModel.setValueAt(String.format("$%.2f", totalAmount), editRow, 3);
            mainModel.setValueAt(comboEstado.getSelectedItem().toString(), editRow, 4);
        } else {
            
        }

        JOptionPane.showMessageDialog(this, "Orden guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        
        comboClient.setSelectedIndex(0);
        comboPlatillo.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
        txtCantidad.setText("1");
        detailModel.setRowCount(0);
        totalAmount = 0.0;
        totalLabel.setText("Total: $0.00");

        if (editRow != -1) {
            editRow = -1; 
            parentPanel.showCard("List"); 
        }
    }
}
