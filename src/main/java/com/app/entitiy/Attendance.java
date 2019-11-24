package com.app.entitiy;


import lombok.*;

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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "student_id")
    private Student students;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
