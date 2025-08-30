package fiap.msmedicamentos.core.medicamento.entity;

import java.time.LocalDate;
import java.util.UUID;

public class EstoqueUnidade {
    private UUID id;
    private UUID medicamentoId;
    private UUID unidadeId;
    private Integer quantidade;
    private LocalDate dataUltimaAtualizacao;
    private String localizacao; // Ex: "Prateleira A3, Posição 5"

    // Construtores
    public EstoqueUnidade() {}

    public EstoqueUnidade(UUID id, UUID medicamentoId, UUID unidadeId,
                         Integer quantidade, LocalDate dataUltimaAtualizacao,
                         String localizacao) {
        this.id = id;
        this.medicamentoId = medicamentoId;
        this.unidadeId = unidadeId;
        this.quantidade = quantidade;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao != null ? dataUltimaAtualizacao : LocalDate.now();
        this.localizacao = localizacao;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getMedicamentoId() { return medicamentoId; }
    public void setMedicamentoId(UUID medicamentoId) { this.medicamentoId = medicamentoId; }

    public UUID getUnidadeId() { return unidadeId; }
    public void setUnidadeId(UUID unidadeId) { this.unidadeId = unidadeId; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public LocalDate getDataUltimaAtualizacao() { return dataUltimaAtualizacao; }
    public void setDataUltimaAtualizacao(LocalDate dataUltimaAtualizacao) { this.dataUltimaAtualizacao = dataUltimaAtualizacao; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    // Métodos de negócio
    public boolean estaDisponivel() {
        return quantidade != null && quantidade > 0;
    }

    public boolean precisaReposicao() {
        return quantidade != null && quantidade < 5; // Exemplo: mínimo de 5 unidades por unidade
    }

    public void adicionarQuantidade(Integer quantidadeAdicionada) {
        if (quantidadeAdicionada != null && quantidadeAdicionada > 0) {
            this.quantidade = (this.quantidade != null ? this.quantidade : 0) + quantidadeAdicionada;
            this.dataUltimaAtualizacao = LocalDate.now();
        }
    }

    public boolean removerQuantidade(Integer quantidadeRemovida) {
        if (quantidadeRemovida != null && quantidadeRemovida > 0 &&
            quantidade != null && quantidade >= quantidadeRemovida) {
            this.quantidade -= quantidadeRemovida;
            this.dataUltimaAtualizacao = LocalDate.now();
            return true;
        }
        return false;
    }
}
