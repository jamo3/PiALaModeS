package com.issinc.pialamodes.service;

import com.issinc.pialamodes.domain.Aircraft;

import java.util.List;

/**
 *  Created by jay.moss on 10/30/2015.
 */
public interface IAircraftService {

    Aircraft create(Aircraft aircraft);
    Aircraft create(String tailNumber, String type);

    List<Aircraft> find();
    Aircraft findByTailNumber(String tailNumber);

}
