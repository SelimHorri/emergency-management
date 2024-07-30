package sa.sahab.app.emergency.domain.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import sa.sahab.app.emergency.domain.entity.EmergencyCall;
import sa.sahab.app.emergency.infrastructure.exception.ObjectNotFoundException;

import java.util.UUID;

public interface EmergencyCallRepository extends ListCrudRepository<EmergencyCall, UUID> {
	
	@Query(value = """
		DELETE FROM EmergencyCall ec
		WHERE ec.ambulance.id = :ambulanceId""")
	@Modifying
	int deleteAllByAmbulanceId(UUID ambulanceId);
	
	@Query(value = """
		DELETE FROM EmergencyCall ec
		WHERE ec.driver.id = :driverId""")
	@Modifying
	int deleteAllByDriverId(UUID driverId);
	
	default EmergencyCall findByIdOrElseThrow(UUID id) {
		return findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(
						"Emergency call with id: %s is not found".formatted(id.toString())));
	}
	
}



