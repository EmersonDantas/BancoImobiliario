package bancoimobiliario;

import arquivo.ArchiveManager;
import excecoes.SaldoInsuficienteException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Classe que representa a posição SorteOuReves no tabuleiro.
 */
public class PilhaSorteOuReves implements Posicao{
    private List<SorteOuReves> cartas;
    private static PilhaSorteOuReves instance;
    
    private PilhaSorteOuReves(ArrayList<SorteOuReves> cartas) throws IOException{
        this.cartas = cartas;
        embaralhar();
    }
    
    /**
     * Padrão singleton que garante se só tenha uma instancia dessa classe.
     * @return PilhaSorteOuReves propria instancia.
     */
    public static synchronized PilhaSorteOuReves getInstance() throws IOException{
        if(instance == null){
            ArchiveManager archMan = ArchiveManager.getInstance();
            ArrayList<SorteOuReves> cartasArq = (ArrayList<SorteOuReves>) archMan.getListSorteOuReves();
            instance = new PilhaSorteOuReves(cartasArq);
        }
        
        return instance;
    }
    
    /**
     * Este método tem como função embaralhar as cartas da pilha.
     */
    public void embaralhar(){
        Collections.shuffle(this.cartas);
    }
    
    /**
     * Este método obtém a carta que está no topo da pilha e a retorna para o jogardor.
     * @return Posicao cartaCol
     */
    public SorteOuReves pegarCarta(){
        SorteOuReves cartaCol = this.cartas.get(0);
        colocarCartaNoFim(cartaCol);
        return cartaCol;
    }
    
    /**
     * Este método tem como objetivo auxiliar o método pegarCarta. Sua função é colocar a carta que foi obtida no fim da pilha.
     * @param carta SorteOuReves
     */
    public void colocarCartaNoFim(SorteOuReves carta){
        this.cartas.add(carta);
        this.cartas.remove(this.cartas.get(0));
    }
    
    /**
     * Este método tem como função remover da pilha a carta SairDaPrisao após um jogador obtê-la;
     */
    public void removeCartaSairDaPrisao(){
        for(int i = 0; i < this.cartas.size(); i++){
            if(cartas.get(i).getAcao().equals("sairDaPrisao")){
                this.cartas.remove(i);
            }
        }
    }
    
    /**
     * Este metódo tem como função re-adicionar a pilha a carta SairDaPrisão após o uso dela por um jogador.
     */
    public void adicionaCartaSairDaPrisao(){
        this.cartas.add(new SorteOuReves("Utilize este cartão para se livrar da prisão", "sairDaPrisao"));
    }
    
    /**
     * Retorna a lista de cartas SorteOuReves.
     * @return 
     */
    public List<SorteOuReves> getCartas() {
        return cartas;
    }
    
    /**
     * Retona uma breve descrição dos atributos da classe.
     * @return descrição
     */
    @Override
    public String getDescricao() {
        return "Pilha de cartas de Sorte Ou Reves";
    }

    /**
     * Retorna o nome da posição.
     * @return nomeDaPosicao
     */
    @Override
    public String getNome() {
        return "Sorte ou Reves";
    }

    @Override
    public boolean realizaAcao(Jogador jogador) {
        JogoFacade facade = null;
        try {
            facade = JogoFacade.getInstance();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        boolean repetir = false;
        SorteOuReves topo = this.pegarCarta();
        System.out.println("- "+topo.getDescricao()+".");

        switch(topo.getAcao()){
            case("pague"):
                System.out.println("Pague $" +topo.getValor()+" ao banco!");
                try {
                    jogador.debitar(topo.getValor());
                    System.out.println("Você pagou $ "+topo.getValor()+" ao banco. Seu Saldo atual é de $"+jogador.getSaldo());
                } catch (SaldoInsuficienteException ex) {
                    System.out.println(ex.getMessage());
                }
                break;

            case("receba"):
                jogador.creditar(topo.getValor());
                System.out.println("Você recebeu $ "+topo.getValor()+" do banco!");
                break;

            case("inicio"):
                jogador.creditar(topo.getValor());
                jogador.setPosicaoNoTabuleiroIndice(0);
                System.out.println("Você recebeu $ "+topo.getValor()+" do banco!");
                break;

            case("parOuImpar"):
                if((facade.getUltimoResultadoDosDados()[0] + facade.getUltimoResultadoDosDados()[1]) % 2 == 0){
                    jogador.creditar(topo.getValor());
                    System.out.println("Sorte! Você recebeu $" +topo.getValor()+ " do banco!");

                }else{
                    System.out.println("Reves! Pague $" +topo.getValor()+ " ao banco!");
                    try {
                        jogador.debitar(topo.getValor());
                        System.out.println("Você pagou $" +topo.getValor()+ " ao banco!");
                    } catch (SaldoInsuficienteException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                break;

            case("sairDaPrisao"):
                jogador.setPossuiCartaSairDaPrisao(true);       
                System.out.println("Esta carta servirá para você sair da prisão quando for preso.");
                this.removeCartaSairDaPrisao();
                break;

            case("vaParaPrisao"):
                jogador.setPosicaoNoTabuleiroIndice(10);
                jogador.setReclusao(3);
                System.out.println("Vá para a prisão, sem reclamar!");
                break;

            case("jogarNovamente"):
                System.out.println("Jogue novamente!");
                repetir = true;
                break;

            case("casamento"):
                System.out.println("Receba $"+topo.getValor()+" de cada jogador!");
                
                ListaEncadeada<Jogador> jogadores = facade.getJogadores();
                Jogador jogadorInicial = jogadores.getPrimeiro();
                Jogador auxJog = jogadores.getPrimeiro();
                while(!auxJog.equals(jogadorInicial)){ // Fazendo com que restante dos jogadores paguem o presente do jogador da vez
                    if(!(auxJog.equals(jogador))){
                        try {
                            auxJog.transferirDinheiro(jogador, topo.getValor());
                        } catch (SaldoInsuficienteException ex) {
                            System.out.println("O jogador "+ auxJog.getNome()+ " não possui saldo suficiente para lhe presentear! :(");
                        }
                        auxJog = jogadores.getProximo(auxJog);
                    }
                }
                break;
        }
        return repetir;
    }
    
}
