package bancoimobiliario;
/**
 * Classe que representa a posição prisão no tabuleiro.
 */
public class Prisao implements Posicao{
    private static Prisao instance;
    
    private Prisao(){ }
      
    /**
     * Padrão singleton que garante se só tenha uma instancia dessa classe.
     * @return Prisao propria instancia.
     */
    public static Prisao getInstance(){
        if(instance == null){
            instance = new Prisao();
        }
        
        return instance;
    }
    
    /**
     * Retona uma breve descrição dos atributos da classe.
     * @return descrição
     */
    @Override
    public String getDescricao() {
        return "Calma, você só está visitando...";
    }
    
    /**
     * Retorna o nome da posição.
     * @return nomeDaPosicao
     */
    @Override
    public String getNome() {
        return "Prisao como visitante";
    }

    @Override
    public boolean realizaAcao(Jogador jogador) {
            System.out.println(this.getDescricao());
        return false;
    }
}
