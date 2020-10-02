package com.qa.springSDET_Project.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClassRoom {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name ="Subject",unique=true)
	private String subject;
	
	@Column(name="Teacher_Name")
	private String teacherName;
	
	@Column(name="Max_class_size")
	@Min(2)
	@Max(28)
	private Integer classSize;
	
	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private List<Student> students;

	
	//create a blank class with a subject
	public ClassRoom(String subject) {
		super();
		this.subject = subject;
	}


	public ClassRoom(String subject, String teacherName, @Min(2) @Max(28) Integer classSize) {
		super();
		this.subject = subject;
		this.teacherName = teacherName;
		this.classSize = classSize;
	}
	
	
	

	
	
	
}
