package com.starblackdian.hotelalura.model.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class Reserva {
	
	private int id;
	private String huesped;
	private Date fechaEntrada;
	private Date fechaSalida;
	private  BigDecimal valor;
	private String formaPago;
	private int idHuesped;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public int getIdHuesped() {
		return idHuesped;
	}
	public void setIdHuesped(int idHuesped) {
		this.idHuesped = idHuesped;
	}
	public String getHuesped() {
		return huesped;
	}
	public void setHuesped(String huesped) {
		this.huesped = huesped;
	}
}
