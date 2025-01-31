package com.booleanuk.api.controller;

import com.booleanuk.api.model.Student;
import com.booleanuk.api.repository.StudentRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.response.StudentListResponse;
import com.booleanuk.api.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    private ErrorResponse errorResponse;
    private StudentResponse studentResponse;
    private StudentListResponse studentListResponse;

    @GetMapping
    public ResponseEntity<Response<?>> getAllStudents() {
        this.studentListResponse.set(this.studentRepository.findAll());
        return ResponseEntity.ok(studentListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createStudent(@RequestBody Student student) {
        if (student.getFirstName() == null || student.getLastName() == null || student.getDOB() == null) {
            this.errorResponse.set("Could not create a new student, please check all fields are correct");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        this.studentResponse.set(this.studentRepository.save(student));
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getStudentById(@PathVariable int id) {
        Student student = this.studentRepository.findById(id).orElse(null);
        if (student == null) {
            this.errorResponse.set("No student with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.studentResponse.set(student);
        return ResponseEntity.ok(studentResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Student studentToUpdate = this.studentRepository.findById(id).orElse(null);
        if (studentToUpdate == null) {
            this.errorResponse.set("No student with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (student.getFirstName() == null || student.getLastName() == null || student.getDOB() == null) {
            this.errorResponse.set("Could not update the specified customer, please check all fields are correct");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDOB(student.getDOB());
        this.studentResponse.set(this.studentRepository.save(studentToUpdate));
        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteStudentById(@PathVariable int id) {
        Student studentToDelete = this.studentRepository.findById(id).orElse(null);
        if (studentToDelete == null) {
            this.errorResponse.set("No student with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.studentRepository.delete(studentToDelete);
        this.studentResponse.set(studentToDelete);
        return ResponseEntity.ok(studentResponse);
    }
}
