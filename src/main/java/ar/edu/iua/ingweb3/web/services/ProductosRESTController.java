package ar.edu.iua.ingweb3.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IProductoBusiness;
import ar.edu.iua.ingweb3.model.Producto;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

@RestController
@RequestMapping("/productos")
public class ProductosRESTController {
	
	@Autowired
	private IProductoBusiness productoBusiness;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Producto>> lista() {

		
//prueba branch
		try {
			return new ResponseEntity<List<Producto>>( productoBusiness.getAll(),HttpStatus.OK);
		} catch (BusinessException e) {
			
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Producto> uno(@PathVariable("id") int id) {

		try {
			return new ResponseEntity<Producto>( productoBusiness.getOne(id),HttpStatus.OK);
			
		} catch (BusinessException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (NotFoundException e) {
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> add(@RequestBody Producto producto){
		
		try {
			productoBusiness.add(producto);
			return new ResponseEntity<Object>(HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}

}
