package org.yaourtcorp.kafka.producer.example.conditions;

import java.util.Map;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnEnvRequiredPropertyCondition implements Condition {

	private final static String PROPERTY_NAME_ATTRIBUTE ="propertyName";
	private final static String VALUE_ATTRIBUTE ="value";
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnEnvRequiredProperty.class.getName());
		String requiredProperty = (String) attributes.get(PROPERTY_NAME_ATTRIBUTE);
		String valueToCheckProperty = (String) attributes.get(VALUE_ATTRIBUTE);
		return context.getEnvironment().getRequiredProperty(requiredProperty).contains(valueToCheckProperty);
	}
}