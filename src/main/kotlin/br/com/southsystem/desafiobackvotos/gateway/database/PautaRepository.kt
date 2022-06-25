package br.com.southsystem.desafiobackvotos.gateway.database

import br.com.southsystem.desafiobackvotos.domain.Pauta
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface PautaRepository: ReactiveCrudRepository<Pauta, Long> {
}