package com.app.utility;

import com.app.entity.Admin;
import com.app.entity.Student;
import com.app.entity.StudentGroup;
import com.app.entity.Teacher;
import com.app.repository.impl.AdminRepository;
import com.app.repository.impl.StudentGroupRepository;
import com.app.repository.impl.StudentRepository;
import com.app.repository.impl.TeacherRepository;
import org.mindrot.jbcrypt.BCrypt;

public class DBTest {
    private static AdminRepository adminRepository = new AdminRepository();
    private static TeacherRepository teacherRepository = new TeacherRepository();
    private static StudentGroupRepository studentGroupRepository = new StudentGroupRepository();
    private static StudentRepository studentRepository = new StudentRepository();

    public static void populateDB() {
        Admin admin = Admin.builder()
                    .email("admin@gradebook.com")
                    .password(BCrypt.hashpw("admin", BCrypt.gensalt()))
                    .build();
        adminRepository.add(admin);

        Teacher teacher = Teacher.builder()
                        .name("Szymon")
                        .surname("Pakulski")
                        .email("teacher@gradebook.com")
                        .password(BCrypt.hashpw("teacher", BCrypt.gensalt()))
                        .build();
        teacherRepository.add(teacher);

        StudentGroup studentGroup = StudentGroup.builder()
                                    .name("I7B4S1")
                                    .build();
        studentGroupRepository.add(studentGroup);
        Student student = Student.builder()
                .name("Szympek")
                .surname("Pakelski")
                .group(studentGroup)
                .build();
        studentRepository.add(student);

        studentGroup = StudentGroup.builder()
                    .name("I7B5S1")
                    .build();
        studentGroupRepository.add(studentGroup);
        student = Student.builder()
                .name("Szampen")
                .surname("Pakolens")
                .group(studentGroup)
                .build();
        studentRepository.add(student);

    }
}
