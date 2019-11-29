package com.app.app;

import com.app.entities.AdminUser;
import com.app.repository.impl.AdminUserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class DBTest {

    public static void populateDB() {
        AdminUser adminUser = new AdminUser();
        AdminUserRepository adminUserRepository = new AdminUserRepository();
        adminUser.setEmail("admin@gradebook.com");
        adminUser.setPassword(BCrypt.hashpw("admin", BCrypt.gensalt()));
        adminUserRepository.add(adminUser);
    }
}
