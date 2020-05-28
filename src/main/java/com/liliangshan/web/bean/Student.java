package com.liliangshan.web.bean;

import java.util.Map;

/************************************
 * Student
 * @author liliangshan
 * @date 2020/5/26
 ************************************/
public class Student {

    private Integer id;
    private String name;
    private String university;
    private Map<String, Object> description;
    private int age;

    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Map<String, Object> getDescription() {
        return description;
    }

    public void setDescription(Map<String, Object> description) {
        this.description = description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", university='" + university + '\'' +
                ", description=" + description +
                ", age=" + age +
                '}';
    }
}
