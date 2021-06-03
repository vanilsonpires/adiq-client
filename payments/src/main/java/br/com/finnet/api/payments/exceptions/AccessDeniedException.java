package br.com.finnet.api.payments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AccessDeniedException extends Exception{
	
	public AccessDeniedException(String message) {
		super(message);
	}

}
