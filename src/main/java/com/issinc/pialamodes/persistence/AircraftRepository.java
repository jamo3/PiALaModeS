package com.issinc.pialamodes.persistence;

import com.issinc.pialamodes.domain.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Created by jay.moss on 10/30/2015.
 */
public interface AircraftRepository extends JpaRepository<Aircraft, String> {

    Aircraft findByTailNumber(String tailNumber);

}
