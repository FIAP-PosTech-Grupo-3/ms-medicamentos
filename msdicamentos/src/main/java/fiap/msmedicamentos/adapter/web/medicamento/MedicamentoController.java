package fiap.msmedicamentos.adapter.web.medicamento;

import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.usecase.AtualizarMedicamentoUseCase;
import fiap.msmedicamentos.core.medicamento.usecase.BuscarMedicamentoPorIdUseCase;
import fiap.msmedicamentos.core.medicamento.usecase.BuscarMedicamentoPorNomeUseCase;
import fiap.msmedicamentos.core.medicamento.usecase.BuscarTodosMedicamentosUseCase;
import fiap.msmedicamentos.core.medicamento.usecase.CadastrarMedicamentoUseCase;
import fiap.msmedicamentos.core.medicamento.usecase.DeletarMedicamentoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicamentos")
@Tag(name = "Medicamentos", description = "API para gerenciamento de medicamentos")
public class MedicamentoController {

    private final CadastrarMedicamentoUseCase cadastrarMedicamentoUseCase;
    private final BuscarMedicamentoPorIdUseCase buscarMedicamentoPorIdUseCase;
    private final BuscarTodosMedicamentosUseCase buscarTodosMedicamentosUseCase;
    private final BuscarMedicamentoPorNomeUseCase buscarMedicamentoPorNomeUseCase;
    private final AtualizarMedicamentoUseCase atualizarMedicamentoUseCase;
    private final DeletarMedicamentoUseCase deletarMedicamentoUseCase;
    private final MedicamentoWebMapper mapper;

    public MedicamentoController(CadastrarMedicamentoUseCase cadastrarMedicamentoUseCase,
                                BuscarMedicamentoPorIdUseCase buscarMedicamentoPorIdUseCase,
                                BuscarTodosMedicamentosUseCase buscarTodosMedicamentosUseCase,
                                BuscarMedicamentoPorNomeUseCase buscarMedicamentoPorNomeUseCase,
                                AtualizarMedicamentoUseCase atualizarMedicamentoUseCase,
                                DeletarMedicamentoUseCase deletarMedicamentoUseCase,
                                MedicamentoWebMapper mapper) {
        this.cadastrarMedicamentoUseCase = cadastrarMedicamentoUseCase;
        this.buscarMedicamentoPorIdUseCase = buscarMedicamentoPorIdUseCase;
        this.buscarTodosMedicamentosUseCase = buscarTodosMedicamentosUseCase;
        this.buscarMedicamentoPorNomeUseCase = buscarMedicamentoPorNomeUseCase;
        this.atualizarMedicamentoUseCase = atualizarMedicamentoUseCase;
        this.deletarMedicamentoUseCase = deletarMedicamentoUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo medicamento", description = "Cria um novo medicamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicamento criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "422", description = "Dados do medicamento são inválidos")
    })
    public ResponseEntity<MedicamentoResponse> cadastrar(
            @Parameter(description = "Dados do medicamento a ser cadastrado") 
            @RequestBody CadastrarMedicamentoRequest request) {
        Medicamento medicamento = mapper.toDomain(request);
        Medicamento medicamentoSalvo = cadastrarMedicamentoUseCase.execute(medicamento);
        MedicamentoResponse response = mapper.toResponse(medicamentoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar medicamento por ID", description = "Busca um medicamento específico pelo seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicamentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado")
    })
    public ResponseEntity<MedicamentoResponse> buscarPorId(
            @Parameter(description = "ID do medicamento") 
            @PathVariable Long id) {
        Medicamento medicamento = buscarMedicamentoPorIdUseCase.execute(id);
        MedicamentoResponse response = mapper.toResponse(medicamento);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Buscar todos os medicamentos", description = "Lista todos os medicamentos com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicamentos retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedMedicamentoResponse.class)))
    })
    public ResponseEntity<PagedMedicamentoResponse> buscarTodos(
            @Parameter(description = "Número da página (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Direção da ordenação (ASC/DESC)") @RequestParam(defaultValue = "ASC") String sortDirection) {
        
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<Medicamento> medicamentos = buscarTodosMedicamentosUseCase.execute(pageable);
        PagedMedicamentoResponse response = mapper.toPagedResponse(medicamentos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar medicamentos por nome", description = "Busca medicamentos que contenham o nome especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicamentos encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedMedicamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Nome do medicamento é obrigatório")
    })
    public ResponseEntity<PagedMedicamentoResponse> buscarPorNome(
            @Parameter(description = "Nome do medicamento para busca") @RequestParam String nome,
            @Parameter(description = "Número da página (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "nome") String sortBy,
            @Parameter(description = "Direção da ordenação (ASC/DESC)") @RequestParam(defaultValue = "ASC") String sortDirection) {
        
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<Medicamento> medicamentos = buscarMedicamentoPorNomeUseCase.execute(nome, pageable);
        PagedMedicamentoResponse response = mapper.toPagedResponse(medicamentos);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicamento", description = "Atualiza um medicamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MedicamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados do medicamento são inválidos")
    })
    public ResponseEntity<MedicamentoResponse> atualizar(
            @Parameter(description = "ID do medicamento") @PathVariable Long id, 
            @Parameter(description = "Dados atualizados do medicamento") @RequestBody CadastrarMedicamentoRequest request) {
        Medicamento medicamento = mapper.toDomain(request);
        Medicamento medicamentoAtualizado = atualizarMedicamentoUseCase.executar(id, medicamento);
        MedicamentoResponse response = mapper.toResponse(medicamentoAtualizado);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar medicamento", description = "Remove um medicamento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicamento deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do medicamento é obrigatório"),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do medicamento") @PathVariable Long id) {
        deletarMedicamentoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}
