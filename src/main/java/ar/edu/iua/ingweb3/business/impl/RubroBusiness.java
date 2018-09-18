package ar.edu.iua.ingweb3.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IRubroBusiness;

import ar.edu.iua.ingweb3.model.Rubro;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;
import ar.edu.iua.ingweb3.model.persistance.RubroRepository;

@Service
public class RubroBusiness implements IRubroBusiness {

	@Autowired
	RubroRepository rubroDao;

	public RubroBusiness() {
	}

	@Override
	public List<Rubro> getAll() throws BusinessException {

		return rubroDao.findAll();
	}

	@Override
	public List<Rubro> search(String part) throws BusinessException {
		List<Rubro> resultado = new ArrayList<Rubro>();

		for (Rubro r : rubroDao.findAll()) {
			if (r.getDescripcion().toLowerCase().indexOf(part.toLowerCase()) != -1) {

				resultado.add(r);
			}
		}

		return resultado;
	}

	@Override
	public Rubro getOne(int id) throws BusinessException, NotFoundException {

		Optional<Rubro> rubroABuscar = rubroDao.findById(id);

		if (rubroABuscar.isPresent()) {

			return rubroABuscar.get();
		} else {
			throw new NotFoundException();
		}
	}

	@Override
	public Rubro add(Rubro rubro) throws BusinessException {

		return rubroDao.save(rubro);
	}

	@Override
	public Rubro update(Rubro rubro) throws BusinessException, NotFoundException {

		Optional<Rubro> rubroABuscar = rubroDao.findById(rubro.getIdRubro());

		if (rubroABuscar.isPresent()) {

			return rubroDao.save(rubro);

		} else {

			throw new NotFoundException();
		}
	}

	@Override
	public void delete(Rubro rubro) throws BusinessException, NotFoundException {

		Optional<Rubro> rubroABuscar = rubroDao.findById(rubro.getIdRubro());

		if (rubroABuscar.isPresent()) {

			rubroDao.delete(rubro);

		} else {

			throw new NotFoundException();
		}

	}

}
