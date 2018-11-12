package arquivo;

import bancoimobiliario.ImpostoDeRenda;
import bancoimobiliario.LucrosOuDividendos;
import bancoimobiliario.ParadaLivre;
import bancoimobiliario.PontoDePartida;
import bancoimobiliario.Prisao;
import bancoimobiliario.SorteOuReves;
import bancoimobiliario.VaParaPrisao;
import java.io.IOException;
import java.util.List;
import bancoimobiliario.Posicao;

/**
 * Essa classe é responsável por gerenciar os exportadores que manipulam os arquivos de propriedades, companhias e SorteOuReves.
 */
public class ArchiveManager {
    private static ArchiveManager instance;
    private Exportador exportador;
    
    private ArchiveManager(){
    }
    
    /**
     * Padrão Singleton, retorna a propria instancia, se já houver. Se não, instancia uma nova e a retorna.
     * @return ArchiveManagerInstance a instancia da propria classe.
     */
    public static synchronized ArchiveManager getInstance(){
        if(instance == null){
            instance = new ArchiveManager();
        }
        
        return instance;
    }
    
    /**
     * Constroi a lista de posições do tabuleiro usando os exportadores.
     * @return tabuleiro Array de Posicao com as posições preechidas.
     * @throws IOException erro de leitura ou escrita em arquivo.
     */
    public synchronized Posicao[] getTabuleiro() throws IOException{
        Posicao[] tabuleiro = new Posicao[40];

        tabuleiro[0] = PontoDePartida.getInstance();
        tabuleiro[10] = Prisao.getInstance();
        tabuleiro[18] = LucrosOuDividendos.getInstance(200);
        tabuleiro[20] = ParadaLivre.getInstance();
        tabuleiro[24] = ImpostoDeRenda.getInstance(200);
        tabuleiro[30] = VaParaPrisao.getInstance();
        
        setExportadorPropriedades();
        tabuleiro = exportador.carregaDados(tabuleiro);
        setExportadorCompanhias();
        tabuleiro = exportador.carregaDados(tabuleiro);
        setExportadorSorteOuReves();
        tabuleiro = exportador.carregaDados(tabuleiro);
        
        return tabuleiro;
    }
    /**
     * Retorna a lista de cartas para a PilhaSorteOuReves, usando o exportador especifico para isso, que é o exportadorSorteOuReves.
     * @return PilhaSorteOuReves
     * @throws IOException 
     */
    public List<SorteOuReves> getListSorteOuReves() throws IOException{
        setExportadorSorteOuReves();
        ExportadorSorteOuReves ex = (ExportadorSorteOuReves) ExportadorSorteOuReves.getInstance();
        return ex.getListSorteOuReves();
    }
    
    /**
     * Seta o exportador para exportador de propriedades.
     */
    public void setExportadorPropriedades(){
        this.exportador = ExportadorPropriedades.getInstance();
    }
    
    /**
     * Seta o exportador para exportador de companhias.
     */
    public void setExportadorCompanhias(){
        this.exportador = ExportadorCompanhias.getInstance();
    }
    
    /**
     * Seta o exportador para exportador de cartas sorte ou reves.
     */
    public void setExportadorSorteOuReves(){
        this.exportador = ExportadorSorteOuReves.getInstance();
    }
}
