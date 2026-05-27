package seoulkitchen.model;

import java.sql.Timestamp;

public class InventarioItem {
    private int id;
    private String nombre;
    private int cantidad;
    private String unidad;
    private int nivelAlerta;
    private Timestamp creadoEn;

    public InventarioItem(int id, String nombre, int cantidad, String unidad, int nivelAlerta, Timestamp creadoEn) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.nivelAlerta = nivelAlerta;
        this.creadoEn = creadoEn;
    }

    public InventarioItem(String nombre, int cantidad, String unidad, int nivelAlerta) {
        this(0, nombre, cantidad, unidad, nivelAlerta, null);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    public int getNivelAlerta() { return nivelAlerta; }
    public void setNivelAlerta(int nivelAlerta) { this.nivelAlerta = nivelAlerta; }
    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }

    @Override
    public String toString() {
        return nombre;
    }
}
