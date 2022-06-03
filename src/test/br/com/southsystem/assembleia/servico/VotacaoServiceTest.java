package br.com.southsystem.assembleia.servico;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.southsystem.assembleia.dto.UsuarioDTO;
import br.com.southsystem.assembleia.entidade.Associado;
import br.com.southsystem.assembleia.entidade.Pauta;
import br.com.southsystem.assembleia.entidade.Votacao;
import br.com.southsystem.assembleia.entidade.VotoId;
import br.com.southsystem.assembleia.entidade.enums.CpfStatusEnum;
import br.com.southsystem.assembleia.entidade.enums.VotoEnum;
import br.com.southsystem.assembleia.exception.EntidadeNaoProcessavelException;
import br.com.southsystem.assembleia.repositorio.VotacaoRepository;

@RunWith(MockitoJUnitRunner.class)
public class VotacaoServiceTest {

	private VotacaoService votacaoService;

	@Mock
	private EntityManager entityManagerMock;

	@Mock
	private VotacaoRepository votacaoRepositoryMock;

	@Mock
	private PautaService pautaServiceMock;

	@Mock
	private AssociadoService associadoServiceMock;

	@Mock
	private UsuarioInformacaoService usuarioInformacaoServiceMock;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		this.votacaoService = new VotacaoServiceImpl(this.entityManagerMock, this.votacaoRepositoryMock,
				this.pautaServiceMock, this.associadoServiceMock, this.usuarioInformacaoServiceMock);
	}

	@Test
	@DisplayName("EntidadeNaoProcessavelException - 1 - A pauta não foi encontrada ou processada.")
	void votarPautaNaoEncontradaOuProcessada1() {
		when(pautaServiceMock.consultarPorId(anyLong())).thenReturn(Optional.empty());

		EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
			Votacao votacao = new Votacao();
			VotoId votoId = new VotoId();
			Associado associado = new Associado();
			associado.setId(1L);
			votoId.setAssociado(associado);
			votacao.setVotoId(votoId);
			votacaoService.votar(votacao);
		});

		assertTrue(thrown.getMessage().contains("A pauta não foi encontrada ou processada."));
	}
	
	@Test
	@DisplayName("EntidadeNaoProcessavelException - 2 - A pauta não foi encontrada ou processada.")
	void votarPautaNaoEncontradaOuProcessada2() {
		when(pautaServiceMock.consultarPorId(anyLong())).thenReturn(Optional.empty());

		EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
			Votacao votacao = new Votacao();
			VotoId votoId = new VotoId();
			Pauta pauta = new Pauta();
			pauta.setId(1L);
			votoId.setPauta(pauta);
			Associado associado = new Associado();
			associado.setId(1L);
			votoId.setAssociado(associado);
			votacao.setVotoId(votoId);
			votacaoService.votar(votacao);
		});

		assertTrue(thrown.getMessage().contains("A pauta não foi encontrada ou processada."));
	}
	
	@Test
	@DisplayName("EntidadeNaoProcessavelException - 1 - O associado não foi encontrado ou processado.")
	void votarAssociadoNaoEncontradoOuProcessado1() {
		Pauta pautaBaseDeDados = new Pauta();
		when(pautaServiceMock.consultarPorId(anyLong())).thenReturn(Optional.of(pautaBaseDeDados));

		EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
			Votacao votacao = new Votacao();
			VotoId votoId = new VotoId();
			Pauta pauta = new Pauta();
			pauta.setId(1L);
			votoId.setPauta(pauta);
			votacao.setVotoId(votoId);
			votacaoService.votar(votacao);
		});

		assertTrue(thrown.getMessage().contains("O associado não foi encontrado ou processado."));
	}
	
	@Test
	@DisplayName("EntidadeNaoProcessavelException - 2 - O associado não foi encontrado ou processado.")
	void votarAssociadoNaoEncontradoOuProcessado2() {
		Pauta pautaBaseDeDados = new Pauta();
		when(pautaServiceMock.consultarPorId(anyLong())).thenReturn(Optional.of(pautaBaseDeDados));
		
		when(associadoServiceMock.consultarPorId(anyLong())).thenReturn(Optional.empty());

		EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
			Votacao votacao = new Votacao();
			VotoId votoId = new VotoId();
			Pauta pauta = new Pauta();
			pauta.setId(1L);
			votoId.setPauta(pauta);
			Associado associado = new Associado();
			associado.setId(1L);
			votoId.setAssociado(associado);
			votacao.setVotoId(votoId);
			votacaoService.votar(votacao);
		});

		assertTrue(thrown.getMessage().contains("O associado não foi encontrado ou processado."));
	}
	
	@Test
	@DisplayName("EntidadeNaoProcessavelException - 1 - Associado não tem permissão para votar.")
	void votarAssociadoNaoTemPermissaoParaVotar1() {
		Pauta pautaBaseDeDados = new Pauta();
		when(pautaServiceMock.consultarPorId(anyLong())).thenReturn(Optional.of(pautaBaseDeDados));
		
		Associado associadoBaseDeDados = new Associado();
		when(associadoServiceMock.consultarPorId(anyLong())).thenReturn(Optional.of(associadoBaseDeDados));

		EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
			Votacao votacao = new Votacao();
			VotoId votoId = new VotoId();
			Pauta pauta = new Pauta();
			pauta.setId(1L);
			votoId.setPauta(pauta);
			Associado associado = new Associado();
			associado.setId(1L);
			votoId.setAssociado(associado);
			votacao.setVotoId(votoId);
			votacaoService.votar(votacao);
		});

		assertTrue(thrown.getMessage().contains("Associado não tem permissão para votar."));
	}
	
	//@Test
	//@DisplayName("EntidadeNaoProcessavelException - 2 - Associado não tem permissão para votar.")
	void votarAssociadoNaoTemPermissaoParaVotar2() {
		Pauta pautaBaseDeDados = new Pauta();
		when(pautaServiceMock.consultarPorId(anyLong())).thenReturn(Optional.of(pautaBaseDeDados));
		
		Associado associadoBaseDeDados = new Associado();
		when(associadoServiceMock.consultarPorId(anyLong())).thenReturn(Optional.of(associadoBaseDeDados));
		
		//TODO: usuarioInformacaoServiceMock falta terminar.
		when(usuarioInformacaoServiceMock.cpfValido(anyString())).thenReturn(new ResponseEntity<>(new UsuarioDTO(CpfStatusEnum.UNABLE_TO_VOTE), HttpStatus.OK));

		EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
			Votacao votacao = new Votacao();
			VotoId votoId = new VotoId();
			Pauta pauta = new Pauta();
			pauta.setId(1L);
			votoId.setPauta(pauta);
			Associado associado = new Associado();
			associado.setId(1L);
			associado.setCpf("07911761426");
			votoId.setAssociado(associado);
			votacao.setVotoId(votoId);
			votacaoService.votar(votacao);
		});

		assertTrue(thrown.getMessage().contains("Associado não tem permissão para votar."));
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("Sucesso - Consulta da votação.")
	void consultarVotacao() {
		CriteriaBuilder cb = mock(CriteriaBuilder.class);
		when(entityManagerMock.getCriteriaBuilder()).thenReturn(cb);

		CriteriaQuery<Votacao> cq = mock(CriteriaQuery.class);
		when(cb.createQuery(eq(Votacao.class))).thenReturn(cq);

		Root<Votacao> votacaoRoot = mock(Root.class);
		when(cq.from(eq(Votacao.class))).thenReturn(votacaoRoot);
		
		Path<Object> pathGet1 = mock(Path.class);
		when(votacaoRoot.get(anyString())).thenReturn(pathGet1);
		
		Path<Object> pathGet2 = mock(Path.class);
		when(pathGet1.get(anyString())).thenReturn(pathGet2);
		
		Path<Object> pathGet3 = mock(Path.class);
		when(pathGet2.get(anyString())).thenReturn(pathGet3);

		TypedQuery<Votacao> query = mock(TypedQuery.class);
		when(entityManagerMock.createQuery(any(CriteriaQuery.class))).thenReturn(query);

		votacaoService.consultar(1L, Optional.of(1L), Optional.of(VotoEnum.SIM));

		Assertions.assertTrue(true, "Consulta executada com sucesso.");
	}

}
