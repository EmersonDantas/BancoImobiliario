package testesDeUnidade;

import bancoimobiliario.Tabuleiro;
import bancoimobiliario.TituloDePosse;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import bancoimobiliario.Posicao;

/**
 * Testa funcções do tabuleiro
 */

public class tabuleiroTest {
    private Tabuleiro tab;
    Posicao[] cartas;
    TituloDePosse titulo;
    
    @Before
    public void setUp() throws Exception {
        tab = Tabuleiro.getInstance();
        cartas = tab.getPosicoes();
    }
    
    /**
     * Testa se está sendo retornado o nome da propriedade por indice corretamente.
     */
    @Test
    public void testNomePosicao(){//Testando se o nome dá posição está sendo retornado corretamente.
        assertEquals("Av.Presidente Vargas", tab.getNomePosicao(3));
        assertEquals("Leblón", tab.getNomePosicao(1));
    }
}
