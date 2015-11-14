package com.issinc.pialamodes.rest;

import com.issinc.pialamodes.ServerApplication;
import com.issinc.pialamodes.domain.Aircraft;
import com.issinc.pialamodes.domain.Callsign;
import com.issinc.pialamodes.domain.Position;
import com.issinc.pialamodes.persistence.AircraftRepository;
import com.issinc.pialamodes.persistence.CallsignRepository;
import com.issinc.pialamodes.persistence.PositionRepository;
import com.issinc.pialamodes.service.IAircraftService;
import com.issinc.pialamodes.service.ICallsignService;
import com.issinc.pialamodes.service.IJourneyService;
import com.issinc.pialamodes.service.IPositionService;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import junit.framework.TestCase;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 *  Created by jay.moss on 11/2/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class JourneyControllerTest extends TestCase {

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

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() throws InterruptedException {
        RestAssured.port = port;

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
    public void canFetchByHexIdent() {
        when().
            get("/journey/abc123/2").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("tailNumber", equalTo("tail-1")).    // single json object returned
            body("type", equalTo("piper-cub"));
    }

    @Test
    public void canFetchAll() {
        when().
            get("/journey/2").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("tailNumber", hasItems("tail-1"));
    }
}