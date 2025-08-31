package fiap.msmedicamentos.adapter.web.medicamento;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import org.springframework.stereotype.Component;

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
            request.getQuantidadeEstoque(),
            request.getPrecisaReceita() != null ? request.getPrecisaReceita() : false,
            request.getEmFalta() != null ? request.getEmFalta() : false
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
        response.setQuantidadeEstoque(medicamento.getQuantidadeEstoque());
        response.setPrecisaReceita(medicamento.isPrecisaReceita());
        response.setEmFalta(medicamento.isEmFalta());
        
        return response;
    }
}
