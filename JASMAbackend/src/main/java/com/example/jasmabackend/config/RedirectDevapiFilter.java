package com.example.jasmabackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Order(1)
//@Component
//public class RedirectDevapiFilter extends OncePerRequestFilter {
//
////    @Autowired
////    ServletContext servletContext;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//        throws ServletException,
//        IOException {
//        System.out.println("Doing filter");
//
//        ServletContext servletContext = request.getSession().getServletContext();
//
//        servletContext.getRequestDispatcher("/").forward(request, response);
////        servletContext.
////        response.sendRedirect ("/");
//
////        filterChain.doFilter(request, response);
//    }
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getRequestURI();
//        System.out.println("Checking for redirect in filter: " + path);
//        return path.equals("/") || path.equals("/error")
//            || path.startsWith("/devapi")
//            || path.endsWith(".html") || path.endsWith(".js") || path.endsWith(".css") || path.endsWith(".ico");
//    }
//}
