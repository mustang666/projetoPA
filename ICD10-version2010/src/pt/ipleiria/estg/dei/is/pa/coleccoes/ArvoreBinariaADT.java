package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public interface ArvoreBinariaADT<T> extends java.io.Serializable {

  public static final java.util.NoSuchElementException ELEMENTO_INVALIDO = new java.util.NoSuchElementException("Elemento Inexistente");

  /**	Insere um elemento na árvore.
  @param e elemento a inserir */
  public void inserir(T e);

  /**	Remove o elemento passado por parâmetro.
  @param e elemento a remover
  @return true se o elemento foi removido; false caso contrário */
  public boolean remover(T e);

  /**     Localiza o elemento passado por parâmetro.
  @param e elemento a localizar
  @return iterador de árvore com os elementos de = critério */
  public Iterador<T> consultar(T e);

  /**     Verifica se a árvore binária está vazia */
  boolean vazia();

  /**     Calcula o número de elementos de uma árvore binária */
  int numElementos();

  /**     Remove todos os elementos de uma árvore binária */
  void removerTodos();

  /**	Constroi o iterador em pre-ordem.
  @return o iterador em pre-ordem */
  public Iterador<T> iteradorPreOrdem();

  /**	Constroi o iterador em-ordem.
  @return o iterador em ordem */
  public Iterador<T> iteradorEmOrdem();

  /**	Constroi o iterador pos-ordem.
  @return o iterador em pos-ordem */
  public Iterador<T> iteradorPosOrdem();

  /**	Constroi o iterador por níveis.
  @return o iterador por níveis */
  public Iterador<T> iteradorPorNiveis();
}
