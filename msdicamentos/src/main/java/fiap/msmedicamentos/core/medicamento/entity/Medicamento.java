package fiap.msmedicamentos.core.medicamento.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento {
    private Long id;
    private String nome;
    private String principioAtivo;
    private String fabricante;
    private String dosagem;

    public boolean isValido() {
        return nome != null && !nome.trim().isEmpty() &&
               principioAtivo != null && !principioAtivo.trim().isEmpty();
    }
}
