package bancoimobiliario;

import excecoes.SaldoInsuficienteException;
import java.io.IOException;

/**
 * Classe que representa uma companhia.
 */
public class Companhia implements Posicao, TituloDePosse, Arquivavel{
    private String nome;
    private int preco;
    private int hipoteca;
    private int multiplicador;
    private Jogador dono;
    
    /**
     * Uma companhia possui esses atributos.
     * @param nome
     * @param preco
     * @param hipoteca
     * @param multiplicador 
     */
    public Companhia(String nome, int preco, int hipoteca, int multiplicador){
        this.nome = nome;
        this.preco = preco;
        this.hipoteca = hipoteca;
        this.multiplicador = multiplicador;
        this.dono = null;
    }
    
    /**
     * Retorna o atual dono dessa companhia.
     * @return dono
     */
    public Jogador getDono() {
        return this.dono;
    }

    /**
     * Seta o atual dono dessa companhia.
     * @param dono 
     */
    public void setDono(Jogador dono) {
        this.dono = dono;
    }

    /**
     * Retorna o nome dessa companhia.
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Seta o nome dessa companhia.
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * retorna o preco do terreno dessa companhia.
     * @return 
     */
    public int getPrecoTerreno() {
        return preco;
    }
    
    /**
     * Seta o preco dessa companhia.
     * @param preco 
     */
    public void setPreco(int preco) {
        this.preco = preco;
    }

    /**
     * Retorna o valor da hipoteca dessa companhia.
     * @return hipoteca
     */
    public int getHipoteca() {
        return hipoteca;
    }

    /**
     * Seta o valor da hipoteca dessa companhia.
     * @param hipoteca 
     */
    public void setHipoteca(int hipoteca) {
        this.hipoteca = hipoteca;
    }

    /**
     * Retorna o multiplicador dessa companhia.
     * @return multiplicador
     */
    public int getMultiplicador() {
        return multiplicador;
    }

    /**
     * Seta o multiplicador dessa companhia.
     * @param multiplicador 
     */
    public void setMultiplicador(int multiplicador) {
        this.multiplicador = multiplicador;
    }

    /**
     * Retorna uma breve descrição dessa classe.
     * @return descricao
     */
    @Override
    public String getDescricao() {
        return "["+this.nome +"] - multiplicador de "+this.multiplicador;
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
        
        if(this.dono == null){ // Se a companhia não tiver dono
            System.out.println("O título da companhia " + this.nome + " está disponível por $" + this.preco+".");
            System.out.println(jogador.getNome()+" você possui $"+jogador.getSaldo()+".");
            System.out.println("Você deseja comprar " + this.nome + "?");
            boolean resposta = facade.simOuNao();

            if(resposta == true){ // Se o jogador aceitou comprar a companhia
                try {
                    jogador.compraCompanhia(this);
                    System.out.println("Titulo de posse adquirido com sucesso!");
                    facade.adicionarDonoDePosse(jogador);
                } catch (SaldoInsuficienteException ex) {
                    System.out.println(ex.getMessage());
                }

            }else{
                System.out.println("Titulo de posse não comprado, vez do proxímo jogador.");
            }
        }else{ // Se a companhia tiver dono, o jogador da vez deverá o valor do produto do multiplicador pela soma dos dados
            if(!(this.dono.equals(jogador))){ // Se ele não for o dono
                int valor = (facade.getUltimoResultadoDosDados()[0] + facade.getUltimoResultadoDosDados()[1])*this.multiplicador;
                System.out.println("Dono: " + this.dono.getNome() + "[" + this.dono.getCor() + "].\n"
                        + "Valor do multiplicador: "+this.multiplicador+"\nResultando no valor a pagar de $"+valor);

                try {
                    jogador.transferirDinheiro(this.dono, valor);
                    System.out.println("Você pagou $ "+valor+ " a "+this.dono.getNome()+" ["+this.dono.getCor()+"].");
                } catch (SaldoInsuficienteException ex) {
                    System.out.println("Não foi possivel pagar o valor acima pelo motivo:");
                    System.out.println(ex.getMessage());
                }

            }else{ // Se ele for o dono
                System.out.println("Você é o dono dessa companhia.");
            }
        }
        return false;
    }
}
