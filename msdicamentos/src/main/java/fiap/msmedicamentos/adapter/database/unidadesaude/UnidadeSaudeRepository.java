package fiap.msmedicamentos.adapter.database.unidadesaude;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeSaudeRepository extends JpaRepository<UnidadeSaudeEntity, Long> {
    
    @Query("SELECT u FROM UnidadeSaudeEntity u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<UnidadeSaudeEntity> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);
    
    Page<UnidadeSaudeEntity> findByAtivaTrue(Pageable pageable);
}
