package com.issinc.pialamodes.service;

import com.issinc.pialamodes.domain.Aircraft;
import com.issinc.pialamodes.domain.Callsign;
import com.issinc.pialamodes.domain.Journey;
import com.issinc.pialamodes.domain.Position;
import com.issinc.pialamodes.persistence.AircraftRepository;
import com.issinc.pialamodes.persistence.CallsignRepository;
import com.issinc.pialamodes.persistence.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 *  Created by jay.moss on 11/13/2015.
 */

@Service
public class JourneyService implements IJourneyService {

    private AircraftRepository aircraftRepo;
    private CallsignRepository callsignRepo;
    private PositionRepository positionRepo;
    private static final Logger log = LoggerFactory.getLogger(PositionService.class);

    @Autowired
    public JourneyService(AircraftRepository aircraftRepo, CallsignRepository callsignRepo, PositionRepository positionRepo) {
        this.aircraftRepo = aircraftRepo;
        this.callsignRepo = callsignRepo;
        this.positionRepo = positionRepo;
    }

    @Override
    public Journey findById(String hexIdent, Integer numberOfMinutes) {

        Aircraft aircraft = aircraftRepo.findOne(hexIdent);
        Callsign callsign = callsignRepo.findOne(hexIdent);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.add(Calendar.MINUTE, -numberOfMinutes);

        List<Position> positions = positionRepo.findLastMinutesById(hexIdent, cal.getTime());

        return new Journey(aircraft,callsign,positions);
    }

    @Override
    public List<String> findIdsForLastMinutes(Integer numberOfMinutes) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.add(Calendar.MINUTE, -numberOfMinutes);
        return positionRepo.findIdsAfter(cal.getTime());
    }

    @Override
    public List<Journey> findAll(Integer numberOfMinutes) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.add(Calendar.MINUTE, -numberOfMinutes);
        List<String> hexIdentList = positionRepo.findIdsAfter(cal.getTime());

        List<Journey> journeyList = new ArrayList<>();
        for (String hexIdent : hexIdentList) {
            Journey aJourney = findById(hexIdent, numberOfMinutes);
            journeyList.add(aJourney);
        }
        return journeyList;
    }
}
