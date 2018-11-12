/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excecoes;

/**
 *
 * @author emers
 */
public class CorNaoExisteException extends Exception{
    public CorNaoExisteException(String msg){
        super(msg);
    }
}
