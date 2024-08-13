package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate template;

    @Test
    void testGetStudent() throws Exception {
        Student student = new Student(null, "test_student", 12);
        ResponseEntity<Student> postResponse = template.postForEntity( "http://localhost:" + port + "/student", student, Student.class);
        Student addedStudent = postResponse.getBody();

        var result = template.getForObject("http://localhost:" + port + "/student?id=" + addedStudent.getId(), Student.class);
        assertThat(result.getName()).isEqualTo("test_student");
        assertThat(result.getAge()).isEqualTo(12);

       template.delete("http://localhost:" + port +"/student?id=" + addedStudent.getId());

        var responseEntity = template.getForEntity("http://localhost:" + port + "/student?id=" + addedStudent.getId(), Student.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void testUpdate() {
        Student student = new Student(null, "test_student", 12);
        ResponseEntity<Student> postResponse = template.postForEntity( "http://localhost:" + port + "/student", student, Student.class);
        Student addedStudent = postResponse.getBody();

        addedStudent.setName("updated_student_1");
        addedStudent.setAge(10);

        template.put("/student?id=" + addedStudent.getId(), addedStudent);
        var resolt = template.getForObject("http://localhost:" + port + "/student?id=" + addedStudent.getId(), Student.class);
        assertThat(resolt.getName()).isEqualTo("updated_student_1");
        assertThat(resolt.getAge()).isEqualTo(10);

    }
    @Test
    void testFilter() {
        var s1 = template.postForEntity("/student", new Student(null, "test_name1", 17), Student.class).getBody();
        var s2 = template.postForEntity("/student", new Student(null, "test_name2", 12), Student.class).getBody();
        var s3 = template.postForEntity("/student", new Student(null, "test_name3", 14), Student.class).getBody();
        var s4 = template.postForEntity("/student", new Student(null, "test_name4", 15), Student.class).getBody();

        var Student = template.getForObject("/student/byAgeBetween?name=test_name1&age=age", Student[].class);
        assertThat(Student.length).isEqualTo(1);
        assertThat(Student).containsExactlyInAnyOrder(s1, s2);

    }



}