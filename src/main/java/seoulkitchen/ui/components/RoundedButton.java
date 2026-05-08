package seoulkitchen.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {
    private int cornerRadius = 15;
    private boolean isHovered = false;
    
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    public RoundedButton(String text, int radius) {
        this(text);
        this.cornerRadius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Color bg = getBackground();
        if (bg == null) {
            bg = Color.LIGHT_GRAY;
        }
        
        if (getModel().isPressed()) {
            graphics.setColor(bg.darker().darker());
        } else if (isHovered) {
            graphics.setColor(new Color(
                Math.max(bg.getRed() - 20, 0), 
                Math.max(bg.getGreen() - 20, 0), 
                Math.max(bg.getBlue() - 20, 0)
            ));
        } else {
            graphics.setColor(bg);
        }
        
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        super.paintComponent(g); // Pintar texto por defecto
        graphics.dispose();
    }
}
