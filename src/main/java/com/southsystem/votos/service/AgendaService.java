package com.southsystem.votos.service;

import com.southsystem.votos.entity.Agenda;
import com.southsystem.votos.exception.NotFoundException;
import com.southsystem.votos.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public Agenda saveAgenda(Agenda agenda) {
        Agenda agendaDb = agendaRepository.save(agenda);
        log.info("Pauta salva: {}", agenda);
        return agendaDb;
    }

    public void openSession(Long idAgenda, Integer time) {
        Optional<Agenda> agenda = agendaRepository.findById(idAgenda);
        if(!agenda.isPresent()) {
            log.info("Pauta de id = {} não encontrada.", idAgenda);
            throw new NotFoundException("Pauta não encontrada: " + idAgenda);
        }

        time = time == null || time == 0 ? 1 : time;
        Agenda agendaDb = agenda.get();

        agendaDb.setDtStart(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        agendaDb.setDtEnd(agendaDb.getDtStart().plusMinutes(time));
        agendaRepository.save(agendaDb);
        log.info("Sessão aberta: {}", agendaDb);
    }

    public Optional<Agenda> findById(Long pautaId) {
        return agendaRepository.findById(pautaId);
    }

    public List<Agenda> findByActive(boolean value) {
        return agendaRepository.findByActive(value);
    }
}
