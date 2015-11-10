package com.issinc.pialamodes.ingest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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
                    String[] lineParts = line.split(",");
                    if (lineParts[0].equals("MSG"))
                    {
                        String id = lineParts[4];
                        //ID Message
                        if (lineParts[1].equals("1"))
                        {
                            String callSign = lineParts[10];
                            log.warn(id + " has callsign " + callSign);
                        }
                        //Surface Position Message
                        else if (lineParts[1].equals("2"))
                        {
                            String altitude = lineParts[11];
                            String groundSpeed = lineParts[12];
                            String latitude = lineParts[14];
                            String longitude = lineParts[15];
                            
                        }
                        //Airborne Position Message
                        else if (lineParts[1].equals("3"))
                        {
                            String altitude = lineParts[11];
                            String latitude = lineParts[14];
                            String longitude = lineParts[15];
                            
                        }
                        //Airborne Velocity Message
                        else if (lineParts[1].equals("4"))
                        {
                            String groundSpeed = lineParts[12];
                            String verticalRate = lineParts[16];
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
