package com.project.planner.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@PropertySource(value = "classpath*:config/application.yml", ignoreResourceNotFound = true)
@Getter
@NoArgsConstructor
public class ApplicationProperties {
    private ApplicationProperties.Security security = new ApplicationProperties.Security();
    private ApplicationProperties.Cors cors = new ApplicationProperties.Cors();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Security {
        private ApplicationProperties.Security.Authentication authentication = new Authentication();

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Authentication {
            private String base64Secret = "aaa";
            private long tokenValidityInSeconds = 86400;
            private long tokenValidityInSecondsForRememberMe = 2592000;

        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Cors {
        private String allowedOrigins = "*";
        private String allowedMethods = "*";
        private String allowedHeaders = "*";
        private String exposedHeaders = "Authorization,Link,X-Total-Count";
        private boolean allowCredentials = true;
        private int maxAge = 1800;

    }
}
