package arquivo;

import bancoimobiliario.Arquivavel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import bancoimobiliario.Posicao;

/**
 * Classe abstrata com implementação padrão de exportação. Ela é resporsável por ler o arquivo e carregar os dados que estão nele.
 */
abstract class Exportador {
    /**
     * Lê o arquivo no diretório atual e retorna uma lista de array de string, na qual cada array contem os dados que serão instanciados futuramente.
     * @return lista de arrays de string contendo os dados que serão instanciados.
     * @throws FileNotFoundException Arquivo não encontrado.
     * @throws IOException Erro de leitura ou escrita em arquivo.
     */
    protected List<String[]> exportar() throws FileNotFoundException, IOException{
        //Verifica se a pasta de arquivos existe, caso não, ele cria uma nova pasta
        File diretorio = new File("./archives");
        if (!diretorio.exists()) {
           diretorio.mkdir();
        }
        //Verifica que o arquivo com os dados do atual exportador existe, caso não, cria um novo arquivo
        File archive = new File(getDirectory());
        if (!archive.exists()) {
            archive.createNewFile();
        }
        //Lê linha a linha e salva em uma lista de arrays de string
        BufferedReader leitor = null;
        List<String[]> dados = new ArrayList<>();
        String dado = null;
        leitor = new BufferedReader(new InputStreamReader(new FileInputStream(getDirectory()), "UTF-8"));
        do{
            dado = leitor.readLine();
            if(dado != null){
                dados.add(dado.split(getSeparator()));  
            }
        }while(dado != null);

        if (leitor != null){
            leitor.close();
        }
        
        return dados;
    }
    
    /**
     * Preenche o tabuleiro com as instancias de carta. Cada subclasse sabe oque instancar e onde posicionar a instancia no array.
     * @param tabuleiro array de posições.
     * @return
     * @throws IOException 
     */
    protected Posicao[] carregaDados(Posicao[] tabuleiro) throws IOException{
        List<String[]> dadosText = exportar();
        for(String[] dados: dadosText){
            tabuleiro[Integer.parseInt(dados[0])] = (Posicao) arquivavelFactory(dados);
        }
        
        return tabuleiro;
    }
    
    /**
     * Retorna o diretorio onde o arquivo está.
     * @return diretorio
     */
    protected abstract String getDirectory();
    /**
     * Caractere que a subclasse usa como parametro de separação dos dados.
     * @return caractere separador.
     */
    protected abstract String getSeparator();
    /**
     * Fabrica de instancias, cada subclasse sabe qual classe instanciar.
     * @param dados array de string que contem os dados que serão instanciados.
     * @return objeto do tipo arquivavel.
     */
    protected abstract Arquivavel arquivavelFactory(String[] dados);
}
