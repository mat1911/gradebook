package com.app.repository.impl;

import com.app.entity.StudentGroup;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

public class StudentGroupRepository extends AbstractGenericRepository<StudentGroup, Long> implements CrudRepository<StudentGroup, Long>{
}
