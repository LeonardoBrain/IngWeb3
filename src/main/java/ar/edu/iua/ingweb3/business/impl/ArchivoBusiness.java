package ar.edu.iua.ingweb3.business.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IArchivoBusiness;
import ar.edu.iua.ingweb3.business.impl.fs.ArchivoFSService;
import ar.edu.iua.ingweb3.model.Archivo;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;
import ar.edu.iua.ingweb3.model.persistance.ArchivoRepository;

@Service
public class ArchivoBusiness implements IArchivoBusiness {
	
	@Autowired
	ArchivoRepository archivoDao;
	
	@Autowired
	private ArchivoFSService archivoFSService;

	public ArchivoBusiness() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Archivo getOne(int id) throws BusinessException, NotFoundException {
		Optional<Archivo> archivo = archivoDao.findById(id);

		if (archivo.isPresent()) {

			return archivo.get();
		} else {
			throw new NotFoundException();
		}
	}

	@Override
	public Archivo add(Archivo archivo) throws BusinessException {
		
		return archivoDao.save(archivo);
	}

	@Override
	public void delete(String nombreArchivo) throws BusinessException, NotFoundException {
		
		archivoFSService.deleteFromFS(nombreArchivo);

	}

	@Override
	public String saveToFS(MultipartFile mf) throws BusinessException {
		return archivoFSService.almacenarArchivo(mf);
	}

	@Override
	public Resource loadFromFS(String nombreArchivo) throws BusinessException, NotFoundException {
		
		return archivoFSService.cargarArchivo(nombreArchivo);
	}
	
	@Override
	public List<String> getFileNamesFromFSByLength(long minLength , long maxLength){
		
		return archivoFSService.getFilesFromFSByLength(minLength, maxLength);
	}
	
	public List<String> getFilesFromFSByMime(String mime){
		
		return archivoFSService.getFilesFromFSByMime(mime);
	}
	
	public List<String> getFilesFromFSByString(String part){
		
		return archivoFSService.getFilesFromFSByString(part);
	}

}
