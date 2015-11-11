package com.issinc.pialamodes.ingest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticDataIngest 
{
    private static final Logger log = LoggerFactory.getLogger(StaticDataIngest.class);
            
    /**
     * @param mfrFilePath manufacturer file path
     * @param registrationFilePath list of files to ingest
     */
    public void ingest(String mfrFilePath, String registrationFilePath)
    {
        Map<String, String> mfrCodes = new TreeMap<String, String>();
        //Build index of manufacturer code and plane type
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(mfrFilePath))) 
        {
            String line = null;
            //Skip first line (header info)
            reader.readLine();
            while ((line = reader.readLine()) != null) 
            {
                String[] splitLine = line.split(",");
                String mfrCode = splitLine[0];
                String mfr = splitLine[1].trim();
                String model = splitLine[2].trim();
                mfrCodes.put(mfrCode, mfr + " " + model);
            }
        } catch (IOException x) 
        {
            log.error("IOException: %s%n", x);
        }      
        
        //Map hex id and plane type
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(registrationFilePath))) 
        {
            String line = null;
            //Skip first line (header info)
            reader.readLine();
            URL aircraftURL = new URL("http://localhost:8080/aircraft");
            
            
            while ((line = reader.readLine()) != null) 
            {
                try
                {
                    JSONObject aircraft1 = new JSONObject();
                    String[] splitLine = line.split(",");
                    aircraft1.put("hexIdent", splitLine[33].trim());
                    aircraft1.put("tailNumber", "N" + splitLine[0].trim());
                    aircraft1.put("type", mfrCodes.get(splitLine[2]));
                    String jsonString = aircraft1.toString();
                    HttpURLConnection conn = (HttpURLConnection) aircraftURL.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    OutputStream os = conn.getOutputStream();
                    os.write(jsonString.getBytes());
                    os.flush();
                    conn.getResponseCode();
                    conn.disconnect();
                    
                }
                catch (JSONException e)
                {
                    log.error("Failed to convert to JSON : " + e.getMessage());
                }               
            }
        } 
        catch (IOException x) 
        {
            log.error("IOException: %s%n", x);
        }
        
    }
    
}
