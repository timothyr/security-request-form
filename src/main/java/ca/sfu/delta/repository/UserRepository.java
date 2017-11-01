package ca.sfu.delta.repository;

import ca.sfu.delta.models.SecurityUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<SecurityUser, Long> {
	
	@Modifying
	@Query("select i from SecurityUser i where i.username = :username")
	public List<SecurityUser> getUser(@Param("username") String username);
}