package ar.edu.iua.ingweb3.model;

import java.util.Date;

public class Producto {
	
	private int id;
	private String descripcion;
	private double precio;
	private boolean enStock;
	private Date vencimiento;
	
	public Producto () {
		
		
	}
	
	
	
	
	public Producto(int id, String descripcion, double precio, boolean enStock, Date vencimiento) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.precio = precio;
		this.enStock = enStock;
		this.vencimiento = vencimiento;
	}




	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public boolean isEnStock() {
		return enStock;
	}
	public void setEnStock(boolean enStock) {
		this.enStock = enStock;
	}
	public Date getVencimiento() {
		return vencimiento;
	}
	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}
	
	
	
	

}
