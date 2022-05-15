package com.southsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.southsystem.domain.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Integer> {
	
	Page<Associate> findByNameContainsIgnoreCase(String name, Pageable pages);

}
