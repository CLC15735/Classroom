package com.qa.springSDET_Project.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//lombok auto creates all the stuff for us
@Entity(name="student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Student {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name="first_name")
//	@NotNull
//	@Size(min=2,max=40)
	private String firstName;
	
	@Column(name="Second_Name")
//	@NotNull
//	@Size(min=2,max=40)
	private String secondName;
	
	@Column(name="Age")
//	@Min(5)
//	@Max(12)
	private Integer age;
	
	@Column(name="Year_Group")
//	@Min(1)
//	@Max(6)
	private Integer yearGroup;

//	public Student(@NotNull @Size(min = 2, max = 40) String firstName,
//			@NotNull @Size(min = 2, max = 40) String secondName, @Min(5) @Max(12) int age,
//			@Min(1) @Max(6) int yearGroup) {
//		super();
//		this.firstName = firstName;
//		this.secondName = secondName;
//		this.age = age;
//		this.yearGroup = yearGroup;
//	}
	
	
	// link class and students
	//state the class to link
	@ManyToOne
	//On delete what it should do 
	private ClassRoom room;
	
	public Student(String firstName, String secondName, int age, int yearGroup) {
	super();
	this.firstName = firstName;
	this.secondName = secondName;
	this.age = age;
	this.yearGroup = yearGroup;
	
}

}
