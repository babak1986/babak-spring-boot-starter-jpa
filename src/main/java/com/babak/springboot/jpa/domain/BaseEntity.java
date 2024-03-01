package com.babak.springboot.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity<PK extends Serializable> {

    @Id
    @GeneratedValue
    private PK id;
    @Nullable
    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Nullable
    private Date createdDate;
    @Nullable
    private String modifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Nullable
    private Date modifiedDate;

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), this.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (!this.getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        } else {
            BaseEntity<PK> that = (BaseEntity<PK>) obj;
            return this.getId() == null ? false : this.getId().equals(that.getId());
        }
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

    @Transient
    public boolean isNew() {
        return id == null;
    }
}