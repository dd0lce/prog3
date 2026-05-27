package seoulkitchen.dao;

import seoulkitchen.model.Cliente;
import seoulkitchen.model.Orden;
import seoulkitchen.model.OrdenDetalle;
import seoulkitchen.utils.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdenDAO {
    
    public List<Orden> obtenerTodas() {
        List<Orden> ordenes = new ArrayList<>();
        String sql = "SELECT o.id, o.id_cliente, o.total, o.estado, o.creado_en, " +
                     "c.nombre as cliente_nombre, c.apellido as cliente_apellido " +
                     "FROM ordenes o " +
                     "JOIN clientes c ON o.id_cliente = c.id " +
                     "ORDER BY o.id DESC";
        
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Orden orden = new Orden(
                    rs.getInt("id"),
                    rs.getInt("id_cliente"),
                    rs.getDouble("total"),
                    rs.getString("estado"),
                    rs.getTimestamp("creado_en")
                );
                
                Cliente c = new Cliente(
                    rs.getString("cliente_nombre"),
                    rs.getString("cliente_apellido"),
                    "", ""
                );
                orden.setCliente(c);
                ordenes.add(orden);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ordenes: " + e.getMessage());
        }
        return ordenes;
    }

    public boolean agregar(Orden orden) {
        String sqlOrden = "INSERT INTO ordenes (id_cliente, total, estado) VALUES (?, ?, ?)";
        String sqlDetalle = "INSERT INTO orden_detalle (id_orden, id_platillo, cantidad, precio_unit) VALUES (?, ?, ?, ?)";
        
        Connection con = null;
        try {
            con = ConexionBD.getInstance().getConexion();
            con.setAutoCommit(false); // Iniciar transacción
            
            int idOrdenGenerado = 0;
            
            try (PreparedStatement psOrden = con.prepareStatement(sqlOrden, Statement.RETURN_GENERATED_KEYS)) {
                psOrden.setInt(1, orden.getIdCliente());
                psOrden.setDouble(2, orden.getTotal());
                psOrden.setString(3, orden.getEstado());
                psOrden.executeUpdate();
                
                ResultSet rs = psOrden.getGeneratedKeys();
                if (rs.next()) {
                    idOrdenGenerado = rs.getInt(1);
                }
            }
            
            if (idOrdenGenerado > 0 && orden.getDetalles() != null && !orden.getDetalles().isEmpty()) {
                try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
                    for (OrdenDetalle detalle : orden.getDetalles()) {
                        psDetalle.setInt(1, idOrdenGenerado);
                        psDetalle.setInt(2, detalle.getIdPlatillo());
                        psDetalle.setInt(3, detalle.getCantidad());
                        psDetalle.setDouble(4, detalle.getPrecioUnit());
                        psDetalle.addBatch();
                    }
                    psDetalle.executeBatch();
                }
            }
            
            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error en rollback: " + ex.getMessage());
                }
            }
            System.err.println("Error al agregar orden: " + e.getMessage());
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean actualizarEstado(int idOrden, String nuevoEstado) {
        String sql = "UPDATE ordenes SET estado=? WHERE id=?";
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idOrden);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado de orden: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idOrden) {
        String sqlDetalle = "DELETE FROM orden_detalle WHERE id_orden=?";
        String sqlOrden = "DELETE FROM ordenes WHERE id=?";
        
        Connection con = null;
        try {
            con = ConexionBD.getInstance().getConexion();
            con.setAutoCommit(false);
            
            try (PreparedStatement psDet = con.prepareStatement(sqlDetalle)) {
                psDet.setInt(1, idOrden);
                psDet.executeUpdate();
            }
            
            try (PreparedStatement psOrd = con.prepareStatement(sqlOrden)) {
                psOrd.setInt(1, idOrden);
                psOrd.executeUpdate();
            }
            
            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error en rollback: " + ex.getMessage());
                }
            }
            System.err.println("Error al eliminar orden: " + e.getMessage());
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
