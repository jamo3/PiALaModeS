package com.issinc.pialamodes.persistence;

import com.issinc.pialamodes.domain.Callsign;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Created by jay.moss on 10/30/2015.
 */
public interface CallsignRepository extends JpaRepository<Callsign, String> {

    Callsign findByCallsign(String callsign);
}
