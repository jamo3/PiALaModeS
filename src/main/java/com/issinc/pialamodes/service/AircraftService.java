package com.issinc.pialamodes.service;

import com.issinc.pialamodes.domain.Aircraft;
import com.issinc.pialamodes.persistence.AircraftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  Created by jay.moss on 10/30/2015.
 */
@Service
public class AircraftService implements IAircraftService  {

    private AircraftRepository aircraftRepo;
    private static final Logger log = LoggerFactory.getLogger(AircraftService.class);

    @Autowired
    public AircraftService(AircraftRepository repo) {
        this.aircraftRepo = repo;
    }

    @Override
    @Transactional
    public Aircraft create(Aircraft aircraft) {
        Aircraft result;

        Aircraft existing = aircraftRepo.findOne(aircraft.getHexIdent());
        if (existing != null) {
            log.warn("attempted insert of existing aircraft, hexIdent: " + aircraft.getHexIdent());
            result = existing;
        }
        else {
            result = aircraftRepo.save(aircraft);
        }
        return result;
    }

    @Override
    public Aircraft create(String hexIdent, String tailNumber, String type) {
        Aircraft result;

        Aircraft existing = aircraftRepo.findOne(hexIdent);
        if (existing != null) {
            log.warn("attempted insert of existing aircraft, hexIdent: " + hexIdent);
            result = existing;
        }
        else {
            Aircraft aircraft = new Aircraft(hexIdent, tailNumber, type);
            result = aircraftRepo.save(aircraft);
        }
        return result;
    }

    @Override
    @Transactional
    public List<Aircraft> find() {
        return aircraftRepo.findAll();
    }

    @Override
    @Transactional
    public Aircraft findByTailNumber(String tailNumber) {
        return aircraftRepo.findByTailNumber(tailNumber);
    }

    @Override
    @Transactional
    public Aircraft findByHexIdent(String hexIdent) {
        return aircraftRepo.findOne(hexIdent);
    }

}
