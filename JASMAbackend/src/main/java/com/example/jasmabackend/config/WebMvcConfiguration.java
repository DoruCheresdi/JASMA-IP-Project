package com.example.jasmabackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("user-photos", registry);
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

        registry.addResourceHandler("/" + dirName + "/**")
            .addResourceLocations("file:/"+ uploadPath + "/");
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        List<String> urlPatterns = List.of(
//            "/login",
//            "/register",
//            "/feed",
//            "/profile"
//        );
//
//        // forward to frontend app at "/":
//        urlPatterns.forEach(pattern -> registry.addViewController(pattern).setViewName("forward:/"));
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    }
}
