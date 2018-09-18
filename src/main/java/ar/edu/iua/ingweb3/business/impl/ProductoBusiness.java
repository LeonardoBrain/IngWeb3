package ar.edu.iua.ingweb3.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IProductoBusiness;
import ar.edu.iua.ingweb3.model.Producto;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;
import ar.edu.iua.ingweb3.model.persistance.ProductRepository;

@Service
public class ProductoBusiness implements IProductoBusiness {

	@Autowired
	ProductRepository productoDao;

	public ProductoBusiness() {

	}

	@Override
	public List<Producto> getAll() throws BusinessException {
		// TODO Auto-generated method stub
		return productoDao.findAll();
	}

	@Override
	public Producto getOne(int id) throws BusinessException, NotFoundException {

		Optional<Producto> producto = productoDao.findById(id);

		if (producto.isPresent()) {

			return producto.get();
		} else {
			throw new NotFoundException();
		}
	}

	@Override
	public Producto add(Producto producto) throws BusinessException {

		return productoDao.save(producto);

	}

	@Override
	public Producto update(Producto producto) throws BusinessException, NotFoundException {

		Optional<Producto> productoABuscar = productoDao.findById(producto.getIdProducto());

		if (productoABuscar.isPresent()) {

			return productoDao.save(producto);

		} else {

			throw new NotFoundException();
		}

	}

	@Override
	public void delete(Producto producto) throws BusinessException, NotFoundException {

		Optional<Producto> productoABuscar = productoDao.findById(producto.getIdProducto());

		if (productoABuscar.isPresent()) {

			productoDao.delete(producto);

		} else {

			throw new NotFoundException();
		}

	}

	@Override
	public List<Producto> search(String part) throws BusinessException {

		List<Producto> resultado = new ArrayList<Producto>();

		for (Producto p : productoDao.findAll()) {
			if (p.getDescripcion().toLowerCase().indexOf(part.toLowerCase()) != -1) {

				resultado.add(p);
			}
		}

		return resultado;
	}

}
