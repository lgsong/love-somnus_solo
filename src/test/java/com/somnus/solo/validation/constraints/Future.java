package com.somnus.solo.validation.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @ClassName:     Future.java
 * @Description:   TODO
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年10月26日 下午2:14:25
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {FutureValidator.class})
public @interface Future {
	String message() default "{javax.validation.constraints.Future.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default {};
}
