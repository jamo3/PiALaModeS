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

import static com.jayway.restassured.RestAssured.given;
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
        Aircraft plane1 = new Aircraft("abc123", "tail-1", "piper-cub");
        service.create(plane1);
    }

    @After
    public void cleanUp() {
        repo.delete("abc123");
    }

    @Test
    public void testCreateAircraftJson() throws Exception {

        JSONObject aircraft1 = new JSONObject();
        aircraft1.put("hexIdent", "def456");
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
            body("hexIdent", equalTo("def456")).    // single json object returned
            body("tailNumber", equalTo("tail-2")).    // single json object returned
            body("type", equalTo("learjet x39"));

        repo.delete("def456");
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
    public void canFetchByHexIdent() {
        when().
            get("/aircraft/abc123").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("tailNumber", equalTo("tail-1")).    // single json object returned
            body("type", equalTo("piper-cub"));
    }


}