package fiap.msmedicamentos.adapter.web.usuario;

import fiap.msmedicamentos.core.usuario.enums.PapelUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Dados para atualização de usuário")
public class AtualizarUsuarioRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Schema(description = "Nome completo do usuário", example = "João Silva", required = true)
    private String nome;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    @Schema(description = "Email do usuário", example = "joao.silva@email.com", required = true)
    private String email;
    
    @NotNull(message = "Papel é obrigatório")
    @Schema(description = "Papel do usuário no sistema", example = "USUARIO", required = true)
    private PapelUsuario papel;
    
    @NotNull(message = "Status ativo é obrigatório")
    @Schema(description = "Indica se o usuário está ativo", example = "true", required = true)
    private Boolean ativo;
}
