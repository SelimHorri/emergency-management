package sa.sahab.app.emergency.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import sa.sahab.app.emergency.domain.entity.Driver;
import sa.sahab.app.emergency.infrastructure.exception.ObjectNotFoundException;

import java.util.UUID;

public interface DriverRepository extends ListCrudRepository<Driver, UUID> {
	
	default Driver findByIdOrElseThrow(UUID id) {
		return findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(
						"Driver with id: %s is not found".formatted(id)));
	}
	
}



