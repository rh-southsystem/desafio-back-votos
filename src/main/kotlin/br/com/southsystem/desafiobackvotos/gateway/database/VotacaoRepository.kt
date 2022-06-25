package br.com.southsystem.desafiobackvotos.gateway.database

import br.com.southsystem.desafiobackvotos.domain.Votacao
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface VotacaoRepository: ReactiveCrudRepository<Votacao, Long> {
}


