**UNIVERSIDADE FEDERAL DA PARAÍBA**<br/>
**Campus IV - Centro de Ciências Aplicadas e Educação**<br/>
**Departamento de Ciências Exatas**

**Disciplina: Programação Orientada a Objetos**<br/>
**Docente:** Fábio Jorge Almeida Morais<br/>
**Grupo:**<br/>
Emerson Ruan Dantas Silva - Matrícula: 20170050755<br/>
Francivaldo Napoleão Herculano - Matrícula: 20170050610

# Relatório do projeto - Banco Imobiliário

# 1. Funcionamento<br/>
O jogo usa o terminal para criar a interação com os jogadores, de maneira
simples, mas intuitiva. Eles são orientados por mensagens e usam comandos
pré-estabelecidos para realizar escolhas durante o jogo.
Para mostrar as cores no terminal utilizei ANSI color scape, essa marcação 
funciona nativamente no NetBeans e no terminal de distribuições linux. Já 
no eclipse é preciso instalar o plugin [Escape ANSI no Console](https://marketplace.eclipse.org/content/ansi-escape-console).

## 1.1. Início do jogo - Quantidade de jogadores<br/>
Assim que o jogo é iniciado é pedido a quantidade de jogadores, algum dos
jogadores deve dar essa informação para que o jogo prossiga.

Exemplo no terminal: 
    
    Digite o número de jogadores [2-6]: 6

Caso o valor informado seja maior ou menor que a quantidade de jogadores
informada, uma mensagem de erro será mostrada e será pedido ao jogador a
quantidade certa.

## 1.2. Nome dos jogadores e cores dos peões<br/>
Após definir número de jogadores, será pedido o nome e cor do peão a cada
jogador, sequencialmente. Cada um deve ter uma cor única, as cores disponíveis
são apresentadas a cada um na sua vez.

Exemplo no terminal:

    Digite o nome do jogador 1: Emerson
    Jogador [1] Emerson, escolha a cor do seu peão entre as opções seguintes:
    ╔╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╗
    ⇒ [preto] [branco] [vermelho] [verde] [azul] [amarelo] [laranja] [rosa] ⇐
    ╚╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╝

Se eventualmente o jogador escolher uma cor inexistente ou indisponível, será
mostrada uma mensagem de erro e o jogador deverá fazer uma nova escolha. Ao fim
das escolhas, será apresentada a mensagem informando que o jogo começou.

Exemplo no terminal: O Banco Imobiliário vai começar. Aproveite!

## 1.3. Início da jogada - vez do jogador<br/>
Após a escolha do nome e cor dos jogadores o jogo se inicia, é apresentado a
informação do jogador da vez, junto com ela os comandos disponíveis.

Exemplo no terminal:

    A jogada de Emerson [verde] começou.
    Comandos disponíveis: [jogar][status][sair]
    Entre com um comando:

Caso o jogador escolha a opção **jogar**, os dados serão lançados e será
mostrado o resultado (valor de cada dado e a soma), a posição no tabuleiro onde
o peão parou e o saldo em dinheiro do jogador.

Exemplo no terminal:

    O jogador Emerson [verde] tirou [5,4] e o peão avançou para 9 - Av. 9 de Julho
    O título da propriedade Av. 9 de Julho está disponível por $220.
    Emerson você possui $1500.
    Você deseja comprar Av. 9 de Julho?
    (Sim/Não):

No exemplo acima o peão do jogador parou na *Av. 9 de Julho*, como essa posição
é uma propriedade e nesse caso está sem dono, é perguntado ao jogador se ele
deseja comprá-la. Caso ele opte por comprar e tenha saldo suficiente, a
propriedade irá para sua lista de títulos e o valor dela será debitado do saldo.

Entretanto, se a propriedade na qual o jogador da vez parou tiver dono, o mesmo 
deverá pagar o valor do aluguel para ele, tendo em conta que tenha saldo suficiente.

Exemplo no terminal:

    O jogador Emerson [verde] tirou [6,2] e o peão avançou para 8 - Av. Rebouças
    Dono: Francivaldo [azul].
    Valor do aluguel: R$18
    Você pagou R$ 18 de aluguel a Francivaldo [azul].

Se o jogador optar pela opção **status** será mostrado as informações do mesmo,
contendo a posição no tabuleiro, saldo e títulos adquiridos. Supondo que no
exemplo acima o jogador tenha comprado a propriedade *Av. 9 de Julho, temos:*

Exemplo no terminal:

    O status de Emerson [verde] é o seguinte:
    Situado na posição 9 - Av. 9 de Julho
    Possui \$1280
    Títulos:
    [Av. 9 de Julho] propriedade azul claro, aluguel R\$18

Caso o jogador tenha desistido do jogo ou simplesmente tenha que sair, ele terá
que escolher a opção **sair**. Ao dar esse comando ele terá que confirmar sua
decisão.

Exemplo no terminal:

    Deseja mesmo sair?
    (Sim/Não):

Se a escolha for **sim**, o jogador será removido da lista de jogadores e o jogo
prosseguirá, se ainda houver a quantidade mínima de jogadores.

	
## 1.4.	Sorte ou Revés<br/>
A depender da posição que o jogador estiver no tabuleiro, é possível que ele caia na posição Sorte ou Revés. Esta posição tem como função dar acesso a uma pilha de cartas com variadas ações. 

Exemplo no terminal:

    O jogador Emerson [verde] tirou [6,2] e o peão avançou 8 posições até - Sorte ou Revés
    - Jogue os dados novamente.
    Jogue novamente!
    Aperte ENTER
		
A ação da carta tirada na pilha é dada logo abaixo a linha que contém as informações do jogador e o número de saltos dado pelo mesmo no tabuleiro. Existem 8 (oito) tipos distintos de ações na pilha, são eles: **pague**, **receba**, **sair da prisão**, **vá para prisão**, **par ou ímpar**, **casamento**, **jogar novamente**, e **início**.

**Pague** - Ao retirar uma carta desse tipo, é debitado do jogador o valor definido pela carta.<br/>
**Receba** - Ao retirar uma carta desse tipo, é creditado ao jogador o valor definido pela carta.<br/>
**Sair da prisão** - Existe apenas uma carta dessa na pilha. Ao retirá-la, o jogador fica com ela guardada até haver a necessidade de usá-la. Após o seu uso ela é adicionada novamente a pilha.<br/>
**Vá para prisão** - Igual a carta anterior, existe apenas uma carta dessa na pilha. Ao retirá-la, o jogador é movido diretamente para a prisão.<br/>
**Par ou ímpar** - Como as duas últimas cartas, existe apenas uma carta desse tipo, porém o seu resultado é totalmente ligado ao resultado dos dados. Se a soma dos dados resultar num número ímpar, será creditado ao jogador R$ 100, senão, esse valor será debitado da conta do jogador.<br/>
**Casamento** - Também existe apenas uma carta desse tipo no tabuleiro. A sua ação é creditar R$ 50 ao jogador.<br/>
**Jogar novamente** -  Novamente, só existe uma carta desse tipo e o nome já diz tudo. O jogador que retirar essa carta terá o direito de jogar novamente.<br/>
**Início** - Essa carta não tem uma ação muito legal. Quem retirá-la voltará para o início e terá creditado R$ 200.<br/>

Aqui abaixo está apresentado mais um exemplo do terminal. Neste o jogador retira uma carta do tipo Pague:

Exemplo no terminal:

    O jogador Francivaldo [azul] tirou [1,1] e o peão avançou 2 posições até - Sorte ou Revés
    - Sua empresa foi multada por poluir demais.
    Pague R$200 ao banco!
    Você pagou R$ 200 ao banco. Seu Saldo atual é de R$1300
    Aperte ENTER

O título da carta vem logo abaixo das informações do jogador juntamente com sua ação (como dito acima). Nesse caso a ação resultou no débito de R$ 200 (duzentos reais). Logo após é retornado o saldo do jogador após a aplicação da ação da carta. Ao fim de tudo é necessário pressionar enter para dar continuidade ao jogo.

## 1.5. Prisão<br/>

Essa posição no tabuleiro tem a função de reclusão do jogador, ou seja, ela bloqueia em parte as funções do jogador por 3 rodadas consecutivas. Entretanto existem regras para o jogador entrar e sair da prisão.

Existem 3 (três) situações distintas das quais o jogador será enviado para prisão. São elas: <br/>
**Sorte ou revés** - Como dito anteriormente, existe a possibilidade do jogador ser enviado para a prisão por meio da carta Vá para prisão presente na pilha sorte ou revés. Para isso basta ele apenas ter a “sorte” de tirar essa carta da pilha.<br/>
**Posição no tabuleiro “Vá para prisão”** - Se você for um cara “sortudo” e cair nessa posição, você será enviado para a prisão, assim como na carta citada acima.<br/>
**Tirando o valor dos dados iguais** - Tirar o valor dos dados iguais por duas vezes ajuda muito na movimentação pelo tabuleiro, entretanto, se você tirar valores iguais nos dados por 3 (três) vezes seguidas você também será enviado para prisão. Esse é o preço que se paga por ter tanta sorte!

Exemplo no terminal (via posição “Vá para prisão” no tabuleiro):

    O jogador Emerson [verde] tirou [1,6] e o peão avançou 7 posições até - Vá para a prisão
    Vá para a prisão, sem reclamar!
    Aperte ENTER

Após a entrada do jogador por uma dessas situações, existem algumas regras para que o mesmo possa sair da prisão. Estas estão destacadas abaixo:

**Sorte ou revés** - Como dito no tópico de mesmo nome, existe uma carta que pode retirar o jogador da prisão caso ele possua essa carta consigo. Esta carta é a “Sair da prisão”. Nessa situação o seguinte menu é apresentado:

Exemplo no terminal:

    A jogada de Emerson [verde] começou.
    Emerson está na prisão.
    Comandos disponíveis: [pagar][carta][jogar][status][sair]
    Entre com um comando:

Ao selecionar a opção “carta” o jogador é retirado da prisão e pode continuar sua jogada logo em seguida.

**Pagar** - Nesta opção o jogador paga R$ 50 (cinquenta reais) ao banco. Ao pagar sua fiança o jogador é imediatamente retirado da prisão e o mesmo já pode iniciar sua jogada. Nessa situação o seguinte menu é apresentado:

Exemplo no terminal:

    A jogada de Francivaldo [azul] começou.
    Francivaldo está na prisão.
    Comandos disponíveis: [pagar][jogar][status][sair]
    Entre com um comando:

**Tirando dados iguais** - Se por um acaso você é bastante “amarrado” e não deseja pagar ao banco, você pode tentar a sorte. Se o jogador lançar os dados e retirar números iguais, o mesmo sairá da prisão e irá avançar o número de casas correspondente a soma dos números dos dados. Para isso é necessário selecionar a opção “jogar” no seguinte menu.

Exemplo no terminal:

    A jogada de Emerson [verde] começou.
    Emerson está na prisão.
    Comandos disponíveis: [pagar][jogar][status][sair]
    Entre com um comando:

**Se o jogador tentar a sorte por 3 rodadas e não conseguir sair da prisão, o mesmo terá R$ 50 (cinquenta reais) debitados da sua conta e liberado em seguida.**

**Prisão - “Visitante”** - Se por acaso o jogador cair na prisão em jogadas normais, ele será considerado “visitante” e portanto não serão aplicadas nenhumas das regras acima destacadas.

## 1.6.	Companhias<br/>
No exemplo abaixo o peão do jogador parou na Companhia de Táxi Aéreo, como essa posição é uma companhia e nesse caso está sem dono, é perguntado ao jogador se ele deseja comprá-la. Caso ele opte por comprar e tenha saldo suficiente, a companhia irá para sua lista de títulos e o valor dela será debitado do saldo. 


Exemplo no terminal:

    O jogador Francivaldo [azul] tirou [5,3] e o peão avançou 8 posições até - Companhia de Táxi Aéreo
    O título da companhia Companhia de Táxi Aéreo está disponível por $200.
    Francivaldo você possui $889.
    Você deseja comprar Companhia de Táxi Aéreo?
    (Sim/Não):

Entretanto, se a propriedade na qual o jogador da vez parou tiver dono, o mesmo deverá pagar o valor do aluguel para ele, tendo em conta que tenha saldo suficiente. Segue abaixo o exemplo:
		
Exemplo no terminal:

    O jogador Emerson [verde] tirou [5,3] e o peão avançou 8 posições até - Companhia de Táxi Aéreo
    Dono: Francivaldo [azul].
    Valor do multiplicador: 50
    Resultando no valor a pagar de R$: 400
		
Se o jogador tiver saldo suficiente irá aparecer abaixo do valor a pagar:

    Você pagou R$ 400 a Francivaldo [azul].
	
Se o jogador não tiver saldo suficiente, irá aparecer essa mensagem:

    Não foi possível pagar o valor acima pelo motivo:
    Saldo Insuficiente.

Se um jogador cair em cima de uma companhia pertencente a ele, será apresentada a seguinte mensagem:

    Você é o dono dessa companhia.

## 1.7. Propriedades<br/>

Assim como as companhias, as propriedades também são títulos de posse. Ou seja, ao realizar a jogada, caso o jogador pare em cima de uma propriedade ele poderá optar por comprá-la, se ela já não tiver um dono.

Exemplo no terminal:

    O jogador Emerson [verde] tirou [2,6] e o peão avançou 8 posições até - Av. Rebouças
    O título da propriedade Av. Rebouças está disponível por $200.
    Emerson você possui $1500.
    Você deseja comprar Av. Rebouças?
    (Sim/Não): 
		
No exemplo acima, caso o jogador escolha **sim**,  o valor da propriedade será descontado do saldo e a propriedade em questão será dele.
Caso a propriedade já tenha um dono, o jogador da vez deverá pagar o aluguel a ele, como no exemplo abaixo.

Exemplo no terminal:

    O jogador Emerson [verde] tirou [6,2] e o peão avançou para 8 - Av. Rebouças
    Dono: Francivaldo [azul].
    Valor do aluguel: R$18
    Você pagou R$ 18 de aluguel a Francivaldo [azul].

Se o jogador da vez parar em uma propriedade que é dele, será mostrada apenas uma mensagem informando isso.

Exemplo no terminal:

    O jogador Emerson [verde] tirou [6,2] e o peão avançou para 8 - Av. Rebouças
    Você é o dono dessa propriedade.

## 1.8. Construção de casas<br/>

Essa funcionalidade só fica disponível quando o jogador da vez deter o monopólio sobre um grupo de propriedade, ou seja, possuir todas as propriedades com a mesma identificação (cor). Se essa condição for verdadeira, a seguinte tela será apresentada:

Exemplo no terminal:

    A jogada de Francivaldo [azul] começou.
    Comandos disponíveis: [construir][jogar][status][sair]
    Entre com um comando: 


Ao digitar a opção construir, será apresentada uma tela com informações do saldo bancário do jogador, bem como as propriedades dos grupos de cores que ele tem monopólio. No exemplo abaixo o jogador possui monopólio sobre o grupo de propriedades de cor vermelha.

Exemplo no terminal:

    Francivaldo possui $1280
    Escolha onde quer construir:
    1-Botafogo tem 0 casa(s) construída(s). Construir custa $50
    2-Flamengo tem 0 casa(s) construída(s). Construir custa $50
    Digite o número da propriedade (0 para sair)

Ao digitar 1 (um) o jogador está afirmando que deseja construir uma casa na propriedade indicada. Com essa afirmação, a seguinte tela será apresentada:
	
Exemplo do terminal:

    Francivaldo possui $1280
    Escolha onde quer construir:
    1-Botafogo tem 0 casa(s) construída(s). Construir custa $50
    2-Flamengo tem 0 casa(s) construída(s). Construir custa $50
    Digite o número da propriedade (0 para sair):1
    Você construiu na propriedade Av.Brigadeiro Faria Lima. Agora ela possui 1 casa(s) construída(s).
    Aperte ENTER


**Observações da funcionalidade:**
O jogador pode construir no máximo 5 (cinco) casas por propriedade, se o mesmo construir as 5 (cinco) casas, ele possuirá um Hotel. A  jogada do jogador da vez só irá terminar quando o mesmo digitar zero (0) e realizar sua jogada normalmente.

## 1.9. Venda de casas<br/>

O comando vender só fica disponível quando o jogador possui casas ou hotéis construídos em suas propriedades. Se essa condição for satisfeita, a seguinte tela irá aparecer:

Exemplo no terminal:

    A jogada de Francivaldo [azul] começou.
    Comandos disponíveis: [construir][vender][jogar][status][sair]
    Entre com um comando: 

Ao digitar a opção vender, será apresentada uma tela com informações do saldo bancário do jogador, bem como as casas (ou hotéis) disponíveis para venda.

Exemplo no terminal:

    Francivaldo possui $1130
    Escolha onde quer vender:
    1-Botafogo tem 3 casa(s) construída(s). Construir custa $50
    2-Flamengo tem 1 casa(s) construída(s). Construir custa $50
    Digite o número da propriedade (0 para sair):

Ao digitar 1 (um) o jogador está afirmando que deseja vender uma casa na propriedade indicada. Com essa afirmação, a seguinte tela será apresentada:
	
Exemplo do terminal:

    Francivaldo possui $1130
    Escolha onde quer vender:
    1-Botafogo tem 3 casa(s) construída(s). Construir custa $50
    2-Flamengo tem 1 casa(s) construída(s). Construir custa $50
    Digite o número da propriedade (0 para sair):1
    Você vendeu uma construção na propriedade Botafogo. Agora ela possui 2 casa(s) construída(s).
    Aperte ENTER

**Observações da funcionalidade:**
O jogador pode vender todas suas casas (ou hotéis). A  jogada do jogador da vez só irá terminar quando o mesmo digitar zero (0) e realizar sua jogada normalmente.


# 2.  Padrões de projeto<br/>

No nosso projeto foi utilizados os seguintes padrões: Singleton, Factory Method, Strategy e Facade.

## 2.1. Singleton<br/>

O padrão singleton garante que exista apenas uma instância de uma determinada classe, esse padrão foi implementado nas classes em que essa ideia se aplica, foram elas: ArchiveManager, Exportadores(todos os concretos), ImpostoDeRenda, JogoFacade, LucrosOuDividendos, ParadaLivre, PilhaSorteOuReves, PontoDePartida, Prisao, Tabuleiro e VaParaPrisao.

## 2.2. Factory Method<br/>

O padrão Factory Method encapsula a criação de objetos. Ele foi usado na classe Exportador, em conjunto com o padrão Strategy. Pois cada subclasse do exportador cria um objeto diferente. O método arquivavelFactory é usado quando é preciso criar uma nova instância para ser adicionada ao tabuleiro, e com ele, cada exportador consegue retornar qual objeto ele instancia.

## 2.3. Strategy<br/>

O padrão Strategy cria uma estratégia diferente para cada variação de algoritmo e delega essa função a ela. Ele foi usado na classe ArchiveManager, ela possui um exportador e delega a função de exportar a ele. Existem 3 variantes de exportação de arquivos: ExportadorPropriedades, ExportadorCompanhias e ExportadorSorteOuReves, cada variação exporta de uma forma diferente, o Archive manager não precisa saber como exportar, ele só pode para o exportador selecionado exportar e tudo dá certo, mas para isso é preciso selecionar antes qual exportador será usado. 

## 2.4. Facade<br/>

O padrão Facade cria uma interface entre o usuário e o sistema(lógica), no projeto ele foi usado para criar uma interface que pudesse ser usada na classe principal(main). Chamada JogoFacade, ela é usada para realizar ações fundamentais de jogabilidade, ocultando algumas lógicas de jogo do main.

# 3. Conceitos de POO aplicados
## 3.1. Herança
**Herança de tipo** - Usada nas Interfaces Posição, TituloDePosse e Arquivavel. A escolha pelo uso da herança de tipo se deu justamente por todas as classes que a herdam necessitarem ter as mesmas funções em comum. 

Exemplo: A classe Tabuleiro é formada por posições, existem 6 (seis) tipos de posição, entretanto todas elas possuem métodos em comum, sendo assim, o uso da herança de tipo se mostrou o mais indicado.

**Herança de implementação** - Usada na classe Exportador, essa que é uma classe abstrata com implementação, onde todas as classes que a herdam além de herdar o seu tipo estão sendo obrigadas a implementar os métodos que são abstratos.

## 3.2. Composição	
Usada em várias classes do projeto. Aqui abaixo estão indicadas as classes em que foram usadas composição:

**JogoFacade** tem 1 (um) Tabuleiro e uma Lista Encadeada de Jogador, essa que é obtida pela classe Jogo;

**Tabuleiro** tem um Array de 40 (quarenta) Objetos Posicao e 1 (um) Objeto do tipo PilhaSorteOuReves;

**PilhaSorteOuReves** tem uma List de 31 (trinta e um) Objetos SorteOuReves;

**Jogo** tem 1 (um) JogoFacade;

**Jogador** tem 1 (um) Objeto ContaCorrente;

**ArchiveManager** tem 1 (um) Objeto Exportador;
	
## 3.3. Polimorfismo
Utilizado no método **realizaAcao** da Interface **Posicao**. A regra para existir polimorfismo é a utilização de herança e sobrescrita, e isso é o que ocorre nesse método especificado. O método realizaAcao é herdado da classe Posicao, e sobrescrito em todas as classes filhas, visto que sua implementação é diferente ao depender do tipo de posição.


# UML DO PROJETO

![alt tag](https://github.com/dcx-cursos/projeto-poo-teamcoffee/blob/beta4/UML/ProjetoPOO-UML.png?raw=true)

[Link do relátório em
docx](https://docs.google.com/document/d/1wfqSmLM4yEb6U_k7-uasAjcuCY6R2JfNpcgX2CRVouE/edit?usp=sharing)

[Link do Javadoc](https://emersondantas.github.io/bancoImobiliario-poo/)
