package fiap.msmedicamentos.adapter.web.unidadesaude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Dados para cadastro/atualização de unidade de saúde")
public class UnidadeSaudeRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome da unidade de saúde", example = "UBS Vila Esperança", required = true)
    private String nome;
    
    @NotBlank(message = "Endereço é obrigatório")
    @Schema(description = "Endereço completo da unidade", example = "Rua das Flores, 123 - Vila Esperança - São Paulo/SP", required = true)
    private String endereco;
    
    @Schema(description = "Indica se a unidade está ativa", example = "true")
    private Boolean ativa = true;
}
