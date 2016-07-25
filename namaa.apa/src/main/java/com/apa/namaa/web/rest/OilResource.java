package com.apa.namaa.web.rest;

import com.apa.namaa.domain.User;
import com.apa.namaa.repository.UserRepository;
import com.apa.namaa.security.SecurityUtils;
import com.codahale.metrics.annotation.Timed;
import com.apa.namaa.domain.Oil;
import com.apa.namaa.repository.OilRepository;
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
 * REST controller for managing Oil.
 */
@RestController
@RequestMapping("/api")
public class OilResource {

    private final Logger log = LoggerFactory.getLogger(OilResource.class);

    @Inject
    private OilRepository oilRepository;
    @Inject
    private UserRepository userRepository;
    /**
     * POST  /oils -> Create a new oil.
     */
    @RequestMapping(value = "/oils",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Oil> create(@RequestBody Oil oil) throws URISyntaxException {
        User owner = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        if(owner !=null) {
            oil.setOwner(owner);
            oil.setDateAdded();
            log.debug("Oil Owner : {}",owner.getLogin());
        }
        log.debug("REST request to save Oil : {}", oil);
        if (oil.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new oil cannot already have an ID").body(null);
        }
        Oil result = oilRepository.save(oil);
        return ResponseEntity.created(new URI("/api/oils/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("oil", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /oils -> Updates an existing oil.
     */
    @RequestMapping(value = "/oils",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Oil> update(@RequestBody Oil oil) throws URISyntaxException {
        log.debug("REST request to update Oil : {}", oil);
        if (oil.getId() == null) {
            return create(oil);
        }
        Oil result = oilRepository.save(oil);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("oil", oil.getId().toString()))
                .body(result);
    }

    /**
     * GET  /oils -> get all the oils.
     */
    @RequestMapping(value = "/oils",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Oil> getAll() {
        log.debug("REST request to get all Oils");
        return oilRepository.findByOwnerIsCurrentUser();
    }

    /**
     * GET  /oils/:id -> get the "id" oil.
     */
    @RequestMapping(value = "/oils/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Oil> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Oil : {}", id);
        Oil oil = oilRepository.findOne(id);
        if (oil == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(oil, HttpStatus.OK);
    }

    /**
     * DELETE  /oils/:id -> delete the "id" oil.
     */
    @RequestMapping(value = "/oils/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Oil : {}", id);
        oilRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("oil", id.toString())).build();
    }
}
