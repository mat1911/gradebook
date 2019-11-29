package com.app.repository.impl;

import com.app.entities.Test;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

public class TestRepository extends AbstractGenericRepository<Test, Long> implements CrudRepository<Test, Long> {
}
