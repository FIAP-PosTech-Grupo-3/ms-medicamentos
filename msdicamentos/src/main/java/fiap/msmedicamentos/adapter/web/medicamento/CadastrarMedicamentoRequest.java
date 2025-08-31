package fiap.msmedicamentos.adapter.web.medicamento;

import fiap.msmedicamentos.core.medicamento.enums.FormaFarmaceutica;
import fiap.msmedicamentos.core.medicamento.enums.TipoMedicamento;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CadastrarMedicamentoRequest {
    private String nome;
    private TipoMedicamento tipo;
    private String fabricante;
    private LocalDate dataValidade;
    private String dosagem;
    private FormaFarmaceutica formaFarmaceutica;
    private String principioAtivo;
    private String lote;
    private LocalDate dataFabricacao;
    private Integer quantidadeEstoque;
    private Boolean precisaReceita;
    private Boolean emFalta;
}
