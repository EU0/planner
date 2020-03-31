package com.project.planner.domain;

import com.project.planner.constants.RegExpConstants;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
@Table(name = "user", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class User extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NonNull
    @Size(min = 5, max = 50)
    @Pattern(regexp = RegExpConstants.LOGIN_REGEX)
    @Column(name = "login", unique = true, length = 50)
    private String login;

    @NonNull
    @Column(name = "password_hash", length = 60)
    private String password;

    @Column(name = "first_name", length = 60)
    @Size(max = 60)
    private String firstName;

    @Column(name = "last_name", length = 60)
    @Size(max = 60)
    private String lastName;

    @NonNull
    @Pattern(regexp = RegExpConstants.EMAIL_REGEX)
    @Column(name = "email", length = 191)
    @Size(max = 191)
    private String email;

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private Set<Authority> authoritySet;

}


