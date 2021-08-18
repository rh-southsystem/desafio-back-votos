package br.com.btr.desafiovotos.pauta.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.btr.desafiovotos.pauta.entity.PautaEntity;
import br.com.btr.desafiovotos.pauta.enums.Voto;
import br.com.btr.desafiovotos.pauta.json.Pauta;
import br.com.btr.desafiovotos.pauta.json.Sessao;
import br.com.btr.desafiovotos.pauta.repository.PautaRepository;
import br.com.btr.desafiovotos.votacao.entity.VotacaoEntity;
import br.com.btr.desafiovotos.votacao.repository.VotacaoRepository;

@Service
public class PautaServiceImpl implements PautaService {

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private VotacaoRepository votacaoRepository;

	@Override
	public void save(Pauta pauta) {
		PautaEntity entity = new PautaEntity();
		entity.setDescricao(pauta.getDescricao());

		this.pautaRepository.save(entity);
		
		pauta.setId(entity.getId());
	}

	@Override
	public void initialize(Long idPauta, Sessao sessao) {
		Optional<PautaEntity> optional = this.pautaRepository.findById(idPauta);
		if (!optional.isPresent()) {
			throw new IllegalArgumentException("Pauta não encontrada!");
		}
		
		PautaEntity entity = optional.get();
		if(entity.getInicio() != null) {
			throw new IllegalArgumentException("Pauta já iniciou a votação ou já está encerrada!");
		}

		entity.setInicio(LocalDateTime.now());
		entity.setFim(entity.getInicio().plusMinutes(sessao.getTempoPautaMinutos() == null ? 1 : sessao.getTempoPautaMinutos()));
		
		this.pautaRepository.save(entity);
	}

	@Override
	public Map<Voto, Integer> getResult(Long idPauta) {
		Optional<PautaEntity> optional = this.pautaRepository.findById(idPauta);
		if (!optional.isPresent()) {
			throw new IllegalArgumentException("Pauta não encontrada!");
		}
		
		List<VotacaoEntity> votos = this.votacaoRepository.findAllByPautaId( idPauta );
		if( CollectionUtils.isEmpty(votos) ) {
			throw new IllegalArgumentException("Pauta não tem votos!");
		}
		
		Map<Voto, Integer> resultado = new LinkedHashMap<>();
		
		votos.stream().collect(Collectors.groupingBy(VotacaoEntity::getVoto)).entrySet().parallelStream().forEach(v -> resultado.put(v.getKey(), v.getValue().size()));
		
		return resultado;
	}
}
