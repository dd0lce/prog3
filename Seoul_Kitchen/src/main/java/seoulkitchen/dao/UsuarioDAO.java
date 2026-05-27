package seoulkitchen.dao;
import seoulkitchen.model.Usuario;
import seoulkitchen.utils.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class UsuarioDAO {
    private static final String SQL_LOGIN =
        "SELECT id, nombre, apellido, email, username, password_hash " +
        "FROM usuarios " +
        "WHERE username = ? AND password_hash = ? " +
        "LIMIT 1";
    private static final String SQL_INSERTAR =
        "INSERT INTO usuarios (nombre, apellido, email, username, password_hash) " +
        "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_USERNAME_EXISTE =
        "SELECT COUNT(*) FROM usuarios WHERE username = ?";
    public Usuario autenticar(String username, String password) throws SQLException {
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_LOGIN)) {
            ps.setString(1, username);
            ps.setString(2, password);   
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;    
    }
    public Usuario registrar(Usuario usuario) throws SQLException {
        validarCamposRegistro(usuario);
        if (usernameExiste(usuario.getUsername())) {
            throw new IllegalStateException(
                "El nombre de usuario '" + usuario.getUsername() + "' ya está en uso.");
        }
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(
                 SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getUsername());
            ps.setString(5, usuario.getPasswordHash());
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new SQLException("El INSERT no afectó ninguna fila.");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    usuario.setId(keys.getInt(1));
                }
            }
        }
        return usuario;
    }
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("email"),
            rs.getString("username"),
            rs.getString("password_hash")
        );
    }
    private boolean usernameExiste(String username) throws SQLException {
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_USERNAME_EXISTE)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    private void validarCamposRegistro(Usuario u) {
        if (u.getNombre()       == null || u.getNombre().trim().isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        if (u.getApellido()     == null || u.getApellido().trim().isEmpty())
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        if (u.getEmail()        == null || u.getEmail().trim().isEmpty())
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío.");
        if (u.getUsername()     == null || u.getUsername().trim().isEmpty())
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío.");
        if (u.getPasswordHash() == null || u.getPasswordHash().trim().isEmpty())
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
    }
}
