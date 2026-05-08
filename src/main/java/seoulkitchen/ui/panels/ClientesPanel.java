package seoulkitchen.ui.panels;

import java.awt.CardLayout;
import javax.swing.JPanel;

public class ClientesPanel extends JPanel {

    private CardLayout cardLayout;
    private ClientesListPanel listPanel;
    private ClienteDetallePanel detallePanel;

    public ClientesPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        listPanel = new ClientesListPanel(this);
        detallePanel = new ClienteDetallePanel(this);

        add(listPanel, "List");
        add(detallePanel, "Detalle");
    }

    public void showCard(String cardName) {
        cardLayout.show(this, cardName);
    }
}
