package ca.sfu.delta.repository;

import ca.sfu.delta.models.DistributionEmail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DistributionEmailRepository extends CrudRepository<DistributionEmail, Long> {
    List<DistributionEmail> findAll();
}
