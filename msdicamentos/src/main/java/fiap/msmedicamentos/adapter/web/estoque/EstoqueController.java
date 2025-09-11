package fiap.msmedicamentos.adapter.web.estoque;

import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import fiap.msmedicamentos.core.estoque.usecase.AdicionarEstoqueUseCase;
import fiap.msmedicamentos.core.estoque.usecase.AtualizarEstoqueUseCase;
import fiap.msmedicamentos.core.estoque.usecase.ConsultarEstoqueUseCase;
import fiap.msmedicamentos.core.estoque.usecase.RemoverEstoqueUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/estoque")
@RequiredArgsConstructor
@Tag(name = "Estoque", description = "API para gerenciamento de estoque de medicamentos")
public class EstoqueController {

    private final AdicionarEstoqueUseCase adicionarEstoqueUseCase;
    private final RemoverEstoqueUseCase removerEstoqueUseCase;
    private final AtualizarEstoqueUseCase atualizarEstoqueUseCase;
    private final ConsultarEstoqueUseCase consultarEstoqueUseCase;
    private final EstoqueWebMapper mapper;

    @PostMapping("/adicionar")
    @Operation(summary = "Adicionar quantidade ao estoque", description = "Adiciona uma quantidade específica ao estoque de um medicamento em uma unidade")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstoqueResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EstoqueResponse> adicionarEstoque(@Valid @RequestBody MovimentarEstoqueRequest request) {
        log.info("Adicionando {} unidades do medicamento {} na unidade {}", 
                request.getQuantidade(), request.getMedicamentoId(), request.getUnidadeSaudeId());
        
        EstoqueMedicamento estoque = adicionarEstoqueUseCase.execute(
                request.getMedicamentoId(), 
                request.getUnidadeSaudeId(), 
                request.getQuantidade()
        );
        
        EstoqueResponse response = mapper.toResponse(estoque);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/remover")
    @Operation(summary = "Remover quantidade do estoque", description = "Remove uma quantidade específica do estoque de um medicamento em uma unidade")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstoqueResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou quantidade insuficiente"),
        @ApiResponse(responseCode = "404", description = "Estoque não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EstoqueResponse> removerEstoque(@Valid @RequestBody MovimentarEstoqueRequest request) {
        log.info("Removendo {} unidades do medicamento {} na unidade {}", 
                request.getQuantidade(), request.getMedicamentoId(), request.getUnidadeSaudeId());
        
        EstoqueMedicamento estoque = removerEstoqueUseCase.execute(
                request.getMedicamentoId(), 
                request.getUnidadeSaudeId(), 
                request.getQuantidade()
        );
        
        EstoqueResponse response = mapper.toResponse(estoque);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualizar estoque", description = "Define uma nova quantidade para o estoque de um medicamento em uma unidade")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstoqueResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EstoqueResponse> atualizarEstoque(@Valid @RequestBody AtualizarEstoqueRequest request) {
        log.info("Atualizando estoque do medicamento {} na unidade {} para {}", 
                request.getMedicamentoId(), request.getUnidadeSaudeId(), request.getQuantidade());
        
        EstoqueMedicamento estoque = atualizarEstoqueUseCase.execute(
                request.getMedicamentoId(), 
                request.getUnidadeSaudeId(), 
                request.getQuantidade(),
                request.getQuantidadeMinima()
        );
        
        EstoqueResponse response = mapper.toResponse(estoque);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todo o estoque", description = "Lista todo o estoque com paginação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de estoque retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<EstoqueResponse>> listarEstoque(
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "ultimaAtualizacao") String sort,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "desc") String direction) {
        
        // Validar parâmetros de paginação
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 10;
        }
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<EstoqueMedicamento> estoques = consultarEstoqueUseCase.buscarTodos(pageable);
        Page<EstoqueResponse> response = estoques.map(mapper::toResponse);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/medicamento/{medicamentoId}")
    @Operation(summary = "Consultar estoque por medicamento", description = "Lista o estoque de um medicamento específico em todas as unidades")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estoque do medicamento retornado com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<EstoqueResponse>> consultarEstoquePorMedicamento(
            @Parameter(description = "ID do medicamento") @PathVariable Long medicamentoId) {
        
        log.info("Consultando estoque para medicamento ID: {}", medicamentoId);
        
        List<EstoqueMedicamento> estoques = consultarEstoqueUseCase.buscarPorMedicamento(medicamentoId);
        List<EstoqueResponse> response = estoques.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/unidade/{unidadeSaudeId}")
    @Operation(summary = "Consultar estoque por unidade", description = "Lista o estoque de todos os medicamentos em uma unidade específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estoque da unidade retornado com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<EstoqueResponse>> consultarEstoquePorUnidade(
            @Parameter(description = "ID da unidade de saúde") @PathVariable Long unidadeSaudeId) {
        
        log.info("Consultando estoque para unidade ID: {}", unidadeSaudeId);
        
        List<EstoqueMedicamento> estoques = consultarEstoqueUseCase.buscarPorUnidadeSaude(unidadeSaudeId);
        List<EstoqueResponse> response = estoques.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/baixo")
    @Operation(summary = "Consultar estoque baixo", description = "Lista medicamentos com estoque abaixo da quantidade mínima")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de medicamentos com estoque baixo"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<EstoqueResponse>> consultarEstoqueBaixo() {
        log.info("Consultando medicamentos com estoque baixo");
        
        List<EstoqueMedicamento> estoques = consultarEstoqueUseCase.buscarEstoqueBaixo();
        List<EstoqueResponse> response = estoques.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
}
