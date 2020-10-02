package com.qa.springSDET_Project.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StudentDTO {

	private Long Id;
	private String firstName;
	private String secondName;
	private Integer age;
	private Integer yearGroup;
	
	
	
}

