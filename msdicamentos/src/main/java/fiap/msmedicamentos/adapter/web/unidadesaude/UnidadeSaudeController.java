    package fiap.msmedicamentos.adapter.web.unidadesaude;

import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import fiap.msmedicamentos.core.unidadesaude.usecase.AtualizarUnidadeSaudeUseCase;
import fiap.msmedicamentos.core.unidadesaude.usecase.BuscarTodasUnidadesSaudeUseCase;
import fiap.msmedicamentos.core.unidadesaude.usecase.BuscarUnidadeSaudePorIdUseCase;
import fiap.msmedicamentos.core.unidadesaude.usecase.BuscarUnidadeSaudePorNomeUseCase;
import fiap.msmedicamentos.core.unidadesaude.usecase.CadastrarUnidadeSaudeUseCase;
import fiap.msmedicamentos.core.unidadesaude.usecase.DeletarUnidadeSaudeUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/unidades-saude")
@Tag(name = "Unidades de Saúde", description = "API para gerenciamento de unidades de saúde")
public class UnidadeSaudeController {

    private final CadastrarUnidadeSaudeUseCase cadastrarUnidadeSaudeUseCase;
    private final BuscarUnidadeSaudePorIdUseCase buscarUnidadeSaudePorIdUseCase;
    private final BuscarTodasUnidadesSaudeUseCase buscarTodasUnidadesSaudeUseCase;
    private final BuscarUnidadeSaudePorNomeUseCase buscarUnidadeSaudePorNomeUseCase;
    private final AtualizarUnidadeSaudeUseCase atualizarUnidadeSaudeUseCase;
    private final DeletarUnidadeSaudeUseCase deletarUnidadeSaudeUseCase;
    private final UnidadeSaudeWebMapper mapper;

    public UnidadeSaudeController(
            CadastrarUnidadeSaudeUseCase cadastrarUnidadeSaudeUseCase,
            BuscarUnidadeSaudePorIdUseCase buscarUnidadeSaudePorIdUseCase,
            BuscarTodasUnidadesSaudeUseCase buscarTodasUnidadesSaudeUseCase,
            BuscarUnidadeSaudePorNomeUseCase buscarUnidadeSaudePorNomeUseCase,
            AtualizarUnidadeSaudeUseCase atualizarUnidadeSaudeUseCase,
            DeletarUnidadeSaudeUseCase deletarUnidadeSaudeUseCase,
            UnidadeSaudeWebMapper mapper) {
        this.cadastrarUnidadeSaudeUseCase = cadastrarUnidadeSaudeUseCase;
        this.buscarUnidadeSaudePorIdUseCase = buscarUnidadeSaudePorIdUseCase;
        this.buscarTodasUnidadesSaudeUseCase = buscarTodasUnidadesSaudeUseCase;
        this.buscarUnidadeSaudePorNomeUseCase = buscarUnidadeSaudePorNomeUseCase;
        this.atualizarUnidadeSaudeUseCase = atualizarUnidadeSaudeUseCase;
        this.deletarUnidadeSaudeUseCase = deletarUnidadeSaudeUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Cadastrar unidade de saúde", description = "Cadastra uma nova unidade de saúde no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Unidade de saúde cadastrada com sucesso",
                    content = @Content(schema = @Schema(implementation = UnidadeSaudeResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UnidadeSaudeResponse> cadastrar(@Valid @RequestBody UnidadeSaudeRequest request) {
        log.info("Recebendo solicitação para cadastrar unidade de saúde: {}", request.getNome());
        log.debug("Request completo: {}", request);
        
        try {
            UnidadeSaude unidadeSaude = mapper.toDomain(request);
            log.debug("Domain object criado: {}", unidadeSaude);
            
            UnidadeSaude unidadeSaudeSalva = cadastrarUnidadeSaudeUseCase.execute(unidadeSaude);
            log.debug("Unidade salva: {}", unidadeSaudeSalva);
            
            UnidadeSaudeResponse response = mapper.toResponse(unidadeSaudeSalva);
            log.info("Resposta criada com sucesso para ID: {}", response.getId());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Erro ao cadastrar unidade de saúde: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar unidade de saúde por ID", description = "Busca uma unidade de saúde pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Unidade de saúde encontrada",
                    content = @Content(schema = @Schema(implementation = UnidadeSaudeResponse.class))),
        @ApiResponse(responseCode = "404", description = "Unidade de saúde não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UnidadeSaudeResponse> buscarPorId(
            @Parameter(description = "ID da unidade de saúde") @PathVariable Long id) {
        log.debug("Buscando unidade de saúde por ID: {}", id);
        
        UnidadeSaude unidadeSaude = buscarUnidadeSaudePorIdUseCase.execute(id);
        UnidadeSaudeResponse response = mapper.toResponse(unidadeSaude);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar unidades de saúde", description = "Lista todas as unidades de saúde com paginação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de unidades de saúde retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<UnidadeSaudeResponse>> buscarTodas(
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "nome") String sort,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "asc") String direction) {
        
        log.debug("Buscando todas as unidades de saúde - página: {}, tamanho: {}", page, size);
        
        // Validar parâmetros de paginação
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 10;
        }
        
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<UnidadeSaude> unidadesSaude = buscarTodasUnidadesSaudeUseCase.execute(pageable);
        Page<UnidadeSaudeResponse> response = unidadesSaude.map(mapper::toResponse);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar unidades por nome", description = "Busca unidades de saúde por nome com paginação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de unidades de saúde encontradas"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<UnidadeSaudeResponse>> buscarPorNome(
            @Parameter(description = "Nome da unidade de saúde") @RequestParam String nome,
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "nome") String sort,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "asc") String direction) {
        
        log.debug("Buscando unidades de saúde por nome: {} - página: {}, tamanho: {}", nome, page, size);
        
        // Validar parâmetros de paginação
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 10;
        }
        
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<UnidadeSaude> unidadesSaude = buscarUnidadeSaudePorNomeUseCase.execute(nome, pageable);
        Page<UnidadeSaudeResponse> response = unidadesSaude.map(mapper::toResponse);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar unidade de saúde", description = "Atualiza os dados de uma unidade de saúde existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Unidade de saúde atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = UnidadeSaudeResponse.class))),
        @ApiResponse(responseCode = "404", description = "Unidade de saúde não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UnidadeSaudeResponse> atualizar(
            @Parameter(description = "ID da unidade de saúde") @PathVariable Long id,
            @Valid @RequestBody UnidadeSaudeRequest request) {
        log.info("Atualizando unidade de saúde ID: {} com dados: {}", id, request.getNome());
        
        UnidadeSaude unidadeSaude = mapper.toDomain(request);
        UnidadeSaude unidadeSaudeAtualizada = atualizarUnidadeSaudeUseCase.execute(id, unidadeSaude);
        UnidadeSaudeResponse response = mapper.toResponse(unidadeSaudeAtualizada);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar unidade de saúde", description = "Remove uma unidade de saúde do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Unidade de saúde deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Unidade de saúde não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da unidade de saúde") @PathVariable Long id) {
        log.info("Deletando unidade de saúde ID: {}", id);
        
        deletarUnidadeSaudeUseCase.execute(id);
        
        return ResponseEntity.noContent().build();
    }
}
