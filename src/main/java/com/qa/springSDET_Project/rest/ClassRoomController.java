package com.qa.springSDET_Project.rest;

import java.util.List;

import org.modelmapper.ModelMapper;
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
import com.qa.springSDET_Project.persistence.domain.ClassRoom;
import com.qa.springSDET_Project.persistence.repository.ClassRoom_repo;
import com.qa.springSDET_Project.service.ClassRoomService;

@RestController
@CrossOrigin
@RequestMapping("/ClassRoom")
public class ClassRoomController {
	
	//spring reflects in a setter called setService its hidden
	private ClassRoomService service;
	
	@Autowired 
	public ClassRoomController ( ClassRoomService service) {
		super();
		this.service = service;
		
	}

	//Create
	@PostMapping("/create")
	// public res.... <BandDTO> create (@requestBody Band band)
	public ResponseEntity<ClassRoomDTO> create(@RequestBody ClassRoom dto){
//		ClassRoomDTO created= this.service.newClass(dto);
		return new ResponseEntity<>(this.service.newClass(dto),HttpStatus.CREATED);
	}
	
	// Read
	@GetMapping("/readAll")
	public ResponseEntity<List<ClassRoomDTO>> getAllClassRooms(){
		return ResponseEntity.ok(this.service.ReadAllClasses());
	}
	
	// Read one
	@GetMapping("/read/{id}")
		public ResponseEntity< ClassRoomDTO> findByID(@PathVariable Long id){
		return ResponseEntity.ok(this.service.read(id));
	}
	
	//Update
	@PutMapping("update/{id}")
	public ResponseEntity<ClassRoomDTO> updateByID (@PathVariable Long id, @RequestBody ClassRoomDTO classroomDTO){
		ClassRoomDTO updated =this.service.update(classroomDTO, id);
		return new ResponseEntity<>(updated, HttpStatus.CREATED);
	}
	
	//Delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ClassRoomDTO> deleteByID(@PathVariable Long id){
	return this.service.delete(id)
			? new ResponseEntity<>(HttpStatus.NO_CONTENT)
			:new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
