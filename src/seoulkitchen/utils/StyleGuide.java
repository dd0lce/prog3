package seoulkitchen.utils;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class StyleGuide {

    // Paleta de Colores
    public static final Color COLOR_BG = new Color(245, 241, 232); // #F5F1E8
    public static final Color COLOR_SIDEBAR = new Color(112, 66, 20); // #704214
    public static final Color COLOR_PRIMARY = new Color(176, 132, 91); // #B0845B

    public static final Color COLOR_TEXT_MAIN = new Color(26, 26, 26); // #1A1A1A
    public static final Color COLOR_TEXT_SECONDARY = new Color(117, 117, 117); // #757575
    public static final Color COLOR_TEXT_LIGHT = Color.WHITE;

    public static final Color COLOR_BADGE_ACTIVE_BG = new Color(200, 230, 201); // #C8E6C9
    public static final Color COLOR_BADGE_ACTIVE_TEXT = new Color(46, 125, 50); // #2E7D32

    public static final Color COLOR_BADGE_PENDING_BG = new Color(255, 249, 196); // #FFF9C4
    public static final Color COLOR_BADGE_PENDING_TEXT = new Color(251, 192, 45); // #FBC02D

    public static final Color COLOR_BADGE_CANCELLED_BG = new Color(255, 205, 210); // #FFCDD2
    public static final Color COLOR_BADGE_CANCELLED_TEXT = new Color(198, 40, 40); // #C62828

    public static final Color COLOR_ALERT_BG = new Color(255, 249, 196); // Amarillo suave

    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 24);
    public static final Font FONT_HEADING = new Font("SansSerif", Font.BOLD, 18);
    public static final Font FONT_REGULAR = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("SansSerif", Font.PLAIN, 12);
    public static final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 14);

    public static void stylePrimaryButton(JButton button) {
        button.setBackground(COLOR_PRIMARY);
        button.setForeground(COLOR_TEXT_LIGHT);
        button.setFont(FONT_BOLD);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
    }

    public static void styleSidebarButton(JButton button) {
        button.setBackground(COLOR_SIDEBAR);
        button.setForeground(COLOR_TEXT_LIGHT);
        button.setFont(FONT_BOLD);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(15, 25, 15, 25));
    }

    public static Border createRoundedBorder() {
        return BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_TEXT_SECONDARY, 1, true),
                new EmptyBorder(8, 12, 8, 12));
    }
}
