package com.apa.namaa.web.rest;

import com.apa.namaa.domain.User;
import com.apa.namaa.repository.UserRepository;
import com.apa.namaa.security.SecurityUtils;
import com.codahale.metrics.annotation.Timed;
import com.apa.namaa.domain.GrainType3;
import com.apa.namaa.repository.GrainType3Repository;
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
 * REST controller for managing GrainType3.
 */
@RestController
@RequestMapping("/api")
public class GrainType3Resource {

    private final Logger log = LoggerFactory.getLogger(GrainType3Resource.class);

    @Inject
    private GrainType3Repository grainType3Repository;
    @Inject
    private UserRepository userRepository;
    /**
     * POST  /grainType3s -> Create a new grainType3.
     */
    @RequestMapping(value = "/grainType3s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrainType3> create(@RequestBody GrainType3 grainType3) throws URISyntaxException {
        User owner = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        if(owner !=null) {
            grainType3.setOwner(owner);
            grainType3.setDateAdded();
            log.debug("GrainType3 Owner : {}",owner.getLogin());
        }
        log.debug("REST request to save GrainType3 : {}", grainType3);
        if (grainType3.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new grainType3 cannot already have an ID").body(null);
        }
        GrainType3 result = grainType3Repository.save(grainType3);
        return ResponseEntity.created(new URI("/api/grainType3s/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("grainType3", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /grainType3s -> Updates an existing grainType3.
     */
    @RequestMapping(value = "/grainType3s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrainType3> update(@RequestBody GrainType3 grainType3) throws URISyntaxException {
        log.debug("REST request to update GrainType3 : {}", grainType3);
        if (grainType3.getId() == null) {
            return create(grainType3);
        }
        GrainType3 result = grainType3Repository.save(grainType3);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("grainType3", grainType3.getId().toString()))
                .body(result);
    }

    /**
     * GET  /grainType3s -> get all the grainType3s.
     */
    @RequestMapping(value = "/grainType3s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GrainType3> getAll() {
        log.debug("REST request to get all GrainType3s");
        return grainType3Repository.findByOwnerIsCurrentUser();
    }

    /**
     * GET  /grainType3s/:id -> get the "id" grainType3.
     */
    @RequestMapping(value = "/grainType3s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrainType3> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get GrainType3 : {}", id);
        GrainType3 grainType3 = grainType3Repository.findOne(id);
        if (grainType3 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(grainType3, HttpStatus.OK);
    }

    /**
     * DELETE  /grainType3s/:id -> delete the "id" grainType3.
     */
    @RequestMapping(value = "/grainType3s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete GrainType3 : {}", id);
        grainType3Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("grainType3", id.toString())).build();
    }
}
