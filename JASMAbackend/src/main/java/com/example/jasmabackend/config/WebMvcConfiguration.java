package com.example.jasmabackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        List<String> urlPatterns = List.of(
            "/login",
            "/register",
            "/feed",
            "/profile"
        );

        // forward to frontend app at "/":
        urlPatterns.forEach(pattern -> registry.addViewController(pattern).setViewName("forward:/"));
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
