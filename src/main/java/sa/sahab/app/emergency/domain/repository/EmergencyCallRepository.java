package sa.sahab.app.emergency.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import sa.sahab.app.emergency.domain.entity.EmergencyCall;
import sa.sahab.app.emergency.infrastructure.exception.ObjectAlreadyExistsException;

import java.util.UUID;

public interface EmergencyCallRepository extends ListCrudRepository<EmergencyCall, UUID> {
	
	default EmergencyCall findByIdOrElseThrow(UUID id) {
		return findById(id)
				.orElseThrow(() -> new ObjectAlreadyExistsException(
						"Emergency call with id: %s is not found".formatted(id.toString())));
	}
	
}



