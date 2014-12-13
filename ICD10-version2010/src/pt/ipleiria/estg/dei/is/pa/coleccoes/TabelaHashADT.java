package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public interface TabelaHashADT<C, V> {

  /** Insere uma associação (chave, valor) sem duplicação */
  boolean inserir(C chave, V valor);

  /** Remove a associação correspondente à chave */
  boolean remover(C chave);

  /** Devolve o valor correspondente à chave ou null */
  V consultar(C chave);

  /** Verifica se a tabela está sem elementos */
  boolean vazia();

  /** Devolve o número de elementos da tabela */
  int numElementos();

  /** Remove todos os elementos da tabela */
  void removerTodos();

  /** Devolve um iterador das associacões da tabela */
  Iterador iterador();

  /** Devolve um iterador das chaves da tabela */
  Iterador iteradorChaves();

  /** Devolve um iterador dos valores da tabela */
  Iterador iteradorValores();
}
