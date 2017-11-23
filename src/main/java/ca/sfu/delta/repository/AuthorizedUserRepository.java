package ca.sfu.delta.repository;

import ca.sfu.delta.models.AuthorizedUser;
import ca.sfu.delta.models.DistributionEmail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorizedUserRepository extends CrudRepository<AuthorizedUser, Long> {
    List<AuthorizedUser> findAll();

    List<AuthorizedUser> findAllByPrivilege(AuthorizedUser.Privilege privilege);
}
