package ar.edu.iua.ingweb3.business.impl.fs;

public class ArchivoFSException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6028457735553100319L;

	public ArchivoFSException() {
		
	}

	public ArchivoFSException(String message) {
		super(message);
		
	}

	public ArchivoFSException(Throwable cause) {
		super(cause);
		
	}

	public ArchivoFSException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ArchivoFSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
