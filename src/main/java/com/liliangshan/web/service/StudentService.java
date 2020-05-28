package com.liliangshan.web.service;

import com.liliangshan.web.annotation.Service;
import com.liliangshan.web.bean.Student;

import java.util.*;

/************************************
 * HelloService
 * @author liliangshan
 * @date 2020/5/25
 ************************************/
@Service
public class StudentService {

    private static List<Student> students = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setId(i);
            student.setName("名字-" + i);
            student.setAge(20 + i);
            student.setUniversity("大学--" + i);
            Map<String, Object> description = new HashMap<>();
            description.put("key" + i, "description-" + i);
            student.setDescription(description);
            students.add(student);
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public Student getItem(Integer id) {
        return students.get(id);
    }

    public Student createItem(Student student) {
        Student maxStudent = students.stream().max(Comparator.comparing(Student::getId)).orElse(null);
        int id = 0;
        if (maxStudent != null) {
            id = maxStudent.getId() + 1;
        }
        student.setId(id);
        students.add(student);
        return student;
    }

    public Student updateItem(Integer id, Student student) {
        student.setId(id);
        students.add(id, student);
        return this.getItem(id);
    }

}
