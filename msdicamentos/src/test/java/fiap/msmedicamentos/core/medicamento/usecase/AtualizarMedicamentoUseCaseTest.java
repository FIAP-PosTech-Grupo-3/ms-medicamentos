package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoInvalidoException;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoNaoEncontradoException;
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
class AtualizarMedicamentoUseCaseTest {

    @Mock
    private MedicamentoGateway medicamentoGateway;

    @InjectMocks
    private AtualizarMedicamentoUseCase atualizarMedicamentoUseCase;

    private Medicamento medicamentoValido;

    @BeforeEach
    void setUp() {
        medicamentoValido = new Medicamento(
            null,
            "Aspirina",
            "Ácido Acetilsalicílico",
            "Bayer",
            "100mg"
        );
    }

    @Test
    void deveAtualizarMedicamentoComSucesso() {
        // Arrange
        Long medicamentoId = 1L;
        Medicamento medicamentoAtualizado = new Medicamento(
            medicamentoId,
            "Aspirina",
            "Ácido Acetilsalicílico", 
            "Bayer",
            "100mg"
        );

        when(medicamentoGateway.existePorId(medicamentoId)).thenReturn(true);
        when(medicamentoGateway.atualizar(any(Medicamento.class))).thenReturn(medicamentoAtualizado);

        // Act
        Medicamento resultado = atualizarMedicamentoUseCase.executar(medicamentoId, medicamentoValido);

        // Assert
        assertNotNull(resultado);
        assertEquals(medicamentoId, resultado.getId());
        assertEquals("Aspirina", resultado.getNome());
        assertEquals("Ácido Acetilsalicílico", resultado.getPrincipioAtivo());
        
        verify(medicamentoGateway).existePorId(medicamentoId);
        verify(medicamentoGateway).atualizar(any(Medicamento.class));
    }

    @Test
    void deveLancarExcecaoQuandoIdNulo() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            atualizarMedicamentoUseCase.executar(null, medicamentoValido);
        });

        assertEquals("ID do medicamento é obrigatório", exception.getMessage());
        verify(medicamentoGateway, never()).existePorId(any());
        verify(medicamentoGateway, never()).atualizar(any());
    }

    @Test
    void deveLancarExcecaoQuandoMedicamentoNaoEncontrado() {
        // Arrange
        Long medicamentoId = 999L;
        when(medicamentoGateway.existePorId(medicamentoId)).thenReturn(false);

        // Act & Assert
        MedicamentoNaoEncontradoException exception = assertThrows(MedicamentoNaoEncontradoException.class, () -> {
            atualizarMedicamentoUseCase.executar(medicamentoId, medicamentoValido);
        });

        assertTrue(exception.getMessage().contains("999"));
        verify(medicamentoGateway).existePorId(medicamentoId);
        verify(medicamentoGateway, never()).atualizar(any());
    }

    @Test
    void deveLancarExcecaoQuandoMedicamentoInvalido() {
        // Arrange
        Long medicamentoId = 1L;
        Medicamento medicamentoInvalido = new Medicamento(
            null,
            "",  // nome vazio - inválido
            "Princípio Ativo",
            "Fabricante",
            "100mg"
        );

        when(medicamentoGateway.existePorId(medicamentoId)).thenReturn(true);

        // Act & Assert
        MedicamentoInvalidoException exception = assertThrows(MedicamentoInvalidoException.class, () -> {
            atualizarMedicamentoUseCase.executar(medicamentoId, medicamentoInvalido);
        });

        assertEquals("Campo obrigatório: Dados do medicamento são inválidos", exception.getMessage());
        verify(medicamentoGateway).existePorId(medicamentoId);
        verify(medicamentoGateway, never()).atualizar(any());
    }

    @Test
    void deveLancarExcecaoQuandoPrincipioAtivoInvalido() {
        // Arrange
        Long medicamentoId = 1L;
        Medicamento medicamentoInvalido = new Medicamento(
            null,
            "Nome Válido",
            "",  // princípio ativo vazio - inválido
            "Fabricante",
            "100mg"
        );

        when(medicamentoGateway.existePorId(medicamentoId)).thenReturn(true);

        // Act & Assert
        assertThrows(MedicamentoInvalidoException.class, () -> {
            atualizarMedicamentoUseCase.executar(medicamentoId, medicamentoInvalido);
        });

        verify(medicamentoGateway).existePorId(medicamentoId);
        verify(medicamentoGateway, never()).atualizar(any());
    }

    @Test
    void deveDefinirIdCorretamenteNoMedicamento() {
        // Arrange
        Long medicamentoId = 5L;
        when(medicamentoGateway.existePorId(medicamentoId)).thenReturn(true);
        when(medicamentoGateway.atualizar(any(Medicamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        atualizarMedicamentoUseCase.executar(medicamentoId, medicamentoValido);

        // Assert - verifica se o ID foi setado corretamente no medicamento
        verify(medicamentoGateway).atualizar(argThat(medicamento -> 
            medicamento.getId().equals(medicamentoId)
        ));
    }
}
