package fiap.msmedicamentos.adapter.web.medicamento;

import fiap.msmedicamentos.core.medicamento.enums.FormaFarmaceutica;
import fiap.msmedicamentos.core.medicamento.enums.TipoMedicamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Dados para cadastro/atualização de medicamento")
public class CadastrarMedicamentoRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome do medicamento", example = "Paracetamol", required = true)
    private String nome;
    
    @NotNull(message = "Tipo é obrigatório")
    @Schema(description = "Tipo do medicamento", example = "ANALGESICO", required = true)
    private TipoMedicamento tipo;
    
    @Schema(description = "Fabricante do medicamento", example = "EMS")
    private String fabricante;
    
    @NotNull(message = "Data de validade é obrigatória")
    @Future(message = "Data de validade deve ser futura")
    @Schema(description = "Data de validade do medicamento", example = "2025-12-31")
    private LocalDate dataValidade;
    
    @Schema(description = "Dosagem do medicamento", example = "500mg")
    private String dosagem;
    
    @Schema(description = "Forma farmacêutica", example = "COMPRIMIDO")
    private FormaFarmaceutica formaFarmaceutica;
    
    @Schema(description = "Princípio ativo do medicamento", example = "Paracetamol")
    private String principioAtivo;
    
    @Schema(description = "Lote do medicamento", example = "LOT123456")
    private String lote;
    
    @Schema(description = "Data de fabricação", example = "2024-01-15")
    private LocalDate dataFabricacao;
    
    @Schema(description = "Indica se precisa de receita médica", example = "false")
    private Boolean precisaReceita;
}
