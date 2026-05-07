package seoulkitchen;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.Sidebar;
import seoulkitchen.ui.panels.CrudPanel;
import seoulkitchen.ui.panels.DashboardPanel;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    private JPanel centerPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        setTitle("THE SEOUL KITCHEN - Sistema de Gestión");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(new BorderLayout());

        // Inicializar CardLayout y Panel Central
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.setBackground(StyleGuide.COLOR_BG);

        // Añadir las vistas al CardLayout
        centerPanel.add(new DashboardPanel(), "Dashboard");
        centerPanel.add(new CrudPanel("Platillos"), "Platillos");
        centerPanel.add(new CrudPanel("Órdenes"), "Ordenes");
        centerPanel.add(new CrudPanel("Clientes"), "Clientes");
        centerPanel.add(new CrudPanel("Inventario"), "Inventario");

        // Inicializar y añadir la Sidebar
        Sidebar sidebar = new Sidebar(centerPanel, cardLayout);
        add(sidebar, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
