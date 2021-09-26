package com.zarzmaacademy.student;


import com.zarzmaacademy.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class StudentController {

	private final StudentRepository studentRepository;

	@GetMapping
	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	@PostMapping
	public Student addStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}
}
