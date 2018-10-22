package ar.edu.iua.ingweb3.business.impl.fs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="archivo")
public class ArchivoFSProperties {

	private String directorioAlmacenamiento;

	public String getDirectorioAlmacenamiento() {
		return directorioAlmacenamiento;
	}

	public void setDirectorioAlmacenamiento(String directorioAlmacenamiento) {
		this.directorioAlmacenamiento = directorioAlmacenamiento;
	}
	
	

}
