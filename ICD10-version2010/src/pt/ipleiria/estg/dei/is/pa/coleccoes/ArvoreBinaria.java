package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public class ArvoreBinaria<T> implements ArvoreBinariaADT<T> {

  private NoAB<T> raiz;
  private Comparacao<T> criterio;
  private int numElementos;

  public ArvoreBinaria(Comparacao<T> comp) {
    raiz = null;
    criterio = comp;
    numElementos = 0;
  }

  private NoAB<T> inserirRecursivo(NoAB<T> no, T e) {
    if (no == null) // Não existe raiz
    {
      return new NoAB<T>(e);
    }
    if (criterio.comparar(e, no.elem) < 0) {
      no.esq = inserirRecursivo(no.esq, e);
    } else {
      no.dir = inserirRecursivo(no.dir, e);
    }
    return no;
  }

  // Versão iterativa
  public void inserir(T e) {
    numElementos++;
    if (raiz == null) //  Não existe raiz
    {
      raiz = new NoAB<T>(e);
    } else {
      NoAB<T> previo;
      NoAB<T> aux = raiz;
      int c;
      // Procura local de inserção
      do {
        previo = aux;
        c = criterio.comparar(e, aux.elem);
        if (criterio.comparar(e, aux.elem) < 0) {
          aux = aux.esq;
        } else {
          aux = aux.dir;
        }
      } while (aux != null);
      if (c < 0) {
        previo.esq = new NoAB<T>(e);
      } else {
        previo.dir = new NoAB<T>(e);
      }
    }
  }

  private void consultarRecursivoAuxiliar(NoAB<T> no, T e,
          ArvoreBinaria<T> a) {
    if (no != null) {
      int c = criterio.comparar(e, no.elem);
      if (c < 0) {
        consultarRecursivoAuxiliar(no.esq, e, a);
      } else if (c > 0) {
        consultarRecursivoAuxiliar(no.dir, e, a);
      } else {
        a.inserir(no.elem);
        consultarRecursivoAuxiliar(no.dir, e, a);
      }
    }
  }

  private Iterador<T> consultarRecursivo(T e) {
    ArvoreBinaria<T> a = new ArvoreBinaria<T>(criterio);
    consultarRecursivoAuxiliar(raiz, e, a);
    return a.iteradorEmOrdem();
  }

  // Versão iterativa
  public Iterador<T> consultar(T e) {
    ArvoreBinaria<T> a = new ArvoreBinaria<T>(criterio);
    NoAB<T> no = pesquisar(raiz, e);
    while (no != null) {
      a.inserir(no.elem);
      no = pesquisar(no.dir, e);
    }
    return a.iteradorEmOrdem();
  }

  public boolean remover(T e) {
    if (raiz == null) {
      return false;
    }
    try {
      raiz = removerIterativo(raiz, e);
      // raiz = removerRecursivo(raiz, e);
      numElementos--;
      return true;
    } catch (java.util.NoSuchElementException ex) {
      return false;
    }
  }

  private NoAB<T> removerRecursivo(NoAB<T> no, T e) {
    if (no == null) {
      throw ELEMENTO_INVALIDO;
    }
    int c = criterio.comparar(e, no.elem);
    if (c < 0) {
      no.esq = removerRecursivo(no.esq, e);
    } else if (c > 0) {
      no.dir = removerRecursivo(no.dir, e);
    } else {
      // Se o elemento do nó não for igual a e
      // procura-o na sub-árvore direita
      if (!no.elem.equals(e)) {
        no.dir = removerRecursivo(no.dir, e);
      } else // Elemento do nó é igual a e
      // Elemento do nó é igual a e
      if (no.esq == null) {
        if (no.dir == null) // Nó sem filhos
        {
          return null;
        }
        // Nó só com filho direito
        return no.dir;
      } else {
        if (no.dir == null) // Nó só com filho esquerdo
        {
          return no.esq;
        }
        // Nó com dois filhos
        no.elem = pesquisarMin(no.dir).elem;
        no.dir =
                removerRecursivo(no.dir, no.elem);
      }
    }
    return no;
  }

  private NoAB<T> removerIterativo(NoAB<T> raiz, T e) {
    if (raiz == null) {
      throw ELEMENTO_INVALIDO;
    }
    int c = criterio.comparar(e, raiz.elem);
    NoAB<T> previo;
    if (c == 0) {
      if (raiz.elem.equals(e)) // O elemento a remover está na raiz
      {
        return removerAux(raiz);
      }
      previo = pesquisarPrevio(raiz.dir, e);
    } else {
      previo = pesquisarPrevio(raiz, e);
    }
    if (previo == null) {
      throw ELEMENTO_INVALIDO;
    }
    c = criterio.comparar(e, previo.elem);
    if (c < 0) {
      previo.esq = removerAux(previo.esq);
    } else {
      previo.dir = removerAux(previo.dir);
    }
    return raiz;
  }

  private NoAB<T> removerAux(NoAB<T> aRemover) {
    if (aRemover.esq == null) {
      if (aRemover.dir == null) {
        return null;
      }
      return aRemover.dir;
    }
    if (aRemover.dir == null) {
      return aRemover.esq;
    }
    NoAB<T> minDireita = pesquisarMin(aRemover.dir);
    aRemover.elem = minDireita.elem;
    aRemover.dir = removerIterativo(aRemover.dir, minDireita.elem);
    return aRemover;
  }

  // Devolve o nó prévio do elemento completo e
  private NoAB<T> pesquisarPrevio(NoAB<T> aux, T e) {
    if (aux == null) {
      return null;
    }
    NoAB previo = null;
    int c;
    do {
      c = criterio.comparar(e, aux.elem);
      if (c == 0) {
        if (aux.elem.equals(e)) {
          return previo;
        }
        previo = aux;
        aux = aux.dir;
      } else {
        previo = aux;
        if (c < 0) {
          aux = aux.esq;
        } else {
          aux = aux.dir;
        }
      }
    } while (aux != null);
    return null;
  }

  public T consultarMin() {
    NoAB<T> no = pesquisarMin(raiz);
    return (no != null) ? no.elem : null;
  }

  public T consultarMax() {
    NoAB<T> no = pesquisarMax(raiz);
    return (no != null) ? no.elem : null;
  }

  public boolean vazia() {
    return raiz == null;
  }

  public int numElementos() {
    return numElementos;
  }

  public void removerTodos() {
    if (raiz == null) {
      return;
    }
    Pilha<NoAB<T>> pilha = new Pilha<NoAB<T>>();
    NoAB<T> no = raiz, aux;
    do {
      if (no == null) {
        if (pilha.isEmpty()) {
          return;
        }
        no = pilha.pop();
      }
      aux = null;
      if (no.esq != null) {
        aux = no.esq;
      }
      if (no.dir != null) {
        if (aux != null) {
          pilha.push(aux);
        }
        aux = no.dir;
      }
      no.esq = no.dir = null;
      no = aux;
    } while (true);
  }

  public Iterador<T> iteradorPreOrdem() {
    return new IteradorPreOrdem<T>(raiz);
  }

  public Iterador<T> iteradorEmOrdem() {
    return new IteradorEmOrdem<T>(raiz);
  }

  public Iterador<T> iteradorPosOrdem() {
    return new IteradorPosOrdem<T>(raiz);
  }

  public Iterador<T> iteradorPorNiveis() {
    return new IteradorPorNiveis<T>(raiz);
  }

  private NoAB<T> pesquisar(NoAB<T> no, T e) {
    if (no == null) {
      return null;
    }
    int c;
    do {
      c = criterio.comparar(e, no.elem);
      if (c < 0) {
        no = no.esq;
      } else if (c > 0) {
        no = no.dir;
      } else {
        return no;
      }
    } while (no != null);
    return null;
  }

  private NoAB<T> pesquisarMin(NoAB<T> noInicial) {
    if (noInicial == null) {
      return null;
    }
    NoAB<T> aux = noInicial;
    while (aux.esq != null) {
      aux = aux.esq;
    }
    return aux;
  }

  private NoAB<T> pesquisarMax(NoAB<T> noInicial) {
    if (noInicial == null) {
      return null;
    }
    NoAB<T> aux = noInicial;
    while (aux.dir != null) {
      aux = aux.dir;
    }
    return aux;
  }
}

class IteradorPreOrdem<T> implements Iterador<T> {

  Pilha<NoAB<T>> pilha;
  NoAB<T> raiz, actual;

  IteradorPreOrdem(NoAB<T> raiz) {
    this.raiz = raiz;
    pilha = new Pilha<NoAB<T>>();
    reiniciar();
  }

  public void reiniciar() {
    actual = null;
    if (raiz != null) {
      pilha.clear();
      pilha.push(raiz);
    }
  }

  public boolean podeAvancar() {
    return !pilha.isEmpty();
  }

  public T avancar() {
    if (!podeAvancar()) {
      throw ELEMENTO_INVALIDO;
    }
    actual = pilha.pop();
    if (actual.dir != null) {
      pilha.push(actual.dir);
    }
    if (actual.esq != null) {
      pilha.push(actual.esq);
    }
    return actual.elem;
  }

  public T corrente() {
    if (actual == null) {
      throw ELEMENTO_INVALIDO;
    }
    return actual.elem;
  }
}

class IteradorEmOrdem<T> implements Iterador<T> {

  Pilha<NoAB<T>> pilha;
  NoAB<T> raiz, actual;

  IteradorEmOrdem(NoAB<T> raiz) {
    pilha = new Pilha<NoAB<T>>();
    this.raiz = raiz;
    reiniciar();
  }

  public void reiniciar() {
    actual = null;
    if (raiz != null) {
      pilha.clear();
      pushEsquerda(raiz);
    }
  }

  public boolean podeAvancar() {
    return !pilha.isEmpty();
  }

  public T avancar() {
    if (!podeAvancar()) {
      throw ELEMENTO_INVALIDO;
    }
    actual = pilha.pop();
    if (actual.dir != null) {
      pushEsquerda(actual.dir);
    }
    return actual.elem;
  }

  public T corrente() {
    if (actual == null) {
      throw ELEMENTO_INVALIDO;
    }
    return actual.elem;
  }

  private void pushEsquerda(NoAB<T> no) {
    do {
      pilha.push(no);
      no = no.esq;
    } while (no != null);
  }
}

class IteradorPosOrdem<T> implements Iterador<T> {

  Pilha<NoAB<T>> pilha;
  NoAB<T> raiz, actual;

  IteradorPosOrdem(NoAB<T> raiz) {
    pilha = new Pilha<NoAB<T>>();
    this.raiz = raiz;
    reiniciar();
  }

  public void reiniciar() {
    actual = null;
    if (raiz != null) {
      pilha.clear();
      pushEsquerdaDireita(raiz);
    }
  }

  public boolean podeAvancar() {
    return !pilha.isEmpty();
  }

  public T avancar() {
    if (!podeAvancar()) {
      throw ELEMENTO_INVALIDO;
    }
    actual = pilha.pop();
    if (!pilha.isEmpty()) {
      NoAB<T> top = pilha.top();
      // Se acabei de tirar o nó da esquerda põe o da direita
      if (top.esq == actual && top.dir != null) {
        pushEsquerdaDireita(top.dir);
      }
    }
    return actual.elem;
  }

  public T corrente() {
    if (actual == null) {
      throw ELEMENTO_INVALIDO;
    }
    return actual.elem;
  }

  private void pushEsquerdaDireita(NoAB<T> no) {
    do {
      pilha.push(no);
      no = (no.esq != null) ? no.esq : no.dir;
    } while (no != null);
  }
}

class IteradorPorNiveis<T> implements Iterador<T> {

  Fila<NoAB<T>> aVisitar;
  NoAB<T> raiz, actual;

  IteradorPorNiveis(NoAB<T> raiz) {
    aVisitar = new Fila<NoAB<T>>();
    this.raiz = raiz;
    reiniciar();
  }

  public void reiniciar() {
    actual = null;
    if (raiz != null) {
      aVisitar = new Fila<NoAB<T>>();
      aVisitar.inserir(raiz);
    }
  }

  public boolean podeAvancar() {
    return !aVisitar.vazia();
  }

  public T avancar() {
    if (!podeAvancar()) {
      throw ELEMENTO_INVALIDO;
    }
    actual = aVisitar.remover();
    if (actual.esq != null) {
      aVisitar.inserir(actual.esq);
    }
    if (actual.dir != null) {
      aVisitar.inserir(actual.dir);
    }
    return actual.elem;
  }

  public T corrente() {
    if (actual == null) {
      throw ELEMENTO_INVALIDO;
    }
    return actual.elem;
  }
}


class NoAB<T> implements java.io.Serializable {

  T elem;
  NoAB<T> esq, dir;

  NoAB(T e, NoAB<T> esq, NoAB<T> dir) {
    elem = e;
    this.esq = esq;
    this.dir = dir;
  }

  NoAB(T e) {
    this(e, null, null);
  }

  protected void finalize() {
    elem = null;
    esq = dir = null;
  }
}
