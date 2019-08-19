package com.example.web.annotations;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: 36560
 * @Date: 2019/8/14 :6:55
 * @Description:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInt {
    int value() default 0;
    String name() default "";
    Constraints constraints() default @Constraints;
}
