import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class myframe extends JFrame {
    
    myframe(){

        this.setVisible(true);
        this.setTitle("dnh prog 3");
        this.setSize(495, 515);
        this.setResizable(true);
        this.setMinimumSize(new Dimension(495, 515));
        this.setMaximumSize(new Dimension(1600, 1000));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon("icons8-cráneo-16.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(Color.darkGray);
        
        users();
    }

    public void login() {
            JPanel panel = new JPanel();
            panel.setBackground(Color.lightGray);
            panel.setSize(450, 450);
            panel.setLocation(20, 20);
            panel.setLayout(null);
            this.add(panel);

            JLabel title_login = new JLabel();
		    title_login.setText("Bienvenido");
		    title_login.setSize(200, 30);
		    title_login.setOpaque(true);
		    title_login.setLocation(125, 10);
		    title_login.setBackground(Color.lightGray);
		    title_login.setFont(new Font("Arial",Font.BOLD,22));
		    title_login.setHorizontalAlignment(JLabel.CENTER);
		    panel.add(title_login);

            JLabel user_label = new JLabel();
            user_label.setText("Usuario:");
            user_label.setSize(100, 30);
            user_label.setLocation(50, 100);
            user_label.setFont(new Font("Arial",Font.PLAIN,16));
            panel.add(user_label);

            JLabel pass_label = new JLabel();
            pass_label.setText("Contraseña:");
            pass_label.setSize(100, 30);
            pass_label.setLocation(50, 150);
            pass_label.setFont(new Font("Arial",Font.PLAIN,16));
            panel.add(pass_label);

            JTextField user_field = new JTextField();
            user_field.setSize(200, 30);
            user_field.setLocation(150, 100);
            panel.add(user_field);  

            JTextField pass_field = new JTextField();
            pass_field.setSize(200, 30);
            pass_field.setLocation(150, 150);
            panel.add(pass_field);  

            JButton login_button = new JButton();
            login_button.setText("Iniciar Sesión");
            login_button.setSize(150, 30);
            login_button.setLocation(150, 200);
            login_button.setBackground(Color.darkGray);
            login_button.setForeground(Color.black);
            panel.add(login_button);

            this.revalidate();
            this.repaint();
        }

        public void register() {
            JPanel panel = new JPanel();
            panel.setBackground(Color.lightGray);
            panel.setSize(450, 450);
            panel.setLocation(20, 20);
            panel.setLayout(null);
            this.add(panel);

            JLabel title_register = new JLabel();
		    title_register.setText("Registro");
		    title_register.setSize(200, 30);
		    title_register.setOpaque(true);
		    title_register.setLocation(125, 10);
		    title_register.setBackground(Color.lightGray);
		    title_register.setFont(new Font("Arial",Font.BOLD,22));
		    title_register.setHorizontalAlignment(JLabel.CENTER);
		    panel.add(title_register);

            JLabel user_label = new JLabel();
            user_label.setText("Nombre de Usuario:");
            user_label.setSize(180, 30);
            user_label.setLocation(155, 75);
            user_label.setFont(new Font("Arial",Font.PLAIN,16));
            panel.add(user_label);

            JLabel pass_label = new JLabel();
            pass_label.setText("Contraseña:");
            pass_label.setSize(180, 30);
            pass_label.setLocation(175, 125);
            pass_label.setFont(new Font("Arial",Font.PLAIN,16));
            panel.add(pass_label);
            
            JLabel confirm_pass_label = new JLabel();
            confirm_pass_label.setText("Confirmar Contraseña:");
            confirm_pass_label.setSize(180, 30);
            confirm_pass_label.setLocation(145, 175);
            confirm_pass_label.setFont(new Font("Arial",Font.PLAIN,16));
            panel.add(confirm_pass_label);

            JTextField user_field = new JTextField();
            user_field.setSize(200, 30);
            user_field.setLocation(125, 100);
            panel.add(user_field);  

            JTextField pass_field = new JTextField();
            pass_field.setSize(200, 30);
            pass_field.setLocation(125, 150);
            panel.add(pass_field);  

            JTextField confirm_pass_field = new JTextField();
            confirm_pass_field.setSize(200, 30);
            confirm_pass_field.setLocation(125, 200);
            panel.add(confirm_pass_field);

            JButton register_button = new JButton();
            register_button.setText("Registrar");
            register_button.setSize(150, 30);
            register_button.setLocation(150, 250);
            register_button.setBackground(Color.darkGray);
            register_button.setForeground(Color.black);
            panel.add(register_button);

            this.revalidate();
            this.repaint();
        }   

        public void calculator() {
            JPanel panel = new JPanel();
            panel.setBackground(Color.lightGray);
            panel.setSize(450, 450);
            panel.setLocation(20, 20);
            panel.setLayout(null);
            this.add(panel);

            JLabel title_calculator = new JLabel();
            title_calculator.setText("Calculadora");
            title_calculator.setSize(200, 30);
            title_calculator.setOpaque(true);
            title_calculator.setLocation(125, 10);
            title_calculator.setBackground(Color.lightGray);
            title_calculator.setFont(new Font("Arial",Font.BOLD,22));
            title_calculator.setHorizontalAlignment(JLabel.CENTER);
            panel.add(title_calculator);

            // Display area
            JTextArea display = new JTextArea();
            display.setBounds(25, 50, 400, 50);
            display.setFont(new Font("Arial", Font.BOLD, 20));
            display.setBackground(Color.white);
            display.setForeground(Color.black);
            display.setEditable(false);
            panel.add(display);

            // Button panel with GridLayout
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBounds(25, 120, 400, 300);
            buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
            buttonPanel.setBackground(Color.lightGray);

            String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
            };

            for (String text : buttons) {
                JButton button = new JButton(text);
                button.setFont(new Font("Arial", Font.BOLD, 16));
                button.setBackground(Color.darkGray);
                button.setForeground(Color.black);
                buttonPanel.add(button);
            }

            panel.add(buttonPanel);
            this.revalidate();
            this.repaint();
        }

       public void users() {
            // Center the panel within the frame
            int panelWidth = 900;
            int panelHeight = 450;
            int panelX = (this.getWidth() - panelWidth) / 2;
            int panelY = (this.getHeight() - panelHeight) / 2;
            
            JPanel users = new JPanel();
            users.setBounds(panelX, panelY, panelWidth, panelHeight);
            users.setBackground(Color.white);
            users.setLayout(null);
            this.add(users);
            // Center the title
            int titleWidth = 200;
            int titleHeight = 40;
            int titleX = (panelWidth - titleWidth) / 2;
            JLabel users_title = new JLabel("USUARIOS");
            users_title.setBounds(titleX, 20, titleWidth, titleHeight);
            users_title.setHorizontalAlignment(JLabel.CENTER);
            users_title.setOpaque(true);
            users_title.setFont(new Font("Arial",Font.BOLD,22));
            users_title.setBackground(Color.decode("#F27A61"));
            users.add(users_title);
		
            // Center the buttons horizontally
            int buttonY = 80;
            int buttonWidth = 100;
            int buttonHeight = 40;
            int buttonSpacing = 20;
            int totalButtonWidth = (buttonWidth * 2) + buttonSpacing;
            int buttonStartX = (panelWidth - totalButtonWidth) / 2;
            
            JButton export = new JButton("Exportar");
            export.setBounds(buttonStartX, buttonY, buttonWidth, buttonHeight);
            users.add(export);
            
            JButton add = new JButton("Añadir");
            add.setBounds(buttonStartX + buttonWidth + buttonSpacing, buttonY, buttonWidth, buttonHeight);
            users.add(add);
		
		Object[] table_head = {"No. control","Nombre","Apellidos","Semestre","Promedio","Acciones"};
		
		Object [][] table_content = {
		    {"20231001","Juan","Pérez García","3","8.7","Editar"},
		    {"20231002","María","López Hernández","5","9.2","Editar"},
		    {"20231003","Carlos","Ramírez Torres","2","7.9","Editar"},
		    {"20231004","Ana","Gómez Sánchez","6","9.5","Editar"},
		    {"20231001","Juan","Pérez García","3","8.7","Editar"},
		    {"20231002","María","López Hernández","5","9.2","Editar"},
		    {"20231003","Carlos","Ramírez Torres","2","7.9","Editar"},
		    {"20231004","Ana","Gómez Sánchez","6","9.5","Editar"},
		    {"20231001","Juan","Pérez García","3","8.7","Editar"},
		    {"20231002","María","López Hernández","5","9.2","Editar"},
		    {"20231003","Carlos","Ramírez Torres","2","7.9","Editar"},
		    {"20231004","Ana","Gómez Sánchez","6","9.5","Editar"},
		    {"20231001","Juan","Pérez García","3","8.7","Editar"},
		    {"20231002","María","López Hernández","5","9.2","Editar"},
		    {"20231003","Carlos","Ramírez Torres","2","7.9","Editar"},
		    {"20231004","Ana","Gómez Sánchez","6","9.5","Editar"}
		};
		
            JTable users_table = new JTable(table_content,table_head);
            JScrollPane scrollPane = new JScrollPane(users_table);
            
            // Center the table
            int tableY = 140;
            int tableWidth = panelWidth - 60; // Leave some margin
            int tableHeight = panelHeight - tableY - 20; // Leave bottom margin
            scrollPane.setBounds(30, tableY, tableWidth, tableHeight);
            
            users.add(scrollPane);
       } 
}