package fiap.msmedicamentos.adapter.web.unidadesaude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Dados de resposta da unidade de saúde")
public class UnidadeSaudeResponse {
    
    @Schema(description = "ID único da unidade de saúde", example = "1")
    private Long id;
    
    @Schema(description = "Nome da unidade de saúde", example = "UBS Vila Esperança")
    private String nome;
    
    @Schema(description = "Endereço completo da unidade", example = "Rua das Flores, 123 - Vila Esperança - São Paulo/SP")
    private String endereco;
    
    @Schema(description = "Indica se a unidade está ativa", example = "true")
    private Boolean ativa;
}
