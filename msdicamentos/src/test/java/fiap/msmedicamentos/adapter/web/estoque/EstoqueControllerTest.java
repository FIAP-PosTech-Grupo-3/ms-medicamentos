package fiap.msmedicamentos.adapter.web.estoque;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.msmedicamentos.core.estoque.entity.EstoqueMedicamento;
import fiap.msmedicamentos.core.estoque.exception.EstoqueNaoEncontradoException;
import fiap.msmedicamentos.core.estoque.usecase.AdicionarEstoqueUseCase;
import fiap.msmedicamentos.core.estoque.usecase.ConsultarEstoqueUseCase;
import fiap.msmedicamentos.core.estoque.usecase.RemoverEstoqueUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = EstoqueController.class, excludeAutoConfiguration = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
class EstoqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdicionarEstoqueUseCase adicionarEstoqueUseCase;

    @MockBean
    private RemoverEstoqueUseCase removerEstoqueUseCase;

    @MockBean
    private ConsultarEstoqueUseCase consultarEstoqueUseCase;

    @MockBean
    private fiap.msmedicamentos.core.estoque.usecase.AtualizarEstoqueUseCase atualizarEstoqueUseCase;

    @MockBean
    private EstoqueWebMapper mapper;

    @Test
    void deveAdicionarEstoqueComSucesso() throws Exception {
        // Arrange
        MovimentarEstoqueRequest request = new MovimentarEstoqueRequest();
        request.setMedicamentoId(1L);
        request.setUnidadeSaudeId(1L);
        request.setQuantidade(50);

        EstoqueMedicamento estoque = new EstoqueMedicamento(1L, 1L, 1L, 100, 10);
        EstoqueResponse response = new EstoqueResponse();
        response.setId(1L);
        response.setMedicamentoId(1L);
        response.setUnidadeSaudeId(1L);
        response.setQuantidade(100);

        when(adicionarEstoqueUseCase.execute(1L, 1L, 50)).thenReturn(estoque);
        when(mapper.toResponse(estoque)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/estoque/adicionar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.medicamentoId").value(1L))
                .andExpect(jsonPath("$.unidadeSaudeId").value(1L))
                .andExpect(jsonPath("$.quantidade").value(100));

        verify(adicionarEstoqueUseCase).execute(1L, 1L, 50);
    }

    @Test
    void deveRemoverEstoqueComSucesso() throws Exception {
        // Arrange
        MovimentarEstoqueRequest request = new MovimentarEstoqueRequest();
        request.setMedicamentoId(1L);
        request.setUnidadeSaudeId(1L);
        request.setQuantidade(20);

        EstoqueMedicamento estoque = new EstoqueMedicamento(1L, 1L, 1L, 30, 10);
        EstoqueResponse response = new EstoqueResponse();
        response.setId(1L);
        response.setMedicamentoId(1L);
        response.setUnidadeSaudeId(1L);
        response.setQuantidade(30);

        when(removerEstoqueUseCase.execute(1L, 1L, 20)).thenReturn(estoque);
        when(mapper.toResponse(estoque)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/estoque/remover")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantidade").value(30));

        verify(removerEstoqueUseCase).execute(1L, 1L, 20);
    }

    @Test
    void deveConsultarEstoquePorMedicamento() throws Exception {
        // Arrange
        Long medicamentoId = 1L;
        List<EstoqueMedicamento> estoques = Arrays.asList(
            new EstoqueMedicamento(1L, 1L, 1L, 50, 10),
            new EstoqueMedicamento(2L, 1L, 2L, 30, 5)
        );

        EstoqueResponse response1 = new EstoqueResponse();
        response1.setId(1L);
        response1.setQuantidade(50);

        EstoqueResponse response2 = new EstoqueResponse();
        response2.setId(2L);
        response2.setQuantidade(30);

        when(consultarEstoqueUseCase.buscarPorMedicamento(medicamentoId)).thenReturn(estoques);
        when(mapper.toResponse(estoques.get(0))).thenReturn(response1);
        when(mapper.toResponse(estoques.get(1))).thenReturn(response2);

        // Act & Assert
        mockMvc.perform(get("/api/estoque/medicamento/{medicamentoId}", medicamentoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(consultarEstoqueUseCase).buscarPorMedicamento(medicamentoId);
    }

    @Test
    void deveListarTodosEstoquesComPaginacao() throws Exception {
        // Arrange
        EstoqueMedicamento estoque1 = new EstoqueMedicamento(1L, 1L, 1L, 100, 20);
        EstoqueMedicamento estoque2 = new EstoqueMedicamento(2L, 2L, 1L, 50, 10);
        
        Page<EstoqueMedicamento> page = new PageImpl<>(Arrays.asList(estoque1, estoque2), PageRequest.of(0, 10), 2);

        when(consultarEstoqueUseCase.buscarTodos(any(PageRequest.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/estoque")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2));

        verify(consultarEstoqueUseCase).buscarTodos(any(PageRequest.class));
    }

    @Test
    void deveRetornar404QuandoEstoqueNaoEncontrado() throws Exception {
        // Arrange
        Long medicamentoId = 999L;
        when(consultarEstoqueUseCase.buscarPorMedicamento(medicamentoId))
                .thenThrow(new EstoqueNaoEncontradoException("Estoque não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/estoque/medicamento/{medicamentoId}", medicamentoId))
                .andExpect(status().isNotFound());

        verify(consultarEstoqueUseCase).buscarPorMedicamento(medicamentoId);
    }

    @Test
    void deveAtualizarEstoqueComSucesso() throws Exception {
        // Arrange
        AtualizarEstoqueRequest request = new AtualizarEstoqueRequest();
        request.setMedicamentoId(1L);
        request.setUnidadeSaudeId(1L);
        request.setQuantidade(75);
        request.setQuantidadeMinima(15);

        EstoqueMedicamento estoque = new EstoqueMedicamento(1L, 1L, 1L, 75, 15);
        EstoqueResponse response = new EstoqueResponse();
        response.setId(1L);
        response.setMedicamentoId(1L);
        response.setUnidadeSaudeId(1L);
        response.setQuantidade(75);
        response.setQuantidadeMinima(15);

        when(atualizarEstoqueUseCase.execute(1L, 1L, 75, 15)).thenReturn(estoque);
        when(mapper.toResponse(estoque)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/estoque/atualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.medicamentoId").value(1L))
                .andExpect(jsonPath("$.quantidade").value(75))
                .andExpect(jsonPath("$.quantidadeMinima").value(15));

        verify(atualizarEstoqueUseCase).execute(1L, 1L, 75, 15);
        verify(mapper).toResponse(estoque);
    }

    @Test
    void deveConsultarEstoquePorUnidadeSaude() throws Exception {
        // Arrange
        Long unidadeSaudeId = 1L;
        EstoqueMedicamento estoque1 = new EstoqueMedicamento(1L, 1L, unidadeSaudeId, 100, 20);
        EstoqueMedicamento estoque2 = new EstoqueMedicamento(2L, 2L, unidadeSaudeId, 50, 10);
        
        List<EstoqueMedicamento> estoques = Arrays.asList(estoque1, estoque2);

        when(consultarEstoqueUseCase.buscarPorUnidadeSaude(unidadeSaudeId)).thenReturn(estoques);

        // Act & Assert
        mockMvc.perform(get("/api/estoque/unidade/{unidadeSaudeId}", unidadeSaudeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(consultarEstoqueUseCase).buscarPorUnidadeSaude(unidadeSaudeId);
    }

    @Test
    void deveConsultarEstoqueBaixo() throws Exception {
        // Arrange
        EstoqueMedicamento estoque1 = new EstoqueMedicamento(1L, 1L, 1L, 5, 10); // Baixo estoque
        EstoqueMedicamento estoque2 = new EstoqueMedicamento(2L, 2L, 1L, 3, 10); // Baixo estoque
        
        List<EstoqueMedicamento> estoquesBaixos = Arrays.asList(estoque1, estoque2);

        when(consultarEstoqueUseCase.buscarEstoqueBaixo()).thenReturn(estoquesBaixos);

        // Act & Assert
        mockMvc.perform(get("/api/estoque/baixo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(consultarEstoqueUseCase).buscarEstoqueBaixo();
    }

    @Test
    void deveRetornar400AoAdicionarEstoqueComDadosInvalidos() throws Exception {
        // Arrange
        MovimentarEstoqueRequest request = new MovimentarEstoqueRequest();
        // Request sem medicamentoId (inválido)
        request.setUnidadeSaudeId(1L);
        request.setQuantidade(50);

        // Act & Assert
        mockMvc.perform(post("/api/estoque/adicionar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(adicionarEstoqueUseCase, never()).execute(any(), any(), any());
    }

    @Test
    void deveRetornar400AoRemoverEstoqueComQuantidadeNegativa() throws Exception {
        // Arrange
        MovimentarEstoqueRequest request = new MovimentarEstoqueRequest();
        request.setMedicamentoId(1L);
        request.setUnidadeSaudeId(1L);
        request.setQuantidade(-10); // Quantidade negativa

        // Act & Assert
        mockMvc.perform(post("/api/estoque/remover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(removerEstoqueUseCase, never()).execute(any(), any(), any());
    }
}
