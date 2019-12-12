package com.app.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "teachers")
@ToString(includeFieldNames = false)
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;
    private String name;
    private String surname;

    @Column(unique = true, nullable = false)
    private String email;

    @ToString.Exclude
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Lesson> lessons = new HashSet<>();

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Mark> marks = new HashSet<>();

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Remark> remarks = new HashSet<>();
}
