package sa.sahab.app.emergency.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class ObjectAlreadyExistsException extends BusinessException {
	
	private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;
	
	public ObjectAlreadyExistsException(String message) {
		super(HTTP_STATUS, message);
	}
	
}



