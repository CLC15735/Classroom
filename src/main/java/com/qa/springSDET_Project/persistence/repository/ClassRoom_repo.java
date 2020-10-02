package com.qa.springSDET_Project.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.springSDET_Project.persistence.domain.ClassRoom;

@Repository
public interface ClassRoom_repo extends JpaRepository<ClassRoom, Long>{

}
