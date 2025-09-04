package fiap.msmedicamentos.adapter.web.medicamento;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MedicamentoWebMapper {
    
    public Medicamento toDomain(CadastrarMedicamentoRequest request) {
        if (request == null) return null;
        
        return new Medicamento(
            null,
            request.getNome(),
            request.getTipo(),
            request.getFabricante(),
            request.getDataValidade(),
            request.getDosagem(),
            request.getFormaFarmaceutica(),
            request.getPrincipioAtivo(),
            request.getLote(),
            request.getDataFabricacao(),
            request.getPrecisaReceita() != null ? request.getPrecisaReceita() : false
        );
    }
    
    public MedicamentoResponse toResponse(Medicamento medicamento) {
        if (medicamento == null) return null;
        
        MedicamentoResponse response = new MedicamentoResponse();
        response.setId(medicamento.getId());
        response.setNome(medicamento.getNome());
        response.setTipo(medicamento.getTipo());
        response.setFabricante(medicamento.getFabricante());
        response.setDataValidade(medicamento.getDataValidade());
        response.setDosagem(medicamento.getDosagem());
        response.setFormaFarmaceutica(medicamento.getFormaFarmaceutica());
        response.setPrincipioAtivo(medicamento.getPrincipioAtivo());
        response.setLote(medicamento.getLote());
        response.setDataFabricacao(medicamento.getDataFabricacao());
        response.setPrecisaReceita(medicamento.isPrecisaReceita());
        
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
