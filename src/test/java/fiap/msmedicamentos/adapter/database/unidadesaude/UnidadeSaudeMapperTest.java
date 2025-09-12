package fiap.msmedicamentos.adapter.database.unidadesaude;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeSaudeMapperTest {

    private final UnidadeSaudeMapper mapper = new UnidadeSaudeMapper();

    @Test
    void deveConverterDomainParaEntity() {
        // Arrange
        UnidadeSaude domain = new UnidadeSaude(1L, "Hospital Central", "Rua das Flores, 123", true);

        // Act
        UnidadeSaudeEntity entity = mapper.toEntity(domain);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("Hospital Central", entity.getNome());
        assertEquals("Rua das Flores, 123", entity.getEndereco());
        assertTrue(entity.getAtiva());
    }

    @Test
    void deveConverterEntityParaDomain() {
        // Arrange
        UnidadeSaudeEntity entity = new UnidadeSaudeEntity();
        entity.setId(2L);
        entity.setNome("Clínica São João");
        entity.setEndereco("Av. Principal, 456");
        entity.setAtiva(false);

        // Act
        UnidadeSaude domain = mapper.toDomain(entity);

        // Assert
        assertEquals(2L, domain.getId());
        assertEquals("Clínica São João", domain.getNome());
        assertEquals("Av. Principal, 456", domain.getEndereco());
        assertFalse(domain.isAtiva());
    }
}
