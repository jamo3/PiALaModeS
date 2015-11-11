package com.issinc.pialamodes.rest;

import com.issinc.pialamodes.domain.Position;
import com.issinc.pialamodes.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *  Created by jay.moss on 11/2/2015.
 */

@RestController
@RequestMapping("/position")
public class PositionController {

    private IPositionService positionService;

    @Autowired
    public PositionController(IPositionService aircraftService) {
        this.positionService = aircraftService;
    }

    // create a position, expect a json position object in body
    @RequestMapping(method=POST,
        consumes=APPLICATION_JSON_VALUE,
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<Position> createPosition(@RequestBody Position newPosition) {
        Position position = positionService.create(newPosition);
        return ResponseEntity.ok().body(position);
    }

    // create a dashboard, expect url encoded data
    @RequestMapping(method=POST,
        consumes=APPLICATION_FORM_URLENCODED_VALUE,
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<Position> createPositionUE(
        @RequestParam(value = "hexIdent") String hexIdent,
        @RequestParam(value = "lat") Double lat,
        @RequestParam(value = "lon") Double lon,
        @RequestParam(value = "heading") Double heading,
        @RequestParam(value = "groundSpeed") Double groundSpeed,
        @RequestParam(value = "verticalRate") Double verticalRate) {

        Position dbObj = positionService.create(hexIdent, lat, lon, heading, groundSpeed, verticalRate);
        return ResponseEntity.ok().body(dbObj);
    }



    // http get request to find all existing aircraft records
    @RequestMapping(method=GET,
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Position>> find() {

        List<Position> dbObjs = positionService.find();
        return ResponseEntity.ok().body(dbObjs);
    }

    // http get request to find a specific dashboard record
    @RequestMapping(method=GET, value="/{hexIdent}",
        produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Position>> findById(@PathVariable("hexIdent") String hexIdent) {

        List<Position> positions = positionService.findLast(hexIdent, 5);
        return ResponseEntity.ok().body(positions);
    }


}
