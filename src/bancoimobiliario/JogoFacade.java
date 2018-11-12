package bancoimobiliario;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Interface que contem lógicas do jogo, fachada que controla algumas funções.
 */
public class JogoFacade {
    private static JogoFacade instance;
    private Tabuleiro tabuleiro;
    private int[] ultimoResultadoDados;
    private Random random;
    private Scanner input;
    private ListaEncadeada<Jogador> listaJogadores;
    private Jogador jogadorDaVez;

    private JogoFacade() throws IOException {
        this.tabuleiro = Tabuleiro.getInstance();
        ultimoResultadoDados = new int[2];
        random = new Random();
        input = new Scanner(System.in);
        listaJogadores = new ListaEncadeada<>();
        jogadorDaVez = null;
    }
    
    /**
     * Padrão singleton que garante se só tenha uma instancia dessa classe.
     * @return JogoFacade propria instancia.
     * @throws IOException 
     */
    public static synchronized JogoFacade getInstance() throws IOException{
        if(instance == null){
            instance = new JogoFacade();
        }
        
        return instance;
    }
    
    /**
     * Este método remove um jogador da lista de jogadores.
     * @param jogador a ser removido.
     */
    public void removeJogador(Jogador jogador){
        jogadorDaVez = listaJogadores.getProximo(jogador);
        listaJogadores.remove(jogador);
    }
    
    /**
     * Este método adiciona um jogador na lista de jogadores.
     * @param jogador a ser adicionado.
     */
    public void adicionaJogador(Jogador jogador){
        listaJogadores.adicionaNoFim(jogador);
        
        if(jogadorDaVez == null){
            jogadorDaVez = listaJogadores.getPrimeiro();
        }
    }
    
    /**
     * Atualiza o jogador da vez, que passa a ser o proximo do anterior e o retorna.
     * @return Jogador da vez
     */
    public Jogador proximoJogador(){
        jogadorDaVez = listaJogadores.getProximo(jogadorDaVez);
        return jogadorDaVez;
    }
    
    /**
     * Retorna o jogador da vez.
     * @return Jogador da vez
     */
    public Jogador getJogadorDaVez(){
        return jogadorDaVez;
    }
    
    /**
     * Retorna a quantidade de jogadores.
     * @return int quantidadeDeJogadres
     */
    public int getQuantJogadores(){
        return listaJogadores.tamanho();
    }
    
    /**
     * Retorna a lista de jogadores.
     * @return jogadores 
     */
    public ListaEncadeada<Jogador> getJogadores(){
        return listaJogadores;
    }
    
    /**
     * Este método pede a entrada do usúario e retorna ela. Ele é usado para simplificar no código a entrada do usuário.
     * @param textoMostrado String
     * @return String entradaDoUsuario
     */
    public String input(String textoMostrado) {
        System.out.print(textoMostrado);
        return input.nextLine();
    }
    
    /**
     * Este método pede a confirmação do usuario e retorna true caso a resposta seja sim e false caso seja não. Caso a resposta não seja uma afirmação ele pedirá novamente.
     * @return boolean
     */
    public boolean simOuNao(){
        String resposta;
        while(true){
            System.out.println("(Sim/Não):");
            resposta = input.nextLine();
            if(resposta.equalsIgnoreCase("sim") || resposta.equalsIgnoreCase("s")){
                return true;
            }else if(resposta.equalsIgnoreCase("não") || resposta.equalsIgnoreCase("nao") || resposta.equalsIgnoreCase("n")){
                return false;
            }else{
                System.out.println("\033[1;31m☹ Entrada inválida!\033[m");
            }
        }
    }
    
    /**
     * Obtem nova posição em que o jogador parou de acordo com a quantidade de saltos que ele obteve.
     * @param saltos
     * @param jogador
     * @return Posição na qual o jogador parou no tabuleiro.
     */
    public int obterNovaPosicao(int saltos, Jogador jogador){
        int posicaoAtual = jogador.getPosicaoNoTabuleiroIndice();
        int novaPosicao = (posicaoAtual+saltos);
        
        if(jogadorDeuAVoltaNotabuleiro(novaPosicao, jogador)){
            novaPosicao -= 40;
        }

        return novaPosicao;
    }
    
    /**
     * Move o jogador no tabuleiro de acordo com a quantidade de saltos.
     * @param jogador
     * @param saltos
     * @return 
     */
    public Posicao moveJogadorNoTabuleiro(int saltos, Jogador jogador){
        int novaPosicao = obterNovaPosicao(saltos, jogador);
        jogador.setPosicaoNoTabuleiroIndice(novaPosicao); 
        return tabuleiro.getPosicoes()[novaPosicao];
    }
    
    /**
     * Verifica se o jogador deu a volta no tabuleiro.
     * @param novaPosicao
     * @param jogador
     * @return 
     */
    public boolean jogadorDeuAVoltaNotabuleiro(int novaPosicao, Jogador jogador){
        if(novaPosicao > 39){
            System.out.println("O jogador "+ jogador.getNome()+ " deu a volta no tabuleiro e ganhou R$200.");
            jogador.creditar(200);
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Retorna o status do jogador, informando a posição, saldo, titulos de posse e se esta presou ou não.
     * @param jogadorDaVez
     * @return statusDoJogador
     */
    public String statusDoJogador(Jogador jogadorDaVez){
        String status = "O status de " + jogadorDaVez.getNome() + " [" + jogadorDaVez.getCor() + "] é o seguinte:\n";
        if(jogadorDaVez.getReclusao()> 0){
            status += "Está preso.\n";
        }else{
            status += "Situado na posição " + jogadorDaVez.getPosicaoNoTabuleiroIndice() + " - " + tabuleiro.getNomePosicao(jogadorDaVez.getPosicaoNoTabuleiroIndice())+"\n";
        }
                        
        status += "Possui $" + jogadorDaVez.getSaldo()+"\n";
        if(jogadorDaVez.getPossuiCartaSairDaPrisao()){
            status += "Possui a carta de saída livre da prisão.\n";
        }
        status += "Títulos:\n"+jogadorDaVez.getInfoTitulos();
        
        return status;
    }
    
    /**
     * Verifica se o ultimo resultado dos dados foram iguais.
     * @return true ou false
     */
    public boolean verificaDadosIguais(){
        return ultimoResultadoDados[0] == ultimoResultadoDados[1];
    }
    
    /**
     * Lança dos dados, gerando dois números aleatórios e atualizando o ultimo resultado dos dados.
     */
    public void jogarDados(){
        int dado1 = this.random.nextInt(6)+1;
        int dado2 = this.random.nextInt(6)+1;
        ultimoResultadoDados = new int[]{dado1, dado2};
    }
    
    /**
     * Obtem os saltos e retorna a nova posição do jogador da vez.
     * @param jogadorDaVez
     * @return Posição do jogador no tabuleiro após a jogada.
     */
    public Posicao jogar(Jogador jogadorDaVez){
        int saltos = ultimoResultadoDados[0] + ultimoResultadoDados[1];
        Posicao posicaoDoJogadorDaVez = moveJogadorNoTabuleiro(saltos, jogadorDaVez);
        return posicaoDoJogadorDaVez;
    }
    
    /**
     * Verifica se o jogador está preso.
     * @param jogador
     * @return true ou false
     */
    public boolean jogadorEstaPreso(Jogador jogador){
        if(jogador.getReclusao() > 0){
            return true;
        }
        
        return false;
    }
    
     /**
     * Este método adiciona um dono a um titulo de posse. É usado para adiionar o jogador como dono da propriedade que comprou, ou seja, quando ele cai em uma propriedade e compra ela.
     * @param dono Jogador
     */
    public void adicionarDonoDePosse(Jogador dono){
        TituloDePosse c = (TituloDePosse) tabuleiro.getPosicoes()[dono.getPosicaoNoTabuleiroIndice()];
        c.setDono(dono);
        tabuleiro.getPosicoes()[dono.getPosicaoNoTabuleiroIndice()] = (Posicao) c;
    }
    
    /**
     * Retorna o tabuleiro do jogo.
     * @return 
     */
    public Tabuleiro getTabuleiro(){
        return this.tabuleiro;
    }
    
    /**
     * Retorna o ultimo resultado dos dados lançados.
     * @return ultimoResultadoDados
     */
    public int[] getUltimoResultadoDosDados(){
        return this.ultimoResultadoDados;
    }
    
    /**
     * Retorna a ultima quantidade de saltos obtida com a soma dos dados.
     * @return saltos
     */
    public int getUltimoSalto(){
        return ultimoResultadoDados[0] + ultimoResultadoDados[1];
    }
    
    /**
     * Prende o jogador colocando-o na prião e setando o tempo de reclusão.
     * @param jogador 
     */
    public void prenderJogador(Jogador jogador){
        jogador.setPosicaoNoTabuleiroIndice(10); // Colocando o jogador na prisão
        jogador.setReclusao(3);
    }

}
