package com.app.entity;

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
    private Set<com.app.entity.Test> tests = new HashSet<>();

    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<com.app.entity.Remark> remark = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    private com.app.entity.StudentGroup group;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

/*    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;*/
}
