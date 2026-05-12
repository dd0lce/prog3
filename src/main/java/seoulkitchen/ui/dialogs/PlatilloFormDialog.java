package seoulkitchen.ui.dialogs;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlatilloFormDialog extends JDialog {

    private JTextField txtNombre;
    private JComboBox<String> comboCat;
    private JTextField txtPrecio;
    private JTextArea txtDesc;
    private JComboBox<String> comboEstado;

    private DefaultTableModel mainModel;
    private int editRow = -1;

    public PlatilloFormDialog(Frame owner, String title) {
        super(owner, title, true);
        setupUI();
    }

    public void loadDataForEdit(DefaultTableModel model, int row) {
        this.mainModel = model;
        this.editRow = row;

        String nombre = model.getValueAt(row, 1).toString();
        String cat = model.getValueAt(row, 2).toString();
        String precio = model.getValueAt(row, 3).toString();
        String estado = model.getValueAt(row, 4).toString();

        txtNombre.setText(nombre);
        
        for(int i=0; i<comboCat.getItemCount(); i++) {
            if(comboCat.getItemAt(i).equalsIgnoreCase(cat)) {
                comboCat.setSelectedIndex(i);
                break;
            }
        }
        
        txtPrecio.setText(precio);
        txtDesc.setText("Descripción del platillo...");

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

        
        gbc.gridy = 8;
        formPanel.add(createLabel("Descripción:"), gbc);
        gbc.gridy = 9;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtDesc = new JTextArea(3, 20);
        txtDesc.setFont(StyleGuide.FONT_REGULAR);
        txtDesc.setBorder(StyleGuide.createRoundedBorder());
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(txtDesc), gbc);

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
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (mainModel != null && editRow != -1) {
            mainModel.setValueAt(txtNombre.getText().trim(), editRow, 1);
            mainModel.setValueAt(comboCat.getSelectedItem().toString(), editRow, 2);
            mainModel.setValueAt(txtPrecio.getText().trim(), editRow, 3);
            mainModel.setValueAt(comboEstado.getSelectedItem().toString(), editRow, 4);
        } else {
            
            JOptionPane.showMessageDialog(this, "Platillo creado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
