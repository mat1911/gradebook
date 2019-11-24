package com.app.repository.impl;

import com.app.entitiy.Attendance;
import com.app.repository.generic.AbstractGenericRepository;
import com.app.repository.generic.CrudRepository;

public class AttendanceRepository extends AbstractGenericRepository<Attendance, Long> implements CrudRepository<Attendance, Long> {
}
