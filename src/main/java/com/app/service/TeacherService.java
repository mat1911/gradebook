package com.app.service;

import com.app.entity.Teacher;
import com.app.repository.impl.TeacherRepository;
import com.app.validator.TeacherValidator;

import java.util.Map;


public class TeacherService {

    private TeacherRepository teacherRepository = new TeacherRepository();
    private TeacherValidator teacherValidator = new TeacherValidator(teacherRepository);

    public Teacher addTeacherToDatabase(String name, String surname, String email){

        Teacher teacher = Teacher.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .build();

        Map<String, String> errors =  teacherValidator.validate(teacher);

        errors.entrySet()
                .stream()
                .filter(err -> !err.getKey().equals("id"))
                .findFirst()
                .ifPresent(err -> {
                    throw new IllegalArgumentException("Some arguments are not valid!");
                });

        Teacher result = teacherRepository.add(teacher).orElseThrow(() -> new IllegalStateException("Teacher is not added to database!"));

        return result;
    }

}
