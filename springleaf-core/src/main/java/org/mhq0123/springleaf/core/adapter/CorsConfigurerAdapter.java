package org.mhq0123.springleaf.core.adapter;

import org.mhq0123.springleaf.core.SpringleafCoreConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

/**
 * 全局跨域
 */
public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(CorsConfigurerAdapter.class);

	@PostConstruct
	public void init() {
		logger.info(">>>>>>>>>>>>>>CorsConfigurerAdapter started ...");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		logger.info(">>>>>>>>>>>>>>addCorsMappings success...");
		registry.addMapping(SpringleafCoreConstants.Cors.MAPPING).allowedOrigins("*");
	}
}