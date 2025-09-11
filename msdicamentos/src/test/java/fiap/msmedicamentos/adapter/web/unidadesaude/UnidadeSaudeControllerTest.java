package fiap.msmedicamentos.adapter.web.unidadesaude;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.msmedicamentos.core.unidadesaude.entity.UnidadeSaude;
import fiap.msmedicamentos.core.unidadesaude.exception.UnidadeSaudeNaoEncontradaException;
import fiap.msmedicamentos.core.unidadesaude.usecase.AtualizarUnidadeSaudeUseCase;
import fiap.msmedicamentos.core.unidadesaude.usecase.BuscarTodasUnidadesSaudeUseCase;
import fiap.msmedicamentos.core.unidadesaude.usecase.BuscarUnidadeSaudePorIdUseCase;
import fiap.msmedicamentos.core.unidadesaude.usecase.BuscarUnidadeSaudePorNomeUseCase;
import fiap.msmedicamentos.core.unidadesaude.usecase.CadastrarUnidadeSaudeUseCase;
import fiap.msmedicamentos.core.unidadesaude.usecase.DeletarUnidadeSaudeUseCase;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UnidadeSaudeController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UnidadeSaudeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CadastrarUnidadeSaudeUseCase cadastrarUnidadeSaudeUseCase;

    @MockBean
    private BuscarUnidadeSaudePorIdUseCase buscarUnidadeSaudePorIdUseCase;

    @MockBean
    private BuscarTodasUnidadesSaudeUseCase buscarTodasUnidadesSaudeUseCase;

    @MockBean
    private BuscarUnidadeSaudePorNomeUseCase buscarUnidadeSaudePorNomeUseCase;

    @MockBean
    private AtualizarUnidadeSaudeUseCase atualizarUnidadeSaudeUseCase;

    @MockBean
    private DeletarUnidadeSaudeUseCase deletarUnidadeSaudeUseCase;

    @MockBean
    private UnidadeSaudeWebMapper mapper;

    @Test
    void deveCadastrarUnidadeSaudeComSucesso() throws Exception {
        // Arrange
        UnidadeSaudeRequest request = new UnidadeSaudeRequest();
        request.setNome("Hospital Central");
        request.setEndereco("Rua das Flores, 123");

        UnidadeSaude unidadeDomain = new UnidadeSaude(null, "Hospital Central", "Rua das Flores, 123", true);
        UnidadeSaude unidadeSalva = new UnidadeSaude(1L, "Hospital Central", "Rua das Flores, 123", true);

        UnidadeSaudeResponse response = new UnidadeSaudeResponse();
        response.setId(1L);
        response.setNome("Hospital Central");
        response.setEndereco("Rua das Flores, 123");
        response.setAtiva(true);

        when(mapper.toDomain(request)).thenReturn(unidadeDomain);
        when(cadastrarUnidadeSaudeUseCase.execute(unidadeDomain)).thenReturn(unidadeSalva);
        when(mapper.toResponse(unidadeSalva)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/unidades-saude")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Hospital Central"))
                .andExpect(jsonPath("$.endereco").value("Rua das Flores, 123"))
                .andExpect(jsonPath("$.ativa").value(true));

        verify(cadastrarUnidadeSaudeUseCase).execute(unidadeDomain);
    }

    @Test
    void deveBuscarUnidadeSaudePorIdComSucesso() throws Exception {
        // Arrange
        Long unidadeId = 1L;
        UnidadeSaude unidade = new UnidadeSaude(1L, "Clínica São João", "Av. Principal, 456", true);

        UnidadeSaudeResponse response = new UnidadeSaudeResponse();
        response.setId(1L);
        response.setNome("Clínica São João");
        response.setEndereco("Av. Principal, 456");
        response.setAtiva(true);

        when(buscarUnidadeSaudePorIdUseCase.execute(unidadeId)).thenReturn(unidade);
        when(mapper.toResponse(unidade)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/unidades-saude/{id}", unidadeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Clínica São João"))
                .andExpect(jsonPath("$.endereco").value("Av. Principal, 456"));

        verify(buscarUnidadeSaudePorIdUseCase).execute(unidadeId);
    }

    @Test
    void deveRetornar404QuandoUnidadeSaudeNaoEncontrada() throws Exception {
        // Arrange
        Long unidadeId = 999L;
        when(buscarUnidadeSaudePorIdUseCase.execute(unidadeId))
                .thenThrow(new UnidadeSaudeNaoEncontradaException("Unidade de saúde não encontrada"));

        // Act & Assert
        mockMvc.perform(get("/api/unidades-saude/{id}", unidadeId))
                .andExpect(status().isNotFound());

        verify(buscarUnidadeSaudePorIdUseCase).execute(unidadeId);
    }

    @Test
    void deveListarUnidadesSaudeComPaginacao() throws Exception {
        // Arrange
        UnidadeSaude unidade1 = new UnidadeSaude(1L, "Hospital A", "Endereço A", true);
        UnidadeSaude unidade2 = new UnidadeSaude(2L, "Hospital B", "Endereço B", true);

        Page<UnidadeSaude> page = new PageImpl<>(Arrays.asList(unidade1, unidade2), PageRequest.of(0, 10), 2);

        when(buscarTodasUnidadesSaudeUseCase.execute(any(PageRequest.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/unidades-saude")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.size").value(10));

        verify(buscarTodasUnidadesSaudeUseCase).execute(any(PageRequest.class));
    }

    @Test
    void deveDeletarUnidadeSaudeComSucesso() throws Exception {
        // Arrange
        Long unidadeId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/unidades-saude/{id}", unidadeId))
                .andExpect(status().isNoContent());

        verify(deletarUnidadeSaudeUseCase).execute(unidadeId);
    }

    @Test
    void deveRetornar404AoDeletarUnidadeSaudeInexistente() throws Exception {
        // Arrange
        Long unidadeId = 999L;
        doThrow(new UnidadeSaudeNaoEncontradaException("Unidade de saúde não encontrada"))
                .when(deletarUnidadeSaudeUseCase).execute(unidadeId);

        // Act & Assert
        mockMvc.perform(delete("/api/unidades-saude/{id}", unidadeId))
                .andExpect(status().isNotFound());

        verify(deletarUnidadeSaudeUseCase).execute(unidadeId);
    }

    @Test
    void deveAtualizarUnidadeSaudeComSucesso() throws Exception {
        // Arrange
        Long unidadeId = 1L;
        UnidadeSaudeRequest request = new UnidadeSaudeRequest();
        request.setNome("UBS Central Atualizada");
        request.setEndereco("Avenida Central, 100");
        request.setAtiva(true);

        UnidadeSaude unidadeSaudeAtualizada = new UnidadeSaude();
        unidadeSaudeAtualizada.setId(unidadeId);
        unidadeSaudeAtualizada.setNome(request.getNome());

        UnidadeSaudeResponse response = new UnidadeSaudeResponse();
        response.setId(unidadeId);
        response.setNome(request.getNome());

        when(mapper.toDomain(any(UnidadeSaudeRequest.class))).thenReturn(unidadeSaudeAtualizada);
        when(atualizarUnidadeSaudeUseCase.execute(eq(unidadeId), any(UnidadeSaude.class)))
                .thenReturn(unidadeSaudeAtualizada);
        when(mapper.toResponse(unidadeSaudeAtualizada)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/unidades-saude/{id}", unidadeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(unidadeId))
                .andExpect(jsonPath("$.nome").value(request.getNome()));

        verify(atualizarUnidadeSaudeUseCase).execute(eq(unidadeId), any(UnidadeSaude.class));
        verify(mapper).toResponse(unidadeSaudeAtualizada);
    }

    @Test
    void deveRetornar404AoAtualizarUnidadeSaudeInexistente() throws Exception {
        // Arrange
        Long unidadeId = 999L;
        UnidadeSaudeRequest request = new UnidadeSaudeRequest();
        request.setNome("UBS Inexistente");
        request.setEndereco("Rua das Flores, 123");

        UnidadeSaude unidadeSaude = new UnidadeSaude();
        when(mapper.toDomain(any(UnidadeSaudeRequest.class))).thenReturn(unidadeSaude);
        
        doThrow(new UnidadeSaudeNaoEncontradaException("Unidade de saúde não encontrada"))
                .when(atualizarUnidadeSaudeUseCase).execute(eq(unidadeId), any(UnidadeSaude.class));

        // Act & Assert
        mockMvc.perform(put("/api/unidades-saude/{id}", unidadeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(atualizarUnidadeSaudeUseCase).execute(eq(unidadeId), any(UnidadeSaude.class));
    }

    @Test
    void deveRetornar400AoAtualizarUnidadeSaudeComDadosInvalidos() throws Exception {
        // Arrange
        Long unidadeId = 1L;
        UnidadeSaudeRequest request = new UnidadeSaudeRequest();
        // Request sem nome (inválido)

        // Act & Assert
        mockMvc.perform(put("/api/unidades-saude/{id}", unidadeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(atualizarUnidadeSaudeUseCase, never()).execute(any(), any());
    }

    @Test
    void deveBuscarUnidadesSaudePorNomeComSucesso() throws Exception {
        // Arrange
        String nome = "Central";
        UnidadeSaude unidade1 = new UnidadeSaude(1L, "UBS Central", "Rua A, 123", true);
        UnidadeSaude unidade2 = new UnidadeSaude(2L, "Hospital Central", "Rua B, 456", true);
        
        Page<UnidadeSaude> page = new PageImpl<>(Arrays.asList(unidade1, unidade2), PageRequest.of(0, 10), 2);

        when(buscarUnidadeSaudePorNomeUseCase.execute(eq(nome), any(PageRequest.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/unidades-saude/buscar")
                        .param("nome", nome)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2));

        verify(buscarUnidadeSaudePorNomeUseCase).execute(eq(nome), any(PageRequest.class));
    }
}
