package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public interface ListaDuplaADT<T>{

  void inserir(T elem);

  boolean remover(T elem);

  boolean vazia();

  Iterador consultar(T elem);

  T consultar(int indice);

  int numElementos();

  IteradorListaDupla iteradorLista();

}
