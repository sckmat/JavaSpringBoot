package com.yegorov.Lab6.dao;

import com.yegorov.Lab6.entity.Subject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectDAO {

    List<Subject> getAllSubjects();

    Subject saveSubject(Subject subject);

    Subject getSubject(int id);

    boolean deleteSubject(int id);
}
