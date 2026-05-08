package seoulkitchen;

import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.Sidebar;
import seoulkitchen.ui.panels.CrudPanel;
import seoulkitchen.ui.panels.DashboardPanel;
import seoulkitchen.ui.panels.OrdenesPanel;
import seoulkitchen.ui.panels.PlatillosPanel;
import seoulkitchen.ui.panels.ClientesPanel;
import seoulkitchen.ui.panels.InventarioPanel;

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
        centerPanel.add(new PlatillosPanel(), "Platillos");
        centerPanel.add(new OrdenesPanel(), "Ordenes");
        centerPanel.add(new ClientesPanel(), "Clientes");
        centerPanel.add(new InventarioPanel(), "Inventario");

        // Inicializar y añadir la Sidebar
        Sidebar sidebar = new Sidebar(centerPanel, cardLayout);
        add(sidebar, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            seoulkitchen.ui.frames.LoginFrame loginFrame = new seoulkitchen.ui.frames.LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
