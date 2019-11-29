package com.app.repository.impl;

import com.app.entities.Mark;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

public class MarkRepository extends AbstractGenericRepository<Mark, Long> implements CrudRepository<Mark, Long> {
}
