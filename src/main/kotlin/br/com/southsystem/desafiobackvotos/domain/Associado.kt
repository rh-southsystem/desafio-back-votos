package br.com.southsystem.desafiobackvotos.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table
data class Associado(

    @Id
    val id: Long,

    @Column
    val nome: String,

    @Column
    val cpf: String
)