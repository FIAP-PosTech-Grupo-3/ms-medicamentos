package fiap.msmedicamentos.adapter.web.estoque;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Dados para atualização de estoque")
public class AtualizarEstoqueRequest {
    
    @NotNull(message = "ID do medicamento é obrigatório")
    @Schema(description = "ID do medicamento", example = "1", required = true)
    private Long medicamentoId;
    
    @NotNull(message = "ID da unidade de saúde é obrigatório")
    @Schema(description = "ID da unidade de saúde", example = "1", required = true)
    private Long unidadeSaudeId;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 0, message = "Quantidade deve ser maior ou igual a zero")
    @Schema(description = "Nova quantidade em estoque", example = "100", required = true)
    private Integer quantidade;
    
    @Min(value = 0, message = "Quantidade mínima deve ser maior ou igual a zero")
    @Schema(description = "Quantidade mínima para alerta", example = "10")
    private Integer quantidadeMinima;
}
