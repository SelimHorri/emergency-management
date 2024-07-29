package sa.sahab.app.emergency.presentation.response;

import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.Objects;

public record ApiPayload<T>(Instant timestamp, ApiStatus status, @Nullable T responseBody) {
	
	public ApiPayload {
		timestamp = Objects.requireNonNullElseGet(timestamp, Instant::now);
	}
	
	public ApiPayload(ApiStatus status, @Nullable T responseBody) {
		this(null, status, responseBody);
	}
	
	public enum ApiStatus {
		SUCCESS, FAILURE
	}
	
	public static <T> ApiPayload<T> ofSuccess(@Nullable T responseBody) {
		return new ApiPayload<>(ApiStatus.SUCCESS, responseBody);
	}
	
	public static <T> ApiPayload<T> ofFailure(@Nullable T responseBody) {
		return new ApiPayload<>(ApiStatus.FAILURE, responseBody);
	}
	
}



