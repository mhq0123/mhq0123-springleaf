package org.mhq0123.springleaf.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName Comment
 * @date 2016-07-04
 * @memo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Comment {
	/** 对应数据库值*/
	public String code() default "";
	
	/** 对应数据库描述*/
	public String desc();
	
}