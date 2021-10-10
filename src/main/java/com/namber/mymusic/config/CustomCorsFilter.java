package com.namber.mymusic.config;

import org.springframework.web.cors.*;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomCorsFilter extends CorsFilter {
    private CorsConfigurationSource localConfigSource;
    private CorsProcessor localProcessor = new DefaultCorsProcessor();

    public CustomCorsFilter(CorsConfigurationSource configSource) {
        super(configSource);
        this.localConfigSource = configSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CorsConfiguration corsConfiguration = this.localConfigSource.getCorsConfiguration(request);
        boolean isValid = this.localProcessor.processRequest(corsConfiguration, request, response);
        if (isValid && !CorsUtils.isPreFlightRequest(request)){
            filterChain.doFilter(request, response);
        }else{
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
