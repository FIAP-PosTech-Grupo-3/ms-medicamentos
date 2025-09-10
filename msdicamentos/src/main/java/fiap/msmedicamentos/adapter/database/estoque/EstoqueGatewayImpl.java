package fiap.msmedicamentos.adapter.database.estoque;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import fiap.msmedicamentos.core.estoque.gateway.EstoqueGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EstoqueGatewayImpl implements EstoqueGateway {

    private final EstoqueMedicamentoRepository repository;
    private final EstoqueMedicamentoMapper mapper;

    @Override
    public EstoqueMedicamento salvar(EstoqueMedicamento estoque) {
        EstoqueMedicamentoEntity entity = mapper.toEntity(estoque);
        EstoqueMedicamentoEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<EstoqueMedicamento> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<EstoqueMedicamento> buscarPorMedicamentoEUnidade(Long medicamentoId, Long unidadeSaudeId) {
        return repository.findByMedicamentoIdAndUnidadeSaudeId(medicamentoId, unidadeSaudeId)
                .map(mapper::toDomain);
    }

    @Override
    public List<EstoqueMedicamento> buscarPorMedicamento(Long medicamentoId) {
        return repository.findByMedicamentoId(medicamentoId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<EstoqueMedicamento> buscarPorUnidadeSaude(Long unidadeSaudeId) {
        return repository.findByUnidadeSaudeId(unidadeSaudeId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EstoqueMedicamento> buscarTodos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public List<EstoqueMedicamento> buscarComEstoqueBaixo() {
        return repository.findEstoqueComQuantidadeBaixa()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public EstoqueMedicamento atualizar(EstoqueMedicamento estoque) {
        return salvar(estoque);
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
