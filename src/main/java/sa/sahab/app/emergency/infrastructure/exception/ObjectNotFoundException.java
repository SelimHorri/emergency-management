package sa.sahab.app.emergency.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends BusinessException {
	
	public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
	
	public ObjectNotFoundException(String message) {
		super(STATUS, message);
	}
	
}



