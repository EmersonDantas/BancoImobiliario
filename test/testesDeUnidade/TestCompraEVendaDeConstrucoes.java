/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesDeUnidade;

import bancoimobiliario.Jogador;
import bancoimobiliario.JogoFacade;
import bancoimobiliario.Propriedade;
import bancoimobiliario.Tabuleiro;
import excecoes.MaximoDeContrucoesAtingidoException;
import excecoes.SaldoInsuficienteException;
import excecoes.SemConstrucoesException;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testa funções de compra e venda de construções em propriedades
 */
public class TestCompraEVendaDeConstrucoes {
    private Propriedade propriedade;
    
    public TestCompraEVendaDeConstrucoes() throws IOException {
        JogoFacade facade = JogoFacade.getInstance();
        Tabuleiro tab = facade.getTabuleiro();
        propriedade = (Propriedade) tab.getPosicoes()[39];
        Jogador peao = new Jogador("Mario", "vermelho");
        propriedade.setDono(peao);
    }
    
    @Before
    public void setUp() {
        propriedade.setContadorDeContrucoes(0);
    }
    
    /**
     * Testa a compra de contruções de uma propriedade.
     * @throws SaldoInsuficienteException
     * @throws MaximoDeContrucoesAtingidoException 
     */
    @Test
    public void testCompraConstrucao() throws SaldoInsuficienteException, MaximoDeContrucoesAtingidoException{
        propriedade.construir();
        assertEquals(1, propriedade.getContadorDeContrucoes());
    }
    
    /**
     * Testa a venda de contruções de uma propriedade.
     * @throws SemConstrucoesException 
     */
    @Test
    public void testVendaConstrucao() throws SemConstrucoesException{
        propriedade.setContadorDeContrucoes(1);
        propriedade.vender();
        assertEquals(0, propriedade.getContadorDeContrucoes());
    }
    
    /**
     * Testa se a exceção está sendo lançada exceção ao tentar contruir além do limite de construções de uma propriedade.
     * @throws SaldoInsuficienteException
     * @throws MaximoDeContrucoesAtingidoException 
     */
    @Test (expected=MaximoDeContrucoesAtingidoException.class)
    public void testMaximoDeContrucoesAtingidoException() throws SaldoInsuficienteException, MaximoDeContrucoesAtingidoException{
        propriedade.setContadorDeContrucoes(5);
        propriedade.construir();
    }
    
}
