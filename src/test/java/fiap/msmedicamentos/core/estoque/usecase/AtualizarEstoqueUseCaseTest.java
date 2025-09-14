package fiap.msmedicamentos.core.estoque.usecase;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import fiap.msmedicamentos.core.estoque.service.EstoqueValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarEstoqueUseCaseTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @Mock
    private EstoqueValidationService validationService;

    @InjectMocks
    private AtualizarEstoqueUseCase atualizarEstoqueUseCase;

    private EstoqueMedicamento estoqueExistente;

    @BeforeEach
    void setUp() {
        estoqueExistente = new EstoqueMedicamento(
                1L,
                1L,
                1L,
                100,
                20,
                LocalDateTime.now().minusDays(1)
        );
    }

    @Test
    void deveAtualizarEstoqueExistenteComSucesso() {
        // Arrange
        when(estoqueGateway.buscarPorMedicamentoEUnidade(1L, 1L)).thenReturn(Optional.of(estoqueExistente));
        when(estoqueGateway.salvar(any(EstoqueMedicamento.class))).thenAnswer(invocation -> {
            EstoqueMedicamento es = invocation.getArgument(0);
            es.setUltimaAtualizacao(LocalDateTime.now());
            return es;
        });

        // Act
        EstoqueMedicamento resultado = atualizarEstoqueUseCase.execute(1L, 1L, 150, 30);

        // Assert
        assertNotNull(resultado);
        assertEquals(150, resultado.getQuantidade());
        assertEquals(30, resultado.getQuantidadeMinima());
        assertNotNull(resultado.getUltimaAtualizacao());
        verify(validationService).validarQuantidadeNaoNegativa(150);
        verify(validationService).validarExistenciaMedicamentoEUnidade(1L, 1L);
        verify(estoqueGateway).salvar(estoqueExistente);
    }

    @Test
    void deveCriarNovoEstoqueAoTentarAtualizarInexistente() {
        // Arrange
        when(estoqueGateway.buscarPorMedicamentoEUnidade(2L, 2L)).thenReturn(Optional.empty());
        when(estoqueGateway.salvar(any(EstoqueMedicamento.class))).thenAnswer(invocation -> {
            EstoqueMedicamento es = invocation.getArgument(0);
            es.setId(2L); // Simula a criação de um novo ID
            es.setUltimaAtualizacao(LocalDateTime.now());
            return es;
        });

        // Act
        EstoqueMedicamento resultado = atualizarEstoqueUseCase.execute(2L, 2L, 50, 10);

        // Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals(50, resultado.getQuantidade());
        assertEquals(10, resultado.getQuantidadeMinima());
        assertNotNull(resultado.getUltimaAtualizacao());
        verify(validationService).validarQuantidadeNaoNegativa(50);
        verify(validationService).validarExistenciaMedicamentoEUnidade(2L, 2L);
        verify(estoqueGateway).salvar(any(EstoqueMedicamento.class));
    }
}
