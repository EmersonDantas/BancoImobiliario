package bancoimobiliario;

import arquivo.ArchiveManager;
import java.io.IOException;
/**
 * Classe que representa o tabuleiro do jogo.
 */
public class Tabuleiro {
    private final Posicao[] posicoes;
    private PilhaSorteOuReves pilhaDeCartasSorteOuReves;
    private static Tabuleiro instance;
    
    private Tabuleiro() throws IOException{
        ArchiveManager archMan = ArchiveManager.getInstance();
        posicoes = archMan.getTabuleiro();
    }
    
    /**
     * Padrão singleton que garante se só tenha uma instancia dessa classe.
     * @return Tabuleiro propria instancia.
     */
    public static synchronized Tabuleiro getInstance() throws IOException{
        if(instance == null){
            instance = new Tabuleiro();
        }
        
        return instance;
    }
    
    /**
     * Retorna a array de posições do tabuleiro.
     * @return posicoes
     */
    public Posicao[] getPosicoes() {
        return posicoes;
    }
    
    /**
     * Este método retorna o nome da posicao na indice recebido como parâmetro.
     * @param posicao indice
     * @return String nomeDaCarta
     */
    public String getNomePosicao(int posicao){
        return this.posicoes[posicao].getNome();
    }
    
    /**
     * Retorna a pilha de cartas sorte ou reves.
     * @return pilhaDeCartasSorteOuReves
     */
    public PilhaSorteOuReves getPilhaDeCartasSorteOuReves() {
        return pilhaDeCartasSorteOuReves;
    }
    
    /**
     * Esse método devolve a carta "Saia da prisão" a pilha de posicoes sorte ou reves, isso acontece quando o jogador que até então estava com ela a usa.
     */
    public void devolveCartaSairDaPrisaoAPilhaSorteOuReves(){
        this.pilhaDeCartasSorteOuReves.adicionaCartaSairDaPrisao();
    }
    
    /**
     * Este método retorna a posicao no indice recebido como parâmetro.
     * @param indice endereco onde se encontra a posição
     * @return Posicao posição no indice
     */
    public Posicao getPosicaoPorIndice(int indice){
        return posicoes[indice];
    }
    
}
