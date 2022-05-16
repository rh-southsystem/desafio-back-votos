package com.southsystem.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.southsystem.domain.Vote;
import com.southsystem.domain.VotePK;

public interface VoteRepository extends JpaRepository<Vote, VotePK> {
	
	List<Vote> findByIdAssemblyId(Integer assemblyId);
	
	@Transactional
	void deleteByIdAssemblyId(Integer assemblyId);

}
