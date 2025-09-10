package fiap.msmedicamentos.adapter.web.unidadesaude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    
    @Pattern(regexp = "^$|^\\([0-9]{2}\\)\\s[0-9]{4,5}-[0-9]{4}$", message = "Telefone deve ter formato (XX) XXXXX-XXXX ou (XX) XXXX-XXXX")
    @Schema(description = "Telefone de contato", example = "(11) 3456-7890")
    private String telefone;
    
    @Email(message = "Email deve ter formato válido")
    @Schema(description = "E-mail para contato", example = "ubs.vilaesperanca@saude.sp.gov.br")
    private String email;
    
    @Schema(description = "Indica se a unidade está ativa", example = "true")
    private Boolean ativa = true;
}
