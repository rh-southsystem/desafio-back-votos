package br.com.southsystem.desafiobackvotos.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class Sessao(

    @Id
    val id: Long,

    @Column("data_hora_inicio")
    val dataHoraInicio: LocalDateTime,

    @Column("data_hora_fim")
    val dataHoraFim: LocalDateTime,

    @Column("id_pauta")
    val idPauta: Long
)