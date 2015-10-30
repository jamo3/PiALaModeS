package com.issinc.pialamodes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "heading")
public class Position extends AbstractBaseEntity implements Serializable {

    @Column(name="timestamp", nullable=false)
    private Date timestamp;

    @Column(name="heading")
    private String heading;

    @Column(name="lat")
    private String lat;

    @Column(name="lon")
    private String lon;

    @Column(name="ground_speed")
    private String groundSpeed;

    @Column(name="vertical_rate")
    private String verticalRate;

    @JsonIgnore
    @Version
    private long version;

    protected Position() {}

    public Position(String hexIdent, Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getHeading() {
        return heading;
    }
    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }
    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getGroundSpeed() {
        return groundSpeed;
    }
    public void setGroundSpeed(String groundSpeed) {
        this.groundSpeed = groundSpeed;
    }

    public String getVerticalRate() {
        return verticalRate;
    }
    public void setVerticalRate(String verticalRate) {
        this.verticalRate = verticalRate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
