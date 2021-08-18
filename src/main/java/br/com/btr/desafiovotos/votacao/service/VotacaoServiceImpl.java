package br.com.btr.desafiovotos.votacao.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.btr.desafiovotos.associado.entity.AssociadoEntity;
import br.com.btr.desafiovotos.associado.repository.AssociadoRepository;
import br.com.btr.desafiovotos.pauta.entity.PautaEntity;
import br.com.btr.desafiovotos.pauta.repository.PautaRepository;
import br.com.btr.desafiovotos.votacao.entity.VotacaoEntity;
import br.com.btr.desafiovotos.votacao.json.Votacao;
import br.com.btr.desafiovotos.votacao.repository.VotacaoRepository;

@Service
public class VotacaoServiceImpl implements VotacaoService {

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private AssociadoRepository associadoRepository;

	@Autowired
	private VotacaoRepository votacaoRepository;

	@Override
	public void vote(Votacao voto) {
		final Optional<PautaEntity> pauta = this.pautaRepository.findById(voto.getIdPauta());
		if (!pauta.isPresent()) {
			throw new IllegalArgumentException("Sessão não encontrada!");
		}

		Optional<AssociadoEntity> associado = this.associadoRepository.findById(voto.getIdAssociado());
		if (!associado.isPresent()) {
			throw new IllegalArgumentException("Associado não encontrado!");
		}

		this.validate(voto, pauta.get(), associado.get());

		VotacaoEntity entity = new VotacaoEntity();
		entity.setAssociado(associado.get());
		entity.setPauta(pauta.get());
		entity.setVoto(voto.getVoto());

		this.votacaoRepository.save(entity);
	}

	@SuppressWarnings("rawtypes")
	private void validate(Votacao voto, PautaEntity pauta, AssociadoEntity associado) {
		LocalDateTime agora = LocalDateTime.now();
		if (pauta.getInicio() == null) {
			throw new IllegalArgumentException("Votação não iniciada!");
		}

		if (agora.isBefore(pauta.getInicio()) || agora.isAfter(pauta.getFim())) {
			throw new IllegalArgumentException("Votação encerrada!");
		}
		
		Optional<VotacaoEntity> votoEntity = votacaoRepository.findByPautaIdAndAssociadoId( pauta.getId(), associado.getId() );
		if( votoEntity.isPresent() ) {
			throw new IllegalArgumentException("Associado já votou!");
		}

		// Validar se o associado pode votar
		RestTemplate rest = new RestTemplate();
		
		ResponseEntity<Map> resposta = rest.getForEntity("https://user-info.herokuapp.com/users/{cpf}".replace("{cpf}", associado.getCpf()),
				Map.class);
		
		if( HttpStatus.NOT_FOUND.equals(resposta.getStatusCode()) ) {
			throw new IllegalArgumentException("CPF do Associado é inválido!");
		}

		boolean podeVotar = "ABLE_TO_VOTE".equals(resposta.getBody().get("status"));
		if (!podeVotar) {
			throw new IllegalArgumentException("Associado não pode votar!");
		}
	}
}
