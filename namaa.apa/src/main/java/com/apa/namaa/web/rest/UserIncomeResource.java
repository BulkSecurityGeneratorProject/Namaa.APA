package com.apa.namaa.web.rest;

import com.apa.namaa.domain.*;
import com.apa.namaa.repository.*;
import com.apa.namaa.security.SecurityUtils;
import com.apa.namaa.web.rest.util.CaluationRules;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hosni on 12/21/15.
 */

/**
 * REST controller for managing user income.
 */
@RestController
@RequestMapping("/api")
public class UserIncomeResource {
    private final Logger log = LoggerFactory.getLogger(UserIncomeResource.class);
    private final boolean IS_WATERED = false;
    private final boolean IS_NON_WATERED = false;
    private  double WATERED = 0.0d;
    private  double NON_WATERED = 0.0d;

    public  static  final CaluationRules USER_CHOICE = CaluationRules.JUDGMENT_EQUALLY;

    Double Total = new Double(0.0d);
    Double Zakat =   new Double(0.0d);
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
    private OilRepository oilRepository;
    @Inject
    private OliveRepository oliveRepository;

    /**
     * GET  /users -> get the "login" user.
     */
    @RequestMapping(value = "/incomes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map<String, Object>> getUserIncomes() {
        log.debug("REST request to get User Incomes :");
        User owner = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        if (owner != null) {
            log.debug("User Incomes Owner : {}", owner.getLogin());
        }

        Map<String, Object> _resources = new HashMap<String, Object>();
        Total = new Double(0.0d);
        Zakat =   new Double(0.0d);
        WATERED = 0.0d;
        NON_WATERED = 0.0d;
        if (fruitRepository.findByOwnerIsCurrentUser() != null) {
            List<Fruit> _fruits = fruitRepository.findByOwnerIsCurrentUser();
            _resources.put("fruit", _fruits);
            for (Fruit f : _fruits) {
                WATERED+=f.getWatered();
                NON_WATERED+=f.getNonWatered();
                Total += f.getDates();
                Total += f.getGrapes();
            }
        }
        if (grainType1Repository.findByOwnerIsCurrentUser() != null) {
            List<GrainType1> _grains1 = grainType1Repository.findByOwnerIsCurrentUser();
            _resources.put("grainType1", _grains1);
            for (GrainType1 g1 : _grains1) {
                WATERED+=g1.getWatered();
                NON_WATERED+=g1.getNonWatered();
                Total += g1.getCorn();
                Total += g1.getMillet();
                Total += g1.getSpelt();
            }
        }
        if (grainType2Repository.findByOwnerIsCurrentUser() != null) {
            List<GrainType2> _grains2 = grainType2Repository.findByOwnerIsCurrentUser();
            _resources.put("grainType2", _grains2);
            for (GrainType2 g2 : _grains2) {
                WATERED+=g2.getWatered();
                NON_WATERED+=g2.getNonWatered();
                Total += g2.getBarley();
                Total += g2.getWheat();
            }
        }
        if (grainType3Repository.findByOwnerIsCurrentUser() != null) {
            List<GrainType3> _grains3 = grainType3Repository.findByOwnerIsCurrentUser();
            _resources.put("grainType3", _grains3);
            for (GrainType3 g3 : _grains3) {
                WATERED+=g3.getWatered();
                NON_WATERED+=g3.getNonWatered();
                Total += g3.getBeans();
                Total += g3.getChickpeas();
                Total += g3.getCowpea();
            }
        }
        if (oilRepository.findByOwnerIsCurrentUser() != null) {
            List<Oil> _oil = oilRepository.findByOwnerIsCurrentUser();
            _resources.put("oil", _oil);
            for (Oil oil : _oil) {
                WATERED+=oil.getWatered();
                NON_WATERED+=oil.getNonWatered();
                Total += oil.getRadish();
                Total += oil.getSafflower();
                Total += oil.getSesame();
            }
        }
        if (oliveRepository.findByOwnerIsCurrentUser() != null) {
            List<Olive> _olive = oliveRepository.findByOwnerIsCurrentUser();
            _resources.put("olive", _olive);
            for (Olive olive : _olive) {
                WATERED+=olive.getWatered();
                NON_WATERED+=olive.getNonWatered();
                Total += olive.getOliveOil();
            }
        }
        log.debug("REST request to get User Incomes : \n Total :{} \n" +
            " WATERED :{} \n" +
            " NON_WATERED :{}",Total,WATERED,NON_WATERED);
        _resources.put("total", Total);
        if (Total >=  615) {
            if (NON_WATERED == Total) {
                Zakat = new Double(Total * 0.1);
            } else if (WATERED == Total) {
                Zakat = new Double(Total * 0.05);
            } else {
                switch (USER_CHOICE) {
                    case RULE_FOR_THE_MAJORITY:
                        if (NON_WATERED >= WATERED) {
                            Zakat = new Double(Total * 0.1);
                        }else{
                            Zakat = new Double(Total * 0.05);
                        }
                        break;
                    case JUDGMENT_EQUALLY:
                        Zakat = new Double( (NON_WATERED * 0.1) + (WATERED * 0.05));
                        break;
                }
            }
        }
        _resources.put("zakat", Zakat);

        return new ResponseEntity<>(_resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/incomes/sum",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Double> getUserIncomesSum() {
        Total = new Double(0.0d);
        ResponseEntity<Map<String, Object>> _result = getUserIncomes();
        log.debug("getUserIncomesSum() : {}", Total.doubleValue());
        return new ResponseEntity<>(Total, HttpStatus.OK);

    }

}

