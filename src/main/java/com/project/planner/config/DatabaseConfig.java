package com.project.planner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.project.planner.repository")
@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfig {
}
