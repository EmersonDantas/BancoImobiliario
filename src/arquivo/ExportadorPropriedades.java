package arquivo;

import bancoimobiliario.Propriedade;

/**
 *
 * @author emers
 */
public class ExportadorPropriedades extends Exportador {

    protected static Exportador instance;

    private ExportadorPropriedades() {
    }
    /**
     * Padrão singleton para garantir que haja somente uma instancia dessa classe.
     * @return ExportadorPropriedades instancia da propria classe.
     */
    protected static synchronized Exportador getInstance() {
        if (instance == null) {
            instance = new ExportadorPropriedades();
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
        return "./archives/properties.txt"; //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Factory de Propriedade, instancia uma nova Propriedade com os dados recebidos no parâmento.
     * @param dados
     * @return Propriedade
     */
    @Override
    protected Propriedade arquivavelFactory(String[] dados) {
        return new Propriedade(dados[1], Integer.parseInt(dados[2]), Integer.parseInt(dados[3]), Integer.parseInt(dados[4]), Integer.parseInt(dados[5]), Integer.parseInt(dados[6]), Integer.parseInt(dados[7]), Integer.parseInt(dados[8]), Integer.parseInt(dados[9]), Integer.parseInt(dados[10]), dados[11]);
    }

}
