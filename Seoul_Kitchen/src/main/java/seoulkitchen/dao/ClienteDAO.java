package seoulkitchen.dao;

import seoulkitchen.model.Cliente;
import seoulkitchen.utils.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getBoolean("activo"),
                    rs.getTimestamp("creado_en")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
        }
        return clientes;
    }

    public boolean agregar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, apellido, email, telefono, activo) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefono());
            ps.setBoolean(5, cliente.isActivo());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, apellido=?, email=?, telefono=?, activo=? WHERE id=?";
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefono());
            ps.setBoolean(5, cliente.isActivo());
            ps.setInt(6, cliente.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (Connection con = ConexionBD.getInstance().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
