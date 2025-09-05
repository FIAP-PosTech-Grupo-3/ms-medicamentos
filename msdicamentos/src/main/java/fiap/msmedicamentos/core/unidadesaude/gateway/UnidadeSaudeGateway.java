package fiap.msmedicamentos.core.unidadesaude.gateway;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UnidadeSaudeGateway {
    UnidadeSaude salvar(UnidadeSaude unidadeSaude);
    Optional<UnidadeSaude> buscarPorId(Long id);
    Page<UnidadeSaude> buscarTodas(Pageable pageable);
    Page<UnidadeSaude> buscarPorNome(String nome, Pageable pageable);
    Page<UnidadeSaude> buscarAtivas(Pageable pageable);
    UnidadeSaude atualizar(UnidadeSaude unidadeSaude);
    void deletar(Long id);
    boolean existePorId(Long id);
}
