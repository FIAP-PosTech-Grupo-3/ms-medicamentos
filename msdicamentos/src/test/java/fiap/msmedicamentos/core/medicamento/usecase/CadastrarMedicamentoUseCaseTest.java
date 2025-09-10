package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoInvalidoException;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarMedicamentoUseCaseTest {

    @Mock
    private MedicamentoGateway medicamentoGateway;

    @InjectMocks
    private CadastrarMedicamentoUseCase cadastrarMedicamentoUseCase;

    private Medicamento medicamentoValido;

    @BeforeEach
    void setUp() {
        medicamentoValido = new Medicamento(
            null,
            "Paracetamol",
            "Paracetamol",
            "EMS",
            "500mg"
        );
    }

    @Test
    void deveCadastrarMedicamentoComSucesso() {
        // Arrange
        Medicamento medicamentoSalvo = new Medicamento(
            1L,
            "Paracetamol",
            "Paracetamol",
            "EMS",
            "500mg"
        );

        when(medicamentoGateway.salvar(any(Medicamento.class))).thenReturn(medicamentoSalvo);

        // Act
        Medicamento resultado = cadastrarMedicamentoUseCase.execute(medicamentoValido);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Paracetamol", resultado.getNome());
        verify(medicamentoGateway).salvar(medicamentoValido);
    }

    @Test
    void deveLancarExcecaoQuandoMedicamentoInvalido() {
        // Arrange
        Medicamento medicamentoInvalido = new Medicamento(
            null,
            "",  // nome vazio
            "Paracetamol",
            "EMS",
            "500mg"
        );

        // Act & Assert
        assertThrows(MedicamentoInvalidoException.class, () -> {
            cadastrarMedicamentoUseCase.execute(medicamentoInvalido);
        });

        verify(medicamentoGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoMedicamentoNulo() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            cadastrarMedicamentoUseCase.execute(null);
        });

        verify(medicamentoGateway, never()).salvar(any());
    }
}
