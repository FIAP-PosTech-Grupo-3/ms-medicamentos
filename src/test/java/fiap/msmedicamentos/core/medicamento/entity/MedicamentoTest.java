package fiap.msmedicamentos.core.medicamento.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MedicamentoTest {

    @Test
    void deveSerValidoComNomeEPrincipioAtivo() {
        // Arrange
        Medicamento medicamento = new Medicamento(
            1L,
            "Paracetamol",
            "Paracetamol",
            "EMS",
            "500mg"
        );

        // Act & Assert
        assertTrue(medicamento.isValido());
    }

    @Test
    void deveSerInvalidoComNomeVazio() {
        // Arrange
        Medicamento medicamento = new Medicamento(
            1L,
            "",
            "Paracetamol",
            "EMS",
            "500mg"
        );

        // Act & Assert
        assertFalse(medicamento.isValido());
    }

    @Test
    void deveSerInvalidoComNomeNulo() {
        // Arrange
        Medicamento medicamento = new Medicamento(
            1L,
            null,
            "Paracetamol",
            "EMS",
            "500mg"
        );

        // Act & Assert
        assertFalse(medicamento.isValido());
    }

    @Test
    void deveSerInvalidoComPrincipioAtivoVazio() {
        // Arrange
        Medicamento medicamento = new Medicamento(
            1L,
            "Paracetamol",
            "",
            "EMS",
            "500mg"
        );

        // Act & Assert
        assertFalse(medicamento.isValido());
    }

    @Test
    void deveSerInvalidoComPrincipioAtivoNulo() {
        // Arrange
        Medicamento medicamento = new Medicamento(
            1L,
            "Paracetamol",
            null,
            "EMS",
            "500mg"
        );

        // Act & Assert
        assertFalse(medicamento.isValido());
    }

    @Test
    void deveSerValidoComFabricanteNulo() {
        // Arrange
        Medicamento medicamento = new Medicamento(
            1L,
            "Paracetamol",
            "Paracetamol",
            null,
            "500mg"
        );

        // Act & Assert
        assertTrue(medicamento.isValido());
    }

    @Test
    void deveSerValidoComDosagemNula() {
        // Arrange
        Medicamento medicamento = new Medicamento(
            1L,
            "Paracetamol",
            "Paracetamol",
            "EMS",
            null
        );

        // Act & Assert
        assertTrue(medicamento.isValido());
    }

    @Test
    void deveSerInvalidoComNomeApenasBranco() {
        // Arrange
        Medicamento medicamento = new Medicamento(
            1L,
            "   ",
            "Paracetamol",
            "EMS",
            "500mg"
        );

        // Act & Assert
        assertFalse(medicamento.isValido());
    }

    @Test
    void deveSerInvalidoComPrincipioAtivoApenasBranco() {
        // Arrange
        Medicamento medicamento = new Medicamento(
            1L,
            "Paracetamol",
            "   ",
            "EMS",
            "500mg"
        );

        // Act & Assert
        assertFalse(medicamento.isValido());
    }
}
