package com.issinc.pialamodes.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 *  Created by jay.moss on 11/13/2015.
 */


public class Journey implements Serializable {

    private String hexIdent;
    private String callsign;
    private String tailNumber;
    private String type;
    private List<Position> positions;

    protected Journey() {
    }
    public Journey (Aircraft aircraft, Callsign callsign, List<Position> positions) {
        if( null != aircraft){
            this.hexIdent = aircraft.getHexIdent();
            this.tailNumber = aircraft.getTailNumber();
            this.type = aircraft.getType();
        }
        if( null != callsign){
            this.callsign = callsign.getCallsign();
            this.hexIdent = callsign.getHexIdent(); //in case aircraft was null
        }
        if(null != positions ){
            this.positions = positions;
            if( null == hexIdent && !positions.isEmpty()){
                this.hexIdent = positions.get(0).getHexIdent();
            }
        }
    }

    public Journey(String hexIdent) {
        this.hexIdent = hexIdent;
    }

    public String getHexIdent() {
        return hexIdent;
    }

    public void setHexIdent(String hexIdent) {
        this.hexIdent = hexIdent;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public String getTailNumber() {
        return tailNumber;
    }
    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
