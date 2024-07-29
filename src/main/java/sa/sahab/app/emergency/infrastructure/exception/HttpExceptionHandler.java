package sa.sahab.app.emergency.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sa.sahab.app.emergency.presentation.response.ApiPayload;

import java.net.URI;

@RestControllerAdvice
@Slf4j
class HttpExceptionHandler {
	
	@ExceptionHandler
	ResponseEntity<ApiPayload<ProblemDetail>> handleMethodArgumentException(MethodArgumentNotValidException e,
																			HttpServletRequest request) {
		return null;
	}
	
	@ExceptionHandler
	<T extends BusinessException> ResponseEntity<ApiPayload<ProblemDetail>> handleBusinessException(T exception, HttpServletRequest request) {
		final var problemDetail = ProblemDetail.forStatusAndDetail(
				exception.getStatus(),
				exception.getMessage());
		problemDetail.setInstance(URI.create(request.getRequestURI()));
		log.error(problemDetail.toString());
		
		return ResponseEntity
				.status(problemDetail.getStatus())
				.body(ApiPayload.ofFailure(problemDetail));
	}
	
}



