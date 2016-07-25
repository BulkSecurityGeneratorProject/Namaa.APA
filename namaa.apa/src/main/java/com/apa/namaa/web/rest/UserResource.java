package com.apa.namaa.web.rest;

import com.apa.namaa.domain.*;
import com.apa.namaa.repository.*;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    double _sum = 0.0d;

    @Inject
    private UserRepository userRepository;
    @Inject
    private FruitRepository fruitRepository;
    @Inject
    private GrainType1Repository grainType1Repository;
    @Inject
    private GrainType2Repository grainType2Repository;
    @Inject
    private GrainType3Repository grainType3Repository;
    @Inject
    private OilRepository  oilRepository;
    @Inject
    private OliveRepository  oliveRepository;
    /**
     * GET  /users -> get all users.
     */
    @RequestMapping(value = "/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<User> getAll() {
        log.debug("REST request to get all Users");
        return userRepository.findAll();
    }

    /**
     * GET  /users/:login -> get the "login" user.
     */
    @RequestMapping(value = "/users/{login}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public User getUserResources(@PathVariable String login, HttpServletResponse response) {
        log.debug("REST request to getUserResources : {}", login);
        User user = userRepository.findOneByLogin(login);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
           return user;
    }
}
