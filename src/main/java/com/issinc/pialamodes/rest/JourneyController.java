package com.issinc.pialamodes.rest;

import com.issinc.pialamodes.domain.Journey;
import com.issinc.pialamodes.service.IJourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *  Created by jay.moss on 11/2/2015.
 */

@RestController
@RequestMapping("/journey")
public class JourneyController {

    private IJourneyService journeyService;

    @Autowired
    public JourneyController(IJourneyService journeyService) {
        this.journeyService = journeyService;
    }

    // http get request to find all existing aircraft records
    @RequestMapping(method=GET, value="/{numMinutes}",
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Journey>> find(@PathVariable("numMinutes") Integer numMinutes) {

        List<Journey> allRecentJourneys = journeyService.findAll(numMinutes);
        return ResponseEntity.ok().body(allRecentJourneys);
    }

    // http get request to find a specific dashboard record
    @RequestMapping(method=GET, value="/{hexIdent}/{numMinutes}",
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<Journey> findById(
        @PathVariable("hexIdent") String hexIdent,
        @PathVariable("numMinutes") Integer numMinutes) {

        Journey journey = journeyService.findById(hexIdent, numMinutes);
        return ResponseEntity.ok().body(journey);
    }


}
