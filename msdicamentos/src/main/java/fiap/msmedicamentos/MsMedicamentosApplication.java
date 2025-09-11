package fiap.msmedicamentos;
    
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class MsMedicamentosApplication {
    
    public static void main(String[] args) {
        log.info("Iniciando MS Medicamentos...");
        SpringApplication.run(MsMedicamentosApplication.class, args);
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        log.info("MS Medicamentos iniciado com sucesso!");
        log.info("Swagger: http://localhost:8080/swagger-ui");
    }
}
