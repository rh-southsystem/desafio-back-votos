package br.com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import br.com.entity.Associado;
import br.com.repository.AssociadoRepository;

@Service
public class AssociadoService  extends AbstractService<Associado, Long>{
	
	@Autowired
	AssociadoRepository dao;
	@Override
	public PagingAndSortingRepository<Associado, Long> getRepository() {
		return dao;
	}
	public Optional<Associado> findByCpf(Long cpf) {
		return dao.findById(cpf);
	}
	public void add(Long cpf) {
		Associado associado = new Associado();
		associado.setCpf(cpf);
		save(associado);
	}

}
