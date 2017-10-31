package ca.sfu.delta.repository;

import ca.sfu.delta.models.URLToken;
import org.springframework.data.repository.CrudRepository;

public interface URLTokenRepository extends CrudRepository<URLToken, Long> {

	Boolean existsByToken(String token);
	URLToken getByToken(String token);
}
