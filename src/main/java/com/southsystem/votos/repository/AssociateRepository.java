package com.southsystem.votos.repository;

import com.southsystem.votos.entity.Associate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociateRepository extends JpaRepository<Associate, Long> {

    Associate findByCpf(String cpf);
}
