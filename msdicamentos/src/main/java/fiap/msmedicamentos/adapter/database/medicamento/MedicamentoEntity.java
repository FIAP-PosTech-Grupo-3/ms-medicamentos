package fiap.msmedicamentos.adapter.database.medicamento;

import fiap.msmedicamentos.adapter.database.estoque.EstoqueMedicamentoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(name = "principio_ativo")
    private String principioAtivo;
    
    private String fabricante;
    
    private String dosagem;
    
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "medicamentoId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EstoqueMedicamentoEntity> estoques;
}
