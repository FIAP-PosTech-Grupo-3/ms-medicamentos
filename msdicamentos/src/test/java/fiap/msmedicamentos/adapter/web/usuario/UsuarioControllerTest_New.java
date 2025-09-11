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
import static org.mockito.Mockito.never;
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
        request.setPapel(PapelUsuario.MEDICO);

        Usuario usuarioDomain = new Usuario();
        usuarioDomain.setNome("João Silva");
        usuarioDomain.setEmail("joao@email.com");
        usuarioDomain.setPapel(PapelUsuario.MEDICO);

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(1L);
        usuarioSalvo.setNome("João Silva");
        usuarioSalvo.setEmail("joao@email.com");
        usuarioSalvo.setPapel(PapelUsuario.MEDICO);

        UsuarioResponse response = new UsuarioResponse();
        response.setId(1L);
        response.setNome("João Silva");
        response.setEmail("joao@email.com");

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
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(cadastrarUsuarioUseCase).execute(usuarioDomain);
    }

    @Test
    void deveBuscarUsuarioPorIdComSucesso() throws Exception {
        // Arrange
        Long usuarioId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setNome("João Silva");
        usuario.setEmail("joao@email.com");

        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuarioId);
        response.setNome("João Silva");
        response.setEmail("joao@email.com");

        when(buscarUsuarioPorIdUseCase.execute(usuarioId)).thenReturn(usuario);
        when(mapper.toResponse(usuario)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/usuarios/{id}", usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

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
        usuario1.setNome("João Silva");
        usuario1.setEmail("joao@email.com");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNome("Maria Santos");
        usuario2.setEmail("maria@email.com");

        Page<Usuario> page = new PageImpl<>(Arrays.asList(usuario1, usuario2), PageRequest.of(0, 10), 2);

        when(buscarTodosUsuariosUseCase.execute(any(PageRequest.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/usuarios")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2));

        verify(buscarTodosUsuariosUseCase).execute(any(PageRequest.class));
    }

    @Test
    void deveAtualizarUsuarioComSucesso() throws Exception {
        // Arrange
        Long usuarioId = 1L;
        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();
        request.setNome("João Silva Atualizado");
        request.setEmail("joao.novo@email.com");
        request.setPapel(PapelUsuario.ADMIN);
        request.setAtivo(true);

        Usuario usuarioDomain = new Usuario();
        usuarioDomain.setNome("João Silva Atualizado");
        usuarioDomain.setEmail("joao.novo@email.com");
        usuarioDomain.setPapel(PapelUsuario.ADMIN);

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setId(1L);
        usuarioAtualizado.setNome("João Silva Atualizado");
        usuarioAtualizado.setEmail("joao.novo@email.com");
        usuarioAtualizado.setPapel(PapelUsuario.ADMIN);
        usuarioAtualizado.setAtivo(true);

        UsuarioResponse response = new UsuarioResponse();
        response.setId(1L);
        response.setNome("João Silva Atualizado");
        response.setEmail("joao.novo@email.com");
        response.setAtivo(true);

        when(mapper.toDomain(request)).thenReturn(usuarioDomain);
        when(atualizarUsuarioUseCase.execute(usuarioId, usuarioDomain)).thenReturn(usuarioAtualizado);
        when(mapper.toResponse(usuarioAtualizado)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/usuarios/{id}", usuarioId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva Atualizado"))
                .andExpect(jsonPath("$.email").value("joao.novo@email.com"))
                .andExpect(jsonPath("$.ativo").value(true));

        verify(atualizarUsuarioUseCase).execute(usuarioId, usuarioDomain);
    }

    @Test
    void deveRetornar404AoAtualizarUsuarioInexistente() throws Exception {
        // Arrange
        Long usuarioId = 999L;
        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();
        request.setNome("Teste");
        request.setEmail("teste@email.com");
        request.setPapel(PapelUsuario.USUARIO);
        request.setAtivo(true);

        Usuario usuarioDomain = new Usuario();
        when(mapper.toDomain(request)).thenReturn(usuarioDomain);
        when(atualizarUsuarioUseCase.execute(usuarioId, usuarioDomain))
                .thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado"));

        // Act & Assert
        mockMvc.perform(put("/api/usuarios/{id}", usuarioId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(atualizarUsuarioUseCase).execute(usuarioId, usuarioDomain);
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

    @Test
    void deveRetornar400AoCadastrarUsuarioComDadosInvalidos() throws Exception {
        // Arrange
        CadastrarUsuarioRequest request = new CadastrarUsuarioRequest();
        // Request sem email (inválido)
        request.setNome("João da Silva");

        // Act & Assert
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(cadastrarUsuarioUseCase, never()).execute(any());
    }

    @Test
    void deveRetornar400AoAtualizarUsuarioComDadosInvalidos() throws Exception {
        // Arrange
        Long usuarioId = 1L;
        AtualizarUsuarioRequest request = new AtualizarUsuarioRequest();
        // Request sem email (inválido)
        request.setNome("João da Silva");

        // Act & Assert
        mockMvc.perform(put("/api/usuarios/{id}", usuarioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(atualizarUsuarioUseCase, never()).execute(any(), any());
    }
}
