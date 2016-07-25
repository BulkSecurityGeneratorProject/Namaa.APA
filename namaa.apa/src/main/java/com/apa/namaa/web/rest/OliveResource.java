package com.apa.namaa.web.rest;

import com.apa.namaa.domain.User;
import com.apa.namaa.repository.UserRepository;
import com.apa.namaa.security.SecurityUtils;
import com.codahale.metrics.annotation.Timed;
import com.apa.namaa.domain.Olive;
import com.apa.namaa.repository.OliveRepository;
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
 * REST controller for managing Olive.
 */
@RestController
@RequestMapping("/api")
public class OliveResource {

    private final Logger log = LoggerFactory.getLogger(OliveResource.class);

    @Inject
    private OliveRepository oliveRepository;
    @Inject
    private UserRepository userRepository;
    /**
     * POST  /olives -> Create a new olive.
     */
    @RequestMapping(value = "/olives",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Olive> create(@RequestBody Olive olive) throws URISyntaxException {
        User owner = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        if(owner !=null) {
            olive.setOwner(owner);
            olive.setDateAdded();
            log.debug("Olive Owner : {}",owner.getLogin());
        }
        log.debug("REST request to save Olive : {}", olive);
        if (olive.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new olive cannot already have an ID").body(null);
        }
        Olive result = oliveRepository.save(olive);
        return ResponseEntity.created(new URI("/api/olives/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("olive", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /olives -> Updates an existing olive.
     */
    @RequestMapping(value = "/olives",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Olive> update(@RequestBody Olive olive) throws URISyntaxException {
        log.debug("REST request to update Olive : {}", olive);
        if (olive.getId() == null) {
            return create(olive);
        }
        Olive result = oliveRepository.save(olive);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("olive", olive.getId().toString()))
                .body(result);
    }

    /**
     * GET  /olives -> get all the olives.
     */
    @RequestMapping(value = "/olives",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Olive> getAll() {
        log.debug("REST request to get all Olives");
        return oliveRepository.findByOwnerIsCurrentUser();
    }

    /**
     * GET  /olives/:id -> get the "id" olive.
     */
    @RequestMapping(value = "/olives/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Olive> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Olive : {}", id);
        Olive olive = oliveRepository.findOne(id);
        if (olive == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(olive, HttpStatus.OK);
    }

    /**
     * DELETE  /olives/:id -> delete the "id" olive.
     */
    @RequestMapping(value = "/olives/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Olive : {}", id);
        oliveRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("olive", id.toString())).build();
    }
}
