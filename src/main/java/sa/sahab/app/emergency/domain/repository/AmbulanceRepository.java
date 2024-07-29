package sa.sahab.app.emergency.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import sa.sahab.app.emergency.domain.entity.Ambulance;
import sa.sahab.app.emergency.infrastructure.exception.ObjectNotFoundException;

import java.util.UUID;

public interface AmbulanceRepository extends ListCrudRepository<Ambulance, UUID> {
	
	default Ambulance findByIdOrElseThrow(UUID id) {
		return findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(
						"Ambulance with id: %s has not been found".formatted(id.toString())));
	}
	
}



