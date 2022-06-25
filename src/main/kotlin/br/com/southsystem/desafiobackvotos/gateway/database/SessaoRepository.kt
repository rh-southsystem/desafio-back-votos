package br.com.southsystem.desafiobackvotos.gateway.database

import br.com.southsystem.desafiobackvotos.domain.Sessao
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface SessaoRepository: ReactiveCrudRepository<Sessao, Long> {
}