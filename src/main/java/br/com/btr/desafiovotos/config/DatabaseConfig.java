package br.com.btr.desafiovotos.config;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.btr.desafiovotos.associado.entity.AssociadoEntity;
import br.com.btr.desafiovotos.associado.repository.AssociadoRepository;

@Configuration
public class DatabaseConfig {

	@Autowired
	private AssociadoRepository repository;

	@Bean
	public void initialize() {
		Iterable<AssociadoEntity> associadosBD = repository.findAll();
		if( associadosBD != null && associadosBD.iterator().hasNext() ) {
			return;
		}
		
		final List<AssociadoEntity> associados = new LinkedList<>();

		associados.add(new AssociadoEntity("Fulano", "22233366638", true));
		associados.add(new AssociadoEntity("Ciclano", "80090667026", true));
		associados.add(new AssociadoEntity("Beltrano", "68415798016", false));
		associados.add(new AssociadoEntity("José", "10074181092", false));
		associados.add(new AssociadoEntity("Joaquim", "01354119045", false));

		this.repository.saveAll(associados);
	}
}
