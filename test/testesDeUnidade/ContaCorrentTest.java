package testesDeUnidade;

import excecoes.SaldoInsuficienteException;
import bancoimobiliario.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
/**
 * Teste de funções da conta corrente
 */
public class ContaCorrentTest {
    private ContaCorrente a;
    private ContaCorrente b;
    @Before
    public void setUp() throws Exception {
        a = new ContaCorrente(1000);
        b = new ContaCorrente(1000);
    }
    
    /**
     * Testa se é possivel instanciar uma conta corrente.
     */
    @Test
    public void instanciar(){
        ContaCorrente contaTest = new ContaCorrente(1000);
    }
    
    /**
     * Testa se está creditando corretamente.
     */
    @Test
    public void creditar(){ //Creditando 500 a conta a e testando se o valr foi atualizado
        a.creditar(500);
        assertEquals(1500, a.getSaldo());
    }
    
    /**
     * Testa se a conta corrente está gerando exceção corretamente.
     * @throws SaldoInsuficienteException 
     */
    @Test (expected=SaldoInsuficienteException.class)
    public void debitarGerandoException() throws SaldoInsuficienteException{ //Testando se é lançado exceção caso o valor de débito seja maior que o saldo da conta
        //Testando exceção
        a.debitar(2000);
    }
    
    /**
     * Testa de a conta corrente está debitando corretamente.
     * @throws SaldoInsuficienteException 
     */
    @Test
    public void debitar() throws SaldoInsuficienteException{ //Testando débitos normais
        //Tesntando um débito normal
        a.debitar(500);
        assertEquals(500, a.getSaldo());
        a.debitar(500);
        assertEquals(0, a.getSaldo());
    }
    
    /**
     * Testa se a conta corrente está gerando exceção ao transferir dinheiro, se nescessário.
     * @throws SaldoInsuficienteException 
     */
    @Test (expected=SaldoInsuficienteException.class)
    public void transferirGerandoException() throws SaldoInsuficienteException{ //Testando um transferência na qual o debitado não possui saldo suficiente
        //Testando exceção
        a.transferir(b,2000);
    }
    
    /**
     * Testa se a conta corrente está transferindo dinheiro corretamente.
     * @throws SaldoInsuficienteException 
     */
    @Test
    public void transferir() throws SaldoInsuficienteException{ //Testando uma trasnferência normal
        //Testando uma transferencia normal
        a.transferir(b, 500);
        assertEquals(500,a.getSaldo());
        assertEquals(1500,b.getSaldo());
    }
    
}
