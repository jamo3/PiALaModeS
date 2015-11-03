package com.issinc.pialamodes.rest;

import com.issinc.pialamodes.domain.Callsign;
import com.issinc.pialamodes.service.ICallsignService;
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
@RequestMapping("/callsign")
public class CallsignController {

    private ICallsignService callsignService;

    @Autowired
    public CallsignController(ICallsignService callsignService) {
        this.callsignService = callsignService;
    }

    // create an callsign, expect a callsign string in json body
    @RequestMapping(method=POST,
        consumes=APPLICATION_JSON_VALUE,
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<Callsign> createConfigJson(@RequestBody Callsign newCallsign) {

        Callsign callsign = callsignService.create(newCallsign.getCallsign());
        return ResponseEntity.ok().body(callsign);
    }

    // http get request to find all existing callsign records
    @RequestMapping(method=GET,
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Callsign>> find() {

        List<Callsign> dbObjs = callsignService.find();
        return ResponseEntity.ok().body(dbObjs);
    }

    // http get request to find a specific dashboard record
    @RequestMapping(method=GET, value="/{callsign}",
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<Callsign> findByName(@PathVariable("callsign") String callsignId) {

        Callsign callsign = callsignService.findByCallsign(callsignId);
        return ResponseEntity.ok().body(callsign);
    }


}
