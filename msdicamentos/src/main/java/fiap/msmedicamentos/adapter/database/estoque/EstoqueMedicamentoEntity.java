package fiap.msmedicamentos.adapter.database.estoque;

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
public class EstoqueMedicamentoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "medicamento_id", nullable = false)
    private Long medicamentoId;
    
    @Column(name = "unidade_saude_id", nullable = false)
    private Long unidadeSaudeId;
    
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade = 0;
    
    @Column(name = "quantidade_minima")
    private Integer quantidadeMinima;
    
    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;
    
    @PrePersist
    @PreUpdate
    public void preUpdate() {
        ultimaAtualizacao = LocalDateTime.now();
    }
}
