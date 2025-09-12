package fiap.msmedicamentos.adapter.database.medicamento;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicamentoMapperTest {

    private final MedicamentoMapper mapper = new MedicamentoMapper();

    @Test
    void deveConverterDomainParaEntity() {
        // Arrange
        Medicamento domain = new Medicamento(1L, "Paracetamol", "Paracetamol", "Medley", "500mg");

        // Act
        MedicamentoEntity entity = mapper.toEntity(domain);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("Paracetamol", entity.getNome());
        assertEquals("Paracetamol", entity.getPrincipioAtivo());
        assertEquals("Medley", entity.getFabricante());
        assertEquals("500mg", entity.getDosagem());
    }

    @Test
    void deveConverterEntityParaDomain() {
        // Arrange
        MedicamentoEntity entity = new MedicamentoEntity();
        entity.setId(2L);
        entity.setNome("Ibuprofeno");
        entity.setPrincipioAtivo("Ibuprofeno");
        entity.setFabricante("Bayer");
        entity.setDosagem("400mg");

        // Act
        Medicamento domain = mapper.toDomain(entity);

        // Assert
        assertEquals(2L, domain.getId());
        assertEquals("Ibuprofeno", domain.getNome());
        assertEquals("Ibuprofeno", domain.getPrincipioAtivo());
        assertEquals("Bayer", domain.getFabricante());
        assertEquals("400mg", domain.getDosagem());
    }
}
