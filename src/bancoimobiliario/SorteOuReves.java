package bancoimobiliario;
/**
 * Carta sorte ou reves
 */
public class SorteOuReves implements Arquivavel{
    private String descricao;
    private int valor;
    private String acao;
    
    public SorteOuReves(String descricao, String acao, int valor){
        this.descricao = descricao;
        this.acao = acao;
        this.valor = valor;
    }
    
    public SorteOuReves(String descricao, String acao){
        this.descricao = descricao;
        this.acao = acao;
    }

    /**
     * Retona a descrição da carta.
     * @return descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Seta a descrição da carta.
     * @param descricao 
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    /**
     * Retorna a ação da carta, que pode ser: pague, receba, casamento, sairDaPrisao, jogueOsDadosNovamente ou parOuImpar.
     * @return acao
     */
    public String getAcao() {
        return acao;
    }
    
    /**
     * Seta a ação dessa carta, que pode ser: pague, receba, casamento, sairDaPrisao, jogueOsDadosNovamente ou parOuImpar.
     * @param acao 
     */
    public void setAcao(String acao) {
        this.acao = acao;
    }
    
    /**
     * Retorna o valor a ser creditado ou debitado.
     * @return valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * seta o valor a ser creditado ou debitado.
     * @param valor
     */
    public void setValor(int valor) {
        this.valor = valor;
    }
    
}