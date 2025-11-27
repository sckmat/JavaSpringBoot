package com.yegorov.Lab6.service;

import com.yegorov.Lab6.entity.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubjectService {

    List<Subject> getAllSubjects();

    Subject saveSubject(Subject subject);

    Subject getSubject(int id);

    boolean deleteSubject(int id);
}
