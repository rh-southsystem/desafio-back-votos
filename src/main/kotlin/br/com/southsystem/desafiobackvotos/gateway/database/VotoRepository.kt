package br.com.southsystem.desafiobackvotos.gateway.database

import br.com.southsystem.desafiobackvotos.domain.Voto
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface VotoRepository: ReactiveCrudRepository<Voto, Long> {
}


