package br.com.southsystem.desafiobackvotos.gateway.database

import br.com.southsystem.desafiobackvotos.domain.Associado
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface AssociadoRepository: ReactiveCrudRepository<Associado, Long> {
}