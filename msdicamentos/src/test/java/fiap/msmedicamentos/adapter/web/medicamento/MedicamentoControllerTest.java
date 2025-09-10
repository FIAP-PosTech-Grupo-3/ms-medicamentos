package fiap.msmedicamentos.adapter.web.medicamento;

import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.msmedicamentos.adapter.web.medicamento.MedicamentoWebMapper;
import fiap.msmedicamentos.adapter.web.medicamento.PagedMedicamentoResponse;
import fiap.msmedicamentos.adapter.web.medicamento.MedicamentoResponse;
import fiap.msmedicamentos.adapter.web.medicamento.CadastrarMedicamentoRequest;
import fiap.msmedicamentos.core.medicamento.entity.Medicamento;
import fiap.msmedicamentos.core.medicamento.exception.MedicamentoNaoEncontradoException;
import fiap.msmedicamentos.core.medicamento.usecase.AtualizarMedicamentoUseCase;
import fiap.msmedicamentos.core.medicamento.usecase.BuscarMedicamentoPorIdUseCase;
import fiap.msmedicamentos.core.medicamento.usecase.BuscarMedicamentoPorNomeUseCase;
import fiap.msmedicamentos.core.medicamento.usecase.BuscarTodosMedicamentosUseCase;
import fiap.msmedicamentos.core.medicamento.usecase.CadastrarMedicamentoUseCase;
import fiap.msmedicamentos.core.medicamento.usecase.DeletarMedicamentoUseCase;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MedicamentoController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
        })
class MedicamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CadastrarMedicamentoUseCase cadastrarMedicamentoUseCase;
    
    @MockBean
    private BuscarMedicamentoPorIdUseCase buscarMedicamentoPorIdUseCase;
    
    @MockBean
    private BuscarTodosMedicamentosUseCase buscarTodosMedicamentosUseCase;
    
    @MockBean
    private BuscarMedicamentoPorNomeUseCase buscarMedicamentoPorNomeUseCase;
    
    @MockBean
    private AtualizarMedicamentoUseCase atualizarMedicamentoUseCase;
    
    @MockBean
    private DeletarMedicamentoUseCase deletarMedicamentoUseCase;    @MockBean
    private MedicamentoWebMapper mapper;

    @Test
    void deveCadastrarMedicamentoComSucesso() throws Exception {
        // Arrange
        CadastrarMedicamentoRequest request = new CadastrarMedicamentoRequest();
        request.setNome("Paracetamol");
        request.setPrincipioAtivo("Paracetamol");
        request.setFabricante("EMS");
        request.setDosagem("500mg");

        Medicamento medicamentoDomain = new Medicamento(null, "Paracetamol", "Paracetamol", "EMS", "500mg");
        Medicamento medicamentoSalvo = new Medicamento(1L, "Paracetamol", "Paracetamol", "EMS", "500mg");
        
        MedicamentoResponse response = new MedicamentoResponse();
        response.setId(1L);
        response.setNome("Paracetamol");
        response.setPrincipioAtivo("Paracetamol");
        response.setFabricante("EMS");
        response.setDosagem("500mg");

        when(mapper.toDomain(request)).thenReturn(medicamentoDomain);
        when(cadastrarMedicamentoUseCase.execute(medicamentoDomain)).thenReturn(medicamentoSalvo);
        when(mapper.toResponse(medicamentoSalvo)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/medicamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Paracetamol"))
                .andExpect(jsonPath("$.principioAtivo").value("Paracetamol"))
                .andExpect(jsonPath("$.fabricante").value("EMS"))
                .andExpect(jsonPath("$.dosagem").value("500mg"));

        verify(cadastrarMedicamentoUseCase).execute(medicamentoDomain);
    }

    @Test
    void deveBuscarMedicamentoPorIdComSucesso() throws Exception {
        // Arrange
        Long medicamentoId = 1L;
        Medicamento medicamento = new Medicamento(1L, "Ibuprofeno", "Ibuprofeno", "Bayer", "400mg");
        
        MedicamentoResponse response = new MedicamentoResponse();
        response.setId(1L);
        response.setNome("Ibuprofeno");
        response.setPrincipioAtivo("Ibuprofeno");
        response.setFabricante("Bayer");
        response.setDosagem("400mg");

        when(buscarMedicamentoPorIdUseCase.execute(medicamentoId)).thenReturn(medicamento);
        when(mapper.toResponse(medicamento)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/medicamentos/{id}", medicamentoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Ibuprofeno"))
                .andExpect(jsonPath("$.fabricante").value("Bayer"));

        verify(buscarMedicamentoPorIdUseCase).execute(medicamentoId);
    }

    @Test
    void deveRetornar404QuandoMedicamentoNaoEncontrado() throws Exception {
        // Arrange
        Long medicamentoId = 999L;
        when(buscarMedicamentoPorIdUseCase.execute(medicamentoId))
                .thenThrow(new MedicamentoNaoEncontradoException(medicamentoId));

        // Act & Assert
        mockMvc.perform(get("/api/medicamentos/{id}", medicamentoId))
                .andExpect(status().isNotFound());

        verify(buscarMedicamentoPorIdUseCase).execute(medicamentoId);
    }

    @Test
    void deveListarMedicamentosComPaginacao() throws Exception {
        // Arrange
        Medicamento medicamento1 = new Medicamento(1L, "Paracetamol", "Paracetamol", "EMS", "500mg");
        Medicamento medicamento2 = new Medicamento(2L, "Ibuprofeno", "Ibuprofeno", "Bayer", "400mg");
        
        Page<Medicamento> page = new PageImpl<>(Arrays.asList(medicamento1, medicamento2), PageRequest.of(0, 10), 2);

        // Criar PagedMedicamentoResponse mockado
        PagedMedicamentoResponse pagedResponse = new PagedMedicamentoResponse();
        pagedResponse.setContent(Arrays.asList(
            createMedicamentoResponse(1L, "Paracetamol", "Paracetamol", "EMS", "500mg"),
            createMedicamentoResponse(2L, "Ibuprofeno", "Ibuprofeno", "Bayer", "400mg")
        ));
        pagedResponse.setTotalElements(2);
        pagedResponse.setPageSize(10);
        pagedResponse.setPageNumber(0);
        pagedResponse.setTotalPages(1);

        when(buscarTodosMedicamentosUseCase.execute(any(PageRequest.class))).thenReturn(page);
        when(mapper.toPagedResponse(page)).thenReturn(pagedResponse);

        // Act & Assert
        mockMvc.perform(get("/api/medicamentos")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.pageSize").value(10));

        verify(buscarTodosMedicamentosUseCase).execute(any(PageRequest.class));
        verify(mapper).toPagedResponse(page);
    }
    
    private MedicamentoResponse createMedicamentoResponse(Long id, String nome, String principioAtivo, 
                                                         String fabricante, String dosagem) {
        MedicamentoResponse response = new MedicamentoResponse();
        response.setId(id);
        response.setNome(nome);
        response.setPrincipioAtivo(principioAtivo);
        response.setFabricante(fabricante);
        response.setDosagem(dosagem);
        return response;
    }

    @Test
    void deveDeletarMedicamentoComSucesso() throws Exception {
        // Arrange
        Long medicamentoId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/medicamentos/{id}", medicamentoId))
                .andExpect(status().isNoContent());

        verify(deletarMedicamentoUseCase).executar(medicamentoId);
    }

    @Test
    void deveRetornar404AoDeletarMedicamentoInexistente() throws Exception {
        // Arrange
        Long medicamentoId = 999L;
        doThrow(new MedicamentoNaoEncontradoException(medicamentoId))
                .when(deletarMedicamentoUseCase).executar(medicamentoId);

        // Act & Assert
        mockMvc.perform(delete("/api/medicamentos/{id}", medicamentoId))
                .andExpect(status().isNotFound());

        verify(deletarMedicamentoUseCase).executar(medicamentoId);
    }
}
