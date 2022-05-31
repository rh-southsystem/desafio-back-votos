package br.com.southsystem.assembleia.servico;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import br.com.southsystem.assembleia.entidade.Associado;
import br.com.southsystem.assembleia.exception.EntidadeNaoProcessavelException;
import br.com.southsystem.assembleia.exception.ObjetoNaoEncontradoException;
import br.com.southsystem.assembleia.repositorio.AssociadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssociadoServiceImpl implements AssociadoService {
	
	private final EntityManager entityManager;
	
	private final AssociadoRepository associadoRepository;
	
	@Override
	public Optional<Associado> criar(Associado associado) {
		log.debug("Associado: {}", associado);
		validarDadoDoCpfAoCriar(associado);
		log.info("Associado criado: {}", associado);
		return Optional.of(this.associadoRepository.save(associado));
	}

	@Override
	public Optional<Associado> alterar(Long id, Associado associadoAlterado) {
		log.debug("idAssociado: {}, associadoAlterado: {}", id, associadoAlterado);
		Optional<Associado> associadoDaBaseDeDadosOptional = this.associadoRepository.findById(id);
		if (associadoDaBaseDeDadosOptional.isPresent()) {
			Associado associado = associadoDaBaseDeDadosOptional.get();
			validarDadoDoCpfNaAlteracao(associado, associadoAlterado);
			associadoAlterado.setId(id);
			log.info("Associado da base de dados: {}", associado);
			log.info("Associado alterado: {}", associadoAlterado);
			return Optional.of(this.associadoRepository.save(associadoAlterado));
		} else {
			log.warn("Objeto informado não foi encontrado. idAssociado: {}", id);
			throw new ObjetoNaoEncontradoException();
		}
	}
	
	@Override
	public Optional<List<Associado>> consultar(Optional<String> id, Optional<String> nome, Optional<Boolean> excluido) {
		log.debug("idAssociado: {}, nome: {}, excluido: {}", id, nome, excluido);
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Associado> cq = cb.createQuery(Associado.class);
		Root<Associado> associadoRoot = cq.from(Associado.class);

		List<Predicate> predicateList = new ArrayList<>();
		if (id.isPresent()) {
			predicateList.add(cb.equal(associadoRoot.get("id"), id.get()));
		}

		if (nome.isPresent()) {
			predicateList.add(cb.like(associadoRoot.get("nome"), "%" + nome.get() + "%"));
		}

		if (excluido.isPresent()) {
			predicateList.add(cb.equal(associadoRoot.get("flExcluido"), excluido.get()));
		}
		cq.where(predicateList.stream().toArray(Predicate[]::new));

		return Optional.of(this.entityManager.createQuery(cq).getResultList());
	}
	
	@Override
	public Optional<Associado> consultarPorId(Long id) {
		log.debug("idAssociado: {}", id);
		return this.associadoRepository.findById(id);
	}

	@Override
	public void excluir(Long id) {
		log.debug("idAssociado: {}", id);
		Optional<Associado> associadoOptional = this.associadoRepository.findById(id);
		if (associadoOptional.isPresent()) {
			Associado associado = associadoOptional.get();
			associado.setFlExcluido(true);
			log.info("Associado excluído: {}", associado);
			this.associadoRepository.save(associado);
		} else {
			log.warn("Objeto informado não foi encontrado. idAssociado: {}", id);
			throw new ObjetoNaoEncontradoException();
		}
	}
	
	private void validarDadoDoCpfAoCriar(Associado associado) {
		Optional<Associado> associadoComCpfOptional = this.associadoRepository.findByCpf(associado.getCpf());
		if (associadoComCpfOptional.isPresent()) {
			log.warn("Não foi possível efetuar o processamento do novo associado, pois já existe o CPF cadastrado na base de dados. AssociadoCriado: {}", associado);
			throw new EntidadeNaoProcessavelException("Não foi possível efetuar o processamento do novo associado, pois já existe o CPF cadastrado na base de dados.");
		}
	}

	private void validarDadoDoCpfNaAlteracao(Associado associadoDaBaseDeDados, Associado associadoAlterado) {
		if (!associadoDaBaseDeDados.getCpf().equalsIgnoreCase(associadoAlterado.getCpf())) {
			Optional<Associado> associadoComCpfOptional = this.associadoRepository.findByCpf(associadoAlterado.getCpf());
			if (associadoComCpfOptional.isPresent() && !associadoComCpfOptional.get().getId().equals(associadoDaBaseDeDados.getId())) {
				log.warn("Não foi possível efetuar o processamento do novo associado, pois já existe o CPF cadastrado na base de dados. AssociadoAlterado: {}", associadoAlterado);
				throw new EntidadeNaoProcessavelException("Não foi possível efetuar o processamento de alteração do associado, pois já existe o CPF cadastrado na base de dados.");
			}
		}
	}
	
}