package seoulkitchen.ui.dialogs;
import seoulkitchen.utils.StyleGuide;
import seoulkitchen.ui.components.RoundedButton;
import seoulkitchen.ui.components.RoundedPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
public class PlatilloDetalleDialog extends JDialog {
    public PlatilloDetalleDialog(Frame owner) {
        super(owner, "Detalle del Platillo", true);
        setupUI();
    }
    private void setupUI() {
        setSize(450, 600);
        setLocationRelativeTo(getOwner());
        getContentPane().setBackground(StyleGuide.COLOR_BG);
        setLayout(new BorderLayout());
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(StyleGuide.COLOR_BG);
        container.setBorder(new EmptyBorder(20, 20, 20, 20));
        RoundedPanel imagePanel = new RoundedPanel(new BorderLayout(), 20);
        imagePanel.setBackground(Color.LIGHT_GRAY);
        imagePanel.setPreferredSize(new Dimension(400, 200));
        imagePanel.setMaximumSize(new Dimension(400, 200));
        JLabel lblImage = new JLabel("📷 Foto del Platillo", SwingConstants.CENTER);
        lblImage.setFont(StyleGuide.FONT_TITLE);
        lblImage.setForeground(Color.DARK_GRAY);
        imagePanel.add(lblImage, BorderLayout.CENTER);
        container.add(imagePanel);
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel lblTitle = new JLabel("Descripción");
        lblTitle.setFont(StyleGuide.FONT_HEADING);
        lblTitle.setForeground(StyleGuide.COLOR_SIDEBAR);
        container.add(lblTitle);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        JTextArea txtDesc = new JTextArea("Delicioso platillo tradicional coreano preparado con ingredientes frescos y auténticos. Perfecto para compartir.");
        txtDesc.setFont(StyleGuide.FONT_REGULAR);
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setOpaque(false);
        txtDesc.setEditable(false);
        container.add(txtDesc);
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel lblIngr = new JLabel("Ingredientes Necesarios");
        lblIngr.setFont(StyleGuide.FONT_HEADING);
        lblIngr.setForeground(StyleGuide.COLOR_SIDEBAR);
        container.add(lblIngr);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        String[] ingredientes = {"Arroz", "Gochujang", "Aceite de Sésamo", "Vegetales Mixtos", "Carne de Res"};
        JList<String> listIngr = new JList<>(ingredientes);
        listIngr.setFont(StyleGuide.FONT_REGULAR);
        listIngr.setBackground(StyleGuide.COLOR_BG);
        container.add(new JScrollPane(listIngr));
        add(container, BorderLayout.CENTER);
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(StyleGuide.COLOR_BG);
        footerPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        RoundedButton btnClose = new RoundedButton("Cerrar");
        StyleGuide.stylePrimaryButton(btnClose);
        btnClose.addActionListener(e -> dispose());
        footerPanel.add(btnClose);
        add(footerPanel, BorderLayout.SOUTH);
    }
}
