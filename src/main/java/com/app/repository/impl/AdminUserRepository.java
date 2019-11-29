package com.app.repository.impl;

import com.app.entities.AdminUser;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

public class AdminUserRepository extends AbstractGenericRepository<AdminUser, Long> implements CrudRepository<AdminUser, Long> {

}
