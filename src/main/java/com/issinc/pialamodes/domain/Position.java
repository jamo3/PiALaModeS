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
public class Position implements Serializable {

    @EmbeddedId
    private PositionId positionId;

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

    protected Position() {    // required for spring jpa
    }

    public Position(String hexIdent, Double lat, Double lon, Double heading, Double groundSpeed, Double verticalRate) {
        this.positionId = new PositionId(hexIdent);
        this.lat = lat;
        this.lon = lon;
        this.heading = heading;
        this.groundSpeed = groundSpeed;
        this.verticalRate = verticalRate;
    }

    public PositionId getPositionId() {
        return positionId;
    }
    public void setPositionId(PositionId positionId) {
        this.positionId = positionId;
    }

    public String getHexIdent() {
        return positionId.getHexIdent();
    }

    public Date getTimestamp() {
        return positionId.getTimestamp();
    }
//    public void setTimestamp(Date timestamp) {
//        this.timestamp = timestamp;
//    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return positionId.equals(position.positionId);

    }

    @Override
    public int hashCode() {
        return positionId.hashCode();
    }
}
