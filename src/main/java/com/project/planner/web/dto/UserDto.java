package com.project.planner.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private boolean activated;
    private Set<String> authoritySet;

}
