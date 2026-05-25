package seoulkitchen.dao;

import seoulkitchen.model.Usuario;
import seoulkitchen.utils.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * UsuarioDAO — Capa de Acceso a Datos para la entidad Usuario.
 *
 * SEGURIDAD: Todas las consultas usan PreparedStatement con parámetros
 *            posicionados (?). NUNCA se concatenan strings en el SQL.
 * RECURSOS:  Cada método gestiona sus propios recursos con try-with-resources.
 */
public class UsuarioDAO {

    // ── SQL Statements ───────────────────────────────────────────────────────
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

    // ── Métodos públicos ─────────────────────────────────────────────────────

    /**
     * Verifica las credenciales contra la base de datos.
     *
     * NOTA IMPORTANTE DE SEGURIDAD:
     * Esta implementación compara el password tal cual está guardado en BD.
     * Para producción real se debe almacenar el hash BCrypt y comparar con
     * BCrypt.checkpw(passwordIngresado, hashAlmacenado).
     * Consulta con tu equipo la política de hashing antes de ir a producción.
     *
     * @param username  Nombre de usuario ingresado en el formulario.
     * @param password  Contraseña ingresada en el formulario.
     * @return          Objeto Usuario si las credenciales son válidas, null si no.
     * @throws SQLException Si ocurre un error de comunicación con la BD.
     */
    public Usuario autenticar(String username, String password) throws SQLException {
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_LOGIN)) {

            ps.setString(1, username);
            ps.setString(2, password);   // reemplazar por hash si se usa BCrypt

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;    // credenciales inválidas → retorna null
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * Validaciones previas al INSERT:
     *  - Ningún campo obligatorio vacío.
     *  - Username no duplicado.
     *
     * @param usuario Objeto Usuario con los datos del formulario.
     * @return El mismo objeto Usuario con el id generado por la BD.
     * @throws IllegalArgumentException Si algún campo obligatorio está vacío.
     * @throws IllegalStateException    Si el username ya existe en la BD.
     * @throws SQLException             Si ocurre un error de comunicación con la BD.
     */
    public Usuario registrar(Usuario usuario) throws SQLException {

        // ── 1. Validación de campos obligatorios ─────────────────────────────
        validarCamposRegistro(usuario);

        // ── 2. Verificar username único ──────────────────────────────────────
        if (usernameExiste(usuario.getUsername())) {
            throw new IllegalStateException(
                "El nombre de usuario '" + usuario.getUsername() + "' ya está en uso.");
        }

        // ── 3. INSERT con try-with-resources ─────────────────────────────────
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

            // Recuperar el ID autogenerado
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    usuario.setId(keys.getInt(1));
                }
            }
        }
        return usuario;
    }

    // ── Métodos privados de apoyo ────────────────────────────────────────────

    /** Mapea un ResultSet posicionado a un objeto Usuario. */
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

    /** Comprueba si un username ya existe en la tabla. */
    private boolean usernameExiste(String username) throws SQLException {
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_USERNAME_EXISTE)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /** Lanza IllegalArgumentException si algún campo obligatorio está vacío. */
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
