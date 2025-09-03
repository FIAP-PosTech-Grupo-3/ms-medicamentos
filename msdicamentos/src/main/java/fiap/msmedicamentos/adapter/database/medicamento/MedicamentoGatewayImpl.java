package fiap.msmedicamentos.adapter.database.medicamento;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MedicamentoGatewayImpl implements MedicamentoGateway {
    
    private final MedicamentoRepository repository;
    private final MedicamentoMapper mapper;
    
    public MedicamentoGatewayImpl(MedicamentoRepository repository, MedicamentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    public Medicamento salvar(Medicamento medicamento) {
        MedicamentoEntity entity = mapper.toEntity(medicamento);
        MedicamentoEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Medicamento> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public Page<Medicamento> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDomain);
    }
    
    @Override
    public Page<Medicamento> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(mapper::toDomain);
    }
    
    @Override
    public Medicamento atualizar(Medicamento medicamento) {
        MedicamentoEntity entity = mapper.toEntity(medicamento);
        MedicamentoEntity updatedEntity = repository.save(entity);
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
