package seoulkitchen.model;

public class OrdenDetalle {
    private int id;
    private int idOrden;
    private int idPlatillo;
    private int cantidad;
    private double precioUnit;
    
    // Transitory fields to display info in UI
    private String platilloNombre;

    public OrdenDetalle(int id, int idOrden, int idPlatillo, int cantidad, double precioUnit) {
        this.id = id;
        this.idOrden = idOrden;
        this.idPlatillo = idPlatillo;
        this.cantidad = cantidad;
        this.precioUnit = precioUnit;
    }

    public OrdenDetalle(int idPlatillo, int cantidad, double precioUnit) {
        this(0, 0, idPlatillo, cantidad, precioUnit);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }
    public int getIdPlatillo() { return idPlatillo; }
    public void setIdPlatillo(int idPlatillo) { this.idPlatillo = idPlatillo; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnit() { return precioUnit; }
    public void setPrecioUnit(double precioUnit) { this.precioUnit = precioUnit; }

    public String getPlatilloNombre() { return platilloNombre; }
    public void setPlatilloNombre(String platilloNombre) { this.platilloNombre = platilloNombre; }

    public double getSubtotal() {
        return this.cantidad * this.precioUnit;
    }
}
