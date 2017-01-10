package org.mhq0123.springleaf.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName Description
 * @date 2016-07-04
 * @memo
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
	String value();
}
