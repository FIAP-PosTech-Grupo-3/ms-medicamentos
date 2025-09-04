package fiap.msmedicamentos.adapter.database.medicamento;

import fiap.msmedicamentos.core.medicamento.enums.FormaFarmaceutica;
import fiap.msmedicamentos.core.medicamento.enums.TipoMedicamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMedicamento tipo;
    
    private String fabricante;
    
    @Column(name = "data_validade")
    private LocalDate dataValidade;
    
    private String dosagem;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_farmaceutica")
    private FormaFarmaceutica formaFarmaceutica;
    
    @Column(name = "principio_ativo")
    private String principioAtivo;
    
    private String lote;
    
    @Column(name = "data_fabricacao")
    private LocalDate dataFabricacao;
    
    @Column(name = "precisa_receita")
    private Boolean precisaReceita;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
