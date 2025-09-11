package fiap.msmedicamentos.adapter.web.medicamento;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MedicamentoWebMapper {
    
    public Medicamento toDomain(CadastrarMedicamentoRequest request) {
        return new Medicamento(
            null,
            request.getNome(),
            request.getPrincipioAtivo(),
            request.getFabricante(),
            request.getDosagem()
        );
    }
    
    public MedicamentoResponse toResponse(Medicamento medicamento) {
        if (medicamento == null) return null;
        
        MedicamentoResponse response = new MedicamentoResponse();
        response.setId(medicamento.getId());
        response.setNome(medicamento.getNome());
        response.setPrincipioAtivo(medicamento.getPrincipioAtivo());
        response.setFabricante(medicamento.getFabricante());
        response.setDosagem(medicamento.getDosagem());
        
        return response;
    }
    
    public PagedMedicamentoResponse toPagedResponse(Page<Medicamento> page) {
        if (page == null) return null;
        
        PagedMedicamentoResponse response = new PagedMedicamentoResponse();
        response.setContent(page.getContent().stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());
        response.setEmpty(page.isEmpty());
        
        return response;
    }
}
