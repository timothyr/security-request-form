package ca.sfu.delta.repository;

import ca.sfu.delta.models.DistributionEntry;
import org.springframework.data.repository.CrudRepository;

public interface DistributionRepository extends CrudRepository<DistributionEntry, Long> {

}
