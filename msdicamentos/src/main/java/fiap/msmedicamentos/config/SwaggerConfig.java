package fiap.msmedicamentos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url("http://localhost:8080").description("Servidor Local")))
                .info(new Info()
                        .title("MS Medicamentos API")
                        .description("API para gerenciamento de medicamentos - Sistema de controle de estoque e cadastro de medicamentos")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FIAP - Pós Tech Grupo 3")
                                .email("grupo3@fiap.com.br")
                                .url("https://github.com/FIAP-PosTech-Grupo-3/ms-medicamentos"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
