package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public class ListaSimplesCircularBaseLimiteDistinto<T> implements java.io.Serializable {

  protected ComparacaoLimite<T> criterio;
  protected NoListaSimplesCircularBaseLimiteDistinto<T> noInicial;

  public ListaSimplesCircularBaseLimiteDistinto(ComparacaoLimite<T> criterio) {
    this.criterio = criterio;
    noInicial = new NoListaSimplesCircularBaseLimiteDistinto<T>(criterio.limite());
  }

  // Não permite inserção de 2 elementos iguais
  public boolean inserir(T elem) {
    NoListaSimplesCircularBaseLimiteDistinto<T> ant = pesquisar(elem);
    if (criterio.comparar(elem, ant.seguinte.elemento) != 0) {
      ant.seguinte = new NoListaSimplesCircularBaseLimiteDistinto<T>(elem, ant.seguinte);
      return true;
    }
    return false;
  }

  public boolean remover(T elem) {
    NoListaSimplesCircularBaseLimiteDistinto<T> ant = pesquisar(elem);
    if (criterio.comparar(elem, ant.seguinte.elemento) == 0) {
      ant.seguinte = ant.seguinte.seguinte;
      return true;
    }
    return false;
  }

  public T consultar(T elem) {
    NoListaSimplesCircularBaseLimiteDistinto<T> ant = pesquisar(elem);
    if (criterio.comparar(elem, ant.seguinte.elemento) == 0) {
      return ant.seguinte.elemento;
    }
    return null;
  }

  public int numElementos() {
    int i = 0;
    NoListaSimplesCircularBaseLimiteDistinto<T> aux = noInicial.seguinte;
    while (aux != noInicial) {
      i++;
      aux = aux.seguinte;
    }
    return i;
  }

  private NoListaSimplesCircularBaseLimiteDistinto<T> pesquisar(T elem) {
    NoListaSimplesCircularBaseLimiteDistinto<T> ant = noInicial;
    while (criterio.comparar(elem, ant.seguinte.elemento) > 0) {
      ant = ant.seguinte;
    }
    return ant;
  }

  public void removerTodos() {
    if (noInicial.seguinte != noInicial) {
      NoListaSimplesCircularBaseLimiteDistinto<T> p = noInicial.seguinte,
              aux;
      do {
        aux = p;
        p = p.seguinte;
        aux.elemento = null;
        aux.seguinte = null;
      } while (p != noInicial);
      noInicial.seguinte = noInicial;
    }
  }

  public boolean vazia() {
    return noInicial.seguinte == noInicial;
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("Lista SCBLim = {\n");
    NoListaSimplesCircularBaseLimiteDistinto<T> aux = noInicial.seguinte;
    while (aux != noInicial) {
      s.append(aux.elemento).append("\n");
      aux = aux.seguinte;
    }
    s.append("}\n");
    return s.toString();
  }
}

class NoListaSimplesCircularBaseLimiteDistinto<T> implements java.io.Serializable {

  T elemento;
  NoListaSimplesCircularBaseLimiteDistinto<T> seguinte;

  NoListaSimplesCircularBaseLimiteDistinto(T limite) { // Criação do nó base limite
    elemento = limite;
    seguinte = this;
  }

  NoListaSimplesCircularBaseLimiteDistinto(T elem,
          NoListaSimplesCircularBaseLimiteDistinto<T> seg) {
    elemento = elem;
    seguinte = seg;
  }

  protected void finalize() {
    elemento = null;
    seguinte = null;
  }
}

class ComparacaoLimiteAssociacao<C, V> implements ComparacaoLimite<Associacao<C, V>> {

  private ComparacaoLimite<C> criterioChave;
  static private Associacao LIMITE;

  public ComparacaoLimiteAssociacao(ComparacaoLimite<C> criterioChave) {
    this.criterioChave = criterioChave;
    LIMITE = new Associacao<C,V>(criterioChave.limite(), null);
  }

  public int comparar(Associacao<C, V> o1, Associacao<C, V> o2) {
    return criterioChave.comparar(o1.getChave(), o2.getChave());
  }

  public Associacao limite() {
    return LIMITE;
  }
}