package com.qa.springSDET_Project.persistence.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.springSDET_Project.dto.StudentDTO;
import com.qa.springSDET_Project.persistence.domain.Student;
import com.qa.springSDET_Project.persistence.repository.Student_repo;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerIntegrationTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private Student_repo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Student testStudent;
	private Student testStudentWithId;
	private StudentDTO studentDTO;
	
	private Long id = 1L;
	
	private StudentDTO mapToDTO (Student student) {
		return this.modelMapper.map(student, StudentDTO.class);
	}
	
	private final String firstName = "Mark";
	private final String secondName = "Johnson";
	private final Integer age = 11;
	private final Integer yearGroup = 6;
	
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		
		this.testStudent = new Student(firstName, secondName, age, yearGroup);
		this.testStudentWithId = this.repo.save(this.testStudent);
		this.studentDTO = this.mapToDTO(testStudentWithId);
		this.id = this.testStudentWithId.getId();
	}
	
	@Test
	void createTest() throws Exception {
		this.mock
			.perform(request(HttpMethod.POST, "/Student/Create").contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(this.testStudent))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().json(this.objectMapper.writeValueAsString(this.studentDTO)));
	}
	
	@Test
	void readAllTest() throws Exception {
		List<StudentDTO> studentList = new ArrayList<>();
		studentList.add(this.studentDTO);
		String expected = this.objectMapper.writeValueAsString(studentList);
		
		String actual = this.mock.perform(request(HttpMethod.GET,"/Student/readAll").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
	
		assertEquals(expected,actual);
	}
	
	@Test
	void readOneTest() throws Exception {
		this.mock.perform(request(HttpMethod.GET, "/Student/read/" + this.id).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(this.objectMapper.writeValueAsString(this.studentDTO)));
	}
	
	@Test
	void updateTest() throws Exception {
		StudentDTO newStudent = new StudentDTO(null, "Cristina", "Canoyra", 10,3);
		Student updatedStudent = new Student(newStudent.getFirstName(), newStudent.getSecondName(), newStudent.getAge(),newStudent.getYearGroup());
		updatedStudent.setId(this.id);
		String expected = this.objectMapper.writeValueAsString(this.mapToDTO(updatedStudent));
		String actual = this.mock.perform(request(HttpMethod.PUT, "/Student/update/" + this.id)
				.contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(newStudent))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
				.andReturn().getResponse().getContentAsString();
		
		assertEquals(expected, actual);
				
	}
	
	@Test
	void deleteTest() throws Exception {
		this.mock.perform(request(HttpMethod.DELETE, "/Student/delete/" + this.id)).andExpect(status().isNoContent());
	}

}
