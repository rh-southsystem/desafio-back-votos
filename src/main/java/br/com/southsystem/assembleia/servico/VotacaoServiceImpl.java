package br.com.southsystem.assembleia.servico;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.southsystem.assembleia.dto.UsuarioDTO;
import br.com.southsystem.assembleia.entidade.Associado;
import br.com.southsystem.assembleia.entidade.Pauta;
import br.com.southsystem.assembleia.entidade.Votacao;
import br.com.southsystem.assembleia.entidade.VotoId;
import br.com.southsystem.assembleia.entidade.enums.CpfStatusEnum;
import br.com.southsystem.assembleia.entidade.enums.VotoEnum;
import br.com.southsystem.assembleia.exception.EntidadeNaoProcessavelException;
import br.com.southsystem.assembleia.repositorio.VotacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VotacaoServiceImpl implements VotacaoService {

	private final EntityManager entityManager;

	private final VotacaoRepository votacaoRepository;

	private final PautaService pautaService;

	private final AssociadoService associadoService;

	private final UsuarioInformacaoService usuarioInformacaoService;

	@Override
	public Optional<Votacao> votar(Votacao votacao) {
		log.debug("Votacao: {}", votacao);
		VotoId votoId = new VotoId();
		votoId.setPauta(buscarPauta(votacao));
		votoId.setAssociado(buscarAssociado(votacao));
		votacao.setVotoId(votoId);
		validarVotoJaRealizado(votoId.getPauta().getId(), votoId.getAssociado().getId());
		log.info("Voto registrado: {}", votacao);
		return Optional.of(this.votacaoRepository.save(votacao));
	}

	@Override
	public Optional<List<Votacao>> consultar(Long idPauta, Optional<Long> idAssociado, Optional<VotoEnum> voto) {
		log.debug("idPauta: {}, idAssociado: {}, voto: {}", idPauta, idAssociado, voto);
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Votacao> cq = cb.createQuery(Votacao.class);
		Root<Votacao> votacaoRoot = cq.from(Votacao.class);

		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(cb.equal(votacaoRoot.get("votoId").get("pauta").get("id"), idPauta));

		if (idAssociado.isPresent()) {
			predicateList.add(cb.equal(votacaoRoot.get("votoId").get("associado").get("id"), idAssociado.get()));
		}

		if (voto.isPresent()) {
			predicateList.add(cb.equal(votacaoRoot.get("voto"), voto.get()));
		}
		cq.where(predicateList.stream().toArray(Predicate[]::new));

		return Optional.of(this.entityManager.createQuery(cq).getResultList());
	}
	
	private void validarVotoJaRealizado(Long idPauta, Long idAssociado) {
		Optional<Votacao> votacaoBaseDeDadosOptional = votacaoRepository.findByVotoIdPautaIdAndVotoIdAssociadoId(idPauta, idAssociado);
		if (votacaoBaseDeDadosOptional.isPresent()) {
			log.warn("O associado já realizou seu voto. idPauta: {}, idAssociado: {}", idPauta, idAssociado);
			throw new EntidadeNaoProcessavelException("O associado já realizou seu voto.");
		}
	}
	
	private Associado buscarAssociado(Votacao votacao) {
		if (!ObjectUtils.isEmpty(votacao) && !ObjectUtils.isEmpty(votacao.getVotoId())
				&& !ObjectUtils.isEmpty(votacao.getVotoId().getAssociado())) {
			Optional<Associado> associadoOptional = associadoService
					.consultarPorId(votacao.getVotoId().getAssociado().getId());
			if (associadoOptional.isPresent()) {
				Associado associado = associadoOptional.get();
				validarPermissaoDeVotoDoAssociado(associado);
				return associado;
			} else {
				log.warn("O associado não foi encontrado ou processado. idAssociado: {}", votacao.getVotoId().getAssociado().getId());
				throw new EntidadeNaoProcessavelException("O associado não foi encontrado ou processado.");
			}
		} else {
			log.warn("O associado não foi encontrado ou processado. votacao: {}", votacao);
			throw new EntidadeNaoProcessavelException("O associado não foi encontrado ou processado.");
		}
	}

	private Pauta buscarPauta(Votacao votacao) {
		if (!ObjectUtils.isEmpty(votacao) && !ObjectUtils.isEmpty(votacao.getVotoId())
				&& !ObjectUtils.isEmpty(votacao.getVotoId().getPauta())) {
			Optional<Pauta> pautaOptional = pautaService.consultarPorId(votacao.getVotoId().getPauta().getId());
			if (pautaOptional.isPresent()) {
				return pautaOptional.get();
			} else {
				log.warn("A pauta não foi encontrada ou processada. idPauta: {}", votacao.getVotoId().getPauta().getId());
				throw new EntidadeNaoProcessavelException("A pauta não foi encontrada ou processada.");
			}
		} else {
			log.warn("A pauta não foi encontrada ou processada. votacao: {}", votacao);
			throw new EntidadeNaoProcessavelException("A pauta não foi encontrada ou processada.");
		}
	}
	
	private void validarPermissaoDeVotoDoAssociado(Associado associado) {
		ResponseEntity<UsuarioDTO> response = usuarioInformacaoService.cpfValido(associado.getCpf());
		if (!ObjectUtils.isEmpty(response) && !ObjectUtils.isEmpty(response.getBody())) {
			UsuarioDTO usuarioDTO = response.getBody();
			if (!ObjectUtils.isEmpty(usuarioDTO) && usuarioDTO.getStatus().equals(CpfStatusEnum.UNABLE_TO_VOTE)) {
				log.warn("Associado não tem permissão para votar. CPF: {}", associado.getCpf());
				throw new EntidadeNaoProcessavelException("Associado não tem permissão para votar.");
			}
		} else {
			log.warn("Associado não tem permissão para votar. CPF: {}", associado.getCpf());
			throw new EntidadeNaoProcessavelException("Associado não tem permissão para votar.");
		}
	}

}