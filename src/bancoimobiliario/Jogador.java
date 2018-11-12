package bancoimobiliario;

import excecoes.SaldoInsuficienteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um jogador.
 */
public class Jogador {
    private String nome;
    private ContaCorrente contaCorrente;
    private String cor;
    private List<Propriedade> propriedadesDoJogador;
    private List<Companhia> companhiasDoJogador;
    private int posicaoNoTabuleiroIndice;
    private boolean possuiCartaSairDaPrisao;
    private int reclusao;
    private int sorteNosDados;
    private List<String> monopolioNoGrupoDeCor;
    
    /**
     * Seta os atributos de um jogador.
     * @param nome
     * @param cor 
     */
    public Jogador(String nome, String cor){
        this.nome = nome;
        this.cor = cor;
        this.contaCorrente = new ContaCorrente(1500);
        this.propriedadesDoJogador = new ArrayList<>();
        this.companhiasDoJogador = new ArrayList<>();
        this.posicaoNoTabuleiroIndice = 0;
        this.possuiCartaSairDaPrisao = false;
        this.reclusao = 0;
        this.sorteNosDados = 0;
        monopolioNoGrupoDeCor = new ArrayList<>();
    }    
    
    /**
     * SorteNosDados é um contatdos de quantas vezes esse jogador teve os dados iguais.
     * @return sorteNosDados
     */
    public int getSorteNosDados() {
        return sorteNosDados;
    }
    
    /**
     * SorteNosDados é um contatdos de quantas vezes esse jogador teve os dados iguais.
     */
    public void setSorteNosDados(int sorteNosDados) {
        this.sorteNosDados = sorteNosDados;
    }
    
    /**
     * Retorna a lista de propriedades do jogador.
     * @return propriedadesDoJogador
     */
    public List<Propriedade> getPropriedadesDoJogador() {
        return propriedadesDoJogador;
    }
    
    /**
     * retorna a lista de companhias do jogador.
     * @return companhiasDoJogador
     */
    public List<Companhia> getCompanhiasDoJogador() {
        return companhiasDoJogador;
    }

    /**
     * Retorna a posição do jogador no tabuleiro.
     * @return posicaoNoTabuleiroIndice
     */
    public int getPosicaoNoTabuleiroIndice() {
        return posicaoNoTabuleiroIndice;
    }

    /**
     * Seta a nova posição do jogador no tabuleiro.
     * @param posicaoNoTabuleiroIndice 
     */
    public void setPosicaoNoTabuleiroIndice(int posicaoNoTabuleiroIndice) {
        this.posicaoNoTabuleiroIndice = posicaoNoTabuleiroIndice;
    }
    
    /**
     * Realiza a compra de uma propriedade, caso o jogador possua saldo suficiente. Caso compre, a propriedade vai para a lista de propriedades do jogador.
     * @param pro propriedade que poderá ser comprada.
     * @throws SaldoInsuficienteException se o jogador não tiver saldo suficiente para comprar.
     */
    public void compraPropriedade(Propriedade pro) throws SaldoInsuficienteException{
        int precoDoTitulo = pro.getPrecoTerreno();
        this.contaCorrente.debitar(precoDoTitulo);
        this.propriedadesDoJogador.add(pro);
    }
    
    /**
     * Realiza a venda de uma propriedade da lista de propriedades do jogador. O valor da venda é creditado na conta corrente do jogador.
     * @param pro propriedade a ser vendida. 
     */
    public void vendePropriedade(Propriedade pro){
        this.propriedadesDoJogador.remove(pro);
        this.contaCorrente.creditar(pro.getPrecoTerreno());
    }
    
    /**
     * Realiza a compra de uma companhia, caso o jogador possua saldo suficiente. Caso compre, a companhia vai para a lista de companhias do jogador.
     * @param com companhia que poderá ser comprada.
     * @throws SaldoInsuficienteException se o jogador não tiver saldo suficiente para comprar.
     */
    public void compraCompanhia(Companhia com) throws SaldoInsuficienteException{
        int precoDoTitulo = com.getPrecoTerreno();
        this.contaCorrente.debitar(precoDoTitulo);
        this.companhiasDoJogador.add(com);
    }
    
    /**
     * Realiza a venda de uma companhia da lista de companhias do jogador. O valor da venda é creditado na conta corrente do jogador.
     * @param com companhia a ser vendida. 
     */
    public void vendeCompanhia(Companhia com){
        this.companhiasDoJogador.remove(com);
        this.contaCorrente.creditar(com.getPrecoTerreno());
    }
    
    /**
     * Returna o nome do jogador.
     * @return nome
     */
    public String getNome(){
        return nome;
    }

    /**
     * Seta o nome do jogador
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Retorna a conta corrente do jogador.
     * @return contaCorrente
     */
    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }
    
    /**
     * Retorna o saldo da conta corrente desse jogador.
     * @return 
     */
    public int getSaldo() {
        return this.contaCorrente.getSaldo();
    }
    
    /**
     * Credita na conta corrente desse jogador.
     * @param valor 
     */
    public void creditar(int valor){
        this.contaCorrente.creditar(valor);
    }
    
    /**
     * Debida da conta corrente desse jogador.
     * @param valor
     * @throws SaldoInsuficienteException 
     */
    public void debitar(int valor) throws SaldoInsuficienteException{
        this.contaCorrente.debitar(valor);
    }
    
    /**
     * Transfere o valor do paramentro da conta corrente desse jogador para o conta corrente do jogador recebido no parâmetro.
     * @param creditado
     * @param valor
     * @throws SaldoInsuficienteException 
     */
    public void transferirDinheiro(Jogador creditado, int valor) throws SaldoInsuficienteException{
        this.contaCorrente.transferir(creditado.getContaCorrente(), valor);
    }

    /**
     * Retorna a cor do jogador.
     * @return cor
     */
    public String getCor() {
        return cor;
    }

    /**
     * Seta a cor do jogador.
     * @param cor 
     */
    public void setCor(String cor) {
        this.cor = cor;
    }
    
    /**
     * Verifica se esse jogador é igual ao jogador recebido no parâmetro.
     * @param jogador
     * @return 
     */
    public boolean equals(Jogador jogador){
        return this.nome.equals(jogador.getNome()) && this.cor.equals(jogador.getCor());
    }
    
    /**
     * Retorna como uma string a lista de propriedades e companhias do jogador.
     * @return String com o nome das propriedades e companhias do jogador.
     */
    public String getInfoTitulos(){
        String infoTitulos = "";
        
        if(this.propriedadesDoJogador.size() > 0 || this.companhiasDoJogador.size() > 0){
            for(int i = 0; i < this.propriedadesDoJogador.size(); i++){
                infoTitulos += propriedadesDoJogador.get(i).getDescricao()+"\n";
            }
            for(int i = 0; i < this.companhiasDoJogador.size(); i++){
                infoTitulos += companhiasDoJogador.get(i).getDescricao()+"\n";
            }
            return infoTitulos;
            
        }else{
            return "Você não possui títulos";
        }
    }
    
    /**
     * Verifica se o jogador possui monopolio dos grupos de cores propriedades, ou seja, se ele possui todas do mesmo grupo de cor. Se houver, esse grupo de cor é adicionado a lista de propriedades com monopolio.
     */
    public void verificaMonopolioDePropriedades(){
        String[] grupoDeCores = new String[]{"rosa","azulClaro","roxo","laranja","vermelho","amarelo","verde","azulEscuro"};
        int[] numDePropriedadesDaCor = new int[]{3,3,3,2,2,3,4,2};
        int contador;
        if(propriedadesDoJogador.size() > 0){
            for(int i = 0; i < grupoDeCores.length;i++){
                contador = 0;
                for(int j = 0; j < propriedadesDoJogador.size(); j++){
                    if( propriedadesDoJogador.get(j).getCor().equals(grupoDeCores[i])){
                        contador++;
                        if((contador == numDePropriedadesDaCor[i]) && (j == (propriedadesDoJogador.size()-1))){
                            this.monopolioNoGrupoDeCor.remove(grupoDeCores[i]);
                            this.monopolioNoGrupoDeCor.add(grupoDeCores[i]);
                        }else if((contador < numDePropriedadesDaCor[i]) && (j == (propriedadesDoJogador.size()-1))){
                            this.monopolioNoGrupoDeCor.remove(grupoDeCores[i]);
                        }
                    }
               } 
            }
        }
    }
    
    /**
     * Retorna true se o jogador possuir monopolio em aomenos um grupo de cores.
     * @return 
     */
    public boolean possuiMonopolioEmAlgumGrupoDeCores(){
        verificaMonopolioDePropriedades();
        return this.monopolioNoGrupoDeCor.size() > 0;
    }
    
    /**
     * Retorna true se o jogador possuir uma contrução em alguma de suas propriedades
     * @return 
     */
    public boolean possuiAlgumaContrucao(){
        for(Propriedade pro: this.propriedadesDoJogador){
            if(pro.getContadorDeContrucoes() > 0){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Retorna a lista com as propriedades que o jogador tem monopolio
     * @return 
     */
    public List<Propriedade> getListaDePropriedadesComMonopolio(){
        List<Propriedade> proComMonopolio = new ArrayList<>();
        for(Propriedade pro: this.propriedadesDoJogador){
            for(String cor: this.monopolioNoGrupoDeCor){
                if(pro.getCor().equals(cor)){
                    proComMonopolio.add(pro);
                    break;
                }
            }
        }
        
        return proComMonopolio;
    }
    
    /**
     * Retorna a condição que diz se o jogador tem ou não a carta de sair da prisão.
     * @return possuiCartaSairDaPrisao
     */
    public boolean getPossuiCartaSairDaPrisao() {
        return possuiCartaSairDaPrisao;
    }
    
    /**
     * Seta a condição do jogador possuir a carta de sair da prisão.
     * @param possuiCartaSairDaPrisao 
     */
    public void setPossuiCartaSairDaPrisao(boolean possuiCartaSairDaPrisao) {
        this.possuiCartaSairDaPrisao = possuiCartaSairDaPrisao;
    }
    
    /**
     * Retona o tempo de reclusão do jogador.
     * @return reclusao
     */
    public int getReclusao() {
        return this.reclusao;
    }
    
    /**
     * Seta a reclusão do jogador.
     * @param reclusao 
     */
    public void setReclusao(int reclusao) {
        this.reclusao = reclusao;
    }
}
