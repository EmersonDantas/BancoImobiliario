package arquivo;

import bancoimobiliario.Companhia;
/**
 * Classe que extende Exportador. Ela sobescreve os métodos abstratos.
 */
public class ExportadorCompanhias extends Exportador{
    protected static Exportador instance;
    
    private ExportadorCompanhias(){}
    
    /**
     * Padrão singleton para garantir que haja somente uma instancia dessa classe.
     * @return ExportadorCompanhias instancia da propria classe.
     */
    protected static synchronized Exportador getInstance() {
        if(instance == null){
            instance = new ExportadorCompanhias();
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
     * @return diretorio
     */
    @Override
    protected String getDirectory() {
        return "./archives/companies.txt";
    }
    
    /**
     * Factory de companhia, instancia uma nova companhia com os dados recebidos no parâmento.
     * @param dados
     * @return Companhia
     */
    @Override
    protected Companhia arquivavelFactory(String[] dados) {
        return new Companhia(dados[1], Integer.parseInt(dados[2]), Integer.parseInt(dados[3]), Integer.parseInt(dados[4]));
    }

    
}
