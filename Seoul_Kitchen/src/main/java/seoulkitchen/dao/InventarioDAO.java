package seoulkitchen.dao;

import seoulkitchen.model.InventarioItem;
import seoulkitchen.utils.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {
    
    public List<InventarioItem> obtenerTodos() {
        List<InventarioItem> items = new ArrayList<>();
        String sql = "SELECT * FROM inventario";
        
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                items.add(new InventarioItem(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("cantidad"),
                    rs.getString("unidad"),
                    rs.getInt("nivel_alerta"),
                    rs.getTimestamp("creado_en")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener inventario: " + e.getMessage());
        }
        return items;
    }

    public boolean agregar(InventarioItem item) {
        String sql = "INSERT INTO inventario (nombre, cantidad, unidad, nivel_alerta) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, item.getNombre());
            ps.setInt(2, item.getCantidad());
            ps.setString(3, item.getUnidad());
            ps.setInt(4, item.getNivelAlerta());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar inventario: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(InventarioItem item) {
        String sql = "UPDATE inventario SET nombre=?, cantidad=?, unidad=?, nivel_alerta=? WHERE id=?";
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, item.getNombre());
            ps.setInt(2, item.getCantidad());
            ps.setString(3, item.getUnidad());
            ps.setInt(4, item.getNivelAlerta());
            ps.setInt(5, item.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar inventario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM inventario WHERE id=?";
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar inventario: " + e.getMessage());
            return false;
        }
    }
}
