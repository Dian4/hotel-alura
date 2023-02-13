package com.starblackdian.hotelalura.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.starblackdian.hotelalura.model.entity.Reserva;

public class ReservaDao extends AbstractDao<Reserva> {

	public ReservaDao() {
		super();
	}
	
	@Override
	public List<Reserva> listarTodos() {
		final String sql = "SELECT * from reserva";
		
		try (
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)
		) {
			final List<Reserva> lista = new ArrayList<>();
			
			while (rs.next()) {
				final Reserva reserva = new Reserva();
				
				reserva.setId(rs.getInt("id"));
				reserva.setFechaEntrada(rs.getDate("fechaentrada"));
				reserva.setFechaSalida(rs.getDate("fechasalida"));
				reserva.setValor(rs.getBigDecimal("valor"));
				reserva.setFormaPago(rs.getString("formapago"));
				reserva.setIdHuesped(rs.getInt("idhuesped"));
				
				lista.add(reserva);
			}
			
			return lista;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public List<Reserva> listarPorHuespedId(int idHuesped) {
		final String sql = "SELECT * from reserva WHERE idhuesped = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, idHuesped);

			try (ResultSet rs = stmt.executeQuery()) {
				final List<Reserva> lista = new ArrayList<>();

				while (rs.next()) {
					final Reserva reserva = new Reserva();

					reserva.setId(rs.getInt("id"));
					reserva.setFechaEntrada(rs.getDate("fechaentrada"));
					reserva.setFechaSalida(rs.getDate("fechasalida"));
					reserva.setValor(rs.getBigDecimal("valor"));
					reserva.setFormaPago(rs.getString("formapago"));
					reserva.setIdHuesped(rs.getInt("idhuesped"));

					lista.add(reserva);
				}

				return lista;
			}

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Reserva buscarPorId(int id) {
		final String sql = "SELECT * from reserva WHERE id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					final Reserva reserva = new Reserva();
					
					reserva.setId(rs.getInt("id"));
					reserva.setFechaEntrada(rs.getDate("fechaentrada"));
					reserva.setFechaSalida(rs.getDate("fechasalida"));
					reserva.setValor(rs.getBigDecimal("valor"));
					reserva.setFormaPago(rs.getString("formapago"));
					reserva.setIdHuesped(rs.getInt("idhuesped"));
					
					return reserva;
				}
				
				return null;
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void crear(Reserva entidad) {
		final String sql = "INSERT INTO reserva (fechaentrada, fechasalida, valor, formapago, idhuesped) VALUES (?,?,?,?,?)";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, entidad.getFechaEntrada());
			stmt.setDate(2, entidad.getFechaSalida());
			stmt.setBigDecimal(3, entidad.getValor());
			stmt.setString(4, entidad.getFormaPago());
			stmt.setInt(5, entidad.getIdHuesped());
			
			stmt.execute();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void actualizar(Reserva entidad) {
		final String sql = "UPDATE reserva SET fechaentrada = ?, fechasalida = ?, valor = ?, formapago = ? WHERE id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setDate(1, entidad.getFechaEntrada());
			stmt.setDate(2, entidad.getFechaSalida());
			stmt.setBigDecimal(3, entidad.getValor());
			stmt.setString(4, entidad.getFormaPago());
			stmt.setInt(5, entidad.getId());
			
			stmt.execute();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void eliminar(int id) {
		final String sql = "DELETE FROM reserva WHERE id = ?";
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			
			stmt.execute();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
