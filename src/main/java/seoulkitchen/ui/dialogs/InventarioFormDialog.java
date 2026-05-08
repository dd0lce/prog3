package seoulkitchen.ui.dialogs;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventarioFormDialog extends JDialog {

    private JTextField txtNombre;
    private JComboBox<String> comboCat;
    private JTextField txtStockInicial;
    private JComboBox<String> comboUnidad;
    private JTextField txtStockMin;
    private JTextField txtStockOptimo;
    private JComboBox<String> comboEstado;

    private DefaultTableModel mainModel;
    private int editRow = -1;

    public InventarioFormDialog(Frame owner, String title) {
        super(owner, title, true);
        setupUI();
    }
    
    public void loadDataForEdit(DefaultTableModel model, int row) {
        this.mainModel = model;
        this.editRow = row;

        String nombre = model.getValueAt(row, 0).toString();
        String cat = model.getValueAt(row, 1).toString();
        String cantidad = model.getValueAt(row, 2).toString();
        String stockMin = model.getValueAt(row, 3).toString();
        String estado = model.getValueAt(row, 4).toString();

        txtNombre.setText(nombre);
        
        for(int i=0; i<comboCat.getItemCount(); i++) {
            if(comboCat.getItemAt(i).equalsIgnoreCase(cat)) {
                comboCat.setSelectedIndex(i);
                break;
            }
        }
        
        txtStockInicial.setText(cantidad.replaceAll("[^0-9.]", ""));
        txtStockMin.setText(stockMin.replaceAll("[^0-9.]", ""));
        
        for(int i=0; i<comboEstado.getItemCount(); i++) {
            if(comboEstado.getItemAt(i).equalsIgnoreCase(estado)) {
                comboEstado.setSelectedIndex(i);
                break;
            }
        }
    }

    private void setupUI() {
        setSize(450, 550);
        setLocationRelativeTo(getOwner());
        getContentPane().setBackground(StyleGuide.COLOR_BG);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(StyleGuide.COLOR_BG);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 5, 5);
        gbc.weightx = 0.5;

        // Row 1: Nombre (Full width)
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(createLabel("Nombre del Ingrediente:"), gbc);
        gbc.gridy = 1;
        txtNombre = createTextField();
        formPanel.add(txtNombre, gbc);

        // Row 2: Categoria y Unidad de Medida
        gbc.gridwidth = 1;
        gbc.gridy = 2; gbc.gridx = 0;
        formPanel.add(createLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        formPanel.add(createLabel("Unidad de Medida:"), gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        comboCat = new JComboBox<>(new String[]{"Granos", "Carnes", "Vegetales", "Salsas", "Aceites", "Especias"});
        comboCat.setFont(StyleGuide.FONT_REGULAR);
        formPanel.add(comboCat, gbc);

        gbc.gridx = 1;
        comboUnidad = new JComboBox<>(new String[]{"kg", "g", "L", "ml", "Unidad", "Caja"});
        comboUnidad.setFont(StyleGuide.FONT_REGULAR);
        formPanel.add(comboUnidad, gbc);

        // Row 3: Stock Inicial y Stock Minimo
        gbc.gridy = 4; gbc.gridx = 0;
        formPanel.add(createLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        formPanel.add(createLabel("Stock Mínimo:"), gbc);

        gbc.gridy = 5; gbc.gridx = 0;
        txtStockInicial = createTextField();
        formPanel.add(txtStockInicial, gbc);

        gbc.gridx = 1;
        txtStockMin = createTextField();
        formPanel.add(txtStockMin, gbc);

        // Row 4: Stock Optimo & Estado
        gbc.gridy = 6; gbc.gridx = 0;
        formPanel.add(createLabel("Stock Óptimo:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(createLabel("Estado:"), gbc);
        
        gbc.gridy = 7; gbc.gridx = 0;
        txtStockOptimo = createTextField();
        formPanel.add(txtStockOptimo, gbc);
        
        gbc.gridx = 1;
        comboEstado = new JComboBox<>(new String[]{"En Stock", "Stock Bajo", "Sin Stock"});
        comboEstado.setFont(StyleGuide.FONT_REGULAR);
        formPanel.add(comboEstado, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Footer buttons
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(StyleGuide.COLOR_BG);
        footerPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        RoundedButton btnCancel = new RoundedButton("Cancelar");
        btnCancel.setFont(StyleGuide.FONT_BOLD);
        btnCancel.setFocusPainted(false);
        btnCancel.setBackground(Color.WHITE);
        btnCancel.addActionListener(e -> dispose());

        RoundedButton btnSave = new RoundedButton("Guardar");
        StyleGuide.stylePrimaryButton(btnSave);
        btnSave.addActionListener(e -> saveAction());

        footerPanel.add(btnCancel);
        footerPanel.add(btnSave);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void saveAction() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (mainModel != null && editRow != -1) {
            String unidad = comboUnidad.getSelectedItem().toString();
            mainModel.setValueAt(txtNombre.getText().trim(), editRow, 0);
            mainModel.setValueAt(comboCat.getSelectedItem().toString(), editRow, 1);
            mainModel.setValueAt(txtStockInicial.getText().trim() + " " + unidad, editRow, 2);
            mainModel.setValueAt(txtStockMin.getText().trim() + " " + unidad, editRow, 3);
            mainModel.setValueAt(comboEstado.getSelectedItem().toString(), editRow, 4);
        } else {
            // New ingredient stub
            JOptionPane.showMessageDialog(this, "Ingrediente creado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }

        dispose();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(StyleGuide.FONT_BOLD);
        label.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(StyleGuide.FONT_REGULAR);
        textField.setBorder(StyleGuide.createRoundedBorder());
        return textField;
    }
}
