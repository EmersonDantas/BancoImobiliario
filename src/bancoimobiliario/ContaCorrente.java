package bancoimobiliario;

import excecoes.SaldoInsuficienteException;

/**
 * Classe que representa uma conta corrente, contendo as operações funcionais para serem usadas pelo jogador.
 */
public class ContaCorrente {
    private int saldo;
    
    public ContaCorrente(int saldo){
        this.saldo = saldo;
    }
    
    /**
     * Incrementa o valor no saldo do jogador.
     * @param valor 
     */
    public void creditar(int valor){
        this.saldo += valor;
    }
    
    /**
     * Decrementa o valor do saldo do jogador.
     * @param valor
     * @throws SaldoInsuficienteException quando o saldo não é suficiente para o debido.
     */
    public void debitar(int valor) throws SaldoInsuficienteException{
        if(this.saldo >= valor && valor >= 0){
            this.saldo -= valor;
        }else{
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }
    }
    
    /**
     * Transfere o valor dessa conta para a conta creditada, recebida no parâmentro.
     * @param cCreditada
     * @param valor
     * @throws SaldoInsuficienteException 
     */
    public void transferir(ContaCorrente cCreditada, int valor) throws SaldoInsuficienteException{
        this.debitar(valor);
        cCreditada.creditar(valor);
    }
    
    /**
     * Retorna o saldo.
     * @return saldo
     */
    public int getSaldo(){
        return this.saldo;
    }
}
