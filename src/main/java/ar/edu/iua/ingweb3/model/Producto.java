package ar.edu.iua.ingweb3.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Producto {

	@Id
	@GeneratedValue
	private int idProducto;

	

	private String descripcion;
	private double precio;
	private boolean enStock;
	private Date vencimiento;
	
	@ManyToOne
    @JoinColumn(name="id_rubro", nullable=false)
	@JsonIgnoreProperties("productos")
	private Rubro rubro;
	

	public Producto() {

	}


	public Producto(int idProducto, String descripcion, double precio, boolean enStock, Date vencimiento, Rubro rubro) {
		super();
		this.idProducto = idProducto;
		this.descripcion = descripcion;
		this.precio = precio;
		this.enStock = enStock;
		this.vencimiento = vencimiento;
		this.rubro = rubro;
	}
	
	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
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


	public Rubro getRubro() {
		return rubro;
	}


	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}
	
	

	

}
