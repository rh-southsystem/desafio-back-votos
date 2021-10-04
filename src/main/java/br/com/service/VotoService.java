package br.com.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.dto.VotoDTO;
import br.com.entity.Associado;
import br.com.entity.Pauta;
import br.com.entity.Voto;
import br.com.repository.VotoRepository;

@Service
public class VotoService  extends AbstractService<Voto, Long>  {

    private static final Logger logger = LoggerFactory.getLogger(VotoService.class);
    
    @Autowired
    VotoRepository dao;
    
    @Autowired
    PautaService pautaService;
    
    @Autowired
    AssociadoService associadoService;
    
    @Autowired
    ConsultaCPFService consultaCPFService;

    @Value("${topic.voto}")
    private String votoTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String voto) {
        this.kafkaTemplate.send(votoTopic, voto);
    }
    
    public void validaVoto(VotoDTO votoDTO) {
    	Optional<Pauta> pauta = pautaService.findById(votoDTO.getIdPauta());
    	Optional<Associado> associado = associadoService.findByCpf(votoDTO.getCpf());
    	
    	if (associado.isEmpty()) {
    		associadoService.add(votoDTO.getCpf());	
    	} else {
    		Optional<Voto> voto = findByPautaAndAssociado(pauta.get(),associado.get());
    		if (voto.isPresent()) {
    			throw new ServiceException("Voto já computado."); 
    		}
    	}
    	
    	if (!pauta.isPresent()) {
			throw new ServiceException("Não foi possível localizara a Pauta."); 
		}
    	
    	if (pauta.get().getDtFim()==null) {
			throw new ServiceException("Pauta não iniciou votação"); 
		}
    	
    	Date dtAtual = new Date();
		if (pauta.get().getDtFim().getTime() < dtAtual.getTime()) {
			throw new ServiceException("Pauta já encerrou votação"); 
		}
    	 
    	if (!consultaCPFService.permiteVotacao(votoDTO.getCpf())) {
    		throw new ServiceException("Não possível votar.");
    	}
    }

	private Optional<Voto> findByPautaAndAssociado(Pauta pauta, Associado associado) {
		return dao.findByPautaAndAssociado(pauta,associado) ;
	}

	@Override
	public PagingAndSortingRepository<Voto, Long> getRepository() {
		return dao;
	}

	public void computarVoto(String votoStr) {
		Gson gson = new Gson();
		VotoDTO votoDTO = gson.fromJson(votoStr, VotoDTO.class);
		Optional<Pauta> pauta = pautaService.findById(votoDTO.getIdPauta());
		Optional<Associado> associado = associadoService.findByCpf(votoDTO.getCpf());
		
		Voto voto = new Voto();
		
		voto.setAssociado(associado.get());
		voto.setPauta(pauta.get());
		voto.setVoto(votoDTO.isVoto());
		save(voto);
		
	}

	public List<Voto> findByPauta(Pauta pauta) {
		return dao.findByPauta(pauta);
	}
}