package fiap.msmedicamentos.core.unidade.entity;

import fiap.msmedicamentos.core.unidade.enums.TipoUnidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeSaude {
    private Long id;
    private String nome;
    private Endereco endereco;
    private String telefone;
    private String email;
    private TipoUnidade tipo;
    private Integer capacidadeAtendimento;
    private String horarioFuncionamento;
    private String cnes;
    private Boolean ativo;

    public boolean isAtiva() {
        return ativo != null && ativo;
    }

    public boolean podeAtenderMaisPacientes() {
        return capacidadeAtendimento != null && capacidadeAtendimento > 0;
    }

    public String getInformacoesContato() {
        return String.format("Telefone: %s | Email: %s", telefone, email);
    }
}
