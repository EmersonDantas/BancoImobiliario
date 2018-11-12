package bancoimobiliario;

import excecoes.MaximoDeContrucoesAtingidoException;
import excecoes.SaldoInsuficienteException;
import excecoes.SemConstrucoesException;
import java.io.IOException;

/**
 * Classe que representa a posição propriedade no tabuleiro.
 */
public class Propriedade implements Posicao, TituloDePosse, Arquivavel{
    private String nome;
    private int precoTerreno;
    private int hipoteca;
    private int precoConstruir;
    private int contadorDeContrucoes;
    private Jogador dono;
    private String cor;
    private int [] alugueis;
    
    /**
     * Seta os atributos de uma propriedade.
     * @param nome
     * @param preco
     * @param aluguelDoTerreno
     * @param precoCasa1
     * @param precoCasa2
     * @param precoCasa3
     * @param precoCasa4
     * @param precoHotel
     * @param hipoteca
     * @param precoDaCasa
     * @param cor 
     */
    public Propriedade(String nome, int preco, int aluguelDoTerreno, int precoCasa1, int precoCasa2, int precoCasa3,
            int precoCasa4, int precoHotel, int hipoteca, int precoDaCasa, String cor){
        alugueis = new int[6];
        this.nome = nome;
        this.precoTerreno = preco;
        alugueis[0] = aluguelDoTerreno;
        alugueis[1] = precoCasa1;
        alugueis[2] = precoCasa2;
        alugueis[3] = precoCasa3;
        alugueis[4] = precoCasa4;
        alugueis[5] = precoHotel;
        this.hipoteca = hipoteca;
        this.precoConstruir = precoDaCasa;
        this.dono = null;
        this.cor = cor;
        this.contadorDeContrucoes = 0;
        
    }
    
    /**
     * Retorna o valor do aluguel de acordo com a quantidade de casas contruidas.
     * @return valor do aluguel.
     */
    public int getPrecoAluguel(){
        return alugueis[this.contadorDeContrucoes];
    }

    /**
     * Retorna a cor do grupo de cores da qual essa propriedade faz parte.
     * @return cor
     */
    public String getCor() {
        return cor;
    }
    
    /**
     * Retorna o dono dessa propriedade.
     * @return dono
     */
    public Jogador getDono() {
        return this.dono;
    }
    
    /**
     * Seta o dono dessa propriedade.
     * @param dono 
     */
    public void setDono(Jogador dono) {
        this.dono = dono;
    }
    
    /**
     * Retorna o nome dessa propriedade.
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o valor da hipoteca dessa propriedade.
     * @return hipoteca
     */
    public int getHipoteca() {
        return hipoteca;
    }

    /**
     * Retorna o preco para se construir nessa propriedade.
     * @return precoConstruir
     */
    public int getPrecoConstruir() {
        return precoConstruir;
    }
    
    /**
     * Retorna o preco do terreno.
     * @return preco do terreno
     */
    public int getPrecoTerreno() {
        return this.precoTerreno;
    }
    
    /**
     * Retona uma breve descrição dos atributos da classe.
     * @return descrição
     */
    public String getDescricao() {
        return "[" +this.nome+"] propriedade " + this.cor + ", aluguel $"+getPrecoAluguel()+", Nº de contruções: "+this.contadorDeContrucoes;
    }
    
    /**
     * Verifica se é possivel constrir nessa propriedade, caso seja possivel, debita do jogador e constroi.
     * @throws SaldoInsuficienteException se o jogador não tiver saldo suficiente para pagar a construção.
     * @throws MaximoDeContrucoesAtingidoException se o limite de construção dessa propriedade for atingido.
     */
    public void construir() throws SaldoInsuficienteException, MaximoDeContrucoesAtingidoException{
        if(this.contadorDeContrucoes < 5){
            dono.debitar(this.precoConstruir);
            this.contadorDeContrucoes++;
        }else{
            throw new MaximoDeContrucoesAtingidoException("Você não pode mais contruir nessa propriedade, ela já tem a quantidade máxima de contruções.");
        }
    }
    
    /**
     * Vende construções dessa propriedade, se houver.
     * @throws SemConstrucoesException se não houver construções para vende.
     */
    public void vender() throws SemConstrucoesException{
        if(this.contadorDeContrucoes > 0){
            dono.creditar(this.precoConstruir / 2);
            this.contadorDeContrucoes--;
        }else{
            throw new SemConstrucoesException("Você não possui construções nessa propriedade!");
        }
    }
    
    /**
     * Retorna a quantidade de construções nessa propriedade.
     * @return contadorDeConstrucoes
     */
    public int getContadorDeContrucoes() {
        return contadorDeContrucoes;
    }

    /**
     * Verifica se essa propriedade possui um hotel.
     * @return condicao
     */
    public boolean isHaHotel() {
        if(this.contadorDeContrucoes == 5){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Seta o contador de construções
     * @param contadorDeContrucoes 
     */
    public void setContadorDeContrucoes(int contadorDeContrucoes) {
        this.contadorDeContrucoes = contadorDeContrucoes;
    }

     /**
     * Realiza a ação da posição.
     * @param jogador jogador que parou nessa posição.
     * @return true se a ação requerer repetição da jogada, false caso contrário.
     */
    @Override
    public boolean realizaAcao(Jogador jogador) {
        JogoFacade facade = null;
        try {
            facade = JogoFacade.getInstance();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        if(this.dono == null){ // Se a propriedade não tiver dono o jogador da vez poderá compra-la
            System.out.println("O título da propriedade " + this.nome + " está disponível por $" + this.precoTerreno+".");
            System.out.println(jogador.getNome()+" você possui $"+jogador.getSaldo()+".");
            System.out.println("Você deseja comprar " + this.nome + "?");
            boolean resposta;

            resposta = facade.simOuNao();


            if(resposta == true){
                try {
                    jogador.compraPropriedade(this);
                    System.out.println("Titulo de posse adquirido com sucesso!");
                    facade.adicionarDonoDePosse(jogador);
                } catch (SaldoInsuficienteException ex) {
                    System.out.println(ex.getMessage());
                }

            }else{
                System.out.println("Titulo de posse não comprado, vez do proxímo jogador.");
            }

        }else{ // Se a propriedade tiver dono, o jogador da vez deverá pagar o aluguel a ele
            if(!(this.dono.equals(jogador))){ // Se ele não for o dono
                System.out.println("Dono: " + this.dono.getNome() + "[" + this.dono.getCor() + "].\n"
                        + "Valor do aluguel: $"+this.getPrecoAluguel());

                try {
                    jogador.transferirDinheiro(this.dono, this.getPrecoAluguel());
                    System.out.println("Você pagou $ "+this.getPrecoAluguel()+ " de aluguel a "+this.dono.getNome()+" ["+this.dono.getCor()+"].");
                } catch (SaldoInsuficienteException ex) {
                    System.out.println("Não foi possivel pagar o aluguel pelo motivo:");
                    System.out.println(ex.getMessage());
                }
            }else{ // Se ele for o dono
                System.out.println("Você é o dono dessa propriedade.");
            }
        }
        return false;
    }
    
    
}
