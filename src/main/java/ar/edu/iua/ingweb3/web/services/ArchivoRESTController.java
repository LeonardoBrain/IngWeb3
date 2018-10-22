package ar.edu.iua.ingweb3.web.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IArchivoBusiness;
import ar.edu.iua.ingweb3.model.Archivo;
import ar.edu.iua.ingweb3.model.Producto;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

@RestController
@RequestMapping(Constantes.URL_ARCHIVOS)
public class ArchivoRESTController {

	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IArchivoBusiness archivoBusiness;

	@PostMapping(value = { "/fs", "/fs/" })
	public ResponseEntity<Archivo> uploadFS(@RequestParam("file") MultipartFile file) {

		Archivo archivo;

		try {
			archivo = guardarFSImpl(file);
			archivoBusiness.add(archivo);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", archivo.getDownloadUri());
			return new ResponseEntity<Archivo>(archivo, responseHeaders, HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<Archivo>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private Archivo guardarFSImpl(MultipartFile file) throws BusinessException {

		String fileName = archivoBusiness.saveToFS(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(Constantes.URL_ARCHIVOS + "/fs/").path(fileName).toUriString();

		Archivo archivo = new Archivo();
		archivo.setNombre(fileName);
		archivo.setLength(file.getSize());
		archivo.setDownloadUri(fileDownloadUri);
		archivo.setMime(file.getContentType());

		return archivo;

	}

	
	private String getMime(Resource resource, HttpServletRequest request) {
		String mime = null;
		try {
			request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			log.info("No se pudo determinar el tipo mime");
		}
		if (mime == null) {
			mime = "application/octet-stream";
		}
		return mime;
	}
	
	@PostMapping(value = { "/fs/multi", "/fs/multi/" })
	public ResponseEntity <List<Object>> uploadFS(@RequestParam("files") MultipartFile[] files) {

		List<Object> archivos = Arrays.asList(files).stream().map(file -> {try {
			return guardarFSImpl(file);
		} catch (BusinessException e) {
			return new ResponseEntity<Archivo>(HttpStatus.INTERNAL_SERVER_ERROR);
		}}).collect(Collectors.toList());
		
		return new ResponseEntity<List<Object>>(archivos,HttpStatus.CREATED);

	}
	
	@GetMapping("/fs/{fileName:.+}")
	public ResponseEntity<Resource> downloadFileFS(@PathVariable String fileName, HttpServletRequest request) {
		Resource resource;
		try {
			resource = archivoBusiness.loadFromFS(fileName);
		} catch (BusinessException e) {
			return new ResponseEntity<Resource>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}

		String mime = getMime(resource, request);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(mime))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@DeleteMapping("/fs/{fileName:.+}")
	public ResponseEntity<Object> delete(@PathVariable String fileName, HttpServletRequest request){
		
		try {
			archivoBusiness.delete(fileName);
		} catch (BusinessException e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Object>(HttpStatus.OK);
		
	}
	
	@GetMapping(value= {"/", ""})
	public ResponseEntity<List<String>> lista(
			
			@RequestParam(required = false, value = "q", defaultValue = "*") String q,
			@RequestParam(required = false, value = "minLength", defaultValue = "-1") long minLength,
			@RequestParam(required = false, value = "maxLength", defaultValue = "-1") long maxLength,
			@RequestParam(required = false, value = "mime", defaultValue = "") String mime) {

		
		if(mime.equals("") && q.equals("*")) {
			
			return new ResponseEntity<List<String>>( archivoBusiness.getFileNamesFromFSByLength(minLength,maxLength ),HttpStatus.OK);
		}else if(!q.equals("*") && mime.equals("") && minLength ==-1 && maxLength ==-1 ){
			
			return new ResponseEntity<List<String>>( archivoBusiness.getFilesFromFSByString(q) ,HttpStatus.OK);
			
		}else {
			
			return new ResponseEntity<List<String>>( archivoBusiness.getFilesFromFSByMime(mime) ,HttpStatus.OK);
		}
			
			
		}

		

	
	


}
