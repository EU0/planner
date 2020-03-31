package com.project.planner.web.mappers;


import com.project.planner.domain.Authority;
import com.project.planner.domain.User;
import com.project.planner.repository.AuthorityRepository;
import com.project.planner.web.dto.UserDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Mappings({
            @Mapping(target = "authoritySet", ignore = true),
    })
    public abstract User toEntity(UserDto userDto);


    @Mappings({
            @Mapping(target = "authoritySet", ignore = true)
    })
    public abstract UserDto toDto(User user);

    @AfterMapping
    void convertToEntityAuthoritySet(@MappingTarget User.UserBuilder target, UserDto source) {
        Set<Authority> authoritySet = new HashSet<>();
        if (Objects.nonNull(source.getAuthoritySet())){
            source.getAuthoritySet()
                    .stream()
                    .map(authorityRepository::findByName)
                    .forEach(authority -> authority.ifPresent(authoritySet::add));
        }
        target.authoritySet(authoritySet);
    }


    @AfterMapping
    void convertToDtoAuthoritySet(@MappingTarget UserDto target, User source) {
        Set<String> authoritySet = source.getAuthoritySet()
                .stream()
                .map(Authority::getName)
                .collect(Collectors.toSet());
        target.setAuthoritySet(authoritySet);
    }


}
