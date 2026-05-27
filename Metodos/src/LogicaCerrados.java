import java.util.ArrayList;
import java.util.List;

/**
 * Modelo (MVC): Contiene la lógica matemática para los Métodos Cerrados
 * (Bisección y Falsa Posición).
 */
public class LogicaCerrados {

    public static List<Object[]> biseccion(EvaluadorFunciones f, double a, double b, double tolerancia, int maxIter) throws Exception {
        if (f.evaluar(a) * f.evaluar(b) >= 0) {
            throw new Exception("El intervalo no encierra una raíz o f(a)*f(b) >= 0.");
        }

        List<Object[]> filas = new ArrayList<>();
        double c = a;
        double errorActual = 100;
        int iter = 1;
        double cAnterior;

        while (iter <= maxIter) {
            cAnterior = c;
            c = (a + b) / 2.0;
            double fc = f.evaluar(c);
            
            if (iter > 1) {
                errorActual = Math.abs((c - cAnterior) / c) * 100;
            }

            filas.add(new Object[]{
                    iter,
                    String.format("%.6f", c),
                    String.format("%.6f", fc),
                    iter == 1 ? "---" : String.format("%.4f%%", errorActual)
            });

            if (iter > 1 && errorActual < tolerancia) break;
            if (fc == 0) break;

            if (f.evaluar(a) * fc < 0) {
                b = c;
            } else {
                a = c;
            }
            iter++;
        }
        
        filas.add(new Object[]{"FINAL", String.format("%.6f", c), "---", "---"});
        return filas;
    }

    public static List<Object[]> falsaPosicion(EvaluadorFunciones f, double a, double b, double tolerancia, int maxIter) throws Exception {
        if (f.evaluar(a) * f.evaluar(b) >= 0) {
            throw new Exception("El intervalo no encierra una raíz o f(a)*f(b) >= 0.");
        }

        List<Object[]> filas = new ArrayList<>();
        double c = a;
        double errorActual = 100;
        int iter = 1;
        double cAnterior;

        while (iter <= maxIter) {
            cAnterior = c;
            double fa = f.evaluar(a);
            double fb = f.evaluar(b);
            
            // Fórmula Falsa Posición
            c = b - (fb * (a - b)) / (fa - fb);
            double fc = f.evaluar(c);
            
            if (iter > 1) {
                errorActual = Math.abs((c - cAnterior) / c) * 100;
            }

            filas.add(new Object[]{
                    iter,
                    String.format("%.6f", c),
                    String.format("%.6f", fc),
                    iter == 1 ? "---" : String.format("%.4f%%", errorActual)
            });

            if (iter > 1 && errorActual < tolerancia) break;
            if (fc == 0) break;

            if (fa * fc < 0) {
                b = c;
            } else {
                a = c;
            }
            iter++;
        }
        
        filas.add(new Object[]{"FINAL", String.format("%.6f", c), "---", "---"});
        return filas;
    }
}
