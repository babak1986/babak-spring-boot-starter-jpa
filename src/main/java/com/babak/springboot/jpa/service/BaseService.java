package com.babak.springboot.jpa.service;

import com.babak.springboot.jpa.domain.BaseEntity;
import com.babak.springboot.jpa.repository.BaseRepository;

import java.io.Serializable;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
public abstract class BaseService<E extends BaseEntity, PK extends Serializable, R extends BaseRepository<E, PK>> {

    public abstract R getRepository();

    public E submit(E entity) {
        return getRepository().save(entity);
    }
}
