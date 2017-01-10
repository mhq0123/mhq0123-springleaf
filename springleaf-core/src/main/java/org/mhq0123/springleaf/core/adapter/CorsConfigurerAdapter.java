package org.mhq0123.springleaf.core.adapter;

import org.mhq0123.springleaf.core.SpringleafCoreConstants;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 全局跨域
 */
@Configurable
public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping(SpringleafCoreConstants.Cors.MAPPING).allowedOrigins("*");
	}
}