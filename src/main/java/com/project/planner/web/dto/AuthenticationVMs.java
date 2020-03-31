package com.project.planner.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;


public final class AuthenticationVMs {

    @Data
    public static class LoginDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private String login;
        private String password;
        private Boolean rememberMe;
    }

    @Data
    public static class JwtDto implements Serializable {
        private static final long serialVersionUID = 1L;

        @JsonProperty("token")
        private String token;
    }

}
