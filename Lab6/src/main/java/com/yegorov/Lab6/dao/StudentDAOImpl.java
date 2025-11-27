package com.yegorov.Lab6.dao;

import com.yegorov.Lab6.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Student> getAllStudents() {
        Query query = entityManager.createQuery("from Student");
        return query.getResultList();
    }

    @Override
    public Student saveStudent(Student student) {
        return entityManager.merge(student);
    }

    @Override
    public Student getStudent(int id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public boolean deleteStudent(int id) {
        Query query = entityManager.createQuery(
                "delete from Student where id = :studentId"
        );
        int deletedCount = query.setParameter("studentId", id)
                .executeUpdate();
        return deletedCount > 0;
    }
}
