package marlon.leoner.projeto.avaliacao.tecnica.utils;

import static org.junit.jupiter.api.Assertions.*;

import marlon.leoner.projeto.avaliacao.tecnica.exception.InvalidParameterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ParseUtilsTest {
    @Test
    @DisplayName("Sucesso ao mascarar o CPF do cliente")
    void validarSucessoAoMascarar() {
        String cpf = "00011122233";
        String expect = "000******33";

        String cpfMasked = ParseUtils.maskCpf(cpf);

        assertEquals(expect, cpfMasked);
    }

    @Test
    @DisplayName("Erro ao mascarar o CPF do cliente")
    void validarErroAoMascararVazio() {
        String cpf = "";
        assertThrows(InvalidParameterException.class, () -> ParseUtils.maskCpf(cpf));
    }

    @Test
    @DisplayName("Erro ao mascarar o CPF do cliente")
    void validarErroAoMascararMuitoCaracter() {
        String cpf = "0001112223344";
        assertThrows(InvalidParameterException.class, () -> ParseUtils.maskCpf(cpf));
    }
}
