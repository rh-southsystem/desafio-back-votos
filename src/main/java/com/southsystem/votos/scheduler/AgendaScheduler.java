package com.southsystem.votos.scheduler;

import com.southsystem.votos.entity.Agenda;
import com.southsystem.votos.producer.SQSMessageProducer;
import com.southsystem.votos.service.AgendaService;
import com.southsystem.votos.service.VotingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AgendaScheduler {

    private final AgendaService agendaService;

    private final SQSMessageProducer sqsMessageProducer;

    private final VotingService votingService;

    @Scheduled(fixedDelayString = "${timer-scheduled}")
    public void closerScheduler() {

        List<Agenda> list = agendaService.findByActive(true);

        list.stream()
                .filter(p -> LocalDateTime.now().isAfter(p.getDtEnd()))
                .forEach(p -> {
                                p.setActive(false);
                                agendaService.saveAgenda(p);
                                log.info("Pauta {} encerrada votação", p.getId());
                                sqsMessageProducer.send(votingService.getVotingResult(p.getId()));
                                log.info("Pauta enviada para fila: ", p);
                            });

    }
}
