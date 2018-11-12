package bancoimobiliario;
/**
 * Classe que representa a posição ParadaLivre no tabuleiro.
 */
public class ParadaLivre implements Posicao{
    private static ParadaLivre instance;
    
    private ParadaLivre(){}
    /**
     * Padrão singleton que garante se só tenha uma instancia dessa classe.
     * @return ParadaLivre propria instancia.
     */
    public static ParadaLivre getInstance(){
        if(instance == null){
            instance = new ParadaLivre();
        }
        
        return instance;
    }
    
    /**
     * Retona uma breve descrição dos atributos da classe.
     * @return descrição
     */
    @Override
    public String getDescricao() {
        return "Parada Livre - Admire a paisagem";
    }
    
    /**
     * Retorna o nome da posição.
     * @return nomeDaPosicao
     */
    @Override
    public String getNome() {
        return "Parada Livre";
    }

    @Override
    public boolean realizaAcao(Jogador jogador) {
        System.out.println(this.getDescricao());
        return false;
    }
    
}
