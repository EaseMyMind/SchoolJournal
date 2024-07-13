package com.example.journal;

import java.sql.Array;
import java.util.Arrays;

public class Student {
    int id;
    String name;
    int passport;
    int[] grades;

    public Student(String name, int passport, int[] grades) {
        this.name = name;
        this.passport = passport;
        this.grades = grades;
    }
    public Student(int id, String name, int passport, int[] grades) {
        this.id = id;
        this.name = name;
        this.passport = passport;
        this.grades = grades;
    }
    public Student(){

    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassport() {
        return passport;
    }

    public void setPassport(int passport) {
        this.passport = passport;
    }

    public int[] getGrades() {
        return grades;
    }

    public void setGrades(int[] grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passport=" + passport +
                ", grades=" + Arrays.toString(grades) +
                '}';
    }
}