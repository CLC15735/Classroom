package com.qa.springSDET_Project.persistence.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.springSDET_Project.dto.StudentDTO;
import com.qa.springSDET_Project.persistence.domain.Student;
import com.qa.springSDET_Project.rest.StudentController;
import com.qa.springSDET_Project.service.StudentService;

@SpringBootTest
public class StudentControllerUnitTest {
	
	@Autowired
	private StudentController controller;
	
	@MockBean
	private StudentService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private StudentDTO mapToDTO (Student student) {
		return this.modelMapper.map(student, StudentDTO.class);
	}
	
	private List<Student> studentList;
	private Student testStudent;
	private Student testStudentWithId;
	private StudentDTO studentDTO;
	
	private final Long id=1L;
	private final String firstName = "Mark";
	private final String secondName = "Johnson";
	private final Integer age = 11;
	private final Integer yearGroup = 6;
	
	@BeforeEach
	void init() {
		//Creating a new empty list that will store the students
		this.studentList = new ArrayList<>();
		//Populating testStudent
		this.testStudent = new Student(firstName, secondName, age, yearGroup);
		//Populating testStudentWithId using the values from testStudent
		this.testStudentWithId = new Student(testStudent.getFirstName(), testStudent.getSecondName(), testStudent.getAge(), testStudent.getYearGroup());
		//Passing the id to the testStudentWithId
		this.testStudentWithId.setId(id);
		//Adding to the list of students
		this.studentList.add(testStudentWithId);
		//Converting into JSON format
		this.studentDTO = this.mapToDTO(testStudentWithId);
	}
	
	@Test
	void createTest() {
		when(this.service.newStudent(testStudent)).thenReturn(this.studentDTO);
		
		StudentDTO testCreate = this.studentDTO;
		assertThat(new ResponseEntity<StudentDTO>(testCreate, HttpStatus.CREATED))
				.isEqualTo(this.controller.create(this.testStudent));
		verify(this.service, times(1)).newStudent(this.testStudent);
	}

	//ReadAllClasses is not being used by the controller
//	@Test
//	void readAllClassesTest() {
//		when(this.service.ReadAllClasses())
//		.thenReturn(this.studentList.stream().map(this::mapToDTO).collect(Collectors.toList()));
//		assertThat(this.controller.getAllClassRooms().getBody().isEmpty()).isFalse();
//		verify(this.service, times(1)).ReadAllClasses();
//	}
	
	@Test
	void readAllTest() {
		when(this.service.read())
			.thenReturn(this.studentList.stream().map(this::mapToDTO).collect(Collectors.toList()));
		assertThat(this.controller.getAllClassRooms().getBody().isEmpty()).isFalse();
		verify(this.service, times(1)).read();
	}
	
	@Test
	void readOneTest() {
		when(this.service.read(this.id))
			.thenReturn(this.studentDTO);
		
		StudentDTO testReadOne = this.studentDTO;
		assertThat(new ResponseEntity<StudentDTO>(testReadOne, HttpStatus.OK))
			.isEqualTo(this.controller.findByID(this.id));
		verify(this.service, times(1)).read(this.id);
	}
	
	@Test
	void updateByIdTest () {
		StudentDTO newStudent = new StudentDTO(null, "Cristina", "López", 10, 3);
		StudentDTO newStudentWithId = new StudentDTO(this.id, newStudent.getFirstName(), newStudent.getSecondName(), newStudent.getAge(), newStudent.getYearGroup());
		
		when(this.service.update(newStudent, this.id)).thenReturn(newStudentWithId);
		assertThat(new ResponseEntity<StudentDTO>(newStudentWithId, HttpStatus.ACCEPTED))
			.isEqualTo(this.controller.updateByID(this.id, newStudent));
		verify(this.service, times(1)).update(newStudent, this.id);
	}
	
	@Test
	void deleteTest() {
		this.controller.deleteByID(this.id);
		verify(this.service, times(1)).delete(this.id);
	}

}
