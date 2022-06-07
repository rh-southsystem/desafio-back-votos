package br.com.southsystem.assembleia.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.southsystem.assembleia.dto.AssociadoBasicoDTO;
import br.com.southsystem.assembleia.dto.AssociadoCompletoDTO;
import br.com.southsystem.assembleia.entidade.Associado;
import br.com.southsystem.assembleia.mapper.AssociadoMapper;
import br.com.southsystem.assembleia.servico.AssociadoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/v1/associado", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AssociadoControllerImpl implements AssociadoController {

    private final AssociadoService associadoService;
    
    private final AssociadoMapper associadoMapper;

    @PostMapping
    public ResponseEntity<Void> criar(AssociadoBasicoDTO associadoBasicoDTO) {
    	Optional<Associado> associadoOptional = this.associadoService.criar(this.associadoMapper.toAssociado(associadoBasicoDTO));
    	if (associadoOptional.isPresent()) {
    		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().queryParam("id", associadoOptional.get().getId().toString()).build().toUri()).build();
		} else {
			return ResponseEntity.unprocessableEntity().build();
		}
    }
    
    @PutMapping(path = "/{id}")
	public ResponseEntity<Void> alterar(Long id, AssociadoBasicoDTO associadoBasicoDTO) {
    	Optional<Associado> associadoOptional = this.associadoService.alterar(id, this.associadoMapper.toAssociado(associadoBasicoDTO));
    	if (associadoOptional.isPresent()) {
    		return ResponseEntity.ok().build();	
    	} else {
    		return ResponseEntity.unprocessableEntity().build();
    	}
	}
    
	@GetMapping
	public ResponseEntity<List<AssociadoCompletoDTO>> consultar(Optional<String> id, Optional<String> nome, Optional<Boolean> excluido) {
		Optional<List<Associado>> associadoOptionalList = this.associadoService.consultar(id, nome, excluido);
    	if (associadoOptionalList.isPresent()) {
    		return ResponseEntity.ok(this.associadoMapper.toAssociadoCompletoDtoList(associadoOptionalList.get()));		
    	} else {
    		return ResponseEntity.noContent().build();
    	}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> excluir(Long id) {
		associadoService.excluir(id);
		return ResponseEntity.noContent().build();
	}

}