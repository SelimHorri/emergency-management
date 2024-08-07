package sa.sahab.app.emergency.presentation.response;

import lombok.Builder;
import sa.sahab.app.emergency.domain.entity.Ambulance;
import sa.sahab.app.emergency.domain.entity.Ambulance.AmbulanceStatus;

import java.util.UUID;

@Builder
public record AmbulanceResponse(UUID id,
								String licensePlate,
								AmbulanceStatus status,
								String location) {
	
	public static AmbulanceResponse from(Ambulance ambulance) {
		return new AmbulanceResponse(
				ambulance.getId(),
				ambulance.getLicensePlate(),
				ambulance.getStatus(),
				ambulance.getLocation());
	}
	
}



