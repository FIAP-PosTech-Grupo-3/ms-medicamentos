package fiap.msmedicamentos.adapter.web.usuario;

import fiap.msmedicamentos.core.usuario.enums.PapelUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Dados de resposta do usuário")
public class UsuarioResponse {
    
    @Schema(description = "ID único do usuário", example = "1")
    private Long id;
    
    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nome;
    
    @Schema(description = "Email do usuário", example = "joao.silva@email.com")
    private String email;
    
    @Schema(description = "Papel do usuário no sistema", example = "USUARIO")
    private PapelUsuario papel;
    
    @Schema(description = "Indica se o usuário está ativo", example = "true")
    private Boolean ativo;
    
    @Schema(description = "Data de criação do usuário", example = "2024-01-15T10:30:00")
    private LocalDateTime dataCriacao;
}
