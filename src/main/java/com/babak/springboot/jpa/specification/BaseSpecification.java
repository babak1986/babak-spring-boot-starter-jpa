package com.babak.springboot.jpa.specification;

import com.babak.springboot.jpa.domain.BaseEntity;
import com.babak.springboot.jpa.search.SearchFilterModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
public class BaseSpecification<E extends BaseEntity, F extends SearchFilterModel> implements Specification<E> {

    private final F filterModel;

    public BaseSpecification(F filterModel) {
        this.filterModel = filterModel;
    }

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and((Predicate[]) filterModel.toPredicates(root, criteriaBuilder).toArray());
    }
}
