package com.southsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.southsystem.domain.Assembly;

public interface AssemblyRepository extends JpaRepository<Assembly, Integer> {
	
	Page<Assembly> findByTitleContainsIgnoreCase(String title, Pageable pageable);
	
	Page<Assembly> findByStatus(Integer status, Pageable pageable);

}
