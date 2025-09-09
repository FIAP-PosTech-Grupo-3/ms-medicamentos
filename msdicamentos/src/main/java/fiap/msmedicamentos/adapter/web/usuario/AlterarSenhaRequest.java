package fiap.msmedicamentos.adapter.web.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Dados para alteração de senha")
public class AlterarSenhaRequest {
    
    @NotBlank(message = "Senha atual é obrigatória")
    @Schema(description = "Senha atual do usuário", example = "senhaAtual123", required = true)
    private String senhaAtual;
    
    @NotBlank(message = "Nova senha é obrigatória")
    @Schema(description = "Nova senha do usuário", example = "novaSenha123", required = true)
    private String novaSenha;
}
