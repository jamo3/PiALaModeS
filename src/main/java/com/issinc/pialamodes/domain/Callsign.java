package com.issinc.pialamodes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 *  Created by jay.moss on 10/9/2015.
 */

@Entity
@Table(name = "callsign")
public class Callsign extends AbstractBaseEntity implements Serializable {

    @Column(name="hex_ident", nullable=false)
    private String hexIdent;

    @Column(name="timestamp", nullable=false)
    private Date timestamp;

    @Column(name="callsign", nullable=false)
    private String callsign;


    @JsonIgnore
    @Version
    private long version;

    protected Callsign() {}

    public Callsign(String hexIdent, String callsign, Date timestamp) {
        this.hexIdent = hexIdent;
        this.callsign = callsign;
        this.timestamp = timestamp;
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

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
