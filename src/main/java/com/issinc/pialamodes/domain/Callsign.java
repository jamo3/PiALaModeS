package com.issinc.pialamodes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *  Created by jay.moss on 10/9/2015.
 */

@Entity
@Table(name = "callsign")
@EntityListeners({AuditingEntityListener.class})
public class Callsign extends AbstractHexIdentEntity implements Serializable {

    @Column(name="callsign", nullable=false)
    private String callsign;

    @LastModifiedDate
    @Column(name="last_modified_date")
    private Date lastModifiedDate;

    @JsonIgnore
    @Version
    private long version;

    protected Callsign() {  // required for jpa, do not use
        super();
    }

    public Callsign(String hexIdent, String callsign) {
        super(hexIdent);
        this.callsign = callsign;
    }

    public String getCallsign() {
        return callsign;
    }
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
