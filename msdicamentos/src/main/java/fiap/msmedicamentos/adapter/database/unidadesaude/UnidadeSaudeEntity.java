package fiap.msmedicamentos.adapter.database.unidadesaude;

import fiap.msmedicamentos.adapter.database.estoque.EstoqueMedicamentoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    private Boolean ativa;
    
    @OneToMany(mappedBy = "unidadeSaudeId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EstoqueMedicamentoEntity> estoques;
}
