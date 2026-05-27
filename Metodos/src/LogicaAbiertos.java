import java.util.ArrayList;
import java.util.List;

/**
 * Modelo (MVC): Contiene la lógica matemática para los Métodos Abiertos
 * (Newton-Raphson, Secante y Newton-Raphson Modificado).
 */
public class LogicaAbiertos {

    public static List<Object[]> newtonRaphson(EvaluadorFunciones f, double x0, double tolerancia, int maxIter) throws Exception {
        List<Object[]> filas = new ArrayList<>();
        double xActual = x0;
        double errorActual = 100;
        int iter = 1;

        while (iter <= maxIter) {
            double fx = f.evaluar(xActual);
            double dfx = f.primeraDerivada(xActual);

            if (Math.abs(dfx) < 1e-12) {
                throw new ArithmeticException("La derivada es muy cercana a cero, división por cero inminente.");
            }

            double xSiguiente = xActual - (fx / dfx);
            errorActual = Math.abs((xSiguiente - xActual) / xSiguiente) * 100;

            filas.add(new Object[]{
                    iter,
                    String.format("%.6f", xSiguiente),
                    String.format("%.6f", f.evaluar(xSiguiente)),
                    String.format("%.4f%%", errorActual)
            });

            if (errorActual < tolerancia) {
                xActual = xSiguiente;
                break;
            }

            xActual = xSiguiente;
            iter++;
        }

        filas.add(new Object[]{"FINAL", String.format("%.6f", xActual), "---", "---"});
        return filas;
    }

    public static List<Object[]> secante(EvaluadorFunciones f, double x0, double x1, double tolerancia, int maxIter) throws Exception {
        List<Object[]> filas = new ArrayList<>();
        double xPrevio = x0;
        double xActual = x1;
        double errorActual = 100;
        int iter = 1;

        while (iter <= maxIter) {
            double fxActual = f.evaluar(xActual);
            double fxPrevio = f.evaluar(xPrevio);

            double denominador = fxActual - fxPrevio;
            if (Math.abs(denominador) < 1e-12) {
                throw new ArithmeticException("División por cero en la Secante (f(xi) es igual a f(xi-1)).");
            }

            double xSiguiente = xActual - (fxActual * (xActual - xPrevio)) / denominador;
            errorActual = Math.abs((xSiguiente - xActual) / xSiguiente) * 100;

            filas.add(new Object[]{
                    iter,
                    String.format("%.6f", xSiguiente),
                    String.format("%.6f", f.evaluar(xSiguiente)),
                    String.format("%.4f%%", errorActual)
            });

            if (errorActual < tolerancia) {
                xActual = xSiguiente;
                break;
            }

            xPrevio = xActual;
            xActual = xSiguiente;
            iter++;
        }

        filas.add(new Object[]{"FINAL", String.format("%.6f", xActual), "---", "---"});
        return filas;
    }

    public static List<Object[]> newtonRaphsonModificado(EvaluadorFunciones f, double x0, double tolerancia, int maxIter) throws Exception {
        List<Object[]> filas = new ArrayList<>();
        double xActual = x0;
        double errorActual = 100;
        int iter = 1;

        while (iter <= maxIter) {
            double fx = f.evaluar(xActual);
            double dfx = f.primeraDerivada(xActual);
            double ddfx = f.segundaDerivada(xActual);

            double denominador = (dfx * dfx) - (fx * ddfx);
            if (Math.abs(denominador) < 1e-12) {
                throw new ArithmeticException("División por cero en Newton-Raphson Modificado.");
            }

            double xSiguiente = xActual - (fx * dfx) / denominador;
            errorActual = Math.abs((xSiguiente - xActual) / xSiguiente) * 100;

            filas.add(new Object[]{
                    iter,
                    String.format("%.6f", xSiguiente),
                    String.format("%.6f", f.evaluar(xSiguiente)),
                    String.format("%.4f%%", errorActual)
            });

            if (errorActual < tolerancia) {
                xActual = xSiguiente;
                break;
            }

            xActual = xSiguiente;
            iter++;
        }

        filas.add(new Object[]{"FINAL", String.format("%.6f", xActual), "---", "---"});
        return filas;
    }
}
