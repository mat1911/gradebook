package com.app.utility;

import com.app.entity.Admin;
import com.app.entity.Teacher;
import com.app.repository.impl.AdminRepository;
import com.app.repository.impl.TeacherRepository;
import org.mindrot.jbcrypt.BCrypt;

public class DBTest {
    private static AdminRepository adminRepository = new AdminRepository();
    private static TeacherRepository teacherRepository = new TeacherRepository();

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
    }
}
