package ca.sfu.delta.controllers;

import ca.sfu.delta.models.DistributionEmail;
import ca.sfu.delta.repository.DistributionEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest API for handling distribution emails - list of emails to send mail when
 * request is accepted
 */
@RestController
public class DistributionController {

    @Autowired
    private DistributionEmailRepository distributionEmailRepository;

    @GetMapping(value="/api/dist/all")
    public List<DistributionEmail> findAll() {
        return distributionEmailRepository.findAll();
    }

    @PostMapping(value="/api/dist/add")
    public ResponseEntity<Void> addDist(@RequestBody DistributionEmail distEmail) {
        if (distEmail.getEmail().equals("")) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        distributionEmailRepository.save(distEmail);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @DeleteMapping(value="/api/dist/delete/{id}")
    public ResponseEntity<Void> deleteDist(@PathVariable("id") long id) {
        DistributionEmail distEmail = distributionEmailRepository.findOne(id);
        if (distEmail == null) {
            System.out.println("Unable to delete dist email. Dist email with id " + id + " not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        distributionEmailRepository.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
