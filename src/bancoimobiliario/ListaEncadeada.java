package bancoimobiliario;

/**
 *  Lista duplamente encadeada com sentinela.
 * @author emerson
 */
public class ListaEncadeada<Item> {
    private int tamanho;
    private No sentinela;
    
    /**
     * Inicia a lista vazia.
     */
    public ListaEncadeada(){
        sentinela = new No();
        sentinela.proximo = sentinela;
        sentinela.anterior = sentinela;
        tamanho = 0;
    }
    
    /**
     * Retorna o tamanho da lista.
     * @return tamanho
     */
    public int tamanho(){
        return this.tamanho;
    }
    
    /**
     * Retorna true casa a lista esteja vazia, e false caso contrario.
     * @return boolean true or false
     */
    public boolean estaVazia(){
        return this.tamanho == 0;
    }
    
    /**
     * Adiciona um item no inicio da lista.
     * @param item 
     */
    public void adicionaNoInicio(Item item){
        No novoNo = new No(item);
        if(estaVazia()){
            novoNo.anterior = this.sentinela;
            novoNo.proximo = this.sentinela;
            this.sentinela.proximo = novoNo;
            this.sentinela.anterior = novoNo;
        }else{
            novoNo.proximo = sentinela.proximo;
            novoNo.anterior = this.sentinela;
            this.sentinela.proximo.anterior = novoNo;
            this.sentinela.proximo = novoNo;
        }
        this.tamanho++;
    }
    
    /**
     * Adiciona um item no fim da lista.
     * @param item 
     */
    public void adicionaNoFim(Item item){
        No x = new No(item);
        if(estaVazia()){
            x.proximo = sentinela;
            x.anterior = sentinela;
            sentinela.anterior = x;
            sentinela.proximo = x;
        }else{
            x.proximo = sentinela;
            x.anterior = sentinela.anterior;
            sentinela.anterior.proximo = x;
            sentinela.anterior = x;
        }
        
        this.tamanho++;
    }
    
    /**
     * Retorna o proximo item do item recebido no parametro.
     * @param item
     * @return item proximo
     */
    public Item getProximo(Item item){
       No x = this.sentinela.proximo;
        for(;x != this.sentinela; x = x.proximo){            
            if(x.item.equals(item)){
                if(x.proximo == sentinela){
                    return sentinela.proximo.item;
                }else{
                    return x.proximo.item;
                }
            }
        }
        return null;
    }
    
    /**
     * Remove o item do paramentro da lista.
     * @param item 
     */
    public void remove(Item item){
        No x = this.sentinela.proximo;
        for(;x != this.sentinela; x = x.proximo){
            if(x.item.equals(item)){
                x.anterior.proximo = x.proximo;
                x.proximo.anterior = x.anterior;
                x = null;
                this.tamanho--;
                break;
            }
        }
    }
    
    /**
     * Retorna o primeiro item da lista.
     * @return item primeiro
     */
    public Item getPrimeiro(){
        if(sentinela.proximo != sentinela){
            return sentinela.proximo.item;
        }
        return null;
    }

    /**
     * Classe que representa um nó que contem o item, o proximo e o anterior No.
     */
    private class No {
        No proximo;
        No anterior;
        Item item;
        private No(Item item) {
            this.item= item;
        }

        private No() {
            this.proximo = null;
            this.anterior = null;
            this.item = null;
        }
    }    
}
