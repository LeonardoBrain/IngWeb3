 package ar.edu.iua.ingweb3.business;



import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.iua.ingweb3.model.Archivo;

import ar.edu.iua.ingweb3.model.exception.NotFoundException;

public interface IArchivoBusiness {

	public Archivo getOne(int id) throws BusinessException, NotFoundException;

	public Archivo add(Archivo archivo) throws BusinessException;

	public void delete(String nombreArchivo) throws BusinessException, NotFoundException;
	
	public String saveToFS(MultipartFile mf) throws BusinessException;
	
	public Resource loadFromFS(String nombreArchivo) throws BusinessException, NotFoundException;
	
	public List<String> getFileNamesFromFSByLength(long minLength , long maxLength);
	
	public List<String> getFilesFromFSByMime(String mime);
	
	public List<String> getFilesFromFSByString(String part);
	
	

}
