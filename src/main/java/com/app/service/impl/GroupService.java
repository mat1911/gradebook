package com.app.service.impl;

import com.app.entity.StudentGroup;
import com.app.repository.generic.CrudRepository;
import com.app.service.generic.AbstractCrudService;
import com.app.service.generic.CrudService;
import com.app.validator.generic.Validator;

public class GroupService extends AbstractCrudService<StudentGroup> implements CrudService<StudentGroup> {

    public GroupService(Validator<StudentGroup> validator, CrudRepository<StudentGroup, Long> crudRepository) {
        super(validator, crudRepository);
    }
}
