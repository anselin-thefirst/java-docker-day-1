package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String DOB;
    @Column
    private String courseTitle;
    @Column
    private String courseStartDate;
    @Column
    private String avgGrade;

    public Student(String firstName, String lastName, String DOB, String courseTitle, String courseStartDate, String avgGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.avgGrade = avgGrade;
    }

    public Student(int id) {
        this.id = id;
    }
}
