package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public interface IteradorListaDupla<T> extends Iterador<T>{

  /** Verifica se pode recuar para o elemento anterior.
      @return true caso ainda existam elementos a percorrer;
      false caso contrário */
  boolean podeRecuar();

  /** Devolve o elemento anterior.
      Caso não exista lança a excepção ELEMENTO_INVALIDO
      @return o elemento anterior */
  T recuar();
}
