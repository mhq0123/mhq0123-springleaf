package com.mhq0123.springleaf.core.config;

import com.mhq0123.springleaf.core.config.type.VersionType;

import java.io.Serializable;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName SystemInfo
 * @date 2016-07-04
 * @memo
 */
public class SystemInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 系统名称 		*/	
	private String systemName;
	/** 发布类型	 	*/ 
	private VersionType versionType;
	/** 发布版本日期		*/
	private String versionDate;
	/** 系统开发者	 	*/
	private String developer;

	public String getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(String versionDate) {
		this.versionDate = versionDate;
	}

	public VersionType getVersionType() {
		return versionType;
	}

	public void setVersionType(VersionType versionType) {
		this.versionType = versionType;
	}
	
	public String getSystemName() {

		return systemName;
	}

	public String getDeveloper() {

		return developer;
	}

	public void setSystemName(String systemName) {

		this.systemName = systemName;
	}

	public void setDeveloper(String developer) {

		this.developer = developer;
	}
	
}
