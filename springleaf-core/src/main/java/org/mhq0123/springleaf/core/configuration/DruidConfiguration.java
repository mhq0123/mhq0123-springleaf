package org.mhq0123.springleaf.core.configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * project: springleaf
 * author:  mhq0123
 * date:    2017/1/8.
 * desc:    Druid数据源配置
 */
public class DruidConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);

    @PostConstruct
    public void init() {
        logger.info(">>>>>>>>>>>>>>DruidConfiguration started:{} ...", JSONObject.toJSONString(this, true));
    }

    /** 白名单*/
    private String allowIps = "127.0.0.1";
    /** 黑名单*/
    private String denyIps = "";
    /** 登陆账号*/
    private String loginUsername = "mhq0123";
    /** 登陆密码*/
    private String loginPassword = "123456";
    /** 是否能重置*/
    private String resetEnable = "false";
    /** 访问路径*/
    private String druidPath = "/druid";

    /**
     * 注册一个StatViewServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet(){
        logger.info(">>>>>>>>>>>>>>druidStatViewServlet init start...");

        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), this.getDruidPath() + "/*");

        //添加初始化参数：initParams

        //白名单：
        servletRegistrationBean.addInitParameter("allow", this.getAllowIps());
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", this.getDenyIps());
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", this.getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", this.getLoginPassword());
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", this.getResetEnable());

        logger.info(">>>>>>>>>>>>>>druidStatViewServlet init success");

        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        logger.info(">>>>>>>>>>>>>>druidStatFilter init start...");

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");

        //添加需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,"+this.druidPath+"/*");

        logger.info(">>>>>>>>>>>>>>druidStatFilter init success");
        return filterRegistrationBean;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getDenyIps() {
        return denyIps;
    }

    public void setDenyIps(String denyIps) {
        this.denyIps = denyIps;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getResetEnable() {
        return resetEnable;
    }

    public void setResetEnable(String resetEnable) {
        this.resetEnable = resetEnable;
    }

    public String getDruidPath() {
        return druidPath;
    }

    public void setDruidPath(String druidPath) {
        this.druidPath = druidPath;
    }
}
