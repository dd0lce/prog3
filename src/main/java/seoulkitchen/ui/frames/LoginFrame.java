package seoulkitchen.ui.frames;

import seoulkitchen.MainFrame;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.utils.ConexionBD;
import seoulkitchen.dao.UsuarioDAO;
import seoulkitchen.model.Usuario;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.ui.components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("THE SEOUL KITCHEN - Iniciar Sesión");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(StyleGuide.COLOR_BG);
        setLayout(new GridBagLayout());

        RoundedPanel centralPanel = new RoundedPanel(new GridBagLayout(), 25);
        centralPanel.setBackground(Color.WHITE);
        centralPanel.setPreferredSize(new Dimension(380, 450));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        
        JLabel titleLabel = new JLabel("THE SEOUL KITCHEN", SwingConstants.CENTER);
        titleLabel.setFont(new Font(StyleGuide.FONT_TITLE.getName(), Font.BOLD, 24));
        titleLabel.setForeground(StyleGuide.COLOR_SIDEBAR);
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 20, 30, 20);
        centralPanel.add(titleLabel, gbc);

        
        JLabel userLabel = new JLabel("USUARIO");
        userLabel.setFont(StyleGuide.FONT_REGULAR);
        userLabel.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 20, 5, 20);
        centralPanel.add(userLabel, gbc);

        
        JTextField userField = new JTextField();
        userField.setFont(StyleGuide.FONT_REGULAR);
        userField.setBorder(StyleGuide.createRoundedBorder());
        userField.setText("Ingresa tu usuario");
        userField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
        addPlaceholder(userField, "Ingresa tu usuario");
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 15, 20);
        centralPanel.add(userField, gbc);

        
        JLabel passLabel = new JLabel("CONTRASEÑA");
        passLabel.setFont(StyleGuide.FONT_REGULAR);
        passLabel.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 20, 5, 20);
        centralPanel.add(passLabel, gbc);

        
        JPasswordField passField = new JPasswordField();
        passField.setFont(StyleGuide.FONT_REGULAR);
        passField.setBorder(StyleGuide.createRoundedBorder());
        passField.setEchoChar((char) 0);
        passField.setText("Ingresa tu contraseña");
        passField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);

        passField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                String pwd = new String(passField.getPassword());
                if (pwd.equals("Ingresa tu contraseña")) {
                    passField.setText("");
                    passField.setEchoChar('•');
                    passField.setForeground(StyleGuide.COLOR_TEXT_MAIN);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                String pwd = new String(passField.getPassword());
                if (pwd.trim().isEmpty()) {
                    passField.setText("Ingresa tu contraseña");
                    passField.setEchoChar((char) 0);
                    passField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
                }
            }
        });
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 30, 20);
        centralPanel.add(passField, gbc);

        
        RoundedButton btnLogin = new RoundedButton("INICIAR SESIÓN");
        StyleGuide.stylePrimaryButton(btnLogin);
        btnLogin.addActionListener(e -> {
            // ── 1. Leer campos y verificar que no estén vacíos/placeholder ──
            String inputUser = userField.getText().trim();
            String inputPass = new String(passField.getPassword()).trim();

            boolean userIsPlaceholder = inputUser.equals("Ingresa tu usuario") || inputUser.isEmpty();
            boolean passIsPlaceholder = inputPass.equals("Ingresa tu contraseña") || inputPass.isEmpty();

            if (userIsPlaceholder || passIsPlaceholder) {
                JOptionPane.showMessageDialog(
                    this,
                    "Por favor ingresa tu usuario y contraseña.",
                    "Campos vacíos",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // ── 2. Autenticar contra la base de datos ────────────────────────
            UsuarioDAO dao = new UsuarioDAO();
            try {
                Usuario usuario = dao.autenticar(inputUser, inputPass);

                if (usuario == null) {
                    // Credenciales incorrectas
                    JOptionPane.showMessageDialog(
                        this,
                        "Usuario o contraseña incorrectos.\nVerifica tus datos e intenta de nuevo.",
                        "Acceso Denegado",
                        JOptionPane.ERROR_MESSAGE
                    );
                    passField.setText("");
                    passField.setEchoChar((char) 0);
                    passField.setText("Ingresa tu contraseña");
                    passField.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
                } else {
                    // ── 3. Éxito: abrir dashboard y destruir ventana de login ─
                    MainFrame dashboard = new MainFrame();
                    dashboard.setVisible(true);
                    this.dispose();
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "No se pudo conectar a la base de datos.\n" +
                    "Verifica que MySQL esté activo y que los datos en ConexionBD sean correctos.\n\n" +
                    "Detalle técnico: " + ex.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 20, 15, 20);
        centralPanel.add(btnLogin, gbc);

        
        JLabel lblRegister = new JLabel("¿No tienes cuenta? Regístrate aquí", SwingConstants.CENTER);
        lblRegister.setFont(StyleGuide.FONT_REGULAR);
        lblRegister.setForeground(StyleGuide.COLOR_PRIMARY);
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    RegistroFrame frame = new RegistroFrame();
                    frame.setVisible(true);
                });
            }
        });
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 20, 30, 20);
        centralPanel.add(lblRegister, gbc);

        add(centralPanel);
    }

    private void addPlaceholder(JTextField field, String placeholder) {
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(StyleGuide.COLOR_TEXT_MAIN);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
                }
            }
        });
    }
}
