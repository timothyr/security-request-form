package ca.sfu.delta.repository;

import ca.sfu.delta.models.AuthorizedUser;
import ca.sfu.delta.models.DistributionEmail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorizedUserRepository extends CrudRepository<AuthorizedUser, Long> {
    List<AuthorizedUser> findAll();

    List<AuthorizedUser> findAllByPrivilege(AuthorizedUser.Privilege privilege);

    @Query("SELECT a FROM AuthorizedUser a WHERE a.email = :email")
    List<AuthorizedUser> findAllByUsername(@Param("email") String email);
}
