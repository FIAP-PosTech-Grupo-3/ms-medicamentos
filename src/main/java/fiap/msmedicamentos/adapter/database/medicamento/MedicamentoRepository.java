package fiap.msmedicamentos.adapter.database.medicamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends JpaRepository<MedicamentoEntity, Long> {
    
    @Query("SELECT m FROM MedicamentoEntity m WHERE LOWER(m.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<MedicamentoEntity> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);
}
