package com.app.service;

import com.app.entity.Admin;
import com.app.entity.Teacher;
import com.app.exception.ObjectNotFoundException;
import com.app.repository.impl.AdminRepository;
import com.app.repository.impl.TeacherRepository;
import com.app.enums.LoginType;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {
    private AdminRepository adminRepository = new AdminRepository();
    private TeacherRepository teacherRepository = new TeacherRepository();

    public LoginType tryLogin(String login, String password) throws ObjectNotFoundException {

        if(adminRepository.findByEmail(login).isPresent()) {
            Admin admin = adminRepository.findByEmail(login).get();
            if (BCrypt.checkpw(password, admin.getPassword()))
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
}