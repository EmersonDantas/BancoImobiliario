package bancoimobiliario;

import excecoes.SaldoInsuficienteException;

/**
 * Classe que representa a posição no tabuleiro "Imposto de renda".
 */
public class ImpostoDeRenda implements Posicao{
    private int valor;
    private static ImpostoDeRenda instance;
    
    private ImpostoDeRenda(int valor) {
        this.valor = valor;
    }
    
    /**
     * Padrão singleton que garante que tenha somente uma instancia dessa classe.
     * @param valor
     * @return ImpostoDeRenda propria instancia.
     */
    public static ImpostoDeRenda getInstance(int valor){
        if(instance == null){
            instance = new ImpostoDeRenda(valor);
        }
        
        return instance;
    }
    
    /**
     * Retona uma breve descrição dos atributos da classe.
     * @return descrição
     */
    @Override
    public String getDescricao() {
        return "[Imposto de renda] valor: $"+this.valor;
    }

    /**
     * Retorna o valor do imposto.
     * @return valor
     */
    public int getValor() {
        return valor;
    }
    
    /**
     * Retorna o nome da posição.
     * @return nomeDaPosicao
     */
    @Override
    public String getNome() {
        return "Imposto de renda";
    }

    @Override
    public boolean realizaAcao(Jogador jogador) {
        System.out.println("Pague $"+ this.valor);
        try {
            jogador.debitar(this.valor);
        } catch (SaldoInsuficienteException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

}
