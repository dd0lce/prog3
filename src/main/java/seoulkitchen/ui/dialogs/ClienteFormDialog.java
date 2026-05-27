package seoulkitchen.ui.dialogs;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.dao.ClienteDAO;
import seoulkitchen.model.Cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClienteFormDialog extends JDialog {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JComboBox<String> comboEstado;
    
    private Cliente clienteActual;
    private ClienteDAO clienteDAO;
    private Runnable onSavedCallback;

    public ClienteFormDialog(Frame owner, String title, Runnable onSavedCallback) {
        super(owner, title, true);
        this.clienteDAO = new ClienteDAO();
        this.onSavedCallback = onSavedCallback;
        setupUI();
    }
    
    public void loadDataForEdit(Cliente cliente) {
        this.clienteActual = cliente;
        txtNombre.setText(cliente.getNombre());
        txtApellido.setText(cliente.getApellido());
        txtEmail.setText(cliente.getEmail());
        txtTelefono.setText(cliente.getTelefono());
        comboEstado.setSelectedIndex(cliente.isActivo() ? 0 : 1);
    }

    private void setupUI() {
        setSize(400, 500);
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
        formPanel.add(createLabel("Nombre:"), gbc);
        gbc.gridy = 1;
        txtNombre = createTextField();
        formPanel.add(txtNombre, gbc);
        
        gbc.gridy = 2;
        formPanel.add(createLabel("Apellido:"), gbc);
        gbc.gridy = 3;
        txtApellido = createTextField();
        formPanel.add(txtApellido, gbc);
        
        gbc.gridy = 4;
        formPanel.add(createLabel("Email:"), gbc);
        gbc.gridy = 5;
        txtEmail = createTextField();
        formPanel.add(txtEmail, gbc);
        
        gbc.gridy = 6;
        formPanel.add(createLabel("Teléfono:"), gbc);
        gbc.gridy = 7;
        txtTelefono = createTextField();
        formPanel.add(txtTelefono, gbc);
        
        gbc.gridy = 8;
        formPanel.add(createLabel("Estado:"), gbc);
        gbc.gridy = 9;
        comboEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
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
        if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y apellido son obligatorios", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean activo = comboEstado.getSelectedIndex() == 0;
        
        if (clienteActual == null) {
            Cliente nuevo = new Cliente(
                0,
                txtNombre.getText().trim(),
                txtApellido.getText().trim(),
                txtEmail.getText().trim(),
                txtTelefono.getText().trim(),
                activo,
                null
            );
            if (clienteDAO.agregar(nuevo)) {
                JOptionPane.showMessageDialog(this, "Cliente creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                if(onSavedCallback != null) onSavedCallback.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            clienteActual.setNombre(txtNombre.getText().trim());
            clienteActual.setApellido(txtApellido.getText().trim());
            clienteActual.setEmail(txtEmail.getText().trim());
            clienteActual.setTelefono(txtTelefono.getText().trim());
            clienteActual.setActivo(activo);
            
            if (clienteDAO.actualizar(clienteActual)) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                if(onSavedCallback != null) onSavedCallback.run();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
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
