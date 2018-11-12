package testesDeUnidade;

import bancoimobiliario.PilhaSorteOuReves;
import bancoimobiliario.SorteOuReves;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Testa métodos da PilhaSorteOuReves
 */
public class PilhaSorteOuRevesTeste {
    private PilhaSorteOuReves pilha;
    @Before
    public void setUp() throws IOException{
        pilha = PilhaSorteOuReves.getInstance();
    }
    
    /**
     * Testa o método pegar carta, que pega a carta do topo da pilha.
     */
    @Test
    public void testPegarCarta(){//Testando se o jogador está pegando a primeira carta da pilha.
        assertEquals(pilha.getCartas().get(0), pilha.pegarCarta());
    }
    
    /**
     * Testa o método que coloca ultima carta carta que foi pega, no fim da fila.
     */
    @Test
    public void testeColocarCartaNoFim(){//Testando se a carta que foi pega foi adicionada ao fim da pilha.
        SorteOuReves teste = pilha.getCartas().get(0);//Aqui defino a carta que será colocada no fim.
        pilha.colocarCartaNoFim(teste);//E aqui é feito o teste do método recebendo a carta definida.
        assertEquals(teste, pilha.getCartas().get(pilha.getCartas().size()-1)); // Testando se a ultima carta é a carta que adicionamos no fim anteriormente
    }
    
    /**
     * Teste o método que remove a carta "Saia da prisão" da pilha.
     */
    @Test
    public void testeRemoverCartaSairDaPrisao(){//Testando se a carta SairDaPrisao foi removida da pilha.
        pilha.removeCartaSairDaPrisao();
        assertEquals(30, pilha.getCartas().size());//A pilha é composta por 31 objetos SorteOuReves, se a carta foi removida a pilha terá tamanho 30.
    }
    
    /**
     * Testa o método que adiciona a carta "Saia da prisão" a pilha.
     */
    @Test
    public void testAddCartaSairDaPrisao(){//Testando se a carta SairDaPrisao foi readcionada corretamente.
        pilha.removeCartaSairDaPrisao();//Aqui removo ela, a pilha fica com tamanho 30.
        assertEquals(30, pilha.getCartas().size());//A pilha é composta por 31 objetos SorteOuReves, se a carta foi removida a pilha terá tamanho 30.
        pilha.adicionaCartaSairDaPrisao();//Aqui ela é re-adicionada.
        assertEquals(31, pilha.getCartas().size());//Se o tamanho da pilha for igual a 31 então quer dizer que funcionou.
    }
}
