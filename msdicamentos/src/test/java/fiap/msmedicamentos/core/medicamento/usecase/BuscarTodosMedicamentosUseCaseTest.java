package fiap.msmedicamentos.core.medicamento.usecase;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarTodosMedicamentosUseCaseTest {

    @Mock
    private MedicamentoGateway medicamentoGateway;

    @InjectMocks
    private BuscarTodosMedicamentosUseCase buscarTodosMedicamentosUseCase;

    @Test
    void deveRetornarListaDeMedicamentos() {
        // Arrange
        List<Medicamento> medicamentos = List.of(
            new Medicamento(1L, "Paracetamol", "Paracetamol", "EMS", "500mg"),
            new Medicamento(2L, "Dipirona", "Dipirona", "Medley", "500mg")
        );
        Page<Medicamento> pagina = new PageImpl<>(medicamentos);
        PageRequest pageRequest = PageRequest.of(0, 10);
        
        when(medicamentoGateway.buscarTodos(pageRequest)).thenReturn(pagina);

        // Act
        Page<Medicamento> resultado = buscarTodosMedicamentosUseCase.execute(pageRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        assertEquals("Paracetamol", resultado.getContent().get(0).getNome());
        verify(medicamentoGateway).buscarTodos(pageRequest);
    }

    @Test
    void deveRetornarPaginaVaziaQuandoNaoHouverMedicamentos() {
        // Arrange
        Page<Medicamento> paginaVazia = new PageImpl<>(List.of());
        PageRequest pageRequest = PageRequest.of(0, 10);
        
        when(medicamentoGateway.buscarTodos(pageRequest)).thenReturn(paginaVazia);

        // Act
        Page<Medicamento> resultado = buscarTodosMedicamentosUseCase.execute(pageRequest);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(medicamentoGateway).buscarTodos(pageRequest);
    }
}
