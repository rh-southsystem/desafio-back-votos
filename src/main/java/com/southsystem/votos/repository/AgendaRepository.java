package com.southsystem.votos.repository;

import com.southsystem.votos.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Query(value = "SELECT * FROM agenda WHERE active = :value", nativeQuery = true)
    List<Agenda> findByActive(boolean value);
}
