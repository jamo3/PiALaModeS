package com.issinc.pialamodes.rest;

import com.issinc.pialamodes.domain.Aircraft;
import com.issinc.pialamodes.service.AircraftService;
import com.issinc.pialamodes.service.IAircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *  Created by jay.moss on 11/2/2015.
 */

@RestController
@RequestMapping("/aircraft")
public class AircraftController {

    private IAircraftService aircraftService;

    @Autowired
    public AircraftController(IAircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    // create an aircraft, expect a json aircraft object in body
    @RequestMapping(method=POST,
        consumes=APPLICATION_JSON_VALUE,
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<Aircraft> createConfigJson(@RequestBody Aircraft newAircraft) {

        Aircraft aircraft = aircraftService.create(newAircraft);
        return ResponseEntity.ok().body(aircraft);
    }

    // http get request to find all existing aircraft records
    @RequestMapping(method=GET,
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Aircraft>> find() {

        List<Aircraft> dbObjs = aircraftService.find();
        return ResponseEntity.ok().body(dbObjs);
    }

    // http get request to find a specific dashboard record
    @RequestMapping(method=GET, value="/{tailNumber}",
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<Aircraft> findByName(@PathVariable("tailNumber") String tailNumber) {

        Aircraft aircraft = aircraftService.findByTailNumber(tailNumber);
        return ResponseEntity.ok().body(aircraft);
    }


}
