package com.project.planner.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authority", schema = "public")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NonNull
    @Size(max = 60)
    @Column(name = "name", length = 60)
    private String name;

    @ManyToMany(mappedBy = "authoritySet", fetch = FetchType.LAZY)
    private List<User> userList;
}
