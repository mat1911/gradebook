package com.app.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean presence;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id")
    private Student students;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
