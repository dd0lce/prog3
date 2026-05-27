package seoulkitchen.model;

import java.sql.Timestamp;

public class Platillo {
    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private String estado;
    private Timestamp creadoEn;

    public Platillo(int id, String nombre, String categoria, double precio, String estado, Timestamp creadoEn) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
        this.creadoEn = creadoEn;
    }

    public Platillo(String nombre, String categoria, double precio, String estado) {
        this(0, nombre, categoria, precio, estado, null);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }

    @Override
    public String toString() {
        return nombre;
    }
}
