package seoulkitchen.ui.dialogs;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.dao.PlatilloDAO;
import seoulkitchen.model.Platillo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PlatilloFormDialog extends JDialog {
    private JTextField txtNombre;
    private JComboBox<String> comboCat;
    private JTextField txtPrecio;
    private JComboBox<String> comboEstado;
    
    private Platillo platilloActual;
    private PlatilloDAO platilloDAO;
    private Runnable onSavedCallback;

    public PlatilloFormDialog(Frame owner, String title, Runnable onSavedCallback) {
        super(owner, title, true);
        this.platilloDAO = new PlatilloDAO();
        this.onSavedCallback = onSavedCallback;
        setupUI();
    }
    
    public void loadDataForEdit(Platillo platillo) {
        this.platilloActual = platillo;
        txtNombre.setText(platillo.getNombre());
        comboCat.setSelectedItem(platillo.getCategoria());
        txtPrecio.setText(String.valueOf(platillo.getPrecio()));
        comboEstado.setSelectedItem(platillo.getEstado());
    }

    private void setupUI() {
        setSize(450, 400);
        setLocationRelativeTo(getOwner());
        getContentPane().setBackground(StyleGuide.COLOR_BG);
        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(StyleGuide.COLOR_BG);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.weightx = 1.0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Nombre del Platillo:"), gbc);
        gbc.gridy = 1;
        txtNombre = createTextField();
        formPanel.add(txtNombre, gbc);
        gbc.gridy = 2;
        formPanel.add(createLabel("Categoría:"), gbc);
        gbc.gridy = 3;
        comboCat = new JComboBox<>(new String[]{"Plato Principal", "Entradas", "Bebidas", "Postres"});
        comboCat.setFont(StyleGuide.FONT_REGULAR);
        formPanel.add(comboCat, gbc);
        gbc.gridy = 4;
        formPanel.add(createLabel("Precio:"), gbc);
        gbc.gridy = 5;
        txtPrecio = createTextField();
        formPanel.add(txtPrecio, gbc);
        gbc.gridy = 6;
        formPanel.add(createLabel("Estado:"), gbc);
        gbc.gridy = 7;
        comboEstado = new JComboBox<>(new String[]{"Activo", "Agotado", "Inactivo"});
        comboEstado.setFont(StyleGuide.FONT_REGULAR);
        formPanel.add(comboEstado, gbc);
        
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
        if (txtNombre.getText().trim().isEmpty() || txtPrecio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y el precio son obligatorios", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        double precio;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (platilloActual == null) {
            Platillo nuevo = new Platillo(
                txtNombre.getText().trim(),
                comboCat.getSelectedItem().toString(),
                precio,
                comboEstado.getSelectedItem().toString()
            );
            if (platilloDAO.agregar(nuevo)) {
                JOptionPane.showMessageDialog(this, "Platillo creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                if(onSavedCallback != null) onSavedCallback.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear platillo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            platilloActual.setNombre(txtNombre.getText().trim());
            platilloActual.setCategoria(comboCat.getSelectedItem().toString());
            platilloActual.setPrecio(precio);
            platilloActual.setEstado(comboEstado.getSelectedItem().toString());
            
            if (platilloDAO.actualizar(platilloActual)) {
                JOptionPane.showMessageDialog(this, "Platillo actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                if(onSavedCallback != null) onSavedCallback.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar platillo.", "Error", JOptionPane.ERROR_MESSAGE);
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
