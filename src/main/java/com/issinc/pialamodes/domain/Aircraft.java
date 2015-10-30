package com.issinc.pialamodes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 *  Created by jay.moss on 10/9/2015.
 */

@Entity
@Table(name = "aircraft")
public class Aircraft extends AbstractBaseEntity implements Serializable {

    @Column(name="tail_number", nullable=false)
    private String tailNumber;

    @Column(name="type", nullable=false)
    private String type;

    @JsonIgnore
    @Version
    private long version;

    protected Aircraft() {}

    public Aircraft(String tailNumber, String type) {
        this.tailNumber = tailNumber;
        this.type = type;
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
