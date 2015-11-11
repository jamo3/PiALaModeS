package com.issinc.pialamodes.ingest;

import static com.jayway.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.issinc.pialamodes.service.IPositionService;

public class StratuxFeedIngest 
{
    private static final Logger log = LoggerFactory.getLogger(StratuxFeedIngest.class);
    IPositionService service;
    String host = "172.20.6.146";
    int port = 30003;

    public void readFeed(String host, int port)
    {
        this.host = host;
        this.port = port;
        readFeed();
    }

    public void readFeed()
    {
        try (Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));)
                {
            String line;

            while ((line = in.readLine()) != null)
            {
                log.info(line);
                String[] lineParts = line.split(",");
                if (lineParts[0].equals("MSG"))
                {
                    String id = lineParts[4];
                    //ID Message
                    if (lineParts[1].equals("1"))
                    {
                        try
                        {
                            JSONObject callsign = new JSONObject();
                            callsign.put("hexIdent", id);
                            callsign.put("callsign", lineParts[10]);
                            String jsonString = callsign.toString();

                            given().
                            contentType("application/json").
                            body(jsonString).
                            when().
                            post("/callsign");
                        }
                        catch (JSONException e)
                        {
                            log.error("Failed to convert to JSON : " + e.getMessage());
                        }
                    }
                    //Surface Position Message
                    else if (lineParts[1].equals("2"))
                    {
                        try
                        {
                            JSONObject position = new JSONObject();
                            position.put("hexIdent", id);
                            position.put("lat", Double.valueOf(lineParts[14]));
                            position.put("lon", Double.valueOf(lineParts[15]));
                            position.put("heading", Double.valueOf(lineParts[13]));
                            position.put("groundSpeed", Double.valueOf(lineParts[12]));
                            String jsonString = position.toString();

                            given().
                            contentType("application/json").
                            body(jsonString).
                            when().
                            post("/position");
                        }
                        catch (JSONException e)
                        {
                            log.error("Failed to convert to JSON : " + e.getMessage());
                        }

                    }
                    //Airborne Position Message
                    else if (lineParts[1].equals("3"))
                    {
                        try
                        {
                            JSONObject position = new JSONObject();
                            position.put("hexIdent", id);
                            position.put("lat", Double.valueOf(lineParts[14]));
                            position.put("lon", Double.valueOf(lineParts[15]));
                            String jsonString = position.toString();

                            given().
                            contentType("application/json").
                            body(jsonString).
                            when().
                            post("/position");
                        }
                        catch (JSONException e)
                        {
                            log.error("Failed to convert to JSON : " + e.getMessage());
                        }
                    }
                    //Airborne Velocity Message
                    else if (lineParts[1].equals("4"))
                    {
                        try
                        {
                            JSONObject position = new JSONObject();
                            position.put("hexIdent", id);
                            position.put("heading", Double.valueOf(lineParts[13]));
                            position.put("groundSpeed", Double.valueOf(lineParts[12]));
                            position.put("verticalRate", Double.valueOf(lineParts[16]));
                            String jsonString = position.toString();

                            given().
                            contentType("application/json").
                            body(jsonString).
                            when().
                            post("/position");
                        }
                        catch (JSONException e)
                        {
                            log.error("Failed to convert to JSON : " + e.getMessage());
                        }
                    }
                }
            }
                }
        catch (IOException e)
        {
            log.error(e.getStackTrace().toString());
        }
    }
}
