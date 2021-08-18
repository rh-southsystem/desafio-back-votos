package br.com.btr.desafiovotos.pauta.service;

import java.util.Map;

import br.com.btr.desafiovotos.pauta.enums.Voto;
import br.com.btr.desafiovotos.pauta.json.Pauta;
import br.com.btr.desafiovotos.pauta.json.Sessao;

public interface PautaService {

	void save(Pauta pauta);

	void initialize(Long idPauta, Sessao sessao);

	Map<Voto, Integer> getResult(Long idPauta);
}
