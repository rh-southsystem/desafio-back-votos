package com.southsystem.votos.controller;

import com.southsystem.votos.controller.doc.AgendaControllerDoc;
import com.southsystem.votos.dto.AgendaDTO;
import com.southsystem.votos.dto.VoteDTO;
import com.southsystem.votos.dto.VotingResultDTO;
import com.southsystem.votos.entity.Agenda;
import com.southsystem.votos.producer.SQSMessageProducer;
import com.southsystem.votos.service.AgendaService;
import com.southsystem.votos.service.VotingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/agenda")
@RequiredArgsConstructor
public class AgendaController implements AgendaControllerDoc {

    private final AgendaService agendaService;

    private final VotingService votingService;

    private final SQSMessageProducer producer;

    @PostMapping
    public ResponseEntity<Void> createAgenda(@RequestBody AgendaDTO agenda) {
        agendaService.saveAgenda(Agenda.builder().description(agenda.getDescription()).build());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{idAgenda}")
    public ResponseEntity<Void> openVoting(@PathVariable Long idAgenda,  @RequestParam(defaultValue = "1") Integer time) {
        agendaService.openSession(idAgenda, time);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/vote")
    public ResponseEntity<Void> sendVote(@RequestBody VoteDTO voteDTO) {
        votingService.sendVote(voteDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{agendaId}/result")
    public ResponseEntity<VotingResultDTO> getVotingResult(@PathVariable Long agendaId) {
        VotingResultDTO result = votingService.getVotingResult(agendaId);
        return new ResponseEntity<VotingResultDTO>(result, HttpStatus.OK);
    }

}
