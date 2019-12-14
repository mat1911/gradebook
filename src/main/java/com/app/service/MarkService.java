package com.app.service;

import com.app.entity.Mark;
import com.app.entity.Student;
import com.app.entity.Subject;
import com.app.repository.impl.MarkRepository;

import java.util.Collections;
import java.util.List;

public class MarkService {
    private MarkRepository markRepository = new MarkRepository();

    public List<Mark> findByStudentAndSubject(Student selectedStudent, Subject selectedSubject) {
        List<Mark> marks = markRepository.findByStudentAndSubject(selectedStudent, selectedSubject);
        return marks.isEmpty() ? Collections.emptyList() : marks;
    }
}
