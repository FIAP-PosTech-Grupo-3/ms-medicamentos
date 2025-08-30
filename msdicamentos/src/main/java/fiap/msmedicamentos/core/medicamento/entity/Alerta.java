package fiap.msmedicamentos.core.medicamento.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Alerta {
    private UUID id;
    private UUID medicamentoId;
    private UUID unidadeId;
    private TipoAlerta tipo;
    private String mensagem;
    private PrioridadeAlerta prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataResolucao;
    private boolean resolvido;
    private String acaoTomada;

    // Construtores
    public Alerta() {}

    public Alerta(UUID id, UUID medicamentoId, UUID unidadeId, TipoAlerta tipo,
                 String mensagem, PrioridadeAlerta prioridade, LocalDateTime dataCriacao,
                 LocalDateTime dataResolucao, boolean resolvido, String acaoTomada) {
        this.id = id;
        this.medicamentoId = medicamentoId;
        this.unidadeId = unidadeId;
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.prioridade = prioridade != null ? prioridade : PrioridadeAlerta.MEDIA;
        this.dataCriacao = dataCriacao != null ? dataCriacao : LocalDateTime.now();
        this.dataResolucao = dataResolucao;
        this.resolvido = resolvido;
        this.acaoTomada = acaoTomada;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getMedicamentoId() { return medicamentoId; }
    public void setMedicamentoId(UUID medicamentoId) { this.medicamentoId = medicamentoId; }

    public UUID getUnidadeId() { return unidadeId; }
    public void setUnidadeId(UUID unidadeId) { this.unidadeId = unidadeId; }

    public TipoAlerta getTipo() { return tipo; }
    public void setTipo(TipoAlerta tipo) { this.tipo = tipo; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public PrioridadeAlerta getPrioridade() { return prioridade; }
    public void setPrioridade(PrioridadeAlerta prioridade) { this.prioridade = prioridade; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataResolucao() { return dataResolucao; }
    public void setDataResolucao(LocalDateTime dataResolucao) { this.dataResolucao = dataResolucao; }

    public boolean isResolvido() { return resolvido; }
    public void setResolvido(boolean resolvido) { this.resolvido = resolvido; }

    public String getAcaoTomada() { return acaoTomada; }
    public void setAcaoTomada(String acaoTomada) { this.acaoTomada = acaoTomada; }

    // Métodos de negócio
    public boolean isAtivo() {
        return !resolvido;
    }

    public boolean isCritico() {
        return prioridade == PrioridadeAlerta.CRITICA;
    }

    public boolean isAltaPrioridade() {
        return prioridade == PrioridadeAlerta.ALTA;
    }

    public void resolver(String acao) {
        this.resolvido = true;
        this.dataResolucao = LocalDateTime.now();
        this.acaoTomada = acao;
    }

    public boolean foiResolvidoRecentemente() {
        return resolvido && dataResolucao != null &&
               dataResolucao.isAfter(LocalDateTime.now().minusHours(24));
    }
}
