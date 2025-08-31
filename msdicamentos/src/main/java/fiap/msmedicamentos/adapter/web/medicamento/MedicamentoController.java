package fiap.msmedicamentos.adapter.web.medicamento;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.usecase.CadastrarMedicamentoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final CadastrarMedicamentoUseCase cadastrarMedicamentoUseCase;
    private final MedicamentoWebMapper mapper;

    public MedicamentoController(CadastrarMedicamentoUseCase cadastrarMedicamentoUseCase,
            MedicamentoWebMapper mapper) {
        this.cadastrarMedicamentoUseCase = cadastrarMedicamentoUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<MedicamentoResponse> cadastrar(@RequestBody CadastrarMedicamentoRequest request) {
        Medicamento medicamento = mapper.toDomain(request);
        Medicamento medicamentoSalvo = cadastrarMedicamentoUseCase.execute(medicamento);
        MedicamentoResponse response = mapper.toResponse(medicamentoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
