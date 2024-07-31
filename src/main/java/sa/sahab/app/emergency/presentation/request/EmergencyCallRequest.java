package sa.sahab.app.emergency.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import sa.sahab.app.AppConstants;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record EmergencyCallRequest(@NotNull(message = "Caller name should not be blank")
								   @Size(max = 50, message = "Caller name should be {max} at max")
								   String callerName,
								   
								   @NotBlank(message = "Caller phone number should not be blank")
								   @Size(max = 10, message = "Caller phone number should be {max} at max")
								   @Digits(integer = 10, fraction = 0, message = "Only digits are permitted")
								   String callerPhoneNumber,
								   
								   @Size(min = 1, max = 255, message = "Location address should be {max} at max")
								   String locationAddress,
								   
								   @Size(min = 1, max = 255, message = "Location address should be {max} at max")
								   String description,
								   
								   @JsonFormat(shape = Shape.STRING, pattern = AppConstants.LOCAL_DATE_TIME_FORMAT)
								   @NotNull(message = "Call time is required")
								   LocalDateTime callTime,
								   
								   @NotNull(message = "Ambulance must be specified")
								   UUID ambulanceId,
								   
								   @NotNull(message = "Driver must be specified")
								   UUID driverId) {}



