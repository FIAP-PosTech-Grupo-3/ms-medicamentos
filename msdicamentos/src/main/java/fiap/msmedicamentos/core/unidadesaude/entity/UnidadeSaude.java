package fiap.msmedicamentos.core.unidadesaude.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeSaude {
    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private String tipo; // UBS, Hospital, Clínica, etc.
    private boolean ativa;

    public boolean isValida() {
        return nome != null && !nome.trim().isEmpty() && 
               endereco != null && !endereco.trim().isEmpty();
    }
}
