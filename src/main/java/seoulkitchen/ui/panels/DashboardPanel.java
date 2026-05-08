package seoulkitchen.ui.panels;

import seoulkitchen.utils.StyleGuide;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import seoulkitchen.ui.components.RoundedPanel;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(StyleGuide.COLOR_BG);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Título del Dashboard
        JLabel titleLabel = new JLabel("Dashboard General");
        titleLabel.setFont(new Font(StyleGuide.FONT_TITLE.getName(), Font.BOLD, 28));
        titleLabel.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel Central para Cards y Atención
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(StyleGuide.COLOR_BG);

        // Cards Superiores
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        cardsPanel.setBackground(StyleGuide.COLOR_BG);
        cardsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        cardsPanel.add(createCard("Ventas Hoy", "$4,520.00", "💰"));
        cardsPanel.add(createCard("Órdenes", "32", "📦"));
        cardsPanel.add(createCard("Clientes", "15", "👥"));
        cardsPanel.add(createCard("Platillos Activos", "24", "🍲"));

        centerPanel.add(cardsPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Panel de Atención
        RoundedPanel attentionPanel = new RoundedPanel(new BorderLayout(), 15);
        attentionPanel.setBackground(StyleGuide.COLOR_ALERT_BG);
        attentionPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        attentionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel alertTitle = new JLabel("⚠️ Atención Requerida");
        alertTitle.setFont(StyleGuide.FONT_HEADING);
        alertTitle.setForeground(StyleGuide.COLOR_BADGE_PENDING_TEXT);
        
        JLabel alertDesc = new JLabel("El inventario de 'Gochujang' y 'Arroz' está por agotarse. Por favor, reabastece pronto.");
        alertDesc.setFont(StyleGuide.FONT_REGULAR);
        alertDesc.setForeground(StyleGuide.COLOR_TEXT_MAIN);

        attentionPanel.add(alertTitle, BorderLayout.NORTH);
        attentionPanel.add(alertDesc, BorderLayout.CENTER);

        centerPanel.add(attentionPanel);
        centerPanel.add(Box.createVerticalGlue()); // Para empujar todo hacia arriba

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String value, String icon) {
        RoundedPanel card = new RoundedPanel(new BorderLayout(), 15);
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(StyleGuide.FONT_REGULAR);
        lblTitle.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font(StyleGuide.FONT_TITLE.getName(), Font.BOLD, 32));
        lblValue.setForeground(StyleGuide.COLOR_PRIMARY);

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font(StyleGuide.FONT_TITLE.getName(), Font.PLAIN, 40));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        card.add(lblIcon, BorderLayout.EAST);

        return card;
    }
}
