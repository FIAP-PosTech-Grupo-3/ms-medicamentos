package fiap.msmedicamentos.adapter.web.medicamento;

import lombok.Data;

import java.util.List;

@Data
public class PagedMedicamentoResponse {
    private List<MedicamentoResponse> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
    private boolean empty;
}
