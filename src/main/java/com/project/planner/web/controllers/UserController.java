package com.project.planner.web.controllers;

import com.project.planner.domain.User;
import com.project.planner.security.exception.InvalidPasswordException;
import com.project.planner.security.jwt.JwtTokenFilter;
import com.project.planner.security.jwt.JwtTokenProvider;
import com.project.planner.service.UserService;
import com.project.planner.web.dto.AuthenticationVMs;
import com.project.planner.web.dto.UserDto;
import com.project.planner.web.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class UserController {

    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(JwtTokenProvider tokenProvider,
                          AuthenticationManagerBuilder authenticationManagerBuilder,
                          UserService userService,
                          UserMapper userMapper) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto userDto) {
        if (!validatePassword(userDto.getPassword())) {
            throw new InvalidPasswordException();
        }

        userService.registerUser(userMapper.toEntity(userDto));
    }

    @RequestMapping("/authenticate")
    public ResponseEntity<AuthenticationVMs.JwtDto> authenticate(@RequestBody AuthenticationVMs.LoginDto login) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                login.getLogin(),
                login.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        boolean rememberMe = Objects.nonNull(login.getRememberMe()) ? login.getRememberMe() : false;

        AuthenticationVMs.JwtDto jwt = new AuthenticationVMs.JwtDto();
        jwt.setToken(tokenProvider.createToken(authentication, rememberMe));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtTokenFilter.AUTHORIZATION_HEADER, "Bearer " + jwt.getToken());
        return new ResponseEntity<>(jwt, httpHeaders, HttpStatus.OK);
    }

    private boolean validatePassword(String password) {
        return !StringUtils.isEmpty(password);
    }
}
