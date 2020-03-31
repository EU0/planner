package com.project.planner.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityAuditorAware implements AuditorAware<String> {

    private SecurityUserDetailService userDetailService;

    @Autowired
    public SecurityAuditorAware(SecurityUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(userDetailService.getCurrentUserLogin().orElse("SYSTEM"));
    }
}
