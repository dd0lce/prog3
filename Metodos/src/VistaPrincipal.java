import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;

/**
 * Vista y Controlador (MVC):
 * Interfaz principal y cableado de ActionListeners a los modelos de lógica.
 * Utiliza un estilo DedSec (hacker).
 */
public class VistaPrincipal extends JFrame {

    private JPanel panelPrincipal;
    private CardLayout cardLayout;

    // Paleta de colores DedSec
    private final Color FONDO_NEGRO = new Color(10, 10, 10);
    private final Color TEXTO_BLANCO = new Color(240, 240, 240);
    private final Color VERDE_NEON = new Color(57, 255, 20);
    private final Font FUENTE_TERMINAL = new Font("Monospaced", Font.BOLD, 14);

    public VistaPrincipal() {
        setTitle("MÉTODOS NUMÉRICOS - UABCS");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Estilo global para OptionPane
        UIManager.put("OptionPane.background", FONDO_NEGRO);
        UIManager.put("Panel.background", FONDO_NEGRO);
        UIManager.put("OptionPane.messageForeground", VERDE_NEON);

        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBackground(FONDO_NEGRO);

        panelPrincipal.add(crearMenuPrincipal(), "MENU");
        panelPrincipal.add(crearModuloCerrados(), "CERRADOS");
        panelPrincipal.add(crearModuloAbiertos(), "ABIERTOS");

        add(panelPrincipal);
    }

    private JPanel crearMenuPrincipal() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FONDO_NEGRO);

        String asciiArt = "<html><pre style='color:#39FF14; text-align:center;'>" +
                "  __  __  _   _  __  _                <br>" +
                " |  \\/  || \\ | |/ / | |               <br>" +
                " | \\  / ||  \\| | ' /| |__    ___  ___ <br>" +
                " | |\\/| || . ` |  < | '_ \\  / _ \\/ __|<br>" +
                " | |  | || |\\  | . \\| | | ||  __/\\__ \\<br>" +
                " |_|  |_||_| \\_|_|\\_\\_| |_| \\___||___/<br>" +
                " v2.0 - PROYECTO FINAL - SYSTEM READY " +
                "</pre></html>";

        panel.add(new JLabel(asciiArt, SwingConstants.CENTER), BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 20, 20));
        panelBotones.setBackground(FONDO_NEGRO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));

        JButton btnCerrados = estilizarBoton("MÉTODOS CERRADOS");
        JButton btnAbiertos = estilizarBoton("MÉTODOS ABIERTOS");

        btnCerrados.addActionListener(e -> cardLayout.show(panelPrincipal, "CERRADOS"));
        btnAbiertos.addActionListener(e -> cardLayout.show(panelPrincipal, "ABIERTOS"));

        panelBotones.add(btnCerrados);
        panelBotones.add(btnAbiertos);

        panel.add(panelBotones, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearModuloCerrados() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(FONDO_NEGRO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelInputs = new JPanel(new GridLayout(6, 2, 10, 10));
        panelInputs.setBackground(FONDO_NEGRO);

        JComboBox<String> cbMetodos = new JComboBox<>(new String[] { "Bisección", "Falsa Posición" });
        cbMetodos.setBackground(FONDO_NEGRO);
        cbMetodos.setForeground(VERDE_NEON);

        JTextField txtFuncion = estilizarTextField("x^2 - 4");
        JTextField txtA = estilizarTextField("1");
        JTextField txtB = estilizarTextField("3");
        JTextField txtError = estilizarTextField("0.001");
        JTextField txtIteraciones = estilizarTextField("50");

        panelInputs.add(estilizarLabel("Seleccionar Método:"));
        panelInputs.add(cbMetodos);
        panelInputs.add(estilizarLabel("Función f(x):"));
        panelInputs.add(txtFuncion);
        panelInputs.add(estilizarLabel("Intervalo [a]:"));
        panelInputs.add(txtA);
        panelInputs.add(estilizarLabel("Intervalo [b]:"));
        panelInputs.add(txtB);
        panelInputs.add(estilizarLabel("Error Tolerado (%):"));
        panelInputs.add(txtError);
        panelInputs.add(estilizarLabel("Max Iteraciones:"));
        panelInputs.add(txtIteraciones);

        panel.add(panelInputs, BorderLayout.NORTH);

        String[] columnas = { "Iteración", "Raíz Approx (c)", "f(c)", "Error Relativo" };
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = estilizarTabla(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(FONDO_NEGRO);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(FONDO_NEGRO);

        JButton btnCalcular = estilizarBoton("CALCULAR >_");
        JButton btnVolver = estilizarBoton("<- VOLVER");

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "MENU"));

        btnCalcular.addActionListener(e -> {
            try {
                String func = txtFuncion.getText();
                double a = Double.parseDouble(txtA.getText());
                double b = Double.parseDouble(txtB.getText());
                double tolerancia = Double.parseDouble(txtError.getText());
                int maxIter = Integer.parseInt(txtIteraciones.getText());

                EvaluadorFunciones evaluador = new EvaluadorFunciones(func);
                modeloTabla.setRowCount(0);

                List<Object[]> resultados;
                if (cbMetodos.getSelectedIndex() == 0) {
                    resultados = LogicaCerrados.biseccion(evaluador, a, b, tolerancia, maxIter);
                } else {
                    resultados = LogicaCerrados.falsaPosicion(evaluador, a, b, tolerancia, maxIter);
                }

                for (Object[] fila : resultados) {
                    modeloTabla.addRow(fila);
                }

            } catch (NumberFormatException ex) {
                mostrarError("Verifica que los campos contengan valores numéricos válidos.");
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        panelBotones.add(btnVolver);
        panelBotones.add(btnCalcular);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearModuloAbiertos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(FONDO_NEGRO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelInputs = new JPanel(new GridLayout(6, 2, 10, 10));
        panelInputs.setBackground(FONDO_NEGRO);

        JComboBox<String> cbMetodos = new JComboBox<>(
                new String[] { "Newton-Raphson", "Secante", "Newton-Raphson Modificado" });
        cbMetodos.setBackground(FONDO_NEGRO);
        cbMetodos.setForeground(VERDE_NEON);

        JTextField txtFuncion = estilizarTextField("x^2 - 4");
        JTextField txtX0 = estilizarTextField("1");
        JTextField txtX1 = estilizarTextField("N/A");
        txtX1.setEnabled(false);
        JTextField txtError = estilizarTextField("0.001");
        JTextField txtIteraciones = estilizarTextField("50");

        JLabel lblX1 = estilizarLabel("Valor Inicial (x1):");

        // Listener para ocultar x1 si no es Secante
        cbMetodos.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                boolean isSecante = "Secante".equals(e.getItem().toString());
                txtX1.setEnabled(isSecante);
                if (!isSecante)
                    txtX1.setText("N/A");
                else
                    txtX1.setText("3");
            }
        });

        panelInputs.add(estilizarLabel("Seleccionar Método:"));
        panelInputs.add(cbMetodos);
        panelInputs.add(estilizarLabel("Función f(x):"));
        panelInputs.add(txtFuncion);
        panelInputs.add(estilizarLabel("Valor Inicial (x0):"));
        panelInputs.add(txtX0);
        panelInputs.add(lblX1);
        panelInputs.add(txtX1);
        panelInputs.add(estilizarLabel("Error Tolerado (%):"));
        panelInputs.add(txtError);
        panelInputs.add(estilizarLabel("Max Iteraciones:"));
        panelInputs.add(txtIteraciones);

        panel.add(panelInputs, BorderLayout.NORTH);

        String[] columnas = { "Iteración", "Raíz Approx (xi)", "f(xi)", "Error Relativo" };
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = estilizarTabla(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(FONDO_NEGRO);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(FONDO_NEGRO);

        JButton btnCalcular = estilizarBoton("CALCULAR >_");
        JButton btnVolver = estilizarBoton("<- VOLVER");

        btnVolver.addActionListener(e -> cardLayout.show(panelPrincipal, "MENU"));

        btnCalcular.addActionListener(e -> {
            try {
                String func = txtFuncion.getText();
                double x0 = Double.parseDouble(txtX0.getText());
                double tolerancia = Double.parseDouble(txtError.getText());
                int maxIter = Integer.parseInt(txtIteraciones.getText());

                EvaluadorFunciones evaluador = new EvaluadorFunciones(func);
                modeloTabla.setRowCount(0);

                List<Object[]> resultados;
                String metodoSel = cbMetodos.getSelectedItem().toString();

                if (metodoSel.equals("Newton-Raphson")) {
                    resultados = LogicaAbiertos.newtonRaphson(evaluador, x0, tolerancia, maxIter);
                } else if (metodoSel.equals("Secante")) {
                    double x1 = Double.parseDouble(txtX1.getText());
                    resultados = LogicaAbiertos.secante(evaluador, x0, x1, tolerancia, maxIter);
                } else {
                    resultados = LogicaAbiertos.newtonRaphsonModificado(evaluador, x0, tolerancia, maxIter);
                }

                for (Object[] fila : resultados) {
                    modeloTabla.addRow(fila);
                }

            } catch (NumberFormatException ex) {
                mostrarError("Verifica que los campos contengan valores numéricos válidos.");
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        panelBotones.add(btnVolver);
        panelBotones.add(btnCalcular);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, "[!] ERROR: " + mensaje, "SYSTEM FAILURE", JOptionPane.ERROR_MESSAGE);
    }

    private JTable estilizarTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setBackground(FONDO_NEGRO);
        tabla.setForeground(TEXTO_BLANCO);
        tabla.setGridColor(VERDE_NEON);
        tabla.setFont(new Font("Monospaced", Font.PLAIN, 12));
        tabla.getTableHeader().setBackground(FONDO_NEGRO);
        tabla.getTableHeader().setForeground(VERDE_NEON);
        tabla.getTableHeader().setFont(FUENTE_TERMINAL);
        return tabla;
    }

    private JButton estilizarBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(FONDO_NEGRO);
        btn.setForeground(VERDE_NEON);
        btn.setFont(FUENTE_TERMINAL);
        btn.setBorder(BorderFactory.createLineBorder(VERDE_NEON, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }

    private JLabel estilizarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(TEXTO_BLANCO);
        lbl.setFont(FUENTE_TERMINAL);
        return lbl;
    }

    private JTextField estilizarTextField(String textoBase) {
        JTextField txt = new JTextField(textoBase);
        txt.setBackground(Color.DARK_GRAY);
        txt.setForeground(VERDE_NEON);
        txt.setFont(FUENTE_TERMINAL);
        txt.setCaretColor(VERDE_NEON);
        txt.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return txt;
    }
}
