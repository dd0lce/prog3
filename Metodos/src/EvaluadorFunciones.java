import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Clase encargada de evaluar las funciones matemáticas ingresadas por el
 * usuario
 * en formato de cadena (String). Utiliza la librería externa exp4j.
 * También implementa aproximaciones numéricas por diferencias finitas centrales
 * para calcular la primera y segunda derivada necesarias en los métodos de
 * Newton-Raphson.
 */
public class EvaluadorFunciones {
    private Expression expression;

    public EvaluadorFunciones(String expresionStr) {
        // Se construye la expresión usando exp4j y definiendo "x" como variable
        this.expression = new ExpressionBuilder(expresionStr)
                .variables("x")
                .build();
    }

    // Evalúa la función f(x)
    public double evaluar(double x) {
        return expression.setVariable("x", x).evaluate();
    }

    // Aproximación numérica de f'(x) por diferencias finitas centrales
    public double primeraDerivada(double x) {
        double h = 1e-5;
        return (evaluar(x + h) - evaluar(x - h)) / (2 * h);
    }

    // Aproximación numérica de f''(x) por diferencias finitas centrales
    public double segundaDerivada(double x) {
        double h = 1e-4;
        return (evaluar(x + h) - 2 * evaluar(x) + evaluar(x - h)) / (h * h);
    }
}
