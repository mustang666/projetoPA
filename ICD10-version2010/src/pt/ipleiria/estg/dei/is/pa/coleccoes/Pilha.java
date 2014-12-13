package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public class Pilha<T> implements PilhaADT<T> {

  private NoPilha<T> lifo;

  public Pilha() {
    lifo = null;
  }

  public void push(T o) {
    lifo = new NoPilha<T>(o, lifo);
  }

  public T pop() {
    if (isEmpty()) {
      return null;
    }
    T aux = lifo.object;
    NoPilha<T> p = lifo;
    lifo = lifo.next;
    p.next = null;
    p.object = null;
    return aux;
  }

  public T top() {
    if (isEmpty()) {
      return null;
    }
    return lifo.object;
  }

  public boolean isEmpty() {
    return lifo == null;
  }

  public void clear() {
    lifo = null;
  }

  /** Lista todos os elementos da pilha desde o seu topo. */
  public String toString() {
    StringBuilder lista = new StringBuilder();
    lista.append("Pilha = {");
    NoPilha<T> aux = lifo;
    while (aux != null) {
      lista.append(aux.object);
      aux = aux.next;
      if (aux != null) {
        lista.append(", ");
      }
    }
    lista.append("}\n");
    return lista.toString();
  }
}

class NoPilha<T> implements java.io.Serializable {

  T object;
  NoPilha<T> next;

  /** Cria um nó com o objecto e o nó seguinte */
  public NoPilha(T object, NoPilha<T> next) {
    this.object = object;
    this.next = next;
  }
}
