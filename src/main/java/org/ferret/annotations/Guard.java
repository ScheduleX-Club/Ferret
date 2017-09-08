package org.ferret.annotations;

import org.ferret.annotations.type.AdviceType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Guard {

    AdviceType value() default AdviceType.Before;
}
