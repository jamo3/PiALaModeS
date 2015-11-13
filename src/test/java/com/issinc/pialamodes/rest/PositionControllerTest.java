package com.issinc.pialamodes.rest;

import com.issinc.pialamodes.ServerApplication;
import com.issinc.pialamodes.domain.Position;
import com.issinc.pialamodes.persistence.PositionRepository;
import com.issinc.pialamodes.service.IPositionService;
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
 *  Created by jay.moss on 11/10/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class PositionControllerTest extends TestCase {

    @Autowired
    IPositionService service;
    @Autowired
    PositionRepository repo;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        Position p001 = new Position("abc123", 25.0D, 25.0D, 90.0D, 123.0D, 3.0D);
        service.create(p001);
        // Thread.sleep(500L);
        Position p002 = new Position("abc123", 25.1D, 25.1D, 90.1D, 123.1D, 3.1D);
        service.create(p002);
    }

    @After
    public void cleanUp() {
        repo.deleteByHexIdent("abc123");
    }

    @Test
    public void canCreatePositionUrlEncoded() {
        given().
            parameters(
                "hexIdent", "abc123",
                "lat", 25.2D,
                "lon", 26.2D,
                "heading", 90.2D,
                "groundSpeed", 123.2D,
                "verticalRate", 3.2D
            ).
        when().
            post("/position").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("hexIdent", equalTo("abc123")).    // single json object returned
            body("positionId.hexIdent", equalTo("abc123")).
            body("timestamp", notNullValue()).
            body("positionId.timestamp", notNullValue()).
            body("lat", equalTo(25.2f)).
            body("lon", equalTo(26.2f)).
            body("heading", equalTo(90.2f)).
            body("groundSpeed", equalTo(123.2f)).
            body("verticalRate", equalTo(3.2f)
            );
    }

    @Test
    public void testCreatePositionJson() throws Exception {
        JSONObject p003Id = new JSONObject();
        p003Id.put("hexIdent", "abc123");

        JSONObject p003 = new JSONObject();
        p003.put("positionId", p003Id);
        p003.put("lat", 25.3D);
        p003.put("lon", 26.3D);
        p003.put("heading", 90.3D);
        p003.put("groundSpeed", 123.3D);
        p003.put("verticalRate", 3.3D);
        String jsonString = p003.toString();

        given().
            contentType("application/json").
            body(jsonString).
        when().
            post("/position").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("hexIdent", equalTo("abc123")).    // single json object returned
            body("positionId.hexIdent", equalTo("abc123")).
            body("timestamp", notNullValue()).
            body("positionId.timestamp", notNullValue()).
            body("lat", equalTo(25.3f)).
            body("lon", equalTo(26.3f)).
            body("heading", equalTo(90.3f)).
            body("groundSpeed", equalTo(123.3f)).
            body("verticalRate", equalTo(3.3f)
            );
    }

    @Test
    public void testFindAll() throws Exception {
        when().
            get("/position").
        then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("hexIdent", hasItems("abc123"));
    }

    @Test
    public void testFindLastMinutes() throws Exception {
        when().
            get("/position/2"). // last 2 minutes
            then().
            statusCode(HttpStatus.SC_OK).
            contentType(ContentType.JSON).
            body("hexIdent", hasItems("abc123"));

    }
}