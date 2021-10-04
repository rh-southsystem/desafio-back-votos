package br.com.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import br.com.dto.IniciarPautaDTO;
import br.com.dto.PautaDTO;
import br.com.entity.Pauta;
import br.com.entity.Voto;
import br.com.repository.PautaRepository;

@Service
public class PautaService  extends AbstractService<Pauta, Long>{

	@Autowired
	PautaRepository dao;
	
	@Autowired
	VotoService votoService;
	
	@Override
	public PagingAndSortingRepository<Pauta, Long> getRepository() {
		return dao;
	}

	public Pauta add(@Valid PautaDTO pautaDTO) {
		
		Pauta pauta = new Pauta();
		pauta.setNome(pautaDTO.getNome());
		return save(pauta);
	}

	public String iniciar(Long id, IniciarPautaDTO iniciarPautaDTO) {
		Optional<Pauta> pauta = findById(id);
		Date data = iniciarPautaDTO.getDtFim();
		
		if (!pauta.isPresent()) {
			throw new ServiceException("Não foi possível localizara a Pauta."); 
		}
		
		if (data ==null) {
			Calendar agora = Calendar.getInstance();
			 agora.add(Calendar.MINUTE, 1);
			 data = agora.getTime();
		}
		
		Date dtAtual = new Date();
		if (data.getTime() < dtAtual.getTime()) {
			throw new ServiceException("Data de fim de votação inválida."); 
		}
		pauta.get().setDtFim(data);
		save(pauta.get());
		return "Pauta "+pauta.get().getNome()+" já pode ser votada";
	}

	
	@CacheEvict(value="pauta", allEntries=true)
	public List<Pauta> obterTodos() {
		List<Pauta> retorno = findAll();
		
		for (Pauta pauta : retorno) {
			List<Voto> lstVotos = votoService.findByPauta(pauta);
			int sim=0;
			int nao=0;
			for (Voto voto : lstVotos) {
				if (voto.isVoto()) {
					sim++;
				} else {
					nao++;
				}
			}
			pauta.setTotalNao(nao);
			pauta.setTotalSim(sim);
			pauta.setTotalVotos(sim+nao);
		}
		
		return retorno;
	}

}
