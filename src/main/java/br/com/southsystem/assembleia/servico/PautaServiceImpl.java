package br.com.southsystem.assembleia.servico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.southsystem.assembleia.entidade.Pauta;
import br.com.southsystem.assembleia.exception.EntidadeNaoProcessavelException;
import br.com.southsystem.assembleia.exception.ObjetoNaoEncontradoException;
import br.com.southsystem.assembleia.repositorio.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PautaServiceImpl implements PautaService {

	private final EntityManager entityManager;

	private final PautaRepository pautaRepository;

	@Override
	public Optional<Pauta> criar(Pauta pauta) {
		log.debug("Pauta: {}", pauta);
		if (ObjectUtils.isEmpty(pauta.getDtFim())) {
			pauta.setDtFim(pauta.getDtInicio().plusMinutes(1));
		}
		log.info("Pauta criada: {}", pauta);
		return Optional.of(this.pautaRepository.save(pauta));
	}

	@Override
	public Optional<Pauta> alterar(Long id, Pauta pautaAlterada) {
		log.debug("idPauta: {}, pautaAlterada: {}", id, pautaAlterada);
		Optional<Pauta> pautaOptional = this.pautaRepository.findById(id);
		if (pautaOptional.isPresent()) {
			Pauta pauta = pautaOptional.get();
			validarSePautaFoiFinalizada(pauta);
			pautaAlterada.setId(id);
			log.info("Pauta da base de dados: {}", pauta);
			log.info("Pauta alterada: {}", pautaAlterada);
			return Optional.of(this.pautaRepository.save(pautaAlterada));
		} else {
			log.warn("Objeto informado não foi encontrado. idPauta: {}", id);
			throw new ObjetoNaoEncontradoException();
		}
	}

	@Override
	public Optional<List<Pauta>> consultar(Optional<String> id, Optional<String> descricao,
			Optional<LocalDateTime> dtInicio, Optional<LocalDateTime> dtFim, Optional<Boolean> excluida) {
		log.debug("idPauta: {}, descricao: {}, dtInicio: {}, dtFim: {}, excluida: {}", id, descricao, dtInicio, dtFim, excluida);
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Pauta> cq = cb.createQuery(Pauta.class);
		Root<Pauta> pautaRoot = cq.from(Pauta.class);

		List<Predicate> predicateList = new ArrayList<>();
		if (id.isPresent()) {
			predicateList.add(cb.equal(pautaRoot.get("id"), id.get()));
		}

		if (descricao.isPresent()) {
			predicateList.add(cb.like(pautaRoot.get("descricao"), "%" + descricao.get() + "%"));
		}

		if (dtInicio.isPresent() && dtFim.isPresent()) {
			predicateList.add(cb.greaterThanOrEqualTo(pautaRoot.get("dtInicio"), dtInicio.get()));
			predicateList.add(cb.lessThanOrEqualTo(pautaRoot.get("dtFim"), dtFim.get()));
		}

		if (excluida.isPresent()) {
			predicateList.add(cb.equal(pautaRoot.get("flExcluida"), excluida.get()));
		}
		cq.where(predicateList.stream().toArray(Predicate[]::new));

		return Optional.of(this.entityManager.createQuery(cq).getResultList());
	}
	
	@Override
	public Optional<Pauta> consultarPorId(Long id) {
		log.debug("idPauta: {}", id);
		return pautaRepository.findById(id);
	}
	
	@Override
	public List<Pauta> consultarAsNaoFinalizadas() {
		return pautaRepository.findByFlFinalizadaFalse();
	}
	
	@Override
	public void excluir(Long id) {
		log.debug("idPauta: {}", id);
		Optional<Pauta> pautaOptional = this.pautaRepository.findById(id);
		if (pautaOptional.isPresent()) {
			Pauta pauta = pautaOptional.get();
			validarSePautaFoiFinalizada(pauta);
			pauta.setFlExcluida(true);
			log.info("Pauta excluída: {}", pauta);
			this.pautaRepository.save(pauta);
		} else {
			log.warn("Objeto informado não foi encontrado. idPauta: {}", id);
			throw new ObjetoNaoEncontradoException();
		}
	}

	private void validarSePautaFoiFinalizada(Pauta pauta) {
		if (pauta.isFlFinalizada()) {
			log.warn("A pauta já foi finalizada. Pauta: {}", pauta);
			throw new EntidadeNaoProcessavelException("A pauta já foi finalizada.");
		}
	}

}