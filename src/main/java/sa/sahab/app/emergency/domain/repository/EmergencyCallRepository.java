package sa.sahab.app.emergency.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import sa.sahab.app.emergency.domain.entity.EmergencyCall;

import java.util.UUID;

public interface EmergencyCallRepository extends ListCrudRepository<EmergencyCall, UUID> {}



