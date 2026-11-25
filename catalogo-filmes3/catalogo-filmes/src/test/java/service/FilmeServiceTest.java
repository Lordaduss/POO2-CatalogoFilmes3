package service;

import dao.FilmeDAO;
import model.Filme;
import model.FilmeBuilder;
import model.Genero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilmeServiceTest {

    private boolean gravou;
    private FilmeService service;
    private Genero acao;

    @BeforeEach
    void preparar() {
        gravou = false;
        acao = new Genero(1, "Ação");

        FilmeDAO daoFalso = new FilmeDAO() {
            @Override
            public int inserir(Filme filme) {
                gravou = true;
                return 10;
            }

            @Override
            public boolean existeTituloAno(String titulo, int ano, int ignorarId) {
                return false;
            }
        };
        service = new FilmeService(daoFalso);
    }

    @Test
    void deveInserirFilmeValido() throws Exception {
        Filme filme = new FilmeBuilder().titulo("Interestelar").ano(2014).nota(9.0).genero(acao).build();

        int id = service.inserir(filme);

        assertEquals(10, id, "O service deve devolver o id gerado pelo DAO");
        assertTrue(gravou, "Um filme válido deve ser enviado ao DAO para gravar");
    }

    @Test
    void deveRecusarNotaForaDoIntervalo() {
        Filme filme = new FilmeBuilder().titulo("Interestelar").ano(2014).nota(11.0).genero(acao).build();

        RegraNegocioException erro = assertThrows(RegraNegocioException.class,
                () -> service.inserir(filme),
                "Nota 11 deveria lançar RegraNegocioException");

        assertEquals("A nota deve estar entre 0 e 10.", erro.getMessage(), "Mensagem de erro da nota");
        assertFalse(gravou, "Com nota inválida o DAO não pode ser chamado");
    }
}
