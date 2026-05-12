package seoulkitchen.ui.dialogs;

import seoulkitchen.utils.StyleGuide;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DetailDialog extends JDialog {

    public DetailDialog(Frame owner, String title) {
        super(owner, title, true);
        setupUI();
    }

    private void setupUI() {
        setSize(500, 600);
        setLocationRelativeTo(getOwner());
        getContentPane().setBackground(StyleGuide.COLOR_BG);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(StyleGuide.COLOR_BG);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        
        mainPanel.add(createSectionTitle("Información Principal"));
        mainPanel.add(createInfoRow("Nombre:", "Bibimbap Especial"));
        mainPanel.add(createInfoRow("Categoría:", "Plato Fuerte"));
        mainPanel.add(createInfoRow("Precio:", "$185.00"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(new JSeparator());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        
        mainPanel.add(createSectionTitle("Historial"));
        mainPanel.add(createInfoRow("Última Venta:", "10 de Octubre, 2023"));
        mainPanel.add(createInfoRow("Creado Por:", "Admin"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(new JSeparator());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        
        mainPanel.add(createSectionTitle("Estadísticas"));
        mainPanel.add(createInfoRow("Total Vendidos:", "342 unidades"));
        mainPanel.add(createInfoRow("Ingresos Generados:", "$63,270.00"));

        add(mainPanel, BorderLayout.CENTER);

        
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(StyleGuide.COLOR_BG);
        footerPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        
        JButton closeButton = new JButton("Cerrar");
        closeButton.setFont(StyleGuide.FONT_BOLD);
        closeButton.setForeground(StyleGuide.COLOR_TEXT_MAIN);
        closeButton.setBackground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        
        footerPanel.add(closeButton);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(StyleGuide.FONT_HEADING);
        label.setForeground(StyleGuide.COLOR_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPanel createInfoRow(String labelText, String valueText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(StyleGuide.COLOR_BG);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(StyleGuide.FONT_BOLD);
        lbl.setForeground(StyleGuide.COLOR_TEXT_SECONDARY);
        lbl.setPreferredSize(new Dimension(120, 20));

        JLabel val = new JLabel(valueText);
        val.setFont(StyleGuide.FONT_REGULAR);
        val.setForeground(StyleGuide.COLOR_TEXT_MAIN);

        panel.add(lbl);
        panel.add(val);
        return panel;
    }
}
