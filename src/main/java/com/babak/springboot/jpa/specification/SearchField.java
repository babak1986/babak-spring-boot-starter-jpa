package com.babak.springboot.jpa.specification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchField {

    String column();

    SearchOperand operand();

    String relation() default "";
}
