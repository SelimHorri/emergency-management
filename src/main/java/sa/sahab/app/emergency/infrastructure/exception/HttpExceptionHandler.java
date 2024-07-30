package sa.sahab.app.emergency.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
	ResponseEntity<ApiPayload<ProblemDetail>> handleMethodArgumentException(MethodArgumentNotValidException e, HttpServletRequest request) {
		record InvalidField(String fieldName, String reason) {}
		
		final var invalidFields = e.getBindingResult().getFieldErrors().stream()
				.map(field -> new InvalidField(field.getField(), field.getDefaultMessage()))
				.toList();
		
		final var problemDetail = ProblemDetail.forStatusAndDetail(e.getStatusCode(), e.getMessage());
		problemDetail.setInstance(URI.create(request.getRequestURI()));
		problemDetail.setProperty("constraintValidations", invalidFields);
		
		return ResponseEntity
				.status(problemDetail.getStatus())
				.body(ApiPayload.ofFailure(problemDetail));
	}
	
	@ExceptionHandler
	<T extends BusinessException> ResponseEntity<ApiPayload<ProblemDetail>> handleBusinessException(T e, HttpServletRequest request) {
		final var problemDetail = ProblemDetail.forStatusAndDetail(
				e.getStatus(),
				e.getMessage());
		problemDetail.setInstance(URI.create(request.getRequestURI()));
		log.error(problemDetail.toString());
		
		return ResponseEntity
				.status(problemDetail.getStatus())
				.body(ApiPayload.ofFailure(problemDetail));
	}
	
	@ExceptionHandler
	ResponseEntity<ApiPayload<ProblemDetail>> handleGeneralException(Exception e, HttpServletRequest request) {
		final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		problemDetail.setInstance(URI.create(request.getRequestURI()));
		log.error(problemDetail.toString());
		return ResponseEntity
				.status(problemDetail.getStatus())
				.body(ApiPayload.ofFailure(problemDetail));
	}
	
}



