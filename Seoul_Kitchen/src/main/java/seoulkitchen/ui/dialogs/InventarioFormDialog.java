package seoulkitchen.ui.dialogs;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.dao.InventarioDAO;
import seoulkitchen.model.InventarioItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InventarioFormDialog extends JDialog {
    private JTextField txtNombre;
    private JTextField txtCantidad;
    private JComboBox<String> comboUnidad;
    private JTextField txtNivelAlerta;
    
    private InventarioItem itemActual;
    private InventarioDAO inventarioDAO;
    private Runnable onSavedCallback;

    public InventarioFormDialog(Frame owner, String title, Runnable onSavedCallback) {
        super(owner, title, true);
        this.inventarioDAO = new InventarioDAO();
        this.onSavedCallback = onSavedCallback;
        setupUI();
    }
    
    public void loadDataForEdit(InventarioItem item) {
        this.itemActual = item;
        txtNombre.setText(item.getNombre());
        txtCantidad.setText(String.valueOf(item.getCantidad()));
        comboUnidad.setSelectedItem(item.getUnidad());
        txtNivelAlerta.setText(String.valueOf(item.getNivelAlerta()));
    }

    private void setupUI() {
        setSize(400, 350);
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
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(createLabel("Nombre del Artículo:"), gbc);
        gbc.gridy = 1;
        txtNombre = createTextField();
        formPanel.add(txtNombre, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 2; gbc.gridx = 0;
        formPanel.add(createLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        formPanel.add(createLabel("Unidad de Medida:"), gbc);
        
        gbc.gridy = 3; gbc.gridx = 0;
        txtCantidad = createTextField();
        formPanel.add(txtCantidad, gbc);
        
        gbc.gridx = 1;
        comboUnidad = new JComboBox<>(new String[]{"kg", "g", "L", "ml", "unidad", "paquete", "litro"});
        comboUnidad.setFont(StyleGuide.FONT_REGULAR);
        formPanel.add(comboUnidad, gbc);
        
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        formPanel.add(createLabel("Nivel de Alerta (Stock Mínimo):"), gbc);
        gbc.gridy = 5;
        txtNivelAlerta = createTextField();
        formPanel.add(txtNivelAlerta, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
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
        if (txtNombre.getText().trim().isEmpty() || txtCantidad.getText().trim().isEmpty() || txtNivelAlerta.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int cantidad;
        int nivelAlerta;
        
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            nivelAlerta = Integer.parseInt(txtNivelAlerta.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad y el nivel de alerta deben ser números enteros", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (itemActual == null) {
            InventarioItem nuevo = new InventarioItem(
                txtNombre.getText().trim(),
                cantidad,
                comboUnidad.getSelectedItem().toString(),
                nivelAlerta
            );
            if (inventarioDAO.agregar(nuevo)) {
                JOptionPane.showMessageDialog(this, "Artículo creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                if(onSavedCallback != null) onSavedCallback.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear artículo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            itemActual.setNombre(txtNombre.getText().trim());
            itemActual.setCantidad(cantidad);
            itemActual.setUnidad(comboUnidad.getSelectedItem().toString());
            itemActual.setNivelAlerta(nivelAlerta);
            
            if (inventarioDAO.actualizar(itemActual)) {
                JOptionPane.showMessageDialog(this, "Artículo actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                if(onSavedCallback != null) onSavedCallback.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar artículo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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
