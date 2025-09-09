package fiap.msmedicamentos.adapter.web.usuario;

import fiap.msmedicamentos.core.usuario.entity.Usuario;
import fiap.msmedicamentos.core.usuario.usecase.AtualizarUsuarioUseCase;
import fiap.msmedicamentos.core.usuario.usecase.BuscarTodosUsuariosUseCase;
import fiap.msmedicamentos.core.usuario.usecase.BuscarUsuarioPorIdUseCase;
import fiap.msmedicamentos.core.usuario.usecase.CadastrarUsuarioUseCase;
import fiap.msmedicamentos.core.usuario.usecase.DeletarUsuarioUseCase;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "API para gerenciamento de usuários")
public class UsuarioController {

    private final CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;
    private final UsuarioWebMapper mapper;

    public UsuarioController(
            CadastrarUsuarioUseCase cadastrarUsuarioUseCase,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase,
            AtualizarUsuarioUseCase atualizarUsuarioUseCase,
            DeletarUsuarioUseCase deletarUsuarioUseCase,
            UsuarioWebMapper mapper) {
        this.cadastrarUsuarioUseCase = cadastrarUsuarioUseCase;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.buscarTodosUsuariosUseCase = buscarTodosUsuariosUseCase;
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "409", description = "Email já está em uso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody CadastrarUsuarioRequest request) {
        log.info("Recebendo solicitação para cadastrar usuário: {}", request.getEmail());
        
        try {
            Usuario usuario = mapper.toDomain(request);
            Usuario usuarioSalvo = cadastrarUsuarioUseCase.execute(usuario);
            UsuarioResponse response = mapper.toResponse(usuarioSalvo);
            
            log.info("Usuário cadastrado com sucesso, ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Erro ao cadastrar usuário: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioResponse> buscarPorId(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        log.debug("Buscando usuário por ID: {}", id);
        
        Usuario usuario = buscarUsuarioPorIdUseCase.execute(id);
        UsuarioResponse response = mapper.toResponse(usuario);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários com paginação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<UsuarioResponse>> buscarTodos(
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "nome") String sort,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "asc") String direction) {
        
        log.debug("Buscando todos os usuários - página: {}, tamanho: {}", page, size);
        
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<Usuario> usuarios = buscarTodosUsuariosUseCase.execute(pageable);
        Page<UsuarioResponse> response = usuarios.map(mapper::toResponse);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar usuários por nome", description = "Busca usuários por nome com paginação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários encontrados"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<UsuarioResponse>> buscarPorNome(
            @Parameter(description = "Nome do usuário") @RequestParam String nome,
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "nome") String sort,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "asc") String direction) {
        
        log.debug("Buscando usuários por nome: {}", nome);
        
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<Usuario> usuarios = buscarTodosUsuariosUseCase.executePorNome(nome, pageable);
        Page<UsuarioResponse> response = usuarios.map(mapper::toResponse);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar usuários ativos", description = "Lista apenas usuários ativos com paginação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários ativos retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Page<UsuarioResponse>> buscarAtivos(
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "nome") String sort,
            @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "asc") String direction) {
        
        log.debug("Buscando usuários ativos - página: {}, tamanho: {}", page, size);
        
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<Usuario> usuarios = buscarTodosUsuariosUseCase.executeAtivos(pageable);
        Page<UsuarioResponse> response = usuarios.map(mapper::toResponse);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "409", description = "Email já está em uso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioResponse> atualizar(
            @Parameter(description = "ID do usuário") @PathVariable Long id,
            @Valid @RequestBody AtualizarUsuarioRequest request) {
        log.info("Atualizando usuário ID: {} com email: {}", id, request.getEmail());
        
        try {
            Usuario usuario = mapper.toDomain(request);
            Usuario usuarioAtualizado = atualizarUsuarioUseCase.execute(id, usuario);
            UsuarioResponse response = mapper.toResponse(usuarioAtualizado);
            
            log.info("Usuário atualizado com sucesso, ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao atualizar usuário: {}", e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        log.info("Deletando usuário ID: {}", id);
        
        deletarUsuarioUseCase.execute(id);
        log.info("Usuário deletado com sucesso, ID: {}", id);
        
        return ResponseEntity.noContent().build();
    }
}
