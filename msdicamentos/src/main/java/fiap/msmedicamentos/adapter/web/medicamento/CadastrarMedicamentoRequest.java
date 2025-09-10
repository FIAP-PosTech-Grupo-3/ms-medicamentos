package fiap.msmedicamentos.adapter.web.medicamento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Dados para cadastro/atualização de medicamento")
public class CadastrarMedicamentoRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome do medicamento", example = "Paracetamol", required = true)
    private String nome;
    
    @Schema(description = "Princípio ativo do medicamento", example = "Paracetamol")
    private String principioAtivo;
    
    @Schema(description = "Fabricante do medicamento", example = "EMS")
    private String fabricante;
    
    @Schema(description = "Dosagem do medicamento", example = "500mg")
    private String dosagem;
}
