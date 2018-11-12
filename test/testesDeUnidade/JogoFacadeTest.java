/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesDeUnidade;

import bancoimobiliario.Jogador;
import bancoimobiliario.JogoFacade;
import bancoimobiliario.Tabuleiro;
import bancoimobiliario.TituloDePosse;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import bancoimobiliario.Posicao;

/**
 * Teste de funções da fachada do jogo
 */
public class JogoFacadeTest {
    private Jogador peao;
    private Jogador peao2;
    private JogoFacade facade;
    private Tabuleiro tab;
    private Posicao[] posicoesNoTabuleiro;
    
    public JogoFacadeTest() throws IOException {
        peao = new Jogador("Mario", "vermelho");
        peao2 = new Jogador("Luigi", "verde");
        facade = JogoFacade.getInstance();
        tab = facade.getTabuleiro();
        posicoesNoTabuleiro = tab.getPosicoes();
    }
    
    @Before
    public void setUp(){
        peao.setPosicaoNoTabuleiroIndice(0);
        peao2.setPosicaoNoTabuleiroIndice(0);
    }
    
    /**
     * Testa se o padrão singleton no jogoFacade está dando a mesma instancia.
     * @throws IOException 
     */
    @Test
    public void testeSingleton() throws IOException{
        JogoFacade facadeTest = JogoFacade.getInstance();
        assertEquals(facadeTest, facade);
    }
    
    /**
     * Testa o metodo obterNovaPosicao, que retorna a nova posicao do jogador no tabuleiro ao jogar os dados e passar o valor da soma no parametro.
     */
    @Test
    public void testeObterNovaPosicao(){
        assertEquals(10, facade.obterNovaPosicao(10, peao));
        assertEquals(5, facade.obterNovaPosicao(5, peao2));
    }
    
    /**
     * Testa a movimentação do jogador no tabuleiro.
     */
    @Test
    public void testMoverJogadorNoTabuleiro(){//Testando a movimentação no Tabuleiro. 
        assertEquals(posicoesNoTabuleiro[5], facade.moveJogadorNoTabuleiro(5, peao)); //Verificando se o peão foi para a posição correta
        assertEquals(posicoesNoTabuleiro[10], facade.moveJogadorNoTabuleiro(10, peao2)); //Verificando se o peão foi para a posição correta
        
    }
    
    /**
     * Testa se o jogador deu a volta no tabuleiro.
     */
    @Test
    public void testJogadorDeuAVoltaNotabuleiro(){
        assertTrue(facade.jogadorDeuAVoltaNotabuleiro(40, peao));
        assertFalse(facade.jogadorDeuAVoltaNotabuleiro(39, peao));
    }
    
    /**
     * Testa se o jogador está preso.
     */
    @Test
    public void testJogadorEstaPreso(){
        facade.prenderJogador(peao);
        assertTrue(facade.jogadorEstaPreso(peao));
        assertFalse(facade.jogadorEstaPreso(peao2));
    }
    
    /**
     * Testa se está adicionando o dono da posse corretamente.
     */
    @Test
    public void testAddDonoPosse(){// Testando se o método AddDonoDePosse está funcionando, este método só é chamado caso o jogador pare em um titulo de posse.
        peao.setPosicaoNoTabuleiroIndice(3); //Atribuindo ao peao do jogador de teste a posição 3 no tabuleiro(que é uma propriedade)
        facade.adicionarDonoDePosse(peao); //Atribuindo o peao como dono do titulo de posse no qual ele parou
        TituloDePosse t = (TituloDePosse)tab.getPosicaoPorIndice(3); //Alterando o tipo da variável para usar o método getDono(), tendo em conta que essa carta é um titulo de posse.
        assertEquals(peao, t.getDono()); //Verificando se o peao foi adicionado como dono do titulo de posse acima
    }
    
}
