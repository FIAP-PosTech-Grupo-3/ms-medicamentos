package fiap.msmedicamentos.adapter.database.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    
    Optional<UsuarioEntity> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM UsuarioEntity u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<UsuarioEntity> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);
    
    Page<UsuarioEntity> findByAtivoTrue(Pageable pageable);
    
    long countByAtivoTrue();
}
