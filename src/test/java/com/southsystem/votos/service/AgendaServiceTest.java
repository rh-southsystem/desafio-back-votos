package com.southsystem.votos.service;

import com.southsystem.votos.entity.Agenda;
import com.southsystem.votos.repository.AgendaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

    @InjectMocks
    private AgendaService agendaService;

    @Mock
    private AgendaRepository agendaRepository;

    @Test
    public void openSession_validAgenda_openSuccess() {
        Mockito.when(agendaRepository.findById(anyLong())).thenReturn(Optional.of(new Agenda()));
        Mockito.when(agendaRepository.save(any(Agenda.class))).thenReturn(any(Agenda.class));

        agendaService.openSession(1L, 60);

        verify(agendaRepository, times(1)).findById(anyLong());
        verify(agendaRepository, times(1)).save(any(Agenda.class));
    }

    @Test
    public void openSession_invalidAgenda_dontOpen() {
        Mockito.when(agendaRepository.findById(anyLong())).thenReturn(Optional.empty());

        agendaService.openSession(1L, 60);

        verify(agendaRepository, times(1)).findById(anyLong());
        verify(agendaRepository, times(0)).save(any(Agenda.class));
    }

    @Test
    public void saveAgenda_validAgenda_sucess() {
        Mockito.when(agendaRepository.save(any(Agenda.class))).thenReturn(Agenda.builder().id(1L).build());

        Agenda agendaDb = agendaService.saveAgenda(Agenda.builder().id(1L).build());

        Assertions.assertEquals(1L, agendaDb.getId());

        verify(agendaRepository, times(1)).save(any(Agenda.class));
    }

    @Test
    public void saveAgenda_idValid_sucess() {
        Mockito.when(agendaRepository.findById(anyLong())).thenReturn(Optional.of(Agenda.builder().id(1L).build()));

        Optional<Agenda> optional = agendaService.findById(1L);

        Assertions.assertEquals(true, optional.isPresent());
        Assertions.assertEquals(1L, optional.get().getId());

        verify(agendaRepository, times(1)).findById(anyLong());
    }

    @Test
    public void findById_paramValid_sucess() {
        Mockito.when(agendaRepository.findByActive(anyBoolean())).thenReturn(Arrays.asList(Agenda.builder().build()));

        List<Agenda> list = agendaService.findByActive(true);

        Assertions.assertEquals(false, list.isEmpty());
        Assertions.assertEquals(1, list.size());

        verify(agendaRepository, times(1)).findByActive(anyBoolean());
    }


}
