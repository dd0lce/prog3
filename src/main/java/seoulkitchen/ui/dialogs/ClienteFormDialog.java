package seoulkitchen.ui.dialogs;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClienteFormDialog extends JDialog {

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JComboBox<String> comboEstado;

    private DefaultTableModel mainModel;
    private int editRow = -1;

    public ClienteFormDialog(Frame owner, String title) {
        super(owner, title, true);
        setupUI();
    }
    
    public void loadDataForEdit(DefaultTableModel model, int row) {
        this.mainModel = model;
        this.editRow = row;

        String nombre = model.getValueAt(row, 1).toString();
        String email = model.getValueAt(row, 2).toString();
        String telefono = model.getValueAt(row, 3).toString();
        String estado = model.getValueAt(row, 4).toString();

        txtNombre.setText(nombre);
        txtEmail.setText(email);
        txtTelefono.setText(telefono);
        
        // Find state item in combo (case insensitive)
        for(int i=0; i<comboEstado.getItemCount(); i++) {
            if(comboEstado.getItemAt(i).equalsIgnoreCase(estado)) {
                comboEstado.setSelectedIndex(i);
                break;
            }
        }
    }

    private void setupUI() {
        setSize(400, 450);
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

        // Nombre
        gbc.gridy = 0;
        formPanel.add(createLabel("Nombre Completo:"), gbc);
        gbc.gridy = 1;
        txtNombre = createTextField();
        formPanel.add(txtNombre, gbc);

        // Email
        gbc.gridy = 2;
        formPanel.add(createLabel("Email:"), gbc);
        gbc.gridy = 3;
        txtEmail = createTextField();
        formPanel.add(txtEmail, gbc);

        // Telefono
        gbc.gridy = 4;
        formPanel.add(createLabel("Teléfono:"), gbc);
        gbc.gridy = 5;
        txtTelefono = createTextField();
        formPanel.add(txtTelefono, gbc);

        // Estado
        gbc.gridy = 6;
        formPanel.add(createLabel("Estado:"), gbc);
        gbc.gridy = 7;
        comboEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
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
            mainModel.setValueAt(txtNombre.getText().trim(), editRow, 1);
            mainModel.setValueAt(txtEmail.getText().trim(), editRow, 2);
            mainModel.setValueAt(txtTelefono.getText().trim(), editRow, 3);
            mainModel.setValueAt(comboEstado.getSelectedItem().toString(), editRow, 4);
        } else {
            // New user creation stub
            JOptionPane.showMessageDialog(this, "Cliente creado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
