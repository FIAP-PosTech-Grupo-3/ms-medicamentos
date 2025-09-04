package fiap.msmedicamentos.adapter.database.estoque;

import fiap.msmedicamentos.adapter.database.medicamento.MedicamentoEntity;
import fiap.msmedicamentos.adapter.database.unidadesaude.UnidadeSaudeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "medicamento_unidade_saude")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoUnidadeSaudeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "medicamento_id", nullable = false)
    private Long medicamentoId;
    
    @Column(name = "unidade_saude_id", nullable = false)
    private Long unidadeSaudeId;
    
    @Column(nullable = false)
    private Integer quantidade;
    
    @Column(name = "quantidade_minima")
    private Integer quantidadeMinima;
    
    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicamento_id", insertable = false, updatable = false)
    private MedicamentoEntity medicamento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_saude_id", insertable = false, updatable = false)
    private UnidadeSaudeEntity unidadeSaude;
    
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        ultimaAtualizacao = LocalDateTime.now();
        if (quantidadeMinima == null) {
            quantidadeMinima = 10;
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
        ultimaAtualizacao = LocalDateTime.now();
    }
}
