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

    public boolean isAtivo() {
        return ativo != null && ativo;
    }

    public boolean isAdmin() {
        return papel == PapelUsuario.ADMIN;
    }

    public boolean podeCadastrarMedicamento() {
        return isAdmin();
    }

    public boolean podeGerenciarUnidadeSaude() {
        return isAdmin();
    }

    public boolean podeGerenciarUsuarios() {
        return isAdmin();
    }

    public boolean isValido() {
        return nome != null && !nome.trim().isEmpty() &&
               email != null && !email.trim().isEmpty() && isEmailValido() &&
               papel != null;
    }

    private boolean isEmailValido() {
        return email.contains("@") && email.contains(".");
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }
}
