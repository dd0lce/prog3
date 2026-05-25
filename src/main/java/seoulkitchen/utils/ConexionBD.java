package seoulkitchen.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConexionBD — Gestiona la conexión JDBC a MySQL usando el patrón Singleton.
 *
 * SEGURIDAD: Centraliza credenciales en un único punto.
 * RECURSOS: Provee getConexion() para usar dentro de try-with-resources
 * en cada DAO, garantizando el cierre automático.
 */
public class ConexionBD {

    // ── Configuración de conexión ────────────────────────────────────────────
    private static final String URL = "jdbc:mysql://localhost:3306/seoul_kitchen"
            + "?useSSL=false"
            + "&serverTimezone=America/Mexico_City"
            + "&allowPublicKeyRetrieval=true"
            + "&characterEncoding=UTF-8";
    private static final String USUARIO = "root"; // ← ajusta según tu entorno
    private static final String PASSWORD = "abcd1234"; // ← ajusta según tu entorno
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // ── Instancia Singleton ──────────────────────────────────────────────────
    private static ConexionBD instancia;

    /** Constructor privado — impide instanciación directa. */
    private ConexionBD() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Driver MySQL no encontrado. Verifica que mysql-connector-j esté en /lib.", e);
        }
    }

    /**
     * Devuelve la única instancia de ConexionBD (thread-safe con doble
     * verificación).
     */
    public static ConexionBD getInstance() {
        if (instancia == null) {
            synchronized (ConexionBD.class) {
                if (instancia == null) {
                    instancia = new ConexionBD();
                }
            }
        }
        return instancia;
    }

    /**
     * Abre y retorna una nueva conexión JDBC.
     *
     * USO OBLIGATORIO — siempre dentro de try-with-resources:
     * 
     * <pre>
     * try (Connection con = ConexionBD.getInstance().getConexion()) {
     *     // usar PreparedStatement aquí
     * }
     * </pre>
     *
     * @return Connection activa a la BD.
     * @throws SQLException si no se puede establecer la conexión.
     */
    public Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    /**
     * Método de diagnóstico — verifica la conexión sin retener recursos.
     *
     * @return true si la conexión es exitosa, false en caso contrario.
     */
    public boolean probarConexion() {
        try (Connection con = getConexion()) {
            return con != null && !con.isClosed();
        } catch (SQLException e) {
            System.err.println("[ConexionBD] Error al probar conexión: " + e.getMessage());
            return false;
        }
    }
}
