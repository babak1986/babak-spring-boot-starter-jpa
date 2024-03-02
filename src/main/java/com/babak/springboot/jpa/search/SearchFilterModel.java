package com.babak.springboot.jpa.search;

import com.babak.springboot.jpa.domain.BaseEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@Getter
@Setter
public abstract class SearchFilterModel<E extends BaseEntity> {

    private int page;
    private int pageSize;

    public abstract List<SearchFilterCondition> conditions();

    private Expression[] values(Object value) {
        if (value instanceof Array) {
            return (Expression[]) ((Object[]) value);
        }
        return new Expression[]{(Expression) value};
    }

    private String likeExpr(Object value) {
        return "%" + value + "%";
    }

    public List<Predicate> toPredicates(Root<E> root, CriteriaBuilder criteriaBuilder) {
        return Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(SearchField.class))
                .map(field -> {
                    SearchField annotation = field.getAnnotation(SearchField.class);
                    try {
                        Object value = field.get(this);
                        return criteriaBuilder.and(switch (annotation.operand()) {
                            case EQ -> criteriaBuilder.equal(root.get(annotation.column()), value);
                            case NO_EQ -> criteriaBuilder.notEqual(root.get(annotation.column()), value);
                            case IN -> {
                                Object[] values = values(value);
                                yield criteriaBuilder.in(root.get(annotation.column()).in(values));
                            }
                            case BETWEEN -> {
                                Object[] values = values(value);
                                yield criteriaBuilder.between(root.get(annotation.column()),
                                        (Comparable) values[0], (Comparable) values[1]);
                            }
                            case LT -> criteriaBuilder.lessThan(root.get(annotation.column()), (Comparable) value);
                            case LE -> criteriaBuilder.le(root.get(annotation.column()), (Expression<? extends Number>) value);
                            case GT -> criteriaBuilder.greaterThan(root.get(annotation.column()), (Comparable) value);
                            case GE -> criteriaBuilder.ge(root.get(annotation.column()), (Expression<? extends Number>) value);
                            case LIKE -> criteriaBuilder.like(root.get(annotation.column()), likeExpr(value));
                            case I_LIKE -> criteriaBuilder.like(criteriaBuilder.lower(root.get(annotation.column())), likeExpr(value.toString().toLowerCase()));
                            case NOT_LIKE -> criteriaBuilder.notLike(root.get(annotation.column()), likeExpr(value));
                            default -> throw new RuntimeException("Invalid search perand");
                        });
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                })
                .toList();
    }
}