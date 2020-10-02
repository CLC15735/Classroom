package com.qa.springSDET_Project.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.springSDET_Project.persistence.domain.Student;

@Repository
public interface Student_repo extends JpaRepository<Student,Long> {

}
