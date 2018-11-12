package arquivo;

import bancoimobiliario.PilhaSorteOuReves;
import bancoimobiliario.SorteOuReves;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import bancoimobiliario.Posicao;

/**
 *
 * @author emers
 */
public class ExportadorSorteOuReves extends Exportador{
    protected static Exportador instance;
    
    /**
     * Padrão singleton para garantir que haja somente uma instancia dessa classe.
     * @return ExportadorSorteOuReves instancia da propria classe.
     */
    protected static synchronized Exportador getInstance() {
        if(instance == null){
            instance = new ExportadorSorteOuReves();
        }
        
        return instance;
    }
    
    /**
     * Retorna o caractere usado como separador de dados.s
     * @return caractere separador.
     */
    @Override
    protected String getSeparator() {
        return "-";
    }
    
    /**
     * Retorna o diretorio no qual o arquivo se encontra.
     * @return String diretorio
     */
    @Override
    protected String getDirectory() {
        return "./archives/luckOrSetback.txt";
    }
    
    /**
     * Factory de companhia, instancia uma nova companhia com os dados recebidos no parâmento.
     * @param dados
     * @return Companhia
     */
    @Override
    protected SorteOuReves arquivavelFactory(String[] dados) {
        SorteOuReves sv = null;
        if(dados.length == 3){
            sv = new SorteOuReves(dados[0], dados[1], Integer.parseInt(dados[2]));
        }else if(dados.length == 2){
            sv = new SorteOuReves(dados[0], dados[1]);
        }
        
        return sv;
    }
    
    /**
     * Sobrescreve da classe Exportador, pois essa classe carregada os dados de forma diferente. Ela adiciona a pilha de sorte ou reves nas posições corretas da array do tabuleiro.
     * @param tabuleiro array com posições do tabuleiro.
     * @return tabuleiro array com posições do tabuleiro.
     * @throws IOException erro de leitura ou escrita de arquivo.
     */
    @Override
    protected Posicao[] carregaDados(Posicao[] tabuleiro) throws IOException{
        PilhaSorteOuReves pilha = PilhaSorteOuReves.getInstance();
        tabuleiro[2] = pilha;
        tabuleiro[12] = pilha;
        tabuleiro[16] = pilha;
        tabuleiro[22] = pilha;
        tabuleiro[27] = pilha;
        tabuleiro[37] = pilha;
        
        return tabuleiro;
    }
    
    /**
     * Preenche e retorna uma List de SorteOuReves, usa o método arquivavelFactory para instanciar cada objeto.
     * @return lista de cartas sorte ou reves.
     * @throws IOException 
     */
    protected ArrayList<SorteOuReves> getListSorteOuReves() throws IOException{
        List<String[]> dadosText = exportar();
        ArrayList<SorteOuReves> cartasSorteOuReves = new ArrayList<>();
        for(String[] dados: dadosText){
            cartasSorteOuReves.add(arquivavelFactory(dados));
        }
        
        return cartasSorteOuReves;
    }
    
}
