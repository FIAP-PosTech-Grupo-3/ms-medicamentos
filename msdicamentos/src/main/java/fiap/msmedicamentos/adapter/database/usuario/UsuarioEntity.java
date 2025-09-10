package fiap.msmedicamentos.adapter.database.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    
    @Column(nullable = false, length = 255)
    private String senha;
    
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PapelUsuarioEntity papel;
    
    @Column(nullable = false)
    private Boolean ativo;
}
