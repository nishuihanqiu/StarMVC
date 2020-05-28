package com.liliangshan.web.controller;

import com.liliangshan.web.annotation.*;
import com.liliangshan.web.bean.Student;
import com.liliangshan.web.core.Result;
import com.liliangshan.web.mvc.WebContext;
import com.liliangshan.web.mvc.bean.View;
import com.liliangshan.web.service.StudentService;
import com.liliangshan.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/************************************
 * HelloController
 * @author liliangshan
 * @date 2020/5/25
 ************************************/
@Controller
@Request("/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @GET
    public Result getStudents() {
        List<Student> students = studentService.getStudents();
        return new Result(students);
    }

    @GET("/item")
    public Result getStudent(@Query("id") Integer id) {
        logger.info("GET --> id:{}", id);
        Student student = studentService.getItem(id);
        logger.info("student:{}", student);
        return new Result(student);
    }

    @PUT("/item")
    public Result updateStudent(@Query("id") Integer id, @Body Student student) {
        logger.info("PUT -> id: {}", id);
        logger.info("update -> student: {}", student);
        return new Result(studentService.updateItem(id, student));
    }

    @GET("/html")
    public View greeting(@Query("name") String name, @Query("age") Integer age) {
        logger.info("current path:{}", WebUtils.getRequestPath(WebContext.getCurrentContext().getRequest()));
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("age", age);
        View view = new View("hello");
        view.addValues(data);
        return view;
    }


}
