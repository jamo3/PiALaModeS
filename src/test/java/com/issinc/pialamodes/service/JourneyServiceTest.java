package com.issinc.pialamodes.service;

import com.issinc.pialamodes.ServerApplication;
import com.issinc.pialamodes.domain.Aircraft;
import com.issinc.pialamodes.domain.Callsign;
import com.issinc.pialamodes.domain.Journey;
import com.issinc.pialamodes.domain.Position;
import com.issinc.pialamodes.persistence.AircraftRepository;
import com.issinc.pialamodes.persistence.CallsignRepository;
import com.issinc.pialamodes.persistence.PositionRepository;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 *  Created by jay.moss on 11/13/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServerApplication.class)
public class JourneyServiceTest extends TestCase {

    @Autowired
    IAircraftService aircraftService;
    @Autowired
    AircraftRepository aircraftRepo;

    @Autowired
    ICallsignService callsignService;
    @Autowired
    CallsignRepository callsignRepo;

    @Autowired
    IPositionService positionService;
    @Autowired
    PositionRepository positionRepo;

    @Autowired
    IJourneyService journeyService;

    @Before
    public void setUp() throws InterruptedException {
        Aircraft plane1 = new Aircraft("abc123", "tail-1", "piper-cub");
        aircraftService.create(plane1);

        Callsign w100 = new Callsign("abc123", "whiskey-100");
        callsignService.create("abc123", "whiskey-100");

        Position p001 = new Position("abc123", 25.0D, 25.0D, 90.0D, 123.0D, 3.0D);
        positionService.create(p001);
        Position p002 = new Position("abc123", 25.1D, 25.51D, 90.1D, 123.1D, 3.1D);
        positionService.create(p002);
    }

    @After
    public void cleanUp() {
        aircraftRepo.delete("abc123");
        callsignRepo.delete("abc123");
        positionRepo.deleteByHexIdent("abc123");
    }

    @Test
    public void testFindById() throws Exception {
        Journey ret = journeyService.findById("abc123", 2);
        assertNotNull("Retrieved Object should not be null", ret);
        assertEquals("hexIdent should match", "abc123", ret.getHexIdent());
        assertEquals("callsign should match", "whiskey-100", ret.getCallsign());
        //TODO add asserts for other fields
    }

    @Test
    public void testFindIdsForLastMinutes() throws Exception {
        List<String> ret = journeyService.findIdsForLastMinutes(2);
        assertNotNull("Retrieved Object should not be null", ret);
        //TODO add asserts for other fields
    }

    @Test
    public void testFindAll() throws Exception {
        List<Journey> ret = journeyService.findAll(2);
        assertNotNull("Retrieved Object should not be null", ret);
        //TODO add asserts for other fields
    }
}