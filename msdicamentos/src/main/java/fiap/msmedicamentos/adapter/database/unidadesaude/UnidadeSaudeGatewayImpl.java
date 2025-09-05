package fiap.msmedicamentos.adapter.database.unidadesaude;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import fiap.msmedicamentos.core.unidadesaude.gateway.UnidadeSaudeGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UnidadeSaudeGatewayImpl implements UnidadeSaudeGateway {
    
    private final UnidadeSaudeRepository repository;
    private final UnidadeSaudeMapper mapper;
    
    public UnidadeSaudeGatewayImpl(UnidadeSaudeRepository repository, UnidadeSaudeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    public UnidadeSaude salvar(UnidadeSaude unidadeSaude) {
        UnidadeSaudeEntity entity = mapper.toEntity(unidadeSaude);
        UnidadeSaudeEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<UnidadeSaude> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public Page<UnidadeSaude> buscarTodas(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDomain);
    }
    
    @Override
    public Page<UnidadeSaude> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(mapper::toDomain);
    }
    
    @Override
    public Page<UnidadeSaude> buscarAtivas(Pageable pageable) {
        return repository.findByAtivaTrue(pageable)
                .map(mapper::toDomain);
    }
    
    @Override
    public UnidadeSaude atualizar(UnidadeSaude unidadeSaude) {
        UnidadeSaudeEntity entity = mapper.toEntity(unidadeSaude);
        UnidadeSaudeEntity updatedEntity = repository.save(entity);
        return mapper.toDomain(updatedEntity);
    }
    
    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }
}
