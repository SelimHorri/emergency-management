package sa.sahab.app.emergency.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa.sahab.app.emergency.domain.entity.EmergencyCall;
import sa.sahab.app.emergency.domain.repository.AmbulanceRepository;
import sa.sahab.app.emergency.domain.repository.DriverRepository;
import sa.sahab.app.emergency.domain.repository.EmergencyCallRepository;
import sa.sahab.app.emergency.presentation.request.EmergencyCallRequest;
import sa.sahab.app.emergency.presentation.response.EmergencyCallResponse;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class EmergencyCallService {
	
	private final EmergencyCallRepository emergencyCallRepository;
	private final AmbulanceRepository ambulanceRepository;
	private final DriverRepository driverRepository;
	
	public List<EmergencyCallResponse> findAllEmergencyCalls() {
		return this.emergencyCallRepository.findAll()
				.stream()
				.map(EmergencyCallResponse::from)
				.toList();
	}
	
	public EmergencyCallResponse findEmergencyCallById(UUID id) {
		return this.emergencyCallRepository.findById(id)
				.map(EmergencyCallResponse::from)
				.orElseThrow();
	}
	
	@Transactional
	public EmergencyCallResponse createEmergencyCall(EmergencyCallRequest emergencyCallRequest) {
		final var emergencyCall = EmergencyCall.builder()
				.callerName(emergencyCallRequest.callerName())
				.callerPhoneNumber(emergencyCallRequest.callerPhoneNumber())
				.locationAddress(emergencyCallRequest.locationAddress())
				.description(emergencyCallRequest.description())
				.callTime(emergencyCallRequest.callTime())
				.ambulance(this.ambulanceRepository.findByIdOrElseThrow(emergencyCallRequest.ambulanceId()))
				.driver(this.driverRepository.findByIdOrElseThrow(emergencyCallRequest.driverId()))
				.build();
		
		final var createdEmergencyCall = this.emergencyCallRepository.save(emergencyCall);
		log.info("A new emergency call with id: {} has been created successfully.", createdEmergencyCall.getId().toString());
		
		return EmergencyCallResponse.from(createdEmergencyCall);
	}
	
}



