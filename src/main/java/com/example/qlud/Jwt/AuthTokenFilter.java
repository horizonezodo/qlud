//
//package com.example.qlud.Jwt;
//
//import com.example.qlud.Wrapper.CachedBodyHttpServletRequest;
//import com.example.qlud.Wrapper.CachedBodyHttpServletResponse;
//import com.example.qlud.service.RequestLogService;
//import com.example.qlud.service.UserDetailServiceImpl;
//import io.jsonwebtoken.JwtException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.time.Instant;
//import java.util.stream.Collectors;
//
//public class AuthTokenFilter extends OncePerRequestFilter {
//
//    @Autowired
//    JwtUntil until;
//
//    @Autowired
//    UserDetailServiceImpl userDetailService;
//
//    @Autowired
//    RequestLogService service;
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
//
//    private String parseJwt(HttpServletRequest request) {
//        String headerAuth = request.getHeader("Authorization");
//        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//            return headerAuth.substring(7, headerAuth.length());
//        }
//        return null;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        Instant start = Instant.now();
//        String requestBody = "";
//        CachedBodyHttpServletResponse cachedResponse = new CachedBodyHttpServletResponse(response);
//        try {
//            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
//            requestBody = new BufferedReader(cachedBodyHttpServletRequest.getReader())
//                    .lines().collect(Collectors.joining(System.lineSeparator()));
//
//            String jwt = parseJwt(cachedBodyHttpServletRequest);
//            if (jwt == null) {
//                filterChain.doFilter(cachedBodyHttpServletRequest, cachedResponse);
//                return;
//            } else if (until.validateJwtToken(jwt)) {
//                String info = until.getInfoFromJwtToken(jwt);
//
//                UserDetails userDetails = userDetailService.loadUserByUsername(info);
//
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(cachedBodyHttpServletRequest));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                filterChain.doFilter(cachedBodyHttpServletRequest, cachedResponse);
//            }
//        } catch (JwtException e) {
//            logger.error("JWT exception: {}", e.getMessage());
//            cachedResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            cachedResponse.getWriter().write("Unauthorized: Invalid token");
//        } catch (UsernameNotFoundException e) {
//            logger.error("User not found: {}", e.getMessage());
//            cachedResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            cachedResponse.getWriter().write("Unauthorized: User not found");
//        } catch (Exception e) {
//            logger.error("Error processing authentication: {}", e.getMessage());
//            cachedResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            cachedResponse.getWriter().write("Internal server error");
//        } finally {
//            Instant end = Instant.now();
//            long duration = java.time.Duration.between(start, end).toMillis();
//            byte[] content = cachedResponse.getContent();
//            String responseBody = new String(content, StandardCharsets.UTF_8);
//
//            // Kiểm tra Content-Type để ghi log
//            String contentType = cachedResponse.getContentType();
//            if (contentType != null && contentType.equalsIgnoreCase("application/json")) {
////                logger.info("Request URL: {}", request.getRequestURL());
////                logger.info("Request Body: {}", requestBody);
////                logger.info("Response Status: {}", cachedResponse.getStatus());
////                logger.info("Start Time: {}", start);
////                logger.info("End Time: {}", end);
////                logger.info("Duration (ms): {}", duration);
////                logger.info("Response Body: {}", responseBody);
//
//                service.saveLog(request.getRequestURL().toString(),request.getMethod(),requestBody,responseBody,response.getStatus(),start,end,duration);
//            }
//
//            // Copy the cached response back to the original response
//            response.getWriter().write(responseBody);
//        }
//    }
//
//
//
//}


package com.example.qlud.Jwt;

import com.example.qlud.model.CustomLog;
import com.example.qlud.service.UserDetailServiceImpl;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtUntil until;

    @Autowired
    CustomLog logs;

    private static final Logger logger2 = LoggerFactory.getLogger(CustomLog.class);

    @Autowired
    UserDetailServiceImpl userDetailService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if(jwt == null){
                filterChain.doFilter(request, response);
                return;
            }
            else if (until.validateJwtToken(jwt)) {
                String info = until.getInfoFromJwtToken(jwt);

                UserDetails userDetails = userDetailService.loadUserByUsername(info);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
        } catch (JwtException e) {
            logger.error("JWT exception: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid token");
        } catch (UsernameNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: User not found");
        } catch (Exception e) {
            logger.error("Error processing authentication: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal server error");
        }
    }
}