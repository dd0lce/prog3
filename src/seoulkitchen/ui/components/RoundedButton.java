package seoulkitchen.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {
    private int cornerRadius = 15;
    private Color hoverColor;
    private Color originalColor;
    
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (originalColor == null) {
                    originalColor = getBackground();
                    hoverColor = originalColor.darker();
                }
                setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(originalColor);
            }
        });
    }

    public RoundedButton(String text, int radius) {
        this(text);
        this.cornerRadius = radius;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        if (originalColor == null) {
            originalColor = bg;
            if (bg != null) {
                hoverColor = new Color(Math.max(bg.getRed() - 20, 0), Math.max(bg.getGreen() - 20, 0), Math.max(bg.getBlue() - 20, 0));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isPressed()) {
            graphics.setColor(getBackground().darker().darker());
        } else {
            graphics.setColor(getBackground());
        }
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        super.paintComponent(g); // Pintar texto por defecto
        graphics.dispose();
    }
}
