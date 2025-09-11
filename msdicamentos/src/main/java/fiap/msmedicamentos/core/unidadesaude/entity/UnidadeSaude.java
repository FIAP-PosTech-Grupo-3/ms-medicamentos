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
    private boolean ativa;

    public boolean isValida() {
        return nome != null && !nome.trim().isEmpty() && 
               endereco != null && !endereco.trim().isEmpty();
    }

    public boolean isAtiva() {
        return ativa;
    }
}
