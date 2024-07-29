package sa.sahab.app.emergency.domain.repository;

import org.springframework.data.repository.ListCrudRepository;
import sa.sahab.app.emergency.domain.entity.Driver;

import java.util.UUID;

public interface DriverRepository extends ListCrudRepository<Driver, UUID> {}



