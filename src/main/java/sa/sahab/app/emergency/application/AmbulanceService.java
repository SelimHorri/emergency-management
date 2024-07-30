package sa.sahab.app.emergency.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa.sahab.app.emergency.domain.entity.Ambulance;
import sa.sahab.app.emergency.domain.repository.AmbulanceRepository;
import sa.sahab.app.emergency.domain.repository.EmergencyCallRepository;
import sa.sahab.app.emergency.presentation.request.AmbulanceRequest;
import sa.sahab.app.emergency.presentation.response.AmbulanceResponse;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class AmbulanceService {
	
	private final AmbulanceRepository ambulanceRepository;
	private final EmergencyCallRepository emergencyCallRepository;
	
	public List<AmbulanceResponse> findAllAmbulances() {
		return this.ambulanceRepository.findAll()
				.stream()
				.map(AmbulanceResponse::from)
				.toList();
	}
	
	public AmbulanceResponse findAmbulanceById(UUID id) {
		return AmbulanceResponse.from(this.ambulanceRepository.findByIdOrElseThrow(id));
	}
	
	@Transactional
	public AmbulanceResponse createAmbulance(AmbulanceRequest ambulanceRequest) {
		final var ambulance = Ambulance.builder()
				.licensePlate(ambulanceRequest.licensePlate())
				.status(ambulanceRequest.status())
				.location(ambulanceRequest.location())
				.build();
		
		final var createdAmbulance = this.ambulanceRepository.save(ambulance);
		log.info("A new ambulance with id: {} has been created.", createdAmbulance.getId().toString());
		
		return AmbulanceResponse.from(createdAmbulance);
	}
	
	@Transactional
	public AmbulanceResponse updateAmbulance(UUID id, AmbulanceRequest ambulanceRequest) {
		final var ambulance = this.ambulanceRepository.findByIdOrElseThrow(id);
		ambulance.setLicensePlate(ambulanceRequest.licensePlate());
		ambulance.setStatus(ambulanceRequest.status());
		ambulance.setLocation(ambulanceRequest.location());
		
		final var updatedAmbulance = this.ambulanceRepository.save(ambulance);
		log.info("Ambulance with id: {} has been updated successfully", updatedAmbulance.getId().toString());
		
		return AmbulanceResponse.from(updatedAmbulance);
	}
	
	@Transactional
	public void deleteAmbulance(UUID id) {
		this.emergencyCallRepository.deleteAllByAmbulanceId(id);
		this.ambulanceRepository.deleteById(id);
	}
	
}



