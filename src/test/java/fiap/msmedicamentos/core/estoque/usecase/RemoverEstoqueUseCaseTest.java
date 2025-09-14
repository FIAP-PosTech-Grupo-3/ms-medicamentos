package fiap.msmedicamentos.core.estoque.usecase;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import fiap.msmedicamentos.core.estoque.exception.EstoqueInvalidoException;
import fiap.msmedicamentos.core.estoque.exception.EstoqueNaoEncontradoException;
import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import fiap.msmedicamentos.core.estoque.service.EstoqueValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoverEstoqueUseCaseTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @Mock
    private EstoqueValidationService validationService;

    private RemoverEstoqueUseCase removerEstoqueUseCase;

    private EstoqueMedicamento estoqueExistente;

    @BeforeEach
    void setUp() {
        removerEstoqueUseCase = new RemoverEstoqueUseCase(estoqueGateway, validationService);
        
        estoqueExistente = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            100,
            10,
            null // ultimaAtualizacao
        );
    }

    @Test
    void deveRemoverEstoqueComSucesso() {
        // Arrange
        when(estoqueGateway.buscarPorMedicamentoEUnidade(1L, 1L))
            .thenReturn(Optional.of(estoqueExistente));
        when(estoqueGateway.salvar(any(EstoqueMedicamento.class)))
            .thenReturn(estoqueExistente);

        // Act
        EstoqueMedicamento resultado = removerEstoqueUseCase.execute(1L, 1L, 30);

        // Assert
        assertNotNull(resultado);
        assertEquals(70, resultado.getQuantidade()); // 100 - 30
        verify(validationService).validarQuantidadePositiva(30);
        verify(validationService).validarExistenciaMedicamentoEUnidade(1L, 1L);
        verify(estoqueGateway).buscarPorMedicamentoEUnidade(1L, 1L);
        verify(estoqueGateway).salvar(estoqueExistente);
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeInsuficiente() {
        // Arrange: Estoque com apenas 10 unidades
        EstoqueMedicamento estoqueComPouco = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            10,
            5,
            null // ultimaAtualizacao
        );
        
        when(estoqueGateway.buscarPorMedicamentoEUnidade(1L, 1L))
            .thenReturn(Optional.of(estoqueComPouco));

        // Act & Assert
        assertThrows(EstoqueInvalidoException.class, () -> {
            removerEstoqueUseCase.execute(1L, 1L, 50); // Tentando remover mais que o disponível
        });

        verify(validationService).validarQuantidadePositiva(50);
        verify(validationService).validarExistenciaMedicamentoEUnidade(1L, 1L);
        verify(estoqueGateway).buscarPorMedicamentoEUnidade(1L, 1L);
        verify(estoqueGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueNaoExiste() {
        // Arrange
        when(estoqueGateway.buscarPorMedicamentoEUnidade(999L, 999L))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EstoqueNaoEncontradoException.class, () -> {
            removerEstoqueUseCase.execute(999L, 999L, 10);
        });

        verify(validationService).validarQuantidadePositiva(10);
        verify(validationService).validarExistenciaMedicamentoEUnidade(999L, 999L);
        verify(estoqueGateway).buscarPorMedicamentoEUnidade(999L, 999L);
        verify(estoqueGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoTentaRemoverDeEstoqueInexistente() {
        // Arrange
        when(estoqueGateway.buscarPorMedicamentoEUnidade(1L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EstoqueNaoEncontradoException.class, () -> {
            removerEstoqueUseCase.execute(1L, 1L, 10);
        });
    }
}
