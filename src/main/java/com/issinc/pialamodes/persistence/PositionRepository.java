package com.issinc.pialamodes.persistence;

import com.issinc.pialamodes.domain.Position;
import com.issinc.pialamodes.domain.PositionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *  Created by jay.moss on 10/30/2015.
 */
public interface PositionRepository extends JpaRepository<Position, PositionId> {

    @Modifying
    @Transactional
    @Query("delete from Position pos where pos.positionId.hexIdent = ?1")
    int deleteByHexIdent(String hexIdent);

    Position findByPositionId(PositionId positionId);

    @Query("select pos from Position pos where pos.positionId.hexIdent = ?1 and pos.positionId.timestamp > ?2 order by pos.positionId.timestamp desc")
    List<Position> findLastMinutesById(String hexIdent, Date endTime);

    @Query("select distinct(pos.positionId.hexIdent) from Position pos where pos.positionId.timestamp > ?1")
    List<String> findIdsAfter(Date endTime);

    @Query("select pos from Position pos where pos.positionId.timestamp > ?1 order by pos.positionId.timestamp desc")
    List<Position> findPositionsAfter(Date endTime);


}
