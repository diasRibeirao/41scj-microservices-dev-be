package br.com.fiap.microservices.services.exceptions;

public class AtivarParceiroException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AtivarParceiroException(String msg) {
		super(msg);
	}

	public AtivarParceiroException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
