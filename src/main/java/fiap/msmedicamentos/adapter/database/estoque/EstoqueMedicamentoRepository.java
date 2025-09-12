package fiap.msmedicamentos.adapter.database.estoque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueMedicamentoRepository extends JpaRepository<EstoqueMedicamentoEntity, Long> {
    
    Optional<EstoqueMedicamentoEntity> findByMedicamentoIdAndUnidadeSaudeId(Long medicamentoId, Long unidadeSaudeId);
    
    List<EstoqueMedicamentoEntity> findByMedicamentoId(Long medicamentoId);
    
    List<EstoqueMedicamentoEntity> findByUnidadeSaudeId(Long unidadeSaudeId);
    
    @Query("SELECT e FROM EstoqueMedicamentoEntity e WHERE e.quantidade <= e.quantidadeMinima AND e.quantidadeMinima IS NOT NULL")
    List<EstoqueMedicamentoEntity> findEstoqueComQuantidadeBaixa();
    
    @Query("SELECT e FROM EstoqueMedicamentoEntity e WHERE e.quantidade = 0")
    List<EstoqueMedicamentoEntity> findEstoqueZerado();
}
