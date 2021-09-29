package com.zarzmaacademy.student;


import com.zarzmaacademy.model.Student;
import javassist.NotFoundException;
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
		return studentRepository.findByActive(true);
	}

	@PostMapping
	public Student addStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}

	@PutMapping("{id}")
	public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
		student.setId(id);
		return studentRepository.save(student);
	}

	@DeleteMapping("{id}")
	public void deleteStudent(@PathVariable Long id) throws NotFoundException {
		Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException("student not found "));
		student.setActive(false);
		studentRepository.save(student);
	}

}
