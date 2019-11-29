package com.app.repository.impl;

import com.app.entities.Teacher;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

public class TeacherRepository extends AbstractGenericRepository<Teacher, Long> implements CrudRepository<Teacher, Long> {
}
