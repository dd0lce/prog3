package seoulkitchen.ui.components;

import seoulkitchen.utils.StyleGuide;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import seoulkitchen.ui.components.RoundedButton;

public class Sidebar extends JPanel {

    private final JPanel centerPanel;
    private final CardLayout cardLayout;

    public Sidebar(JPanel centerPanel, CardLayout cardLayout) {
        this.centerPanel = centerPanel;
        this.cardLayout = cardLayout;
        setupUI();
    }

    private void setupUI() {
        setPreferredSize(new Dimension(280, 1024));
        setBackground(StyleGuide.COLOR_SIDEBAR);
        setLayout(new BorderLayout());

        // Logo / Título
        JLabel logoLabel = new JLabel("THE SEOUL KITCHEN");
        logoLabel.setFont(new Font(StyleGuide.FONT_TITLE.getName(), Font.BOLD, 22));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(new EmptyBorder(40, 20, 40, 20));
        add(logoLabel, BorderLayout.NORTH);

        // Menú de Navegación
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(StyleGuide.COLOR_SIDEBAR);

        menuPanel.add(createNavButton("📊 Dashboard", "Dashboard"));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(createNavButton("🍲 Platillos", "Platillos"));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(createNavButton("📦 Órdenes", "Ordenes"));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(createNavButton("👥 Clientes", "Clientes"));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(createNavButton("📋 Inventario", "Inventario"));

        add(menuPanel, BorderLayout.CENTER);

        // Botón Cerrar Sesión
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(StyleGuide.COLOR_SIDEBAR);
        bottomPanel.setBorder(new EmptyBorder(20, 20, 40, 20));

        RoundedButton logoutButton = new RoundedButton("🚪 Cerrar Sesión");
        StyleGuide.styleSidebarButton(logoutButton);
        logoutButton.setForeground(StyleGuide.COLOR_BADGE_CANCELLED_BG); // Un poco de color rojo suave para destacar
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            }
        });

        bottomPanel.add(logoutButton, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createNavButton(String text, String cardName) {
        RoundedButton button = new RoundedButton(text);
        StyleGuide.styleSidebarButton(button);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.addActionListener(e -> cardLayout.show(centerPanel, cardName));
        return button;
    }
}
