package com.app.repository.impl;

import com.app.entity.Student;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

public class StudentRepository extends AbstractGenericRepository<Student, Long> implements CrudRepository<Student, Long> {
}
