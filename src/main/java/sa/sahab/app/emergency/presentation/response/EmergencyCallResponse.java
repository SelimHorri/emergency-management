package sa.sahab.app.emergency.presentation.response;

import lombok.Builder;
import sa.sahab.app.emergency.domain.entity.EmergencyCall;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record EmergencyCallResponse(UUID id,
									String callerName,
									String callerPhoneNumber,
									String locationAddress,
									String description,
									LocalDateTime callTime,
									AmbulanceResponse ambulance,
									DriverResponse driver) {
	
	public static EmergencyCallResponse from(EmergencyCall emergencyCall) {
		return EmergencyCallResponse.builder()
				.id(emergencyCall.getId())
				.callerName(emergencyCall.getCallerName())
				.callerPhoneNumber(emergencyCall.getCallerPhoneNumber())
				.locationAddress(emergencyCall.getLocationAddress())
				.description(emergencyCall.getDescription())
				.callTime(emergencyCall.getCallTime())
				.ambulance(AmbulanceResponse.from(emergencyCall.getAmbulance()))
				.driver(DriverResponse.from(emergencyCall.getDriver()))
				.build();
	}
	
}



