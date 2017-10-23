package ca.sfu.delta.repository;

import ca.sfu.delta.models.RequestID;
import org.springframework.data.repository.CrudRepository;

public interface RequestIDRepository extends CrudRepository<RequestID, Long> {

	Integer getNextID(Integer year);
}
