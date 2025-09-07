package fiap.msmedicamentos.core.medicamento.entity;

import fiap.msmedicamentos.core.medicamento.enums.FormaFarmaceutica;
import fiap.msmedicamentos.core.medicamento.enums.TipoMedicamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento {
    private Long id;
    private String nome;
    private TipoMedicamento tipo;
    private String fabricante;
    private LocalDate dataValidade;
    private String dosagem;
    private FormaFarmaceutica formaFarmaceutica;
    private String principioAtivo;
    private String lote;
    private LocalDate dataFabricacao;
    private boolean precisaReceita;

    public boolean isValido() {
        return nome != null && !nome.trim().isEmpty() &&
                tipo != null &&
                dataValidade != null && dataValidade.isAfter(LocalDate.now());
    }
}
