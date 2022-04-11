package com.hellxz.cas;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class CasConfig {
    /**
     * 自定义cas服务地址
     */
    @Value("${custom.cas.casServerUrlPrefix:}")
    private String casServerUrlPrefix;

    /**
     * 自定义服务标识，格式为{protocol}:{hostName}:{port}
     */
    @Value("${custom.cas.serverName:}")
    private String serverName;

    /**
     * 监听登出事件，清除session与token之间的映射关系及CAS会话记录
     */
    @Bean
    public ServletListenerRegistrationBean<EventListener> casSingleSignOutListener() {
        ServletListenerRegistrationBean<EventListener> singleSignOutListener = new ServletListenerRegistrationBean<>();
        singleSignOutListener.setListener(new SingleSignOutHttpSessionListener());
        return singleSignOutListener;
    }

    @Bean
    @Order(0)
    public FilterRegistrationBean<SingleSignOutFilter> casSingleSignOutFilter() {
        FilterRegistrationBean<SingleSignOutFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SingleSignOutFilter());
        registration.setName("CAS Single Sign Out Filter");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("casServerUrlPrefix", casServerUrlPrefix); // CAS服务端地址，会拼接为登录地址
        initParams.put("serverName", serverName); // 服务地址
        registration.setInitParameters(initParams);
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * 拦截所有请求，将未携带票据与会话中无票据的请求都重定向到CAS登录地址
     */
    @Bean
    @Order(1)
    public FilterRegistrationBean<AuthenticationFilter> casAuthenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuthenticationFilter());
        registration.setName("CAS Authentication Filter");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("casServerUrlPrefix", casServerUrlPrefix); // CAS服务端地址，会拼接为登录地址
        initParams.put("serverName", serverName); // 服务地址

        // 自定义忽略认证的路径或表达式，这里用来免登录访问【退出登录提示】页面
        initParams.put("ignorePattern", "/logoutPage");

        registration.setInitParameters(initParams);
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * 拦截所有请求，使用获取的票据向CAS服务端发起校验票据请求
     */
    @Bean
    @Order(2)
    public FilterRegistrationBean<Cas30ProxyReceivingTicketValidationFilter> cas30TicketValidationFilter() {
        FilterRegistrationBean<Cas30ProxyReceivingTicketValidationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new Cas30ProxyReceivingTicketValidationFilter());
        registration.setName("CAS30 Ticket Validation Filter");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("casServerUrlPrefix", casServerUrlPrefix); // CAS服务端地址，会拼接为服务校验地址
        initParams.put("serverName", serverName);
        initParams.put("redirectAfterValidation", "false"); // 禁用校验通过后去除ticket重定向回来，继续请求接口
        registration.setInitParameters(initParams);
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * 包装HttpServletRequest，使CAS登录成功的用户名等信息存入请求中<br>
     * <br>
     * 登录成功后以下两个方法将不再返回null: <br>
     * 
     * <pre>
     * HttpServletRequest#getUserPrincipal()
     * HttpServletRequest#getRemoteUser()
     * </pre>
     */
    @Bean
    @Order(3)
    public FilterRegistrationBean<HttpServletRequestWrapperFilter> httpServletRequestWrapperFilter() {
        FilterRegistrationBean<HttpServletRequestWrapperFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HttpServletRequestWrapperFilter());
        registration.setName("HttpServletRequest Wrapper Filter");
        registration.addUrlPatterns("/*");
        return registration;
    }

}
