package fiap.msmedicamentos.core.medicamento.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class MovimentacaoMedicamento {
    private UUID id;
    private UUID medicamentoId;
    private UUID unidadeId;
    private UUID usuarioId;
    private TipoMovimentacao tipo;
    private Integer quantidade;
    private LocalDateTime dataMovimentacao;
    private String motivo;
    private String lote;
    private LocalDateTime dataValidade;

    // Construtores
    public MovimentacaoMedicamento() {}

    public MovimentacaoMedicamento(UUID id, UUID medicamentoId, UUID unidadeId,
                                  UUID usuarioId, TipoMovimentacao tipo,
                                  Integer quantidade, LocalDateTime dataMovimentacao,
                                  String motivo, String lote, LocalDateTime dataValidade) {
        this.id = id;
        this.medicamentoId = medicamentoId;
        this.unidadeId = unidadeId;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.dataMovimentacao = dataMovimentacao != null ? dataMovimentacao : LocalDateTime.now();
        this.motivo = motivo;
        this.lote = lote;
        this.dataValidade = dataValidade;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getMedicamentoId() { return medicamentoId; }
    public void setMedicamentoId(UUID medicamentoId) { this.medicamentoId = medicamentoId; }

    public UUID getUnidadeId() { return unidadeId; }
    public void setUnidadeId(UUID unidadeId) { this.unidadeId = unidadeId; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public TipoMovimentacao getTipo() { return tipo; }
    public void setTipo(TipoMovimentacao tipo) { this.tipo = tipo; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public LocalDateTime getDataMovimentacao() { return dataMovimentacao; }
    public void setDataMovimentacao(LocalDateTime dataMovimentacao) { this.dataMovimentacao = dataMovimentacao; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public LocalDateTime getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDateTime dataValidade) { this.dataValidade = dataValidade; }

    // Métodos de negócio
    public boolean isEntrada() {
        return tipo == TipoMovimentacao.ENTRADA;
    }

    public boolean isSaida() {
        return tipo == TipoMovimentacao.SAIDA;
    }

    public boolean isValida() {
        return medicamentoId != null && unidadeId != null && usuarioId != null &&
               tipo != null && quantidade != null && quantidade > 0;
    }

    public boolean temLote() {
        return lote != null && !lote.trim().isEmpty();
    }

    public boolean estaExpirado() {
        return dataValidade != null && LocalDateTime.now().isAfter(dataValidade);
    }
}
