package com.app.service;

import com.app.entities.AdminUser;

import com.app.entity.Teacher;
import com.app.exception.ObjectNotFoundException;
import com.app.repository.impl.AdminUserRepository;
import com.app.repository.impl.TeacherRepository;
import org.mindrot.jbcrypt.BCrypt;


//Login -> gets info from adminUsers and teachers
public class LoginService {
    private AdminUserRepository adminUserRepository = new AdminUserRepository();
    private TeacherRepository teacherRepository = new TeacherRepository();

    public LoginType tryLogin(String login, String password) throws ObjectNotFoundException {

        if(adminUserRepository.findByEmail(login).isPresent()) {
            AdminUser adminUser = adminUserRepository.findByEmail(login).get();
            if (BCrypt.checkpw(password, adminUser.getPassword()))
                return LoginType.ADMIN;
            else
                throw new IllegalArgumentException("Wrong password!");
        }
        else if(teacherRepository.findByEmail(login).isPresent()) {
            Teacher teacher = teacherRepository.findByEmail(login).get();
            if (BCrypt.checkpw(password, teacher.getPassword()))
                return LoginType.TEACHER;
            else
                throw new IllegalArgumentException("Wrong password!");

        }
        throw new ObjectNotFoundException("No user with such login!");
    }

    public enum LoginType {
        ADMIN, TEACHER
    }
}