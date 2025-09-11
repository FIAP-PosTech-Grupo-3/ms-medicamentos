package fiap.msmedicamentos.adapter.web.estoque;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Resposta com dados do estoque")
public class EstoqueResponse {
    
    @Schema(description = "ID do estoque", example = "1")
    private Long id;
    
    @Schema(description = "ID do medicamento", example = "1")
    private Long medicamentoId;
    
    @Schema(description = "ID da unidade de saúde", example = "1")
    private Long unidadeSaudeId;
    
    @Schema(description = "Quantidade em estoque", example = "100")
    private Integer quantidade;
    
    @Schema(description = "Quantidade mínima para alerta", example = "10")
    private Integer quantidadeMinima;
    
    @Schema(description = "Indica se precisa de reposição", example = "false")
    private Boolean precisaReposicao;
}
