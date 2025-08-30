package fiap.msmedicamentos.core.medicamento.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Prescricao {
    private UUID id;
    private UUID pacienteId;
    private UUID medicoId;
    private UUID unidadeId;
    private List<ItemPrescricao> itens;
    private LocalDateTime dataPrescricao;
    private LocalDate dataValidade;
    private String observacoes;
    private StatusPrescricao status;

    // Construtores
    public Prescricao() {}

    public Prescricao(UUID id, UUID pacienteId, UUID medicoId, UUID unidadeId,
                     List<ItemPrescricao> itens, LocalDateTime dataPrescricao,
                     LocalDate dataValidade, String observacoes, StatusPrescricao status) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.unidadeId = unidadeId;
        this.itens = itens;
        this.dataPrescricao = dataPrescricao != null ? dataPrescricao : LocalDateTime.now();
        this.dataValidade = dataValidade;
        this.observacoes = observacoes;
        this.status = status != null ? status : StatusPrescricao.ATIVA;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getPacienteId() { return pacienteId; }
    public void setPacienteId(UUID pacienteId) { this.pacienteId = pacienteId; }

    public UUID getMedicoId() { return medicoId; }
    public void setMedicoId(UUID medicoId) { this.medicoId = medicoId; }

    public UUID getUnidadeId() { return unidadeId; }
    public void setUnidadeId(UUID unidadeId) { this.unidadeId = unidadeId; }

    public List<ItemPrescricao> getItens() { return itens; }
    public void setItens(List<ItemPrescricao> itens) { this.itens = itens; }

    public LocalDateTime getDataPrescricao() { return dataPrescricao; }
    public void setDataPrescricao(LocalDateTime dataPrescricao) { this.dataPrescricao = dataPrescricao; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public StatusPrescricao getStatus() { return status; }
    public void setStatus(StatusPrescricao status) { this.status = status; }

    // Métodos de negócio
    public boolean isValida() {
        return dataValidade != null && LocalDate.now().isBefore(dataValidade) &&
               status == StatusPrescricao.ATIVA;
    }

    public boolean podeSerDispensada() {
        return isValida() && itens != null && !itens.isEmpty();
    }

    public void cancelar() {
        this.status = StatusPrescricao.CANCELADA;
    }

    public void finalizar() {
        this.status = StatusPrescricao.FINALIZADA;
    }

    public boolean contemMedicamento(UUID medicamentoId) {
        if (itens == null || medicamentoId == null) return false;
        return itens.stream()
                   .anyMatch(item -> medicamentoId.equals(item.getMedicamentoId()));
    }
}
