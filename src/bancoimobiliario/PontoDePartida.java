package bancoimobiliario;
/**
 * Classe que representa a posição Ponto de partida no tabuleiro.
 */
public class PontoDePartida implements Posicao{
    private static PontoDePartida instance;
    
    private PontoDePartida(){}
    
    /**
     * Padrão singleton que garante se só tenha uma instancia dessa classe.
     * @return PontoDePartida propria instancia.
     */
    public static PontoDePartida getInstance(){
        if(instance == null){
            instance = new PontoDePartida();
        }
        
        return instance;
    }
    
    /**
     * Retona uma breve descrição dos atributos da classe.
     * @return descrição
     */
    @Override
    public String getDescricao() {
        return "De volta as origens...";
    }
    
    /**
     * Retorna o nome da posição.
     * @return nomeDaPosicao
     */
    @Override
    public String getNome() {
        return "Ponto de partida";
    }

    @Override
    public boolean realizaAcao(Jogador jogador) {
        System.out.println(this.getDescricao());
        return false;
    }
    
}
