package com.qa.springSDET_Project.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.springSDET_Project.dto.ClassRoomDTO;
import com.qa.springSDET_Project.dto.StudentDTO;
import com.qa.springSDET_Project.persistence.domain.Student;
import com.qa.springSDET_Project.persistence.repository.Student_repo;
import com.qa.springSDET_Project.service.StudentService;

@RestController
@CrossOrigin
@RequestMapping("/Student")
public class StudentController {

	private StudentService service;

	@Autowired
	public StudentController(StudentService service) {
		super();
		this.service = service;
	}

	@PostMapping("/Create")
	public ResponseEntity<StudentDTO> create(@RequestBody Student student) {
//		StudentDTO created= this.service.newClass(student);
		return new ResponseEntity<StudentDTO>(this.service.newStudent(student), HttpStatus.CREATED);
	}

	// Read
	@GetMapping("/readAll")
	public ResponseEntity<List<StudentDTO>> getAllClassRooms() {
		return ResponseEntity.ok(this.service.read());
	}

	// Read one
	@GetMapping("/read/{id}")
	public ResponseEntity<StudentDTO> findByID(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.read(id));
	}

	// Update
	@PutMapping("update/{id}")
	public ResponseEntity<StudentDTO> updateByID(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
		StudentDTO updated = this.service.update(studentDTO, id);
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}

	// Delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<StudentDTO> deleteByID(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
