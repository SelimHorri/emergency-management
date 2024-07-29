package sa.sahab.app.emergency.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	
	public List<EmergencyCallResponse> findAllEmergencyCalls() {
		return null;
	}
	
	public EmergencyCallResponse findEmergencyCallById(UUID id) {
		return null;
	}
	
	@Transactional
	public EmergencyCallResponse createEmergencyCall(EmergencyCallRequest emergencyCallRequest) {
		return null;
	}
	
}



