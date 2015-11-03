package com.issinc.pialamodes.service;

import com.issinc.pialamodes.domain.Callsign;

import java.util.List;

/**
 *  Created by jay.moss on 10/30/2015.
 */
public interface ICallsignService {

    Callsign create(String callsign);

    List<Callsign> find();
    Callsign findByCallsign(String callsign);

}
