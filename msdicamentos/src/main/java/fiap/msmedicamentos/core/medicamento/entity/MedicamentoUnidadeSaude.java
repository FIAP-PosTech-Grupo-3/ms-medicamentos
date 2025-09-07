package fiap.msmedicamentos.core.medicamento.entity;

import java.time.LocalDateTime;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoUnidadeSaude {
    private Long id;
    private Long medicamentoId;
    private Long unidadeSaudeId;
    private Integer quantidade;
    private Integer quantidadeMinima;
    private LocalDateTime ultimaAtualizacao;
    
    // Para quando precisarmos dos objetos completos
    private Medicamento medicamento;
    private UnidadeSaude unidadeSaude;

    public boolean temEstoque() {
        return quantidade != null && quantidade > 0;
    }

    public boolean precisaReposicao() {
        return quantidade != null && quantidadeMinima != null && 
               quantidade <= quantidadeMinima;
    }

    public boolean isValido() {
        return medicamentoId != null && unidadeSaudeId != null && 
               quantidade != null && quantidade >= 0;
    }
}
