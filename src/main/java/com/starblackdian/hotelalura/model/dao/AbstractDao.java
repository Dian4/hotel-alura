package com.starblackdian.hotelalura.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDao<Entity> implements AutoCloseable {

	protected final Connection conn;
	private final String connectionString = "jdbc:mysql://localhost/hotelalura";
	private final String username = "hotelalurauser";
	private final String password = "hotelalurauser";
	
	public AbstractDao() {
		try {
			conn = DriverManager.getConnection(connectionString, username, password);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public abstract List<Entity> listarTodos();
	
	public abstract Entity buscarPorId(int id);
	
	public abstract void crear(Entity entidad);
	
	public abstract void actualizar(Entity entidad);
	
	public abstract void eliminar(int id);
	
	@Override
	public void close() {
		try {
			conn.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
