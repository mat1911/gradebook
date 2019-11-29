package com.app.repository.impl;

import com.app.entities.Subject;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

public class SubjectRepository extends AbstractGenericRepository<Subject, Long> implements CrudRepository<Subject, Long> {
}
