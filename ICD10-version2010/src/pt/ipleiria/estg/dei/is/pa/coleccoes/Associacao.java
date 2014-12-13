package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public class Associacao<C, V> implements java.io.Serializable {

  private C chave;
  private V valor;

  public Associacao(C chave, V valor) {
    this.chave = chave;
    this.valor = valor;
  }

  public C getChave() {
    return chave;
  }

  public V getValor() {
    return valor;
  }

  public void setValor(V valor) {
    this.valor = valor;
  }

  public String toString() {
    return "Chave: " + chave + " Valor: " + valor;
  }
}
