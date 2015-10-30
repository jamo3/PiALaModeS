package com.issinc.pialamodes.persistence;

import com.issinc.pialamodes.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Created by jay.moss on 10/30/2015.
 */
public interface PositionRepository extends JpaRepository<Position, String> {

}
