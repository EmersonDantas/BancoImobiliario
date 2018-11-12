package bancoimobiliario;

import java.io.IOException;

/**
 * Classe que representa a posição "va para prisão" no tabuleiro.
 */
public class VaParaPrisao implements Posicao{
    private static VaParaPrisao instance;
    
    private VaParaPrisao(){ }
    
    /**
     * Padrão singleton que garante se só tenha uma instancia dessa classe.
     * @return VaParaPrisao propria instancia.
     */
    public static VaParaPrisao getInstance(){
        if(instance == null){
            instance = new VaParaPrisao();
        }
        
        return instance;
    }
    
    /**
     * Retona uma breve descrição dos atributos da classe.
     * @return descrição
     */
    @Override
    public String getDescricao() {
        return "Vá para a prisão, sem reclamar!";
    }

     /**
     * Retorna o nome da posição.
     * @return nomeDaPosicao
     */
    @Override
    public String getNome() {
        return "Vá para a prisão";
    }

    @Override
    public boolean realizaAcao(Jogador jogador) {
        JogoFacade facade = null;
        try {
            facade = JogoFacade.getInstance();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(this.getDescricao());
        facade.prenderJogador(jogador);
        return false;
    }
    
}
