package com.spardagames.exceptions;

public class ComandoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ComandoInvalidoException(String message) {
		super(message);
	}

}
