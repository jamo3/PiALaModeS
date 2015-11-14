package com.issinc.pialamodes.service;

import com.issinc.pialamodes.domain.Journey;

import java.util.List;

/**
 *  Created by jay.moss on 10/30/2015.
 */
public interface IJourneyService {

    Journey findById(String hexIdent, Integer numberOfMinutes);
    List<String> findIdsForLastMinutes(Integer numberOfMinutes);
    List<Journey> findAll(Integer numberOfMinutes);
}
