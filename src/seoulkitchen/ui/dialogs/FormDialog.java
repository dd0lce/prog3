package seoulkitchen.ui.dialogs;

import seoulkitchen.utils.StyleGuide;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import seoulkitchen.ui.components.RoundedButton;

public class FormDialog extends JDialog {

    public FormDialog(Frame owner, String title) {
        super(owner, title, true);
        setupUI();
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
        formPanel.add(createLabel("Nombre del Platillo:"), gbc);
        gbc.gridy = 1;
        formPanel.add(createTextField(), gbc);

        // Precio
        gbc.gridy = 2;
        formPanel.add(createLabel("Precio:"), gbc);
        gbc.gridy = 3;
        formPanel.add(createTextField(), gbc);

        // Estado
        gbc.gridy = 4;
        formPanel.add(createLabel("Estado:"), gbc);
        gbc.gridy = 5;
        JComboBox<String> comboEstado = new JComboBox<>(new String[]{"Activo", "Agotado", "Inactivo"});
        comboEstado.setFont(StyleGuide.FONT_REGULAR);
        formPanel.add(comboEstado, gbc);

        // Filler
        gbc.gridy = 6;
        gbc.weighty = 1.0;
        formPanel.add(Box.createGlue(), gbc);

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
        btnSave.addActionListener(e -> dispose());

        footerPanel.add(btnCancel);
        footerPanel.add(btnSave);
        add(footerPanel, BorderLayout.SOUTH);
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
