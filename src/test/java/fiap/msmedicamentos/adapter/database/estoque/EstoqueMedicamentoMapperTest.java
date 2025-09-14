package fiap.msmedicamentos.adapter.database.estoque;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueMedicamentoMapperTest {

    private final EstoqueMedicamentoMapper mapper = new EstoqueMedicamentoMapper();

    @Test
    void deveConverterDomainParaEntity() {
        // Arrange
        EstoqueMedicamento domain = new EstoqueMedicamento(1L, 10L, 5L, 100, 20, null);

        // Act
        EstoqueMedicamentoEntity entity = mapper.toEntity(domain);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals(10L, entity.getMedicamentoId());
        assertEquals(5L, entity.getUnidadeSaudeId());
        assertEquals(100, entity.getQuantidade());
        assertEquals(20, entity.getQuantidadeMinima());
    }

    @Test
    void deveConverterEntityParaDomain() {
        // Arrange
        EstoqueMedicamentoEntity entity = new EstoqueMedicamentoEntity();
        entity.setId(2L);
        entity.setMedicamentoId(15L);
        entity.setUnidadeSaudeId(8L);
        entity.setQuantidade(50);
        entity.setQuantidadeMinima(10);
        entity.setUltimaAtualizacao(java.time.LocalDateTime.now());

        // Act
        EstoqueMedicamento domain = mapper.toDomain(entity);

        // Assert
        assertEquals(2L, domain.getId());
        assertEquals(15L, domain.getMedicamentoId());
        assertEquals(8L, domain.getUnidadeSaudeId());
        assertEquals(50, domain.getQuantidade());
        assertEquals(10, domain.getQuantidadeMinima());
        assertNotNull(domain.getUltimaAtualizacao());
    }
}
