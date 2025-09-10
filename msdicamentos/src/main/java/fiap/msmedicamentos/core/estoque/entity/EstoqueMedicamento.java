package fiap.msmedicamentos.core.estoque.entity;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueMedicamento {
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

    public void adicionarQuantidade(Integer qtd) {
        if (qtd != null && qtd > 0) {
            this.quantidade = (this.quantidade == null ? 0 : this.quantidade) + qtd;
            this.ultimaAtualizacao = LocalDateTime.now();
        }
    }

    public void removerQuantidade(Integer qtd) {
        if (qtd != null && qtd > 0 && this.quantidade != null) {
            this.quantidade = Math.max(0, this.quantidade - qtd);
            this.ultimaAtualizacao = LocalDateTime.now();
        }
    }

    public void definirQuantidade(Integer qtd) {
        if (qtd != null && qtd >= 0) {
            this.quantidade = qtd;
            this.ultimaAtualizacao = LocalDateTime.now();
        }
    }
}
