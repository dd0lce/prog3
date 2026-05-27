package seoulkitchen.model;

import java.sql.Timestamp;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private boolean activo;
    private Timestamp creadoEn;

    public Cliente(int id, String nombre, String apellido, String email, String telefono, boolean activo, Timestamp creadoEn) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.activo = activo;
        this.creadoEn = creadoEn;
    }

    public Cliente(String nombre, String apellido, String email, String telefono) {
        this(0, nombre, apellido, email, telefono, true, null);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }
}
