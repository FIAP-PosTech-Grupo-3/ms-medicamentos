package fiap.msmedicamentos.adapter.database.unidadesaude;

import fiap.msmedicamentos.adapter.database.estoque.EstoqueMedicamentoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "unidades_saude")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeSaudeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String endereco;
    
    private String telefone;
    
    private String email;
    
    private Boolean ativa;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "unidadeSaudeId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EstoqueMedicamentoEntity> estoques;
    
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (ativa == null) {
            ativa = true;
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
