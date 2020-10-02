package com.qa.springSDET_Project.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qa.springSDET_Project.dto.ClassRoomDTO;
import com.qa.springSDET_Project.exception.ClassRoomNotFound;
import com.qa.springSDET_Project.persistence.domain.ClassRoom;
import com.qa.springSDET_Project.persistence.repository.ClassRoom_repo;
import com.qa.springSDET_Project.utility.SDETBeanUtils;

@Service
public class ClassRoomService {

private ClassRoom_repo repo;
	
	private ModelMapper mapper;
	
	
	//Crud functionality is needed in here.
	//Create
	//Read
	//Update
	//Delete
	
	@Autowired
	public ClassRoomService ( ClassRoom_repo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private ClassRoomDTO mapToDo(ClassRoom classRoom) {
		return this.mapper.map(classRoom, ClassRoomDTO.class);
	}
	private ClassRoom mapFromDTO(ClassRoomDTO classRoomDTO) {
		return this.mapper.map(classRoomDTO, ClassRoom.class);
	}
	
	//Create
//	pass in domain class 
	public ClassRoomDTO newClass(ClassRoom classRoom) {
//		ClassRoom toSave = this.mapFromDTO(classRoomDTO);
		ClassRoom saved = this.repo.save(classRoom);
		return this.mapToDo(saved);
	}
//	Read
	public List<ClassRoomDTO> ReadAllClasses(){
		List<ClassRoom> listOFClass =this.repo.findAll();
		List<ClassRoomDTO> listOFClassDTO = listOFClass.stream()
				.map(this::mapToDo)
				.collect(Collectors.toList());
		return listOFClassDTO;
	}
	
	//Read one
	public ClassRoomDTO read(Long id) {
		return this.mapToDo(this.repo.findById(id).orElseThrow(ClassRoomNotFound::new));
	}
	
	//Delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return this.repo.existsById(id);
		
	}
	
	//Update
	public ClassRoomDTO update (ClassRoomDTO classRoomDTO, Long id) {
		ClassRoom toUpdate = this.repo.findById(id).orElseThrow(ClassRoomNotFound::new);
		SDETBeanUtils.mergeNotNull(classRoomDTO, toUpdate);
		return this.mapToDo(this.repo.save(toUpdate));
	}
	
	
}
//}
