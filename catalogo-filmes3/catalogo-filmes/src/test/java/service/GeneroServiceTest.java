package service;

import dao.FilmeDAO;
import dao.GeneroDAO;
import model.Genero;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneroServiceTest {

    private boolean excluiu;

    private GeneroService criarService(int filmesDoGenero) {
        excluiu = false;

        FilmeDAO filmeDAO = new FilmeDAO() {
            @Override
            public int contarPorGenero(int generoId) {
                return filmesDoGenero;
            }
        };
        GeneroDAO generoDAO = new GeneroDAO() {
            @Override
            public void excluir(int id) {
                excluiu = true;
            }
        };
        return new GeneroService(generoDAO, filmeDAO);
    }

    @Test
    void naoDeveExcluirGeneroComFilmes() {
        GeneroService service = criarService(2);

        RegraNegocioException erro = assertThrows(RegraNegocioException.class,
                () -> service.excluir(new Genero(1, "Ação")),
                "Gênero com filmes não pode ser excluído");

        assertTrue(erro.getMessage().contains("2"), "A mensagem deve informar a quantidade de filmes");
        assertFalse(excluiu, "O DAO não pode excluir um gênero em uso");
    }
}
