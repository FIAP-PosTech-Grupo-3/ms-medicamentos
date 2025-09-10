package fiap.msmedicamentos.adapter.web.medicamento;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Dados de resposta do medicamento")
public class MedicamentoResponse {
    
    @Schema(description = "ID único do medicamento", example = "1")
    private Long id;
    
    @Schema(description = "Nome do medicamento", example = "Paracetamol")
    private String nome;
    
    @Schema(description = "Princípio ativo do medicamento", example = "Paracetamol")
    private String principioAtivo;
    
    @Schema(description = "Fabricante do medicamento", example = "EMS")
    private String fabricante;
    
    @Schema(description = "Dosagem do medicamento", example = "500mg")
    private String dosagem;
}
