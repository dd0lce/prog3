package seoulkitchen.model;

/**
 * Modelo de dominio para la entidad Usuario.
 * Mapea la tabla: usuarios (id, nombre, apellido, email, username, password_hash)
 */
public class Usuario {

    private int    id;
    private String nombre;
    private String apellido;
    private String email;
    private String username;
    private String passwordHash;

    // ── Constructor completo ─────────────────────────────────────────────────
    public Usuario(int id, String nombre, String apellido,
                   String email, String username, String passwordHash) {
        this.id           = id;
        this.nombre       = nombre;
        this.apellido     = apellido;
        this.email        = email;
        this.username     = username;
        this.passwordHash = passwordHash;
    }

    /** Constructor para registro (sin id aún). */
    public Usuario(String nombre, String apellido,
                   String email, String username, String passwordHash) {
        this(0, nombre, apellido, email, username, passwordHash);
    }

    // ── Getters / Setters ────────────────────────────────────────────────────
    public int    getId()           { return id; }
    public String getNombre()       { return nombre; }
    public String getApellido()     { return apellido; }
    public String getEmail()        { return email; }
    public String getUsername()     { return username; }
    public String getPasswordHash() { return passwordHash; }

    public void setId(int id)                     { this.id = id; }
    public void setNombre(String nombre)          { this.nombre = nombre; }
    public void setApellido(String apellido)      { this.apellido = apellido; }
    public void setEmail(String email)            { this.email = email; }
    public void setUsername(String username)      { this.username = username; }
    public void setPasswordHash(String ph)        { this.passwordHash = ph; }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", username='" + username + "', nombre='" + nombre + "'}";
    }
}
