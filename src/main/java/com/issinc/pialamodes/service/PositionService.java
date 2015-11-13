package com.issinc.pialamodes.service;

import com.issinc.pialamodes.domain.Position;
import com.issinc.pialamodes.persistence.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *  Created by jay.moss on 11/4/2015.
 */
@Service
public class PositionService implements IPositionService {

    private PositionRepository positionRepo;
    private static final Logger log = LoggerFactory.getLogger(PositionService.class);

    @Autowired
    public PositionService(PositionRepository repo) {
        this.positionRepo = repo;
    }

    @Override
    @Transactional
    public Position create(Position pos) {
        return positionRepo.save(new Position(
            pos.getHexIdent(), pos.getLat(), pos.getLon(), pos.getHeading(), pos.getGroundSpeed(), pos.getVerticalRate()));

    }

    @Override
    @Transactional
    public Position create(String hexIdent, Double lat, Double lon, Double heading, Double groundSpeed, Double verticalRate) {
        return positionRepo.save(new Position(hexIdent, lat, lon, heading, groundSpeed, verticalRate));
    }

    @Override
    @Transactional
    public List<Position> find() {
        return positionRepo.findAll();
    }

    @Override
    @Transactional
    public List<Position> findLastMinutes(Integer numberOfMinutes) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));//Munich time
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -numberOfMinutes);

        return positionRepo.findLastMinutes(calendar.getTime());
    }
}
