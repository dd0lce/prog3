package seoulkitchen.dao;

import seoulkitchen.model.Platillo;
import seoulkitchen.utils.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatilloDAO {
    
    public List<Platillo> obtenerTodos() {
        List<Platillo> platillos = new ArrayList<>();
        String sql = "SELECT * FROM platillos";
        
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                platillos.add(new Platillo(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getString("estado"),
                    rs.getTimestamp("creado_en")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener platillos: " + e.getMessage());
        }
        return platillos;
    }

    public boolean agregar(Platillo platillo) {
        String sql = "INSERT INTO platillos (nombre, categoria, precio, estado) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, platillo.getNombre());
            ps.setString(2, platillo.getCategoria());
            ps.setDouble(3, platillo.getPrecio());
            ps.setString(4, platillo.getEstado());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar platillo: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Platillo platillo) {
        String sql = "UPDATE platillos SET nombre=?, categoria=?, precio=?, estado=? WHERE id=?";
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, platillo.getNombre());
            ps.setString(2, platillo.getCategoria());
            ps.setDouble(3, platillo.getPrecio());
            ps.setString(4, platillo.getEstado());
            ps.setInt(5, platillo.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar platillo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM platillos WHERE id=?";
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar platillo: " + e.getMessage());
            return false;
        }
    }
}
