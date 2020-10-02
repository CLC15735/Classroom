package com.qa.springSDET_Project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.qa.springSDET_Project.dto.StudentDTO;
import com.qa.springSDET_Project.exception.StudentNotFound;
import com.qa.springSDET_Project.persistence.domain.Student;
import com.qa.springSDET_Project.persistence.repository.Student_repo;
import com.qa.springSDET_Project.utility.SDETBeanUtils;
@Service
public class StudentService {
	
	private Student_repo repo;
	
	private ModelMapper mapper;
	
	@Autowired public StudentService(Student_repo repo,ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private StudentDTO mapToDo(Student student) {
		return this.mapper.map(student, StudentDTO.class);
	}
	private Student mapFromDTO(StudentDTO studentDTO) {
		return this.mapper.map(studentDTO, Student.class);
	}
	//Create
		public StudentDTO newStudent(Student student) {
//			Student toSave = this.mapFromDTO(studentDTO);
			Student saved = this.repo.save(student);
			return this.mapToDo(saved);
		}
//		Read
		public List<StudentDTO> ReadAllClasses(){
			return this.repo.findAll()
					.stream()
					.map(this::mapToDo)
					.collect(Collectors.toList());
		}
		public List<StudentDTO> read() {
			List<StudentDTO> dtos = new ArrayList<>();
			for (Student student : this.repo.findAll()) {
				dtos.add(this.mapToDo(student));
			}
			return dtos;
//			return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
		}
		
		//Read one
		public StudentDTO read(Long id) {
			return this.mapToDo(this.repo.findById(id).orElseThrow(StudentNotFound::new));
		}
		
		//Delete
		public boolean delete(Long id) {
			if (!this.repo.existsById(id)) {
				throw new StudentNotFound();
			}
			this.repo.deleteById(id);
			return !this.repo.existsById(id);
			
		}
		
		//Update
		public StudentDTO update (StudentDTO studentDTO, Long id) {
			Student toUpdate = this.repo.findById(id).orElseThrow(StudentNotFound::new);
			SDETBeanUtils.mergeNotNull(studentDTO, toUpdate);
			return this.mapToDo(this.repo.save(toUpdate));
		}
	
}
