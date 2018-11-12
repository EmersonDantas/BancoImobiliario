package bancoimobiliario;
/**
 * Classe que representa a posição LucrosOuDividendos no tabuleiro.
 */
public class LucrosOuDividendos implements Posicao{
    private int valor;
    private static LucrosOuDividendos instance;
   
    private LucrosOuDividendos(int valor){
        this.valor = valor;
    }
    
    /**
     * Padrão singleton que garante se só tenha uma instancia dessa classe.
     * @return LucrosOuDividendos propria instancia.
     */
    public static LucrosOuDividendos getInstance(int valor){
        if(instance == null){
            instance = new LucrosOuDividendos(valor);
        }
        
        return instance;
    }
    
    /**
     * Retona uma breve descrição dos atributos da classe.
     * @return descrição
     */
    @Override
    public String getDescricao() {
        return "[Lucros ou Dividendos] - valor: $"+this.valor;
    }
    
    /**
     * Retorna o valor
     * @return valor
     */
    public int getValor() {
        return valor;
    }
    
    /**
     * Seta o valor.
     * @param valor 
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * Retorna o nome da posição.
     * @return nomeDaPosicao
     */
    @Override
    public String getNome() {
        return "Lucros ou Dividendos";
    }

    @Override
    public boolean realizaAcao(Jogador jogador) {
        System.out.println("Receba $"+this.valor);
        jogador.creditar(this.valor);
        System.out.println("Você recebeu $"+this.valor);
        return false;
    }
    
}
