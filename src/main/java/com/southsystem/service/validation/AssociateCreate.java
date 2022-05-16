package com.southsystem.service.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AssociateCreateValidator.class)
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AssociateCreate {

	String message() default "Validation error";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
