package com.app.service.impl;

import com.app.entity.Student;
import com.app.entity.StudentGroup;
import com.app.repository.generic.CrudRepository;
import com.app.repository.impl.StudentRepository;
import com.app.service.generic.AbstractCrudService;
import com.app.service.generic.CrudService;
import com.app.validator.generic.Validator;
import com.app.validator.impl.StudentValidator;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.util.Map;

public class StudentsService extends AbstractCrudService<Student> implements CrudService<Student> {
    public StudentsService(Validator<Student> validator, CrudRepository<Student, Long> crudRepository) {
        super(validator, crudRepository);
    }

    public Student addStudentsToDatabase(ObservableList<TextField> fieldsWithObjectData, StudentGroup studentGroup){

        Student student = Student.builder()
                .name(fieldsWithObjectData.get(0).getText())
                .surname(fieldsWithObjectData.get(1).getText())
                .group(studentGroup)
                .build();

        StudentValidator studentValidator = new StudentValidator();

        Map<String, String> errors = studentValidator.validate(student);

        errors.entrySet()
                .stream()
                .filter(err -> !err.getKey().equals("id"))
                .findFirst()
                .ifPresent(err -> {
                    throw new IllegalArgumentException("Some arguments are not valid!");
                });

        StudentRepository studentRepository = new StudentRepository();

        return studentRepository.add(student)
                .orElseThrow(() -> new IllegalStateException("Student is not added to database!"));

    }
}
