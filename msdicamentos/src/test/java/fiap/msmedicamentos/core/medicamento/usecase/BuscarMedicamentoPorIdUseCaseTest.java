package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoNaoEncontradoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarMedicamentoPorIdUseCaseTest {

    @Mock
    private MedicamentoGateway medicamentoGateway;

    @InjectMocks
    private BuscarMedicamentoPorIdUseCase buscarMedicamentoPorIdUseCase;

    private Medicamento medicamento;

    @BeforeEach
    void setUp() {
        medicamento = new Medicamento(
            1L,
            "Paracetamol",
            "Paracetamol",
            "EMS",
            "500mg"
        );
    }

    @Test
    void deveRetornarMedicamentoQuandoEncontrado() {
        // Arrange
        when(medicamentoGateway.buscarPorId(1L)).thenReturn(Optional.of(medicamento));

        // Act
        Medicamento resultado = buscarMedicamentoPorIdUseCase.execute(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Paracetamol", resultado.getNome());
        verify(medicamentoGateway).buscarPorId(1L);
    }

    @Test
    void deveLancarExcecaoQuandoMedicamentoNaoEncontrado() {
        // Arrange
        when(medicamentoGateway.buscarPorId(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MedicamentoNaoEncontradoException.class, () -> {
            buscarMedicamentoPorIdUseCase.execute(999L);
        });

        verify(medicamentoGateway).buscarPorId(999L);
    }

    @Test
    void deveLancarExcecaoQuandoIdNulo() {
        // Act & Assert
        assertThrows(MedicamentoNaoEncontradoException.class, () -> {
            buscarMedicamentoPorIdUseCase.execute(null);
        });

        verify(medicamentoGateway, never()).buscarPorId(any());
    }
}
