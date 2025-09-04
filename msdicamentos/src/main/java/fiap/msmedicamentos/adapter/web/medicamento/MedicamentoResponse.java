package fiap.msmedicamentos.adapter.web.medicamento;

import fiap.msmedicamentos.core.medicamento.enums.FormaFarmaceutica;
import fiap.msmedicamentos.core.medicamento.enums.TipoMedicamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Dados de resposta do medicamento")
public class MedicamentoResponse {
    
    @Schema(description = "ID único do medicamento", example = "1")
    private Long id;
    
    @Schema(description = "Nome do medicamento", example = "Paracetamol")
    private String nome;
    
    @Schema(description = "Tipo do medicamento", example = "ANALGESICO")
    private TipoMedicamento tipo;
    
    @Schema(description = "Fabricante do medicamento", example = "EMS")
    private String fabricante;
    
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
