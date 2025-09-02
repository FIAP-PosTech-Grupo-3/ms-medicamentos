package fiap.msmedicamentos.adapter.database.medicamento;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.gateway.MedicamentoGateway;
import org.springframework.stereotype.Component;

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
}
