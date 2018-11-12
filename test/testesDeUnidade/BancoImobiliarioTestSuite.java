/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesDeUnidade;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author emers
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({testesDeUnidade.ContaCorrentTest.class, testesDeUnidade.JogoFacadeTest.class, testesDeUnidade.PilhaSorteOuRevesTeste.class, testesDeUnidade.tabuleiroTest.class, TestCompraEVendaDeConstrucoes.class})
public class BancoImobiliarioTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }
    
}
