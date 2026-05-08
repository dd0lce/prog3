package seoulkitchen.ui.panels;

import java.awt.CardLayout;
import javax.swing.JPanel;

public class InventarioPanel extends JPanel {

    private CardLayout cardLayout;
    private InventarioListPanel listPanel;
    private InventarioDetallePanel detallePanel;

    public InventarioPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        listPanel = new InventarioListPanel(this);
        detallePanel = new InventarioDetallePanel(this);

        add(listPanel, "List");
        add(detallePanel, "Detalle");
    }

    public void showCard(String cardName) {
        cardLayout.show(this, cardName);
    }
}
