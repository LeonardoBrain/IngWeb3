package ar.edu.iua.ingweb3.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Rubro {
	
	@Id
	@GeneratedValue
	private int idRubro;
	
	private String descripcion;
	
	@OneToMany(mappedBy="rubro")
	private List<Producto> productos;

	public Rubro() {

	}

	public Rubro(int idRubro, String descripcion, List<Producto> productos) {
		super();
		this.idRubro = idRubro;
		this.descripcion = descripcion;
		this.productos = productos;
	}

	public int getIdRubro() {
		return idRubro;
	}

	public void setIdRubro(int idRubro) {
		this.idRubro = idRubro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	
	
	
	
	

}
