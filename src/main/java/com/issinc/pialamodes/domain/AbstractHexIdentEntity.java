package com.issinc.pialamodes.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 *  Created by jay.moss on 10/22/2015.
 */
@MappedSuperclass
public abstract class AbstractHexIdentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String hexIdent;

    protected AbstractHexIdentEntity() { }

    public AbstractHexIdentEntity(String hexIdent) {
        this.hexIdent = hexIdent;
    }

    public String getHexIdent() {
        return hexIdent;
    }

    public void setHexIdent(String hexIdent) {
        this.hexIdent = hexIdent;
    }

    @Override
    public int hashCode() {
        return hexIdent.hashCode();/**/
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof AbstractHexIdentEntity)) {
            return false;
        }
        AbstractHexIdentEntity other = (AbstractHexIdentEntity) obj;
        return getHexIdent().equals(other.getHexIdent());
    }
}