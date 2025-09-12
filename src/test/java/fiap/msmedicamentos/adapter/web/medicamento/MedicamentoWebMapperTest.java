package fiap.msmedicamentos.adapter.web.medicamento;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicamentoWebMapperTest {

    private final MedicamentoWebMapper mapper = new MedicamentoWebMapper();

    @Test
    void deveConverterRequestParaDomain() {
        // Arrange
        CadastrarMedicamentoRequest request = new CadastrarMedicamentoRequest();
        request.setNome("Dipirona");
        request.setPrincipioAtivo("Dipirona Sódica");
        request.setFabricante("Neo Química");
        request.setDosagem("500mg/ml");

        // Act
        Medicamento domain = mapper.toDomain(request);

        // Assert
        assertNull(domain.getId());
        assertEquals("Dipirona", domain.getNome());
        assertEquals("Dipirona Sódica", domain.getPrincipioAtivo());
        assertEquals("Neo Química", domain.getFabricante());
        assertEquals("500mg/ml", domain.getDosagem());
    }

    @Test
    void deveConverterDomainParaResponse() {
        // Arrange
        Medicamento domain = new Medicamento(5L, "Amoxicilina", "Amoxicilina", "Eurofarma", "875mg");

        // Act
        MedicamentoResponse response = mapper.toResponse(domain);

        // Assert
        assertEquals(5L, response.getId());
        assertEquals("Amoxicilina", response.getNome());
        assertEquals("Amoxicilina", response.getPrincipioAtivo());
        assertEquals("Eurofarma", response.getFabricante());
        assertEquals("875mg", response.getDosagem());
    }
}
