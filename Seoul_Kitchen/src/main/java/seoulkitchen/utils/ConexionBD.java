package seoulkitchen.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/seoul_kitchen"
            + "?useSSL=false"
            + "&serverTimezone=America/Mexico_City"
            + "&allowPublicKeyRetrieval=true"
            + "&characterEncoding=UTF-8";
    private static final String USUARIO = "root"; 
    private static final String PASSWORD = "abcd1234"; 
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static ConexionBD instancia;
    private ConexionBD() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Driver MySQL no encontrado. Verifica que mysql-connector-j esté en /lib.", e);
        }
    }
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
    public Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
    public boolean probarConexion() {
        try (Connection con = getConexion()) {
            return con != null && !con.isClosed();
        } catch (SQLException e) {
            System.err.println("[ConexionBD] Error al probar conexión: " + e.getMessage());
            return false;
        }
    }
}
