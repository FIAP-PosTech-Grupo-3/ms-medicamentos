package fiap.msmedicamentos.adapter.web.estoque;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Dados para movimentação de estoque")
public class MovimentarEstoqueRequest {
    
    @NotNull(message = "ID do medicamento é obrigatório")
    @Schema(description = "ID do medicamento", example = "1", required = true)
    private Long medicamentoId;
    
    @NotNull(message = "ID da unidade de saúde é obrigatório")
    @Schema(description = "ID da unidade de saúde", example = "1", required = true)
    private Long unidadeSaudeId;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    @Schema(description = "Quantidade a ser movimentada", example = "50", required = true)
    private Integer quantidade;
}
