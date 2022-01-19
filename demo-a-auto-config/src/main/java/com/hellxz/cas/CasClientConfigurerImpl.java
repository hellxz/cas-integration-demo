package com.hellxz.cas;

import java.util.Map;

import org.jasig.cas.client.boot.configuration.CasClientConfigurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Component;

/**
 * cas-client-support-springboot 依赖提供了CAS客户端的自动配置，
 * 当自动配置不满足需要时，可通过实现{@link CasClientConfigurer}接口来重写需要自定义的逻辑
 */
@Component
public class CasClientConfigurerImpl implements CasClientConfigurer {

    /**
     * 配置认证过滤器，添加忽略参数，使/logoutPage登出提示页免登录
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void configureAuthenticationFilter(final FilterRegistrationBean authenticationFilter) {
        Map initParameters = authenticationFilter.getInitParameters();
        initParameters.put("ignorePattern", "/logoutPage");
    }

}
