package com.apa.namaa.web.rest;

import com.apa.namaa.domain.User;
import com.apa.namaa.repository.UserRepository;
import com.apa.namaa.security.SecurityUtils;
import com.codahale.metrics.annotation.Timed;
import com.apa.namaa.domain.GrainType1;
import com.apa.namaa.repository.GrainType1Repository;
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
import java.util.List;

/**
 * REST controller for managing GrainType1.
 */
@RestController
@RequestMapping("/api")
public class GrainType1Resource {

    private final Logger log = LoggerFactory.getLogger(GrainType1Resource.class);

    @Inject
    private GrainType1Repository grainType1Repository;
    @Inject
    private UserRepository userRepository;
    /**
     * POST  /grainType1s -> Create a new grainType1.
     */
    @RequestMapping(value = "/grainType1s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrainType1> create(@RequestBody GrainType1 grainType1) throws URISyntaxException {
        User owner = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        if(owner !=null) {
            grainType1.setOwner(owner);
            grainType1.setDateAdded();
            log.debug("GrainType1 Owner : {}",owner.getLogin());
        }
        log.debug("REST request to save GrainType1 : {}", grainType1);
        if (grainType1.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new grainType1 cannot already have an ID").body(null);
        }
        GrainType1 result = grainType1Repository.save(grainType1);
        return ResponseEntity.created(new URI("/api/grainType1s/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("grainType1", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /grainType1s -> Updates an existing grainType1.
     */
    @RequestMapping(value = "/grainType1s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrainType1> update(@RequestBody GrainType1 grainType1) throws URISyntaxException {
        log.debug("REST request to update GrainType1 : {}", grainType1);
        if (grainType1.getId() == null) {
            return create(grainType1);
        }
        GrainType1 result = grainType1Repository.save(grainType1);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("grainType1", grainType1.getId().toString()))
                .body(result);
    }

    /**
     * GET  /grainType1s -> get all the grainType1s.
     */
    @RequestMapping(value = "/grainType1s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GrainType1> getAll() {
        log.debug("REST request to get all GrainType1s");
        return grainType1Repository.findByOwnerIsCurrentUser();
    }

    /**
     * GET  /grainType1s/:id -> get the "id" grainType1.
     */
    @RequestMapping(value = "/grainType1s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrainType1> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get GrainType1 : {}", id);
        GrainType1 grainType1 = grainType1Repository.findOne(id);
        if (grainType1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(grainType1, HttpStatus.OK);
    }

    /**
     * DELETE  /grainType1s/:id -> delete the "id" grainType1.
     */
    @RequestMapping(value = "/grainType1s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete GrainType1 : {}", id);
        grainType1Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("grainType1", id.toString())).build();
    }
}
