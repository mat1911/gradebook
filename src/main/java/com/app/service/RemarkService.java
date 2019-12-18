package com.app.service;

import com.app.entity.Remark;
import com.app.entity.Student;
import com.app.repository.impl.RemarkRepository;

import java.util.Collections;
import java.util.List;

public class RemarkService {
    RemarkRepository remarkRepository = new RemarkRepository();

    public List<Remark> findByStudent(Student selectedStudent) {
        List<Remark> remarks = remarkRepository.findByStudent(selectedStudent);
        return remarks.isEmpty() ? Collections.emptyList() : remarks;
    }

    public void delete(Remark mark) {
        remarkRepository.delete(mark.getId());
    }

    public void add(Remark mark) {
        remarkRepository.add(mark);
    }

    public void update(Remark mark) {
        remarkRepository.update(mark);
    }
}
