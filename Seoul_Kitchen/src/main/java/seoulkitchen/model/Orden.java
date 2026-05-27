package seoulkitchen.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Orden {
    private int id;
    private int idCliente;
    private double total;
    private String estado;
    private Timestamp creadoEn;

    // Relaciones para mostrar en UI
    private Cliente cliente;
    private List<OrdenDetalle> detalles;

    public Orden(int id, int idCliente, double total, String estado, Timestamp creadoEn) {
        this.id = id;
        this.idCliente = idCliente;
        this.total = total;
        this.estado = estado;
        this.creadoEn = creadoEn;
        this.detalles = new ArrayList<>();
    }

    public Orden(int idCliente, double total, String estado) {
        this(0, idCliente, total, estado, null);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<OrdenDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<OrdenDetalle> detalles) { this.detalles = detalles; }

    public void addDetalle(OrdenDetalle detalle) {
        this.detalles.add(detalle);
        calcularTotal();
    }

    public void calcularTotal() {
        this.total = 0;
        for (OrdenDetalle d : detalles) {
            this.total += d.getSubtotal();
        }
    }
}
