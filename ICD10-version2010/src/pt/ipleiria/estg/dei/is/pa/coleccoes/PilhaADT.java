package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public interface PilhaADT<T>{
    
  /** Coloca um objecto no topo da pilha.
      @param o objecto a colocar no topo */
  public void push(T o);

  /** Retira o objecto que está no topo da pilha e devolve-o.
      @return o objecto retirado do topo da pilha
              ou null se estiver vazia */
  public T pop();

  /** Devolve objecto que está no topo da pilha sem o retirar.
      @return o objecto que está no topo da pilha
              ou null se estiver vazia */
  public T top();

  /** Verifica se a pilha está vazia.
      @return true caso a pilha se encontre vazia; false caso contrário */
  public boolean isEmpty();
}
