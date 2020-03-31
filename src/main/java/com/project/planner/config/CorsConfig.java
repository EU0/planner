package com.project.planner.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class CorsConfig {

    private ApplicationProperties applicationProperties;

    @Autowired
    public CorsConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader(applicationProperties.getCors().getAllowedHeaders());
        Arrays.stream(applicationProperties.getCors().getAllowedMethods().split(","))
                .forEach(corsConfiguration::addAllowedMethod);
        corsConfiguration.addAllowedOrigin(applicationProperties.getCors().getAllowedOrigins());
        if (Objects.nonNull(corsConfiguration.getAllowedOrigins())
                && !corsConfiguration.getAllowedOrigins().isEmpty()) {
            configurationSource.registerCorsConfiguration( "api/**", corsConfiguration);
            configurationSource.registerCorsConfiguration("management/**", corsConfiguration);
        }


        return new CorsFilter(configurationSource);
    }
}
