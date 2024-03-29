package com.babak.springboot.jpa.service;

import com.babak.springboot.jpa.domain.BaseEntity;
import com.babak.springboot.jpa.repository.BaseRepository;
import com.babak.springboot.jpa.search.SearchFilterModel;
import com.babak.springboot.jpa.specification.BaseSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
public abstract class BaseService<E extends BaseEntity, PK extends Serializable, R extends BaseRepository<E, PK>> {

    public abstract R getRepository();

    public E find(PK id) {
        return getRepository().findById(id).orElse(null);
    }

    public E submit(E entity) {
        return getRepository().save(entity);
    }

    public <F extends SearchFilterModel> Page<E> search(F filterModel) {
        BaseSpecification<E, F> specification = new BaseSpecification<>(filterModel);
        return getRepository().findAll(specification,
                Pageable.ofSize(filterModel.getPageSize()).withPage(filterModel.getPage()));
    }
}
