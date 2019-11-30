package com.app.app;

import com.app.entity.AdminUser;
import com.app.entity.Teacher;
import com.app.repository.impl.AdminUserRepository;
import com.app.repository.impl.TeacherRepository;
import org.mindrot.jbcrypt.BCrypt;

public class DBTest {
    private static AdminUserRepository adminUserRepository = new AdminUserRepository();
    private static TeacherRepository teacherRepository = new TeacherRepository();

    public static void populateDB() {
        AdminUser adminUser = AdminUser.builder()
                            .email("admin@gradebook.com")
                            .password(BCrypt.hashpw("admin", BCrypt.gensalt()))
                            .build();
        adminUserRepository.add(adminUser);

        Teacher teacher = Teacher.builder()
                        .name("Szymon")
                        .surname("Pakulski")
                        .email("teacher@gradebook.com")
                        .password(BCrypt.hashpw("teacher", BCrypt.gensalt()))
                        .build();
        teacherRepository.add(teacher);
    }
}
