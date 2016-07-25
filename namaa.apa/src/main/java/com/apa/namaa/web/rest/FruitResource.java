package com.apa.namaa.web.rest;

import com.apa.namaa.domain.User;
import com.apa.namaa.repository.UserRepository;
import com.apa.namaa.security.SecurityUtils;
import com.codahale.metrics.annotation.Timed;
import com.apa.namaa.domain.Fruit;
import com.apa.namaa.repository.FruitRepository;
import com.apa.namaa.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

/**
 * REST controller for managing Fruit.
 */
@RestController
@RequestMapping("/api")
public class FruitResource {

    private final Logger log = LoggerFactory.getLogger(FruitResource.class);

    @Inject
    private FruitRepository fruitRepository;
    @Inject
    private UserRepository userRepository;
    /**
     * POST  /fruits -> Create a new fruit.
     */
    @RequestMapping(value = "/fruits",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fruit> create(@RequestBody Fruit fruit) throws URISyntaxException {

        User owner = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        if(owner !=null) {
            fruit.setOwner(owner);
            fruit.setDateAdded();
            log.debug("Fruit Owner : {}",owner.getLogin());
        }
        log.debug("REST request to save Fruit : {}", fruit);
        if (fruit.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fruit cannot already have an ID").body(null);
        }

        Fruit result = fruitRepository.save(fruit);
        return ResponseEntity.created(new URI("/api/fruits/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("fruit", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /fruits -> Updates an existing fruit.
     */
    @RequestMapping(value = "/fruits",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fruit> update(@RequestBody Fruit fruit) throws URISyntaxException {
        log.debug("REST request to update Fruit : {}", fruit);
        if (fruit.getId() == null) {
            return create(fruit);
        }
        Fruit result = fruitRepository.save(fruit);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("fruit", fruit.getId().toString()))
                .body(result);
    }

    /**
     * GET  /fruits -> get all the fruits.
     */
    @RequestMapping(value = "/fruits",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Fruit> getAll() {
        log.debug("REST request to get all Fruits");
        return fruitRepository.findByOwnerIsCurrentUser();
    }

    /**
     * GET  /fruits/:id -> get the "id" fruit.
     */
    @RequestMapping(value = "/fruits/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fruit> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Fruit : {}", id);
        Fruit fruit = fruitRepository.findOneByOwnerIsCurrentUser(id);
        if (fruit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fruit, HttpStatus.OK);
    }

    /**
     * DELETE  /fruits/:id -> delete the "id" fruit.
     */
    @RequestMapping(value = "/fruits/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Fruit : {}", id);
        fruitRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fruit", id.toString())).build();
    }
}
