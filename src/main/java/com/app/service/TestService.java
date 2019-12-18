package com.app.service;

import com.app.entity.Test;
import com.app.repository.impl.TestRepository;

public class TestService {
    private TestRepository testRepository = new TestRepository();

    public void delete(Test test) {
        testRepository.delete(test.getId());
    }


    public void addOrUpdate(Test test) {
        if(testRepository.findOne(test.getId()).isPresent())
            testRepository.update(test);
        else testRepository.add(test);
    }
}
