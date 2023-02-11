package com.starblackdian.hotelalura.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.starblackdian.hotelalura.model.entity.Huesped;

public class HuespedDao extends AbstractDao<Huesped> {
	
	public HuespedDao() {
		super();
	}
	
	@Override
	public List<Huesped> listarTodos() {
		final String sql = "SELECT * from huesped";
		
		try (
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)
		) {
			final List<Huesped> lista = new ArrayList<>();
			
			while (rs.next()) {
				final Huesped huesped = new Huesped();
				
				huesped.setId(rs.getInt("id"));
				huesped.setNombre(rs.getString("nombre"));
				huesped.setApellido(rs.getString("apellido"));
				huesped.setFechaNacimiento(rs.getDate("fechanacimiento"));
				huesped.setNacionalidad(rs.getString("nacionalidad"));
				huesped.setTelefono(rs.getString("telefono"));
				
				lista.add(huesped);
			}
			
			return lista;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Huesped buscarPorId(int id) {
		final String sql = "SELECT * from huesped WHERE id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					final Huesped huesped = new Huesped();
					
					huesped.setId(rs.getInt("id"));
					huesped.setNombre(rs.getString("nombre"));
					huesped.setApellido(rs.getString("apellido"));
					huesped.setFechaNacimiento(rs.getDate("fechanacimiento"));
					huesped.setNacionalidad(rs.getString("nacionalidad"));
					huesped.setTelefono(rs.getString("telefono"));
					
					return huesped;
				}
				
				return null;
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void crear(Huesped entidad) {
		final String sql = "INSERT INTO huesped (nombre, apellido, fechanacimiento, nacionalidad, telefono) VALUES (?,?,?,?,?)";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, entidad.getNombre());
			stmt.setString(2, entidad.getApellido());
			stmt.setDate(3, entidad.getFechaNacimiento());
			stmt.setString(4, entidad.getNacionalidad());
			stmt.setString(5, entidad.getTelefono());
			
			stmt.execute();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void actualizar(Huesped entidad) {
		final String sql = "UPDATE huesped SET nombre = ?, apellido = ?, fechanacimiento = ?, nacionalidad = ?, telefono = ? WHERE id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, entidad.getNombre());
			stmt.setString(2, entidad.getApellido());
			stmt.setDate(3, entidad.getFechaNacimiento());
			stmt.setString(4, entidad.getNacionalidad());
			stmt.setString(5, entidad.getTelefono());
			stmt.setInt(6, entidad.getId());
			
			stmt.execute();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void eliminar(int id) {
		final String sql = "DELETE FROM huesped WHERE id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			
			stmt.execute();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
