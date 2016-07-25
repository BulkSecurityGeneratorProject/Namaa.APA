package com.apa.namaa.web.rest;

import com.apa.namaa.domain.User;
import com.apa.namaa.repository.UserRepository;
import com.apa.namaa.security.SecurityUtils;
import com.codahale.metrics.annotation.Timed;
import com.apa.namaa.domain.GrainType2;
import com.apa.namaa.repository.GrainType2Repository;
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
 * REST controller for managing GrainType2.
 */
@RestController
@RequestMapping("/api")
public class GrainType2Resource {

    private final Logger log = LoggerFactory.getLogger(GrainType2Resource.class);

    @Inject
    private GrainType2Repository grainType2Repository;
    @Inject
    private UserRepository userRepository;
    /**
     * POST  /grainType2s -> Create a new grainType2.
     */
    @RequestMapping(value = "/grainType2s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrainType2> create(@RequestBody GrainType2 grainType2) throws URISyntaxException {
        User owner = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        if(owner !=null) {
            grainType2.setOwner(owner);
            grainType2.setDateAdded();
            log.debug("GrainType2 Owner : {}",owner.getLogin());
        }
        log.debug("REST request to save GrainType2 : {}", grainType2);
        if (grainType2.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new grainType2 cannot already have an ID").body(null);
        }
        GrainType2 result = grainType2Repository.save(grainType2);
        return ResponseEntity.created(new URI("/api/grainType2s/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("grainType2", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /grainType2s -> Updates an existing grainType2.
     */
    @RequestMapping(value = "/grainType2s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrainType2> update(@RequestBody GrainType2 grainType2) throws URISyntaxException {
        log.debug("REST request to update GrainType2 : {}", grainType2);
        if (grainType2.getId() == null) {
            return create(grainType2);
        }
        GrainType2 result = grainType2Repository.save(grainType2);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("grainType2", grainType2.getId().toString()))
                .body(result);
    }

    /**
     * GET  /grainType2s -> get all the grainType2s.
     */
    @RequestMapping(value = "/grainType2s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GrainType2> getAll() {
        log.debug("REST request to get all GrainType2s");
        return grainType2Repository.findByOwnerIsCurrentUser();
    }

    /**
     * GET  /grainType2s/:id -> get the "id" grainType2.
     */
    @RequestMapping(value = "/grainType2s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrainType2> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get GrainType2 : {}", id);
        GrainType2 grainType2 = grainType2Repository.findOne(id);
        if (grainType2 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(grainType2, HttpStatus.OK);
    }

    /**
     * DELETE  /grainType2s/:id -> delete the "id" grainType2.
     */
    @RequestMapping(value = "/grainType2s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete GrainType2 : {}", id);
        grainType2Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("grainType2", id.toString())).build();
    }
}
