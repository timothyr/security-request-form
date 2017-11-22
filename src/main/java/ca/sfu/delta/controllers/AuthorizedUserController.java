package ca.sfu.delta.controllers;

import ca.sfu.delta.models.AuthorizedUser;
import ca.sfu.delta.models.DistributionEmail;
import ca.sfu.delta.repository.AuthorizedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest API for managing the list of who can login as security and admin users
 * This controller just manages the list and does not do any authentication/authorization
 */
@RestController
public class AuthorizedUserController {

    @Autowired
    private AuthorizedUserRepository authorizedUserRepository;

    @GetMapping(value="/api/authuser/all")
    public List<AuthorizedUser> findAll(@RequestParam(value = "privilege", required=false)
                                                    AuthorizedUser.Privilege privilege) {
        if (privilege == AuthorizedUser.Privilege.SECURITY) {
            return authorizedUserRepository.findAllByPrivilege(AuthorizedUser.Privilege.SECURITY);
        } else if (privilege == AuthorizedUser.Privilege.ADMIN) {
            return authorizedUserRepository.findAllByPrivilege(AuthorizedUser.Privilege.ADMIN);
        } else {
            return authorizedUserRepository.findAll();
        }
    }

    @PostMapping(value="/api/authuser/add")
    public ResponseEntity<Void> addDist(@RequestBody AuthorizedUser authorizedUser) {
        authorizedUserRepository.save(authorizedUser);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @DeleteMapping(value="/api/authuser/delete/{id}")
    public ResponseEntity<Void> deleteDist(@PathVariable("id") long id) {
        AuthorizedUser authorizedUser = authorizedUserRepository.findOne(id);
        if (authorizedUser == null) {
            System.out.println("Unable to delete auth user. Auth user with id " + id + " not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        authorizedUserRepository.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
