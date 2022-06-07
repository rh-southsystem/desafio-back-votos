package br.com.southsystem.assembleia.servico;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.southsystem.assembleia.entidade.Associado;
import br.com.southsystem.assembleia.exception.EntidadeNaoProcessavelException;
import br.com.southsystem.assembleia.exception.ObjetoNaoEncontradoException;
import br.com.southsystem.assembleia.repositorio.AssociadoRepository;

@RunWith(MockitoJUnitRunner.class)
public class AssociadoServiceTest {

	private AssociadoService associadoService;

	@Mock
	private EntityManager entityManagerMock;

	@Mock
	private AssociadoRepository associadoRepositoryMock;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		this.associadoService = new AssociadoServiceImpl(this.entityManagerMock, this.associadoRepositoryMock);
	}

	@Test
	@DisplayName("EntidadeNaoProcessavelException - Criar associado com CPF já cadastrado na base de dados.")
	void criarComCpfJaCadastrado() {
		String cpf = "07911761426";
		when(associadoRepositoryMock.findByCpf(anyString()))
				.thenReturn(Optional.of(new Associado(1L, "MARCELL", cpf, false)));

		EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
			Associado associado = new Associado();
			associado.setNome("USUARIO NOVO");
			associado.setCpf(cpf);
			associadoService.criar(associado);
		});

		assertTrue(thrown.getMessage().contains(
				"Não foi possível efetuar o processamento do novo associado, pois já existe o CPF cadastrado na base de dados."));

	}

	@Test
	@DisplayName("Sucesso - Criar associado.")
	void criarAssociado() {
		String nome = "USUARIO NOVO";
		String cpf = "07911761426";

		when(associadoRepositoryMock.findByCpf(anyString())).thenReturn(Optional.empty());

		when(associadoRepositoryMock.save(any())).thenReturn(new Associado(1L, nome, cpf, true));

		Associado associado = new Associado();
		associado.setNome(nome);
		associado.setCpf(cpf);
		Optional<Associado> associadoBaseDeDadosOptional = associadoService.criar(associado);

		associadoBaseDeDadosOptional
				.ifPresent(associadoBaseDeDados -> Assertions.assertEquals(associadoBaseDeDados.getId(), 1L));

	}

	@Test
	@DisplayName("ObjetoNaoEncontradoException - Ao alterar, objeto informado não foi encontrado.")
	void alterarAssociadoNaoEncontrado() {
		when(associadoRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

		ObjetoNaoEncontradoException thrown = assertThrows(ObjetoNaoEncontradoException.class, () -> {
			Associado associadoAlterado = new Associado();
			associadoAlterado.setNome("USUARIO ALTERADO");
			associadoAlterado.setCpf("07911761426");
			associadoService.alterar(1L, associadoAlterado);
		});

		assertTrue(thrown.getMessage().contains("Objeto informado não foi encontrado."));

	}

	@Test
	@DisplayName("EntidadeNaoProcessavelException - Alterar associado com CPF já cadastrado na base de dados.")
	void alterarComCpfJaCadastrado() {
		when(associadoRepositoryMock.findById(anyLong()))
				.thenReturn(Optional.of(new Associado(1L, "MARCELL", "07911761426", false)));

		when(associadoRepositoryMock.findByCpf(anyString()))
				.thenReturn(Optional.of(new Associado(2L, "ANNYELLE", "0839941022", false)));

		EntidadeNaoProcessavelException thrown = assertThrows(EntidadeNaoProcessavelException.class, () -> {
			Associado associadoAlterado = new Associado();
			associadoAlterado.setNome("MARCELL");
			associadoAlterado.setCpf("0839941022");
			associadoAlterado.setFlExcluido(false);
			associadoService.alterar(1L, associadoAlterado);
		});

		assertTrue(thrown.getMessage().contains(
				"Não foi possível efetuar o processamento de alteração do associado, pois já existe o CPF cadastrado na base de dados."));
	}

	@Test
	@DisplayName("Sucesso - Alterar associado.")
	void alterarAssociado() {
		when(associadoRepositoryMock.findById(anyLong()))
				.thenReturn(Optional.of(new Associado(1L, "MARCELL", "07911761426", false)));

		when(associadoRepositoryMock.findByCpf(anyString()))
				.thenReturn(Optional.of(new Associado(1L, "MARCELL", "07911761426", false)));

		when(associadoRepositoryMock.save(any())).thenReturn(new Associado(1L, "ANNYELLE", "0839941022", true));

		Associado associadoAlterado = new Associado();
		associadoAlterado.setNome("ANNYELLE");
		associadoAlterado.setCpf("0839941022");
		associadoAlterado.setFlExcluido(false);
		Optional<Associado> associadoBaseDeDadosOptional = associadoService.alterar(1L, associadoAlterado);

		associadoBaseDeDadosOptional.ifPresent(associadoBaseDeDados -> Assertions
				.assertEquals(associadoBaseDeDados.getNome(), associadoAlterado.getNome()));

	}

	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("Sucesso - Consulta de associado.")
	void consultarAssociado() {
		CriteriaBuilder cb = mock(CriteriaBuilder.class);
		when(entityManagerMock.getCriteriaBuilder()).thenReturn(cb);

		CriteriaQuery<Associado> cq = mock(CriteriaQuery.class);
		when(cb.createQuery(eq(Associado.class))).thenReturn(cq);
		
		Root<Associado> associadoRoot = mock(Root.class);
		when(cq.from(eq(Associado.class))).thenReturn(associadoRoot);
		

		TypedQuery<Associado> query = mock(TypedQuery.class);
		when(entityManagerMock.createQuery(any(CriteriaQuery.class))).thenReturn(query);

		associadoService.consultar(Optional.of("1"),
				Optional.of("NOME"), Optional.of(false));

		Assertions.assertTrue(true, "Consulta executada com sucesso.");
	}
	
	@Test
	@DisplayName("Sucesso - Consulta por id de um associado.")
	void consultarPorId() {
		Associado associado = new Associado();
		Optional<Associado> associadoOptional = Optional.of(associado);
		when(associadoRepositoryMock.findById(anyLong())).thenReturn(associadoOptional);

		Optional<Associado> associadoOptionalBaseDeDadosOptional = associadoService.consultarPorId(1L);

		associadoOptionalBaseDeDadosOptional.ifPresent(associadoBaseDeDados -> Assertions
				.assertEquals(associadoBaseDeDados, associado));
	}
	
	@Test
	@DisplayName("Sucesso - Excluir associado.")
	void excluir() {
		Associado associado = new Associado();
		Optional<Associado> associadoOptional = Optional.of(associado);
		when(associadoRepositoryMock.findById(anyLong())).thenReturn(associadoOptional);

		associadoService.excluir(1L);

		Assertions.assertTrue(true, "Associado excluído.");
	}
	
	@Test
	@DisplayName("ObjetoNaoEncontradoException - Ao excluir, objeto informado não foi encontrado.")
	void excluirAssociadoNaoEncontrado() {
		when(associadoRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

		ObjetoNaoEncontradoException thrown = assertThrows(ObjetoNaoEncontradoException.class, () -> {
			associadoService.excluir(1L);
		});

		assertTrue(thrown.getMessage().contains("Objeto informado não foi encontrado."));
	}

}
