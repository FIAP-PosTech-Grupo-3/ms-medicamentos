package fiap.msmedicamentos.core.unidade.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeSaude {
    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private String horarioFuncionamento;

}
