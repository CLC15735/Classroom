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
import com.qa.springSDET_Project.dto.ClassRoomDTO;
import com.qa.springSDET_Project.persistence.domain.ClassRoom;
import com.qa.springSDET_Project.persistence.repository.ClassRoom_repo;

@SpringBootTest
@AutoConfigureMockMvc
public class ClassRoomControllerIntegrationTest {
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private ClassRoom_repo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long id;
	private ClassRoom testClassRoom;
	private ClassRoom testClassRoomWithId;
	
	private ClassRoomDTO mapToDTO(ClassRoom classRoom) {
		return this.modelMapper.map(classRoom, ClassRoomDTO.class);
	}
	
	private final String subject = "Maths";
	private final String teacherName = "Silvia";
	private final Integer classSize = 11;
	
	
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testClassRoom = new ClassRoom(subject, teacherName, classSize);
		this.testClassRoomWithId = this.repo.save(this.testClassRoom);
		this.id = this.testClassRoomWithId.getId();
	
	}
	
	@Test 
	void createTest() throws Exception {
		this.mock 
			.perform(request(HttpMethod.POST, "/ClassRoom/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(testClassRoom))
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(content().json(this.objectMapper.writeValueAsString(testClassRoomWithId)));
	}
	
	@Test
	void readAllTest() throws Exception {
		List<ClassRoom> classRoomList = new ArrayList<>();
		classRoomList.add(this.testClassRoomWithId);
		
		String content = this.mock
				.perform(request(HttpMethod.GET, "/ClassRoom/readAll")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertEquals(this.objectMapper.writeValueAsString(classRoomList), content);
	}
	
	@Test
	void readOneTest() throws Exception {
		this.mock
			.perform(request(HttpMethod.GET, "/ClassRoom/read/" + this.id)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(this.objectMapper.writeValueAsString(this.testClassRoom)));
	}
	
	@Test
	void updateTest() throws Exception {
		ClassRoom newClassRoom = new ClassRoom("Chemistry", "John", 15);
		ClassRoom updatedClassRoom = new ClassRoom(newClassRoom.getSubject(), newClassRoom.getTeacherName(), newClassRoom.getClassSize());
		updatedClassRoom.setId(this.id);
		
		String result = this.mock
				.perform(request(HttpMethod.PUT, "/ClassRoom/update/" + this.id)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.objectMapper.writeValueAsString(newClassRoom)))
				.andExpect(status().isCreated())
				.andReturn().getResponse().getContentAsString();
		
		assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedClassRoom)), result);
	}
	
	@Test
	void deleteTest() throws Exception {
		this.mock
			.perform(request(HttpMethod.DELETE, "/ClassRoom/delete/"+this.id))
			.andExpect(status().isInternalServerError());
					
	}

}
