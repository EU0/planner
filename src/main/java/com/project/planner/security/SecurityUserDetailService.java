package com.project.planner.security;

import com.project.planner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SecurityUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public SecurityUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findUserByLoginIgnoreCase(login)
                .map(this::createSecureUser)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Пользователь с логином: %s не найден", login)));
    }

    private User createSecureUser(com.project.planner.domain.User user) {
        List<GrantedAuthority> grantedAuthorityList = user.getAuthoritySet()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new User(
                user.getLogin(),
                user.getPassword(),
                grantedAuthorityList
        );
    }

    public Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(Authentication::getPrincipal)
                .map(o -> {
                    if (o instanceof UserDetails) {
                        return ((UserDetails) o).getUsername();
                    } else if (o instanceof String) {
                        return (String) o;
                    }
                    return null;
                });
    }
}
