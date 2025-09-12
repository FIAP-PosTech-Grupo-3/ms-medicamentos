package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoNaoEncontradoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarMedicamentoUseCaseTest {

    @Mock
    private MedicamentoGateway gateway;

    @Mock 
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private DeletarMedicamentoUseCase deletarMedicamentoUseCase;

    @Test
    void deveDeletarMedicamentoComSucesso() {
        // Arrange
        Long medicamentoId = 1L;
        when(gateway.existePorId(medicamentoId)).thenReturn(true);
        when(estoqueGateway.buscarPorMedicamento(medicamentoId)).thenReturn(List.of());

        // Act
        assertDoesNotThrow(() -> {
            deletarMedicamentoUseCase.executar(medicamentoId);
        });

        // Assert
        verify(gateway).existePorId(medicamentoId);
        verify(estoqueGateway).buscarPorMedicamento(medicamentoId);
        verify(gateway).deletar(medicamentoId);
    }

    @Test
    void deveLancarExcecaoQuandoMedicamentoNaoExiste() {
        // Arrange
        Long medicamentoId = 999L;
        when(gateway.existePorId(medicamentoId)).thenReturn(false);

        // Act & Assert
        assertThrows(MedicamentoNaoEncontradoException.class, () -> {
            deletarMedicamentoUseCase.executar(medicamentoId);
        });

        verify(gateway).existePorId(medicamentoId);
        verify(gateway, never()).deletar(any());
    }
}
