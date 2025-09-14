package fiap.msmedicamentos.core.estoque.usecase;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarEstoqueUseCaseTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private ConsultarEstoqueUseCase consultarEstoqueUseCase;

    @Test
    void deveBuscarEstoquePorMedicamento() {
        // Arrange
        Long medicamentoId = 1L;
        EstoqueMedicamento estoque = new EstoqueMedicamento(1L, medicamentoId, 1L, 50, 10, null);
        
        when(estoqueGateway.buscarPorMedicamento(medicamentoId))
            .thenReturn(java.util.Arrays.asList(estoque));

        // Act
        java.util.List<EstoqueMedicamento> resultado = consultarEstoqueUseCase.buscarPorMedicamento(medicamentoId);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(50, resultado.get(0).getQuantidade());
        verify(estoqueGateway).buscarPorMedicamento(medicamentoId);
    }

    @Test
    void deveRetornarListaVaziaQuandoEstoqueNaoExiste() {
        // Arrange
        Long medicamentoId = 999L;
        
        when(estoqueGateway.buscarPorMedicamento(medicamentoId))
            .thenReturn(java.util.Arrays.asList());

        // Act
        java.util.List<EstoqueMedicamento> resultado = consultarEstoqueUseCase.buscarPorMedicamento(medicamentoId);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(estoqueGateway).buscarPorMedicamento(medicamentoId);
    }

    @Test
    void deveListarTodosEstoquesPaginados() {
        // Arrange
        EstoqueMedicamento estoque1 = new EstoqueMedicamento(1L, 1L, 1L, 50, 10, null);
        EstoqueMedicamento estoque2 = new EstoqueMedicamento(2L, 2L, 1L, 30, 5, null);
        Page<EstoqueMedicamento> pagina = new PageImpl<>(java.util.Arrays.asList(estoque1, estoque2));
        PageRequest pageRequest = PageRequest.of(0, 10);
        
        when(estoqueGateway.buscarTodos(pageRequest)).thenReturn(pagina);

        // Act
        Page<EstoqueMedicamento> resultado = consultarEstoqueUseCase.buscarTodos(pageRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        verify(estoqueGateway).buscarTodos(pageRequest);
    }
}
