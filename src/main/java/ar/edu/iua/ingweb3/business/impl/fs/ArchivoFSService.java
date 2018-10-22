package ar.edu.iua.ingweb3.business.impl.fs;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArchivoFSService {

	private final Path localizacionAlmacenamiento;

	@Autowired
	public ArchivoFSService(ArchivoFSProperties prop) {
		this.localizacionAlmacenamiento = Paths.get(prop.getDirectorioAlmacenamiento()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.localizacionAlmacenamiento);
		} catch (IOException e) {
			throw new ArchivoFSException(
					"No se ha podido crear el directorio para almacenar archivos" + this.localizacionAlmacenamiento, e);
		}

	}

	public String almacenarArchivo(MultipartFile file) throws ArchivoFSException {

		String nombreArchivo = StringUtils.cleanPath(file.getOriginalFilename());
		Path targetLocation = this.localizacionAlmacenamiento.resolve(nombreArchivo);

		try {

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new ArchivoFSException("No se pudo almacenar el archivo " + nombreArchivo, e);
		}

		return nombreArchivo;

	}

	public Resource cargarArchivo(String nombreArchivo) {

		Path path = this.localizacionAlmacenamiento.resolve(nombreArchivo).normalize();
		try {
			Resource resource = new UrlResource(path.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new ArchivoFSNotFoundException("Archivo no encontrado");
			}
		} catch (MalformedURLException e) {
			throw new ArchivoFSNotFoundException("Archivo no encontrado", e);
		}

	}

	public void deleteFromFS(String nombreArchivo) {
		Path targetLocation = this.localizacionAlmacenamiento.resolve(nombreArchivo);

		try {
			Resource resource = new UrlResource(targetLocation.toUri());
			if (resource.exists()) {
				Files.delete(targetLocation);

			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<String> getFilesFromFSByLength(long minLength, long maxLength) {

		File folder = new File("/home/leonardo/Documents/ingenieriaWebIII/ingweb3-workspace/ingweb3/subidos");
		File[] archivos = folder.listFiles();

		List<String> nombreArchivos = new ArrayList();

		boolean banderaMin = minLength >= 0;
		boolean banderaMax = maxLength >= 0;

		if (banderaMin && !banderaMax) {

			for (int i = 0; i < archivos.length; i++) {

				if (archivos[i].isFile() && archivos[i].length() >= minLength) {

					nombreArchivos.add(archivos[i].getName());
				}
			}

		} else if (!banderaMin && banderaMax) {

			for (int i = 0; i < archivos.length; i++) {

				if (archivos[i].isFile() && archivos[i].length() <= maxLength) {

					nombreArchivos.add(archivos[i].getName());
				}
			}

		} else if (banderaMin && banderaMax) {

			for (int i = 0; i < archivos.length; i++) {

				if (archivos[i].isFile() && (archivos[i].length() >= minLength && archivos[i].length() <= maxLength)) {

					nombreArchivos.add(archivos[i].getName());
				}
			}

		} else {

			for (int i = 0; i < archivos.length; i++) {

				if (archivos[i].isFile()) {

					nombreArchivos.add(archivos[i].getName());
				}
			}

		}

		return nombreArchivos;

	}

	public List<String> getFilesFromFSByMime(String mime) {

		File folder = new File("/home/leonardo/Documents/ingenieriaWebIII/ingweb3-workspace/ingweb3/subidos");
		File[] archivos = folder.listFiles();
		List<String> nombreArchivos = new ArrayList();

		if (mime.equals("")) {

			for (int i = 0; i < archivos.length; i++) {

				if (archivos[i].isFile()) {

					nombreArchivos.add(archivos[i].getName());
				}
			}

		} else {

			for (int i = 0; i < archivos.length; i++) {

				System.out.println(new MimetypesFileTypeMap().getContentType(archivos[i]));
				try {
					if (archivos[i].isFile()
							&& Files.probeContentType(archivos[i].getAbsoluteFile().toPath()).equals(mime)) {

						nombreArchivos.add(archivos[i].getName());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return nombreArchivos;

	}

	public List<String> getFilesFromFSByString(String part) {

		File folder = new File("/home/leonardo/Documents/ingenieriaWebIII/ingweb3-workspace/ingweb3/subidos");
		File[] archivos = folder.listFiles();
		List<String> nombreArchivos = new ArrayList();
		

		for (int i = 0; i < archivos.length; i++) {

			if (archivos[i].isFile() && archivos[i].getName().contains(part)) {

				nombreArchivos.add(archivos[i].getName());
			}
		}
		
		
		
		return nombreArchivos;
	}

}
