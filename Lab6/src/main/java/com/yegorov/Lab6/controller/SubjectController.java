package com.yegorov.Lab6.controller;

import com.yegorov.Lab6.entity.Subject;
import com.yegorov.Lab6.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> allSubjects() {
        List<Subject> allSubjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(allSubjects);
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<?> getSubject(@PathVariable("id") int id) {
        Subject subject = subjectService.getSubject(id);
        if (subject == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Дисциплина с id=" + id + " не найдена");
        }
        return ResponseEntity.ok(subject);
    }

    @PostMapping("/subjects")
    public ResponseEntity<?> saveSubject(@RequestBody Subject subject) {
        Subject saved = subjectService.saveSubject(subject);
        if (saved == null || saved.getId() == null) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не удалось создать дисциплину");
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/subjects")
    public ResponseEntity<?> updateSubject(@RequestBody Subject subject) {
        if (subject.getId() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Для обновления дисциплины нужно передать id");
        }

        Subject existing = subjectService.getSubject(subject.getId());
        if (existing == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Дисциплина с id=" + subject.getId() + " не найдена");
        }

        Subject updated = subjectService.saveSubject(subject);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable("id") int id) {
        boolean deleted = subjectService.deleteSubject(id);
        if (deleted) {
            return ResponseEntity.ok("Дисциплина с id=" + id + " успешно удалена");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Дисциплина с id=" + id + " не найдена");
        }
    }
}
