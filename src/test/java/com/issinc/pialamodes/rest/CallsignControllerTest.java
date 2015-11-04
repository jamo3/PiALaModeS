package com.issinc.pialamodes.rest;

import com.issinc.pialamodes.ServerApplication;
import com.issinc.pialamodes.persistence.CallsignRepository;
import com.issinc.pialamodes.service.ICallsignService;
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
import static org.hamcrest.Matchers.*;

/**
 *  Created by jay.moss on 11/2/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class CallsignControllerTest extends TestCase {

    @Autowired
    ICallsignService service;
    @Autowired
    CallsignRepository repo;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        service.create("abc123", "tango-100");
    }

    @After
    public void cleanUp() {
        repo.delete("abc123");
    }

    @Test
    public void testCreateCallsign() throws Exception {

        JSONObject callsign1 = new JSONObject();
        callsign1.put("hexIdent", "def456");
        callsign1.put("callsign", "alpha-300");
        String jsonString = callsign1.toString();

        given().
            contentType("application/json").
            body(jsonString).
        when().
            post("/callsign").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("hexIdent", equalTo("def456")).
            body("lastModifiedDate", notNullValue()).
            body("callsign", equalTo("alpha-300"));    // single json object returned
        repo.delete("def456");
    }

    @Test
    public void canFetchAll() {
        when().
            get("/callsign").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("callsign", hasItems("tango-100"));
    }

    @Test
    public void canFetchOne() {
        when().
            get("/callsign/tango-100").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("callsign", equalTo("tango-100"));    // single json object returned
    }
}