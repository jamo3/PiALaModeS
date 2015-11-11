package com.issinc.pialamodes.ingest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

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
            while ((line = reader.readLine()) != null) 
            {
                String[] splitLine = line.split(",");
                String idNumber = splitLine[33].trim();
                String tailNumber = "N" + splitLine[0].trim(); 
                String planeType = mfrCodes.get(splitLine[2]);
                log.info("id Number: " + idNumber + ", tail number: " + tailNumber + ", plane type: " + planeType);
                
            }
        } catch (IOException x) 
        {
            log.error("IOException: %s%n", x);
        }
        
    }
    
}
