package com.babak.springboot.jpa.repository;

import com.babak.springboot.jpa.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
public interface BaseRepository<E extends BaseEntity, PK extends Serializable>
        extends JpaRepository<E, PK>, JpaSpecificationExecutor<E> {
}
