package sa.sahab.app.emergency.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa.sahab.app.emergency.domain.entity.Driver;
import sa.sahab.app.emergency.domain.repository.DriverRepository;
import sa.sahab.app.emergency.presentation.request.DriverRequest;
import sa.sahab.app.emergency.presentation.response.DriverResponse;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class DriverService {
	
	private final DriverRepository driverRepository;
	
	public List<DriverResponse> findAllDrivers() {
		return this.driverRepository.findAll()
				.stream()
				.map(DriverResponse::from)
				.toList();
	}
	
	public DriverResponse findDriverById(UUID id) {
		return DriverResponse.from(this.driverRepository.findByIdOrElseThrow(id));
	}
	
	@Transactional
	public DriverResponse createDriver(DriverRequest driverRequest) {
		final var driver = Driver.builder()
				.name(driverRequest.name())
				.licenseNumber(driverRequest.licenseNumber())
				.phoneNumber(driverRequest.phoneNumber())
				.build();
		
		final var createdDriver = this.driverRepository.save(driver);
		log.info("A new driver with id: {} has been created successfully.", createdDriver.getId().toString());
		
		return DriverResponse.from(createdDriver);
	}
	
	@Transactional
	public DriverResponse updateDriver(UUID id, DriverRequest driverRequest) {
		final var driver = this.driverRepository.findByIdOrElseThrow(id);
		driver.setName(driverRequest.name());
		driver.setLicenseNumber(driverRequest.licenseNumber());
		driver.setPhoneNumber(driverRequest.phoneNumber());
		
		final var updatedDriver = this.driverRepository.save(driver);
		log.info("Driver with id: {} has been updated successfully.", updatedDriver.getId().toString());
		
		return DriverResponse.from(updatedDriver);
	}
	
	@Transactional
	public void deleteDriver(UUID id) {
		this.driverRepository.delete(this.driverRepository.findByIdOrElseThrow(id));
	}
	
}



