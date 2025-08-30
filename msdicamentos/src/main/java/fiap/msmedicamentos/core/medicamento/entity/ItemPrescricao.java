package fiap.msmedicamentos.core.medicamento.entity;

import java.util.UUID;

public class ItemPrescricao {
    private UUID id;
    private UUID medicamentoId;
    private Integer quantidade;
    private String posologia; // Ex: "1 comprimido a cada 8 horas"
    private String duracaoTratamento; // Ex: "7 dias"
    private String instrucoesEspeciais;

    // Construtores
    public ItemPrescricao() {}

    public ItemPrescricao(UUID id, UUID medicamentoId, Integer quantidade,
                         String posologia, String duracaoTratamento,
                         String instrucoesEspeciais) {
        this.id = id;
        this.medicamentoId = medicamentoId;
        this.quantidade = quantidade;
        this.posologia = posologia;
        this.duracaoTratamento = duracaoTratamento;
        this.instrucoesEspeciais = instrucoesEspeciais;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getMedicamentoId() { return medicamentoId; }
    public void setMedicamentoId(UUID medicamentoId) { this.medicamentoId = medicamentoId; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public String getPosologia() { return posologia; }
    public void setPosologia(String posologia) { this.posologia = posologia; }

    public String getDuracaoTratamento() { return duracaoTratamento; }
    public void setDuracaoTratamento(String duracaoTratamento) { this.duracaoTratamento = duracaoTratamento; }

    public String getInstrucoesEspeciais() { return instrucoesEspeciais; }
    public void setInstrucoesEspeciais(String instrucoesEspeciais) { this.instrucoesEspeciais = instrucoesEspeciais; }

    // Métodos de negócio
    public boolean isValido() {
        return medicamentoId != null && quantidade != null && quantidade > 0;
    }

    public boolean temInstrucoesEspeciais() {
        return instrucoesEspeciais != null && !instrucoesEspeciais.trim().isEmpty();
    }
}
