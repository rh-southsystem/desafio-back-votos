package br.com.assembliescorp.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.assembliescorp.domain.entities.AssociateEntity;

public interface AssociateRepository extends JpaRepository<AssociateEntity, Long> {

}
