package seoulkitchen.ui.panels;
import java.awt.CardLayout;
import javax.swing.JPanel;
public class OrdenesPanel extends JPanel {
    private CardLayout cardLayout;
    private OrdenesListPanel listPanel;
    private OrdenesFormPanel formPanel;
    public OrdenesPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        formPanel = new OrdenesFormPanel(this);
        listPanel = new OrdenesListPanel(this);
        add(listPanel, "List");
        add(formPanel, "Form");
    }
    public void showCard(String cardName) {
        cardLayout.show(this, cardName);
    }
    public OrdenesFormPanel getFormPanel() {
        return formPanel;
    }
}
