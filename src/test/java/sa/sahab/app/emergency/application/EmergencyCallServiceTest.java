package sa.sahab.app.emergency.application;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import sa.sahab.app.emergency.domain.entity.Ambulance;
import sa.sahab.app.emergency.domain.entity.Driver;
import sa.sahab.app.emergency.domain.entity.EmergencyCall;
import sa.sahab.app.emergency.domain.repository.AmbulanceRepository;
import sa.sahab.app.emergency.domain.repository.DriverRepository;
import sa.sahab.app.emergency.domain.repository.EmergencyCallRepository;
import sa.sahab.app.emergency.infrastructure.exception.ObjectNotFoundException;
import sa.sahab.app.emergency.presentation.response.AmbulanceResponse;
import sa.sahab.app.emergency.presentation.response.DriverResponse;
import sa.sahab.app.emergency.presentation.response.EmergencyCallResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmergencyCallServiceTest {
	
	private final EmergencyCallService emergencyCallService;
	private final EmergencyCallRepository emergencyCallRepository;
	private final AmbulanceRepository ambulanceRepository;
	private final DriverRepository driverRepository;
	
	{
		this.emergencyCallRepository = Mockito.mock(EmergencyCallRepository.class);
		this.ambulanceRepository = Mockito.mock(AmbulanceRepository.class);
		this.driverRepository = Mockito.mock(DriverRepository.class);
		this.emergencyCallService = new EmergencyCallService(
				this.emergencyCallRepository,
				this.ambulanceRepository,
				this.driverRepository);
	}
	
	@Test
	void whenDoFindAll_thenAllEmergencyCallsShouldBeReturned() {
		
		var e1 = new EmergencyCall(
				UUID.randomUUID(),
				"Alice Johnson",
				"9876543210",
				"456 Elm Street",
				"Car accident",
				LocalDateTime.parse("2022-10-15T10:30:00"),
				Ambulance.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174002")).build(),
				Driver.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174003")).build());
		
		var e2 = new EmergencyCall(
				UUID.randomUUID(),
				"Bob Smith",
				"5551234567",
				"789 Oak Avenue",
				"Injured hiker",
				LocalDateTime.parse("2022-10-15T11:15:00"),
				Ambulance.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174004")).build(),
				Driver.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174005")).build());
		
		var allEmergencyCalls = List.of(e1, e2);
		
		when(this.emergencyCallRepository.findAll())
			.thenReturn(allEmergencyCalls);
		
		var all = this.emergencyCallService.findAllEmergencyCalls();
		assertThat(all)
				.isNotNull()
				.isNotEmpty()
				.isUnmodifiable()
				.hasOnlyElementsOfType(EmergencyCallResponse.class)
				.hasSameSizeAs(allEmergencyCalls)
				.allSatisfy(e -> {
					assertThat(e).isNotNull();
					assertThat(e.id())
							.isNotNull()
							.isInstanceOf(UUID.class);
					assertThat(e.callerName()).isNotBlank();
					assertThat(e.callTime())
							.isNotNull()
							.isInstanceOf(LocalDateTime.class);
					assertThat(e.ambulance())
							.isNotNull()
							.isInstanceOf(AmbulanceResponse.class);
					assertThat(e.ambulance().id())
							.isNotNull()
							.isInstanceOf(UUID.class);
					assertThat(e.driver())
							.isNotNull()
							.isInstanceOf(DriverResponse.class);
					assertThat(e.driver().id())
							.isNotNull()
							.isInstanceOf(UUID.class);
				});
	}
	
	@Test
	void givenInvalidId_whenDoFindById_thenExceptionShouldBeThrown() {
		var invalidId = UUID.fromString("3c4d5e6f-7a8b-9c0d-1e2f-3a4b5c6d7e8e");
		var e = new ObjectNotFoundException("Emergency call with id: %s is not found".formatted(invalidId.toString()));
		
		when(this.emergencyCallRepository
				.findByIdOrElseThrow(invalidId))
			.thenThrow(e);
		
		assertThatExceptionOfType(ObjectNotFoundException.class)
				.isThrownBy(() -> this.emergencyCallService.findEmergencyCallById(invalidId))
				.withMessage(e.getMessage())
				.extracting(ObjectNotFoundException::getStatus, InstanceOfAssertFactories.type(HttpStatus.class))
				.isEqualTo(HttpStatus.NOT_FOUND);
		
	}
	
}



