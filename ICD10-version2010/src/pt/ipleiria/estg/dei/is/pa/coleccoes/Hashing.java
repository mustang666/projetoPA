package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public interface Hashing<C> {

  //IllegalArgumentException TIPO_INCORRECTO = new IllegalArgumentException();

  int posicaoTabela(C chave, int tamanhoTabela);
}
