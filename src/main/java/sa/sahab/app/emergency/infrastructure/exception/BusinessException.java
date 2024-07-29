package sa.sahab.app.emergency.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {
	
	private final HttpStatus status;
	private final String message;
	
	public BusinessException(HttpStatus status, String message) {
		super(message);
		this.status = status;
		this.message = message;
	}
	
}



