package com.app.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Long lessonNumber;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<com.app.entities.Test> tests = new HashSet<>();

    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<com.app.entities.Remark> remark = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "group_id")
    private com.app.entities.StudentGroup group;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

/*    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;*/
}
