package com.issinc.pialamodes.service;

import com.issinc.pialamodes.domain.Position;

import java.util.List;

/**
 *  Created by jay.moss on 10/30/2015.
 */
public interface IPositionService {

    Position create(Position position);
    Position create(String hexIdent, Double lat, Double lon, Double heading, Double groundSpeed, Double verticalRate);

    List<Position> find();
    List<Position> findLast(String hexIdent, Integer numberOfPositions);
}
