package br.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.entity.Associado;

	
	@Repository
	public interface AssociadoRepository extends JpaRepository<Associado, Long> {


}
