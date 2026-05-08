package seoulkitchen.ui.frames;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.ui.components.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class RegistroFrame extends JFrame {

    public RegistroFrame() {
        setTitle("THE SEOUL KITCHEN - Registro");
        setSize(550, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(StyleGuide.COLOR_BG);
        setLayout(new GridBagLayout());

        RoundedPanel centralPanel = new RoundedPanel(new GridBagLayout(), 25);
        centralPanel.setBackground(Color.WHITE);
        centralPanel.setPreferredSize(new Dimension(480, 600));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Title
        JLabel titleLabel = new JLabel("REGISTRO", SwingConstants.CENTER);
        titleLabel.setFont(new Font(StyleGuide.FONT_TITLE.getName(), Font.BOLD, 24));
        titleLabel.setForeground(StyleGuide.COLOR_SIDEBAR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 20, 5, 20);
        centralPanel.add(titleLabel, gbc);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Crea tu cuenta de administrador", SwingConstants.CENTER);
        subtitleLabel.setFont(StyleGuide.FONT_REGULAR);
        subtitleLabel.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 25, 20);
        centralPanel.add(subtitleLabel, gbc);

        // Nombre
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 20, 5, 10);
        centralPanel.add(createLabel("Nombre"), gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 15, 10);
        centralPanel.add(createTextField(), gbc);

        // Apellido
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.insets = new Insets(5, 10, 5, 20);
        centralPanel.add(createLabel("Apellido"), gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 15, 20);
        centralPanel.add(createTextField(), gbc);

        // Email (Full width)
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 20, 5, 20);
        centralPanel.add(createLabel("Email"), gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 20, 15, 20);
        centralPanel.add(createTextField(), gbc);

        // Usuario (Full width)
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 20, 5, 20);
        centralPanel.add(createLabel("Usuario"), gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(0, 20, 15, 20);
        centralPanel.add(createTextField(), gbc);

        // Contraseña
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 20, 5, 10);
        centralPanel.add(createLabel("Contraseña"), gbc);

        gbc.gridy = 9;
        gbc.insets = new Insets(0, 20, 25, 10);
        centralPanel.add(createPasswordField(), gbc);

        // Confirmar Contraseña
        gbc.gridy = 8;
        gbc.gridx = 1;
        gbc.insets = new Insets(5, 10, 5, 20);
        centralPanel.add(createLabel("Confirmar Contraseña"), gbc);

        gbc.gridy = 9;
        gbc.insets = new Insets(0, 10, 25, 20);
        centralPanel.add(createPasswordField(), gbc);

        // Success Label
        JLabel successLabel = new JLabel("Cuenta creada exitosamente", SwingConstants.CENTER);
        successLabel.setFont(StyleGuide.FONT_BOLD);
        successLabel.setForeground(StyleGuide.COLOR_BADGE_ACTIVE_TEXT);
        successLabel.setVisible(false);
        gbc.gridy = 10;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 20, 10, 20);
        centralPanel.add(successLabel, gbc);

        // Botón CREAR CUENTA
        RoundedButton btnRegister = new RoundedButton("CREAR CUENTA");
        StyleGuide.stylePrimaryButton(btnRegister);
        btnRegister.addActionListener(e -> {
            successLabel.setVisible(true);
            btnRegister.setEnabled(false);
            Timer timer = new Timer(1500, evt -> {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    LoginFrame frame = new LoginFrame();
                    frame.setVisible(true);
                });
            });
            timer.setRepeats(false);
            timer.start();
        });
        gbc.gridy = 11;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 20, 30, 20);
        centralPanel.add(btnRegister, gbc);

        add(centralPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(StyleGuide.FONT_REGULAR);
        label.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(StyleGuide.FONT_REGULAR);
        textField.setBorder(StyleGuide.createRoundedBorder());
        return textField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passField = new JPasswordField();
        passField.setFont(StyleGuide.FONT_REGULAR);
        passField.setBorder(StyleGuide.createRoundedBorder());
        return passField;
    }
}
