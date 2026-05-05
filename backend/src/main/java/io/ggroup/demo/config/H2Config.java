package io.ggroup.demo.config;

import jakarta.servlet.Servlet;
import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2Config {

    @Bean
    public ServletRegistrationBean<Servlet> h2ConsoleServlet() {
        ServletRegistrationBean<Servlet> bean =
                new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2-console/*");
        bean.setLoadOnStartup(1);
        return bean;
    }
}