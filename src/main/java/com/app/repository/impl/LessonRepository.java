package com.app.repository.impl;

import com.app.entitiy.Lesson;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

public class LessonRepository extends AbstractGenericRepository<Lesson, Long> implements CrudRepository<Lesson, Long> {
}
