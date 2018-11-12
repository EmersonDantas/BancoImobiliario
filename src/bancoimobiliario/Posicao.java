package bancoimobiliario;

/**
 * Classe que representa uma posição no tabuleiro.
 */
public interface Posicao {
    /**
     * Retorna o nome da posição.
     * @return String nomeDaPosição
     */
    public String getNome();
    
    /**
     * Retorna uma breve descrição da posição.
     * @return String descricao
     */
    public String getDescricao();
    
    /**
     * Realiza a ação da posição.
     * @param jogador jogador que parou nessa posição.
     * @return true se a ação requerer repetição da jogada, false caso contrário.
     */
    public boolean realizaAcao(Jogador jogador);
}
