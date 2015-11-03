package com.issinc.pialamodes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "position")
@EntityListeners({AuditingEntityListener.class})
public class Position extends AbstractHexIdentEntity implements Serializable {

    @Column(name="timestamp", nullable=false)
    private Date timestamp;

    @Column(name="heading")
    private Double heading;

    @Column(name="lat")
    private Double lat;

    @Column(name="lon")
    private Double lon;

    @Column(name="ground_speed")
    private Double groundSpeed;

    @Column(name="vertical_rate")
    private Double verticalRate;

    @JsonIgnore
    @Version
    private long version;

    public Position(String hexIdent, Double lat, Double lon, Double groundSpeed, Double verticalRate) {
        super(hexIdent);
        this.timestamp = new Date();    // always now for a new object
        this.lat = lat;
        this.lon = lon;
        this.groundSpeed = groundSpeed;
        this.verticalRate = verticalRate;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getHeading() {
        return heading;
    }
    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }
    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getGroundSpeed() {
        return groundSpeed;
    }
    public void setGroundSpeed(Double groundSpeed) {
        this.groundSpeed = groundSpeed;
    }

    public Double getVerticalRate() {
        return verticalRate;
    }
    public void setVerticalRate(Double verticalRate) {
        this.verticalRate = verticalRate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
