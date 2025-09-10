package fiap.msmedicamentos.adapter.web.usuario;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.msmedicamentos.core.usuario.entity.Usuario;
import fiap.msmedicamentos.core.usuario.enums.PapelUsuario;
import fiap.msmedicamentos.core.usuario.exception.UsuarioNaoEncontradoException;
import fiap.msmedicamentos.core.usuario.usecase.AtualizarUsuarioUseCase;
import fiap.msmedicamentos.core.usuario.usecase.BuscarTodosUsuariosUseCase;
import fiap.msmedicamentos.core.usuario.usecase.BuscarUsuarioPorIdUseCase;
import fiap.msmedicamentos.core.usuario.usecase.CadastrarUsuarioUseCase;
import fiap.msmedicamentos.core.usuario.usecase.DeletarUsuarioUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UsuarioController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase;

    @MockBean
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @MockBean
    private BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase;

    @MockBean
    private AtualizarUsuarioUseCase atualizarUsuarioUseCase;

    @MockBean
    private DeletarUsuarioUseCase deletarUsuarioUseCase;

    @MockBean
    private UsuarioWebMapper mapper;

    @Test
    void deveCadastrarUsuarioComSucesso() throws Exception {
        // Arrange
        CadastrarUsuarioRequest request = new CadastrarUsuarioRequest();
        request.setNome("João Silva");
        request.setEmail("joao@email.com");
        request.setSenha("senha123");
        request.setPapel(PapelUsuario.USUARIO);
        request.setAtivo(true);

        Usuario usuarioDomain = new Usuario();
        usuarioDomain.setNome("João Silva");
        usuarioDomain.setEmail("joao@email.com");
        usuarioDomain.setSenha("senha123");
        usuarioDomain.setPapel(PapelUsuario.USUARIO);

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(1L);
        usuarioSalvo.setNome("João Silva");
        usuarioSalvo.setEmail("joao@email.com");
        usuarioSalvo.setPapel(PapelUsuario.USUARIO);
        usuarioSalvo.setAtivo(true);

        UsuarioResponse response = new UsuarioResponse();
        response.setId(1L);
        response.setNome("João Silva");
        response.setEmail("joao@email.com");
        response.setAtivo(true);

        when(mapper.toDomain(request)).thenReturn(usuarioDomain);
        when(cadastrarUsuarioUseCase.execute(usuarioDomain)).thenReturn(usuarioSalvo);
        when(mapper.toResponse(usuarioSalvo)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.ativo").value(true));

        verify(cadastrarUsuarioUseCase).execute(usuarioDomain);
    }

    @Test
    void deveBuscarUsuarioPorIdComSucesso() throws Exception {
        // Arrange
        Long usuarioId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Maria Santos");
        usuario.setEmail("maria@email.com");
        usuario.setPapel(PapelUsuario.ADMIN);
        usuario.setAtivo(true);

        UsuarioResponse response = new UsuarioResponse();
        response.setId(1L);
        response.setNome("Maria Santos");
        response.setEmail("maria@email.com");
        response.setAtivo(true);

        when(buscarUsuarioPorIdUseCase.execute(usuarioId)).thenReturn(usuario);
        when(mapper.toResponse(usuario)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/{id}", usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Maria Santos"))
                .andExpect(jsonPath("$.email").value("maria@email.com"));

        verify(buscarUsuarioPorIdUseCase).execute(usuarioId);
    }

    @Test
    void deveRetornar404QuandoUsuarioNaoEncontrado() throws Exception {
        // Arrange
        Long usuarioId = 999L;
        when(buscarUsuarioPorIdUseCase.execute(usuarioId))
                .thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/{id}", usuarioId))
                .andExpect(status().isNotFound());

        verify(buscarUsuarioPorIdUseCase).execute(usuarioId);
    }

    @Test
    void deveListarUsuariosComPaginacao() throws Exception {
        // Arrange
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNome("João");
        usuario1.setEmail("joao@email.com");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNome("Maria");
        usuario2.setEmail("maria@email.com");

        Page<Usuario> page = new PageImpl<>(Arrays.asList(usuario1, usuario2), PageRequest.of(0, 10), 2);

        when(buscarTodosUsuariosUseCase.execute(any(PageRequest.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/usuarios")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.size").value(10));

        verify(buscarTodosUsuariosUseCase).execute(any(PageRequest.class));
    }

    @Test
    void deveDeletarUsuarioComSucesso() throws Exception {
        // Arrange
        Long usuarioId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/usuarios/{id}", usuarioId))
                .andExpect(status().isNoContent());

        verify(deletarUsuarioUseCase).execute(usuarioId);
    }

    @Test
    void deveRetornar404AoDeletarUsuarioInexistente() throws Exception {
        // Arrange
        Long usuarioId = 999L;
        doThrow(new UsuarioNaoEncontradoException("Usuário não encontrado"))
                .when(deletarUsuarioUseCase).execute(usuarioId);

        // Act & Assert
        mockMvc.perform(delete("/api/usuarios/{id}", usuarioId))
                .andExpect(status().isNotFound());

        verify(deletarUsuarioUseCase).execute(usuarioId);
    }
}
