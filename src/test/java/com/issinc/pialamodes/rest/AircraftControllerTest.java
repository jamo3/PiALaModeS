package com.issinc.pialamodes.rest;

import com.issinc.pialamodes.ServerApplication;
import com.issinc.pialamodes.domain.Aircraft;
import com.issinc.pialamodes.persistence.AircraftRepository;
import com.issinc.pialamodes.service.IAircraftService;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import junit.framework.TestCase;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *  Created by jay.moss on 11/2/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AircraftControllerTest extends TestCase {

    @Autowired
    IAircraftService service;
    @Autowired
    AircraftRepository repo;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        repo.deleteAll();
        Aircraft plane1 = new Aircraft("tail-1", "piper-cub");
        service.create(plane1);
    }

    @Test
    public void testCreateConfigJson() throws Exception {

        JSONObject aircraft1 = new JSONObject();
        aircraft1.put("tailNumber", "tail-2");
        aircraft1.put("type", "learjet x39");
        String jsonString = aircraft1.toString();

        given().
            contentType("application/json").
            body(jsonString).
        when().
            post("/aircraft").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("tailNumber", equalTo("tail-2")).    // single json object returned
            body("type", equalTo("learjet x39"));
    }

    @Test
    public void canFetchAll() {
        when().
            get("/aircraft").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("tailNumber", hasItems("tail-1"));
    }

    @Test
    public void canFetchOne() {
        when().
            get("/aircraft/tail-1").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("tailNumber", equalTo("tail-1")).    // single json object returned
            body("type", equalTo("piper-cub"));
    }


}