package fiap.msmedicamentos.core.usuario.entity;

import fiap.msmedicamentos.core.usuario.enums.PapelUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private PapelUsuario papel;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimoAcesso;

    public boolean isAtivo() {
        return ativo != null && ativo;
    }

    public boolean isAdmin() {
        return papel == PapelUsuario.ADMIN;
    }

    public boolean podeCadastrarMedicamento() {
        return isAdmin();
    }

    public void registrarAcesso() {
        this.ultimoAcesso = LocalDateTime.now();
    }
}
