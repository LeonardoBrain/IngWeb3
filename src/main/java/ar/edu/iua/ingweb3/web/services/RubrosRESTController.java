package ar.edu.iua.ingweb3.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IRubroBusiness;
import ar.edu.iua.ingweb3.model.Rubro;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

@RestController
@RequestMapping("/rubros")
public class RubrosRESTController {
	
	@Autowired
	private IRubroBusiness rubroBusiness;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Rubro>> lista() {

		

		try {
			return new ResponseEntity<List<Rubro>>( rubroBusiness.getAll(),HttpStatus.OK);
		} catch (BusinessException e) {
			
			return new ResponseEntity<List<Rubro>>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Rubro> uno(@PathVariable("id") int id) {

		try {
			return new ResponseEntity<Rubro>( rubroBusiness.getOne(id),HttpStatus.OK);
			
		} catch (BusinessException e) {
			return new ResponseEntity<Rubro>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (NotFoundException e) {
			return new ResponseEntity<Rubro>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> add(@RequestBody Rubro rubro){
		
		try {
			rubroBusiness.add(rubro);
			HttpHeaders responseHeaders =  new HttpHeaders();
			responseHeaders.set("location", "/productos/" + rubro.getIdRubro());
			return new ResponseEntity<Object>(responseHeaders, HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Object> update(@RequestBody Rubro rubro){
		
		try {
			rubroBusiness.update(rubro);
			
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (NotFoundException e) {
			
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Object> delete(@RequestBody Rubro rubro){
		
		try {
			rubroBusiness.delete(rubro);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	@RequestMapping(value = { "/q={part}" }, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Rubro>> search(@PathVariable("part") String part) {

		

		try {
			return new ResponseEntity<List<Rubro>>( rubroBusiness.search(part),HttpStatus.OK);
		} catch (BusinessException e) {
			
			return new ResponseEntity<List<Rubro>>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

	}

}
