package ar.edu.iua.ingweb3.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IProductoBusiness;
import ar.edu.iua.ingweb3.model.Producto;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

@Service
public class ProductoBusiness implements IProductoBusiness {

	private List<Producto> r = new ArrayList<Producto>();

	public ProductoBusiness() {

		r.add(new Producto(1, "Arroz", 41.50, true, new Date()));
		r.add(new Producto(2, "Leche", 35, true, new Date()));
		r.add(new Producto(3, "Cerveza", 49.50, true, null));

	}

	@Override
	public List<Producto> getAll() throws BusinessException {
		// TODO Auto-generated method stub
		return r;
	}

	@Override
	public Producto getOne(int id) throws BusinessException, NotFoundException {

		Producto pr = null;
		for (Producto p : r) {
			if (p.getId() == id) {
				pr = p;
				break;
			}

		}

		if (pr == null) {
			throw new NotFoundException();
		}

		return pr;
	}

	@Override
	public Producto add(Producto producto) throws BusinessException {

		r.add(producto);
		return producto;
	}

	@Override
	public Producto update(Producto producto) throws BusinessException, NotFoundException {

		boolean found = false;

		for (int t = 0; t < r.size(); t++) {

			Producto prod = r.get(t);

			if (prod.getId() == producto.getId()) {
				r.set(t, producto);
				found = true;
				break;
			}
		}

		if (!found) {

			throw new NotFoundException();

		}

		return producto;
	}

	@Override
	public void delete(Producto producto) throws BusinessException, NotFoundException {
		boolean found = false;

		for (int t = 0; t < r.size(); t++) {

			Producto prod = r.get(t);

			if (prod.getId() == producto.getId()) {
				r.remove(t);
				found = true;
				break;
			}
		}

		if (!found) {

			throw new NotFoundException();

		}

	}

	@Override
	public List<Producto> search(String part) throws BusinessException {

		List<Producto> resultado = new ArrayList<Producto>();

		for (Producto p : r) {
			if (p.getDescripcion().toLowerCase().indexOf(part.toLowerCase()) != -1) {

				resultado.add(p);
			}
		}

		return resultado;
	}

}
