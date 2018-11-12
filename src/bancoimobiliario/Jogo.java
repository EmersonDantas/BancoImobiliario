package bancoimobiliario;
import excecoes.CorIndisponivelException;
import excecoes.CorNaoExisteException;
import excecoes.MaximoDeContrucoesAtingidoException;
import excecoes.SaldoInsuficienteException;
import excecoes.SemConstrucoesException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe principal que faz a interface do jogo com o usuario.
 */
public class Jogo {
    private static JogoFacade facade;
    public static void main(String[] args){
        try {
            facade = JogoFacade.getInstance();
        } catch (IOException ex) {
            System.out.println("Erro fatal na leitura de arquivos, fechando jogo. "+ex.getMessage());
            System.exit(0);
        }
        
        //Array de cores com formatação ANSI usadas na apresentação das cores para o jogador escolher e será a cor do jogador por todo o jogo.
        //No eclipse é preciso a extenção "ANSI Escape in Console" para que as cores funcionem.
        String [] coresFormatadas = new String[] {"\u001b[37;0mpreto\u001b[m","\u001b[30;1mbranco\u001b[m","\u001b[31;1mvermelho\u001b[m","\u001b[32;1mverde\u001b[m","\u001b[34;1mazul\u001b[m","\u001b[33;1mamarelo\u001b[m","\u001b[33;2mlaranja\u001b[m","\u001b[35;1mrosa\u001b[m"};
        ArrayList<String> cores = new ArrayList<>();
        //Coloquei as cores em uma ArrayList para ficar mais fácil a remoção e verificação de cores.
        cores.addAll(Arrays.asList(coresFormatadas));
        
        int quantidadeDeJogadores = quantidadeJogadoresInput();
        recebeJogadores(quantidadeDeJogadores, cores, coresFormatadas);
        
        pula50Linhas();
        System.out.println("\033[1;32mO Banco Imobiliário vai começar. Aproveite!\033[m");
        
        //Loop responsável pela rotação entre os jogadores
        while(facade.getQuantJogadores() >= 2){ //Condição de parada do jogo, o ultimo jogador a falir vence (será implementado a condição de falência futuramente).
            boolean prosseguir = acaoDoJogadorDaVez(facade.getJogadorDaVez());
            if(prosseguir){
                facade.proximoJogador();
            }
            
        }
        System.out.println("Fim de jogo.");
    }
    
    /**
     * Recebe os jogadores pedindo o número e cor, repete caso o jogador informe cores inválidas.
     * @param quantidadeDeJogadores quantidade de jogadores.
     * @param cores lista de cores
     * @param coresFormatadas array de cores
     */
    public static void recebeJogadores(int quantidadeDeJogadores, ArrayList<String> cores, String[] coresFormatadas){
        for(int i = 0; i < quantidadeDeJogadores; i++){
            pula50Linhas();
            String nomeDoJogador = facade.input("Digite o nome do jogador " + (i+1) + ": ");
            
            String corDoJogador = null;
            boolean parar = false;
            while(parar != true){
                System.out.println("Jogador [" + (i+1) + "] "  + nomeDoJogador + ", escolha a cor do seu peão entre as opções seguintes:");
                imprimeCoresDisponiveis(cores);
                corDoJogador = facade.input(": ");
                try{
                    corDoJogador = formataCorComEscapeAnsi(coresFormatadas, corDoJogador); //Pode gerar exceção de que a cor não existe.
                    verificaDispoCor(corDoJogador, cores); //Pode gerar exceção de que a cor está indisponível.
                    parar = true;
                } catch (CorNaoExisteException | CorIndisponivelException ex) {
                    pula50Linhas();
                    System.out.println(ex.getMessage());
                }
            }
            facade.adicionaJogador(new Jogador(nomeDoJogador, corDoJogador)); // Adiciona o novo jogador.
        }
    }
    
    /**
     * Este método pede a ação do jogador e executa a ação da posição na qual ele parou no tabuleiro, além de repetir a jogada caso preciso.
     * @param jogadorDaVez Jogador da vez
     */
    public static boolean acaoDoJogadorDaVez(Jogador jogadorDaVez){
        boolean prosseguir = true;
        System.out.println("A jogada de " + jogadorDaVez.getNome() + " ["+jogadorDaVez.getCor()+"]" + " começou.");
        String comando;
        
        if(facade.jogadorEstaPreso(jogadorDaVez)){ // Se o jogador estiver preso, terá as seguintes opções
            System.out.println(jogadorDaVez.getNome() + " está na prisão.");
            
            if(jogadorDaVez.getPossuiCartaSairDaPrisao() == true){ // Se o jogador preso tiver a carta "Sair da prisão"
                System.out.println("Comandos disponíveis: [pagar][carta][jogar][status][sair]");
                
            }else{
                System.out.println("Comandos disponíveis: [pagar][jogar][status][sair]");
            }
            
        }else if (jogadorDaVez.possuiMonopolioEmAlgumGrupoDeCores()){
            if(jogadorDaVez.possuiAlgumaContrucao()){
               System.out.println("Comandos disponíveis: [construir][vender][jogar][status][sair]"); 
            }else{
                System.out.println("Comandos disponíveis: [construir][jogar][status][sair]"); 
            }
            
        }else{
            System.out.println("Comandos disponíveis: [jogar][status][sair]");
        }
        
        comando = facade.input("Entre com um comando: ").toLowerCase();
        pula50Linhas();
        
        switch(comando){
            case("jogar"):
                prosseguir = opcaoJogar(jogadorDaVez);
                break;

            case("sair"):
                System.out.println("Deseja mesmo sair?");
                boolean respostaPerg = facade.simOuNao(); //Pede a confirmação do jogador.

                if(respostaPerg == true){
                    System.out.println("Jogador "+ jogadorDaVez.getNome() + " ["+jogadorDaVez.getCor()+"]"+" saiu.");
                    facade.removeJogador(jogadorDaVez);
                    prosseguir =  false;
                }else{
                    return acaoDoJogadorDaVez(jogadorDaVez); // Chama recursivo para repetir a jogada
                }
                break;

            case("status"):
                pula50Linhas();
                System.out.println(facade.statusDoJogador(jogadorDaVez));
                facade.input("Aperte ENTER");
                pula50Linhas();
                return acaoDoJogadorDaVez(jogadorDaVez); // Chama recursivo para repetir a jogada
                
            case("carta"): // Caso o jogador esteja preso, essa opção será ativa no menú de jogo dele. Caso escolha essa opção ele deixará a prisão e devolverá a carta a pilha de sorte ou reves.
                jogadorDaVez.setPossuiCartaSairDaPrisao(false);
                facade.getTabuleiro().devolveCartaSairDaPrisaoAPilhaSorteOuReves();
                jogadorDaVez.setReclusao(0);
                System.out.println("Você usou sua carta 'Sair da prisão' e poderá jogar agora.\n");
                facade.input("Aperte ENTER");
                pula50Linhas();
                return acaoDoJogadorDaVez(jogadorDaVez); // Chama recursivo para repetir a jogada
                
            case("pagar"):
                try{
                    jogadorDaVez.debitar(50);
                    System.out.println("Foi debitado $ 50 da sua conta.");
                    jogadorDaVez.setReclusao(0);
                    System.out.println("Você pagou a fiança e poderá jogar agora.\n");
                    facade.input("Aperte ENTER");
                    pula50Linhas();
                    return acaoDoJogadorDaVez(jogadorDaVez); // Chama recursivo para repetir a jogada

                }catch (SaldoInsuficienteException ex){
                    System.out.println("Não foi possivel fazer o pagamento da sua fiança!");
                    System.out.println(ex.getMessage());
                }
                break;
                
            case("construir"):
                opcaoConstruir(jogadorDaVez);
                prosseguir =  false;
                facade.input("Aperte ENTER");
                pula50Linhas();
                break;
                
            case("vender"):
                opcaoVender(jogadorDaVez);
                prosseguir =  false;
                facade.input("Aperte ENTER");
                pula50Linhas();
                break;
                
            default:
                pula50Linhas();
                System.out.println("\033[1;31mComando inválido! Digite uma das opções a baixo.\033[m");
                return acaoDoJogadorDaVez(jogadorDaVez); // Chama recursivo para repetir a jogada

        }
        return prosseguir;
    }
    
    /**
     * Mostra as informações ao jogador da vez que escolheu a opção jogar.
     * @param jogadorDaVez o jogador da vez.
     * @return prosseguir que diz a quem chama esse método se deve prosseguir para o proximo jogador ou não. Usado para repetir a jogada.
     */
    public static boolean opcaoJogar(Jogador jogadorDaVez){
        facade.jogarDados();
        if(facade.verificaDadosIguais()){  // Incrementa o contador de dadosIguais, caso aconteça isso na jogada atual.
            jogadorDaVez.setSorteNosDados(jogadorDaVez.getSorteNosDados()+1);
        }else{
            jogadorDaVez.setSorteNosDados(0); // Se não forem iguais, automaticamente é anulado o contador.
        }

        if(facade.jogadorEstaPreso(jogadorDaVez)){ // Se o jogador estiver preso
            if(jogadorDaVez.getSorteNosDados() > 0){ // Se conseguir tirar os dados iguais
                System.out.println("Que sorte! Você tirou ["+facade.getUltimoResultadoDosDados()[0]+","+facade.getUltimoResultadoDosDados()[1]+"] e por isso sairá da prisão avançando " + facade.getUltimoSalto() + " casas.\n");
                jogadorDaVez.setReclusao(0);
                jogadorDaVez.setSorteNosDados(0); // Setando o contador de dados iguais para 0, pois o jogador que acabou de sair da prisão com isso não pode repetir a jogada.

            }else{ // Se o jogador não conseguir os dados iguais
                System.out.println("Você tirou ["+facade.getUltimoResultadoDosDados()[0]+","+facade.getUltimoResultadoDosDados()[1]+"] e por isso vai continuar na prisão!");
                int contReclusao = jogadorDaVez.getReclusao();
                contReclusao--;
                jogadorDaVez.setReclusao(contReclusao);

                if(facade.jogadorEstaPreso(jogadorDaVez) == false){
                    try{
                        jogadorDaVez.debitar(50);
                        System.out.println("Você poderá jogar na proxima partida, foi debitado $50 da sua conta.");
                    }catch(SaldoInsuficienteException erro){
                        System.out.println("Você poderá jogar na proxima partida, mesmo não tendo saldo para pagar a fiança.");
                    }
                }else{
                   System.out.println("Faltam "+ jogadorDaVez.getReclusao() + " jogadas para você sair da prisão.\n"); 
                }
                facade.input("Aperte ENTER");
                pula50Linhas();
                return true;
            }
        }

        //Ação da posicao atual do jogador na jogada é tratada abaixo
        //Recebe a posição onde o jogador parou para poder realizar as ações
        Posicao posicaoDoPeao = facade.jogar(jogadorDaVez);
        System.out.println("O jogador "+ jogadorDaVez.getNome() + " ["+jogadorDaVez.getCor()+"] "+"tirou ["+facade.getUltimoResultadoDosDados()[0]+","+facade.getUltimoResultadoDosDados()[1]+"] e o peão avançou " + facade.getUltimoSalto() + " posições até - " + facade.getTabuleiro().getNomePosicao(jogadorDaVez.getPosicaoNoTabuleiroIndice()));                    
        boolean repetir = acaoDaPosicao(posicaoDoPeao, jogadorDaVez); // Verifica qual o tipo de posicao onde o jogador parou e toma a devida ação, retorna true caso tenha conseguido a sorte de repetir a jogada na carta SorteOuReves.

        //Verifica se os dados foram iguais para repetir a jogada ou prender o jogador caso tenha tirado dados iguais por 3 vezes.
        if(facade.jogadorEstaPreso(jogadorDaVez) == false){ //Se o Jogador não estiver preso, ele podera repetir a jogada caso tenha obtido dados iguais ou tenha obtido a carta para repetir na sorte
            if (jogadorDaVez.getSorteNosDados() > 0 && jogadorDaVez.getSorteNosDados() < 3){
                System.out.println("Você obteve resultados iguais nos dados! Vamos joga-los novamente.\n");
                return false;
                
            }else if(jogadorDaVez.getSorteNosDados() == 3){ // Se o jogador obter dados iguais por 3 vezes será preso.
                System.out.println(jogadorDaVez.getNome() + " ["+jogadorDaVez.getCor()+"]" + " obteve dados iguais por 3 vezes, está preso por 3 rodadas.\n");
                facade.prenderJogador(jogadorDaVez);
                jogadorDaVez.setSorteNosDados(0); // Resetando o contador de dados iguais
                facade.input("Aperte ENTER");
                pula50Linhas();
            }

            if(repetir){
                return false;
            } 
        }
        
        return true; 
    }
    
    /**
     * Mostra ao jogador as propriedades enumeradas e dá a possibilidade do jogador escolher em qual delas construir.
     * @param jogadorDaVez Jogador da vez na jogada.
     */
    public static void opcaoConstruir(Jogador jogadorDaVez){
        List<Propriedade> propriedades = jogadorDaVez.getListaDePropriedadesComMonopolio();
        System.out.println(jogadorDaVez.getNome()+" possui $" + jogadorDaVez.getSaldo());
        System.out.println("Escolha onde quer construir:");
        for(int k = 0;k < propriedades.size(); k++){
            if(!(propriedades.get(k).isHaHotel())){
                System.out.println((k+1)+"-"+propriedades.get(k).getNome()+" tem "+propriedades.get(k).getContadorDeContrucoes()+" casa(s) contruida(s). Construir custa $"+propriedades.get(k).getPrecoConstruir());
            }else{
                System.out.println((k+1)+"-"+propriedades.get(k).getNome()+" tem um hotel.");    
            }
        }

        int opcao = 0;
        try{
            do{
                opcao = Integer.parseInt(facade.input("Digite o número da propriedade (0 para sair):"));
            }while(opcao < 0 || opcao > jogadorDaVez.getPropriedadesDoJogador().size());
        }catch(NumberFormatException ex){
            System.out.println("Entrada inválida! tente novamente.");
            facade.input("Aperte ENTER");
            pula50Linhas();
            opcaoConstruir(jogadorDaVez); // Chama recursivo para o jogador fazer novas escolhas
        }

        if(opcao != 0){
            try {
                propriedades.get(opcao-1).construir();
                System.out.print("Você construiu na propriedade "+propriedades.get(opcao-1).getNome()+". Agora ela possui ");
                if(propriedades.get(opcao-1).isHaHotel()){
                    System.out.println("um hotel.");
                }else{
                    System.out.println(propriedades.get(opcao-1).getContadorDeContrucoes()+ " casa(s) construida(s).");
                }
                facade.input("Aperte ENTER");
                pula50Linhas();
                opcaoConstruir(jogadorDaVez); // Chama recursivo para o jogador fazer novas escolhas
                

            } catch (SaldoInsuficienteException | MaximoDeContrucoesAtingidoException ex) {
                System.out.println(ex.getMessage());
                facade.input("Aperte ENTER");
                pula50Linhas();
                opcaoConstruir(jogadorDaVez); // Chama recursivo para o jogador fazer novas escolhas
            }
        }
    }
    
    /**
     * Mostra ao jogador as propriedades enumeradas e dá a possibilidade do jogador escolher em qual delas que vender uma contrução.
     * @param jogadorDaVez Jogador da vez na jogada.
     */
    public static void opcaoVender(Jogador jogadorDaVez){
        List<Propriedade> propriedades = jogadorDaVez.getListaDePropriedadesComMonopolio();
        System.out.println(jogadorDaVez.getNome()+" possui $" + jogadorDaVez.getSaldo());
        System.out.println("Escolha onde quer vender:");
        for(int k = 0;k < propriedades.size(); k++){
            if(!(propriedades.get(k).isHaHotel())){
                System.out.println((k+1)+"-"+propriedades.get(k).getNome()+" tem "+propriedades.get(k).getContadorDeContrucoes()+" casa(s) construida(s). Construir custa $"+propriedades.get(k).getPrecoConstruir());
            }else{
                System.out.println((k+1)+"-"+propriedades.get(k).getNome()+" tem um hotel.");    
            }
        }

        int opcao = 0;
        
        try{
            do{
                opcao = Integer.parseInt(facade.input("Digite o número da propriedade (0 para sair):"));
            }while(opcao < 0 || opcao > jogadorDaVez.getPropriedadesDoJogador().size());
        }catch(NumberFormatException ex){
            System.out.println("Entrada inválida! tente novamente.");
            facade.input("Aperte ENTER");
            pula50Linhas();
            opcaoVender(jogadorDaVez); // Chama recursivo para o jogador fazer novas escolhas
        }
        
        if(opcao != 0){
            try {
                propriedades.get(opcao-1).vender();
                System.out.print("Você vendeu uma construção na propriedade "+propriedades.get(opcao-1).getNome()+". Agora ela possui ");
                if(propriedades.get(opcao-1).isHaHotel()){
                    System.out.println("um hotel.");
                }else{
                    System.out.println(propriedades.get(opcao-1).getContadorDeContrucoes()+ " casa(s) construida(s).");
                }
                facade.input("Aperte ENTER");
                pula50Linhas();
                opcaoVender(jogadorDaVez); // Chama recursivo para o jogador fazer novas escolhas
                
            } catch (SemConstrucoesException ex) {
                System.out.println(ex.getMessage());
                facade.input("Aperte ENTER");
                pula50Linhas();
                opcaoVender(jogadorDaVez); // Chama recursivo para o jogador fazer novas escolhas
            }
        }
    }
    
    /**
     * Este método faz a ação da posicao onde o jogador parou de acordo com o tipo.
     * @param posicao onde o jogador parou.
     * @param jogador da vez.
     * @return repetir informa se o jogador pegou a posicao no sorte ou reves que faz ele repetir a jogada.
     */
    private static boolean acaoDaPosicao(Posicao posicao, Jogador jogador){
        boolean repetir = posicao.realizaAcao(jogador);
        facade.input("Aperte ENTER"); //Para o jogador confirmar a leitura
        pula50Linhas();// Lipa o terminal
        return repetir; //Retorna true se o jogador obter a posicao "Jogue novamente"
    }
    
    /**
     * Este método é responsável por pedir a quantidade de jogadores, obedecendo as regras e tratando exceções.
     * @return quantidadeDeJogadores
     */
    public static int quantidadeJogadoresInput(){
        int quantJog;
        
        while(true){
            try{
                quantJog = Integer.parseInt(facade.input("Digite o número de jogadores [2-6]: "));
                if(quantJog >= 2 && quantJog <= 6){
                    return quantJog;
                
                }else{
                    System.out.println("\033[1;31mQuantidade de jogadores inválida!\033[m");
                }
            }catch(NumberFormatException ex){
                System.out.println("\033[1;31mDigite apenas números!\033[m");
            }
        }
    }
    
    /**
     * Esse método recebe a entrada do usuario relativa a cor de escolha e retorna ela formatada com com escape ANSI, caso a cor desejada exista.
     * @param coresFormatadas List com as cores já formatadas com ANSI color scape
     * @param entrada Entrada do usuário
     * @return String cor formatada
     * @throws CorNaoExisteException Caso a cor escolhida pelo jogador não exista.
     */
    public static String formataCorComEscapeAnsi(String[] coresFormatadas, String entrada) throws CorNaoExisteException{
        String corSelecionada = null;
        entrada = entrada.toLowerCase();
        
        switch(entrada){
            case("preto"):
                corSelecionada = coresFormatadas[0];
                break;
                
            case("branco"):
                corSelecionada = coresFormatadas[1];
                break;
            
            case("vermelho"):
                corSelecionada = coresFormatadas[2];
                break;
                
            case("verde"):
                corSelecionada = coresFormatadas[3];
                break;
            
            case("azul"):
                corSelecionada = coresFormatadas[4];
                break;
                
            case("amarelo"):
                corSelecionada = coresFormatadas[5];
                break;
                
            case("laranja"):
                corSelecionada = coresFormatadas[6];
                break;
                
            case("rosa"):
                corSelecionada = coresFormatadas[7];
                break;
                
            default:
                throw new CorNaoExisteException("\033[1;31m☹ Cor escolhida não existe. Escolha uma válida!\033[m");
                 
        }
        return corSelecionada;
    }
    
    /**
     * Este método pula 50 linhas para limpar o terminal
     */
    private static void pula50Linhas(){
        for(int k = 0; k < 50;k++){
            System.out.println("");
        }
    }   
    
    /**
     * Este método printa as cores disponíveis (As que estão formatadas com escape ANSI) com uma borda.
     * @param coresAnsi lista de cores com escape ANSI
     */
    private static void imprimeCoresDisponiveis(ArrayList<String> coresAnsi){
        int quantCharCores = 0;
        System.out.print("╔");
        System.out.print(String.format("%87s"," ").replace(" ", "╌"));
        System.out.print("╗\n");
        
        System.out.print("⇒ ");
        for(int k = 0; k < coresAnsi.size(); k++){
            System.out.print("["+coresAnsi.get(k)+"] ");
        }
        System.out.println("⇐");
        
        System.out.print("╚");
        System.out.print(String.format("%87s"," ").replace(" ", "╌"));
        System.out.print("╝\n");
        
    }
    
    /**
     * Este método verifica se a cor escolhida pelo jogador está disponível.
     * @param corEscolhida cor escolhida pelo usuário, já com color escape ANSI
     * @param coresAnsi lista de cores disponíveis com ANSI color scape.
     * @throws CorIndisponivelException caso a cor já tenha sido escolhida por outro jogador.
     */
    private static void verificaDispoCor(String corEscolhida, ArrayList<String> coresAnsi) throws CorIndisponivelException{
        int indexOf = coresAnsi.indexOf(corEscolhida);
        if(indexOf == -1){
            throw new CorIndisponivelException("\033[1;31m☹ A cor\033[m "+corEscolhida+" \033[1;31mnão está disponível!\033[m");
        }
        coresAnsi.remove(corEscolhida);
    }
}
