package br.com.southsystem.desafiobackvotos.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class Votacao(

    @Id
    val id: Long,

    @Column("data_hora")
    val dataHoraInicio: LocalDateTime,

    @Column("id_associado")
    val idAssociado: Long,

    @Column("id_pauta")
    val idPauta: Long,

    @Column("id_sessao")
    val idSessao: Long
)