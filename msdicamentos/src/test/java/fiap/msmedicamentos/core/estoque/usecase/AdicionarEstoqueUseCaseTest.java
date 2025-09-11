package fiap.msmedicamentos.core.estoque.usecase;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import fiap.msmedicamentos.core.estoque.exception.EstoqueInvalidoException;
import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import fiap.msmedicamentos.core.estoque.service.EstoqueValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdicionarEstoqueUseCaseTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @Mock
    private EstoqueValidationService validationService;

    @InjectMocks
    private AdicionarEstoqueUseCase adicionarEstoqueUseCase;

    private EstoqueMedicamento estoqueExistente;

    @BeforeEach
    void setUp() {
        estoqueExistente = new EstoqueMedicamento(
            1L,
            1L,
            1L,
            50,
            10
        );
    }

    @Test
    void deveAdicionarEstoqueQuandoJaExiste() {
        // Arrange
        when(estoqueGateway.buscarPorMedicamentoEUnidade(1L, 1L))
            .thenReturn(Optional.of(estoqueExistente));
        when(estoqueGateway.salvar(any(EstoqueMedicamento.class)))
            .thenReturn(estoqueExistente);

        // Act
        EstoqueMedicamento resultado = adicionarEstoqueUseCase.execute(1L, 1L, 20);

        // Assert
        assertNotNull(resultado);
        assertEquals(70, resultado.getQuantidade()); // 50 + 20
        verify(validationService).validarQuantidadePositiva(20);
        verify(validationService).validarExistenciaMedicamentoEUnidade(1L, 1L);
        verify(estoqueGateway).buscarPorMedicamentoEUnidade(1L, 1L);
        verify(estoqueGateway).salvar(estoqueExistente);
    }

    @Test
    void deveCriarNovoEstoqueQuandoNaoExiste() {
        // Arrange
        EstoqueMedicamento estoqueSalvo = new EstoqueMedicamento(
            2L,
            1L,
            1L,
            30,
            10
        );

        when(estoqueGateway.buscarPorMedicamentoEUnidade(1L, 1L))
            .thenReturn(Optional.empty());
        when(estoqueGateway.salvar(any(EstoqueMedicamento.class)))
            .thenReturn(estoqueSalvo);

        // Act
        EstoqueMedicamento resultado = adicionarEstoqueUseCase.execute(1L, 1L, 30);

        // Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals(30, resultado.getQuantidade());
        verify(validationService).validarQuantidadePositiva(30);
        verify(validationService).validarExistenciaMedicamentoEUnidade(1L, 1L);
        verify(estoqueGateway).buscarPorMedicamentoEUnidade(1L, 1L);
        verify(estoqueGateway).salvar(any(EstoqueMedicamento.class));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeInvalida() {
        // Arrange
        doThrow(new EstoqueInvalidoException("Quantidade deve ser positiva"))
            .when(validationService).validarQuantidadePositiva(-5);

        // Act & Assert
        assertThrows(EstoqueInvalidoException.class, () -> {
            adicionarEstoqueUseCase.execute(1L, 1L, -5);
        });

        verify(validationService).validarQuantidadePositiva(-5);
        verify(estoqueGateway, never()).buscarPorMedicamentoEUnidade(any(), any());
        verify(estoqueGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoMedicamentoOuUnidadeInvalidos() {
        // Arrange
        doThrow(new EstoqueInvalidoException("Medicamento ou unidade não encontrados"))
            .when(validationService).validarExistenciaMedicamentoEUnidade(999L, 999L);

        // Act & Assert
        assertThrows(EstoqueInvalidoException.class, () -> {
            adicionarEstoqueUseCase.execute(999L, 999L, 10);
        });

        verify(validationService).validarQuantidadePositiva(10);
        verify(validationService).validarExistenciaMedicamentoEUnidade(999L, 999L);
        verify(estoqueGateway, never()).buscarPorMedicamentoEUnidade(any(), any());
        verify(estoqueGateway, never()).salvar(any());
    }
}
