package com.app.service.impl;

import com.app.entity.Attendance;
import com.app.entity.Lesson;
import com.app.entity.Student;
import com.app.repository.impl.AttendanceRepository;

import java.util.Optional;

public class AttendanceService {
    private AttendanceRepository attendanceRepository = new AttendanceRepository();

    public void setPresence(Student student, Lesson selectedLesson, Boolean new_val) {
        Optional<Attendance> att = attendanceRepository.findAll().stream()
                .filter(at -> at.getLesson().getId().equals(selectedLesson.getId()) && at.getStudent().getId().equals(student.getId()))
                .findFirst();
        att.ifPresentOrElse(attendance -> {
            attendance.setPresence(new_val);
            attendanceRepository.update(attendance);
        }, () -> {
            Attendance attendance = Attendance.builder()
                    .lesson(selectedLesson)
                    .presence(new_val)
                    .student(student)
                    .build();
            attendanceRepository.add(attendance);
        });
    }

    public Boolean isPresent(Student student, Lesson selectedLesson) {
        Optional<Attendance> attendance =  attendanceRepository.findAll().stream()
                .filter(att -> att.getLesson().getId().equals(selectedLesson.getId()) && att.getStudent().getId().equals(student.getId()))
                .findFirst();
        if(attendance.isPresent())
            return attendance.get().getPresence();
        else return false;
    }
}
