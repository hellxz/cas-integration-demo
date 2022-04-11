package com.hellxz.cas;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class CasManualConfigApp {

    /**
     * 自定义全局单点登出地址，由cas服务端地址/logout?service=当前serviceName/logoutPage组成<br>
     * 当cas全局登出（带TGC访问cas的/logout接口）成功后，会重定向service参数地址<br>
     * 
     * <pre>
     * 需注意：service参数必须含登录时注册给CAS的serviceName，否则只废弃CAS会话而不会重定向
     * </pre>
     */
    @Value("${custom.cas.casSingleLogoutUrl:}")
    private String casSingleLogoutUrl;

    public static void main(String[] args) {
        SpringApplication.run(CasManualConfigApp.class, args);
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        return "服务B测试通过";
    }

    /**
     * 首页，需要登录
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
      //@formatter:off
        return "<h1>登录成功，您好 " + request.getRemoteUser() + "</h1><br><br>"
                + "<a href=\"/logout\">退出登录</a><br><br>"
                + "<a href=\"" + casSingleLogoutUrl + "\">全局退出登录</a>";
      //@formatter:on
    }

    /**
     * 登出提示页，免登录
     */
    @GetMapping("/logoutPage")
    public String logoutPage(HttpServletResponse response) {
        //@formatter:off
        return "<h1>您已退出登录成功。</h1><br><br>"
                + "<a href=\"/index\">去登录</a><br><br>"
                + "<a href=\"" + casSingleLogoutUrl + "\">全局退出登录</a>";
        //@formatter:on
    }

    /**
     * 退出登录，跳转登出提示页
     */
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 过期会话
            session.invalidate();
        }
        // 跳转登出提示页
        response.sendRedirect("/logoutPage");
    }
}
