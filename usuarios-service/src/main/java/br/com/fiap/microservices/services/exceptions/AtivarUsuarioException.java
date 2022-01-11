package br.com.fiap.microservices.services.exceptions;

public class AtivarUsuarioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AtivarUsuarioException(String msg) {
		super(msg);
	}

	public AtivarUsuarioException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
