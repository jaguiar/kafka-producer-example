package org.yaourtcorp.kafka.producer.example.conditions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

/**
 * Annotation to check a condition by looking for required property in env
 * @author jaguiar
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnEnvRequiredPropertyCondition.class)
public @interface ConditionalOnEnvRequiredProperty {
	public String propertyName();
	public String value();
}