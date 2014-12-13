package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public class TabelaHashListas<C, V> implements TabelaHashADT<C, V>, java.io.Serializable {

  protected ListaSimplesCircularBaseLimiteDistinto<Associacao<C, V>>[] tabela;
  protected Hashing hashing;
  protected int numElementos;

  public TabelaHashListas(int tamanho, Hashing hashing,
          ComparacaoLimite criterioChave) {
    this.hashing = hashing;
    tabela = new ListaSimplesCircularBaseLimiteDistinto[tamanho];
    ComparacaoLimite cia =
            new ComparacaoLimiteAssociacao(criterioChave);
    for (int i = 0; i < tabela.length; i++) {
      tabela[i] = new ListaSimplesCircularBaseLimiteDistinto<Associacao<C, V>>(cia);
    }
    numElementos = 0;
  }

  public boolean inserir(C chave, V valor) {
    if (tabela[posicaoTabela(chave)].inserir(new Associacao<C, V>(chave, valor))) {
      numElementos++;
      return true;
    }
    return false;
  }

  public boolean remover(C chave) {
    if (tabela[posicaoTabela(chave)].remover(new Associacao(chave, null))) {
      numElementos--;
      return true;
    }
    return false;
  }

  public boolean vazia() {
    return numElementos == 0;
  }

  // Devolve o valor correspondente à chave
  public V consultar(C chave) {
    Associacao<C, V> a = (Associacao) tabela[posicaoTabela(chave)].consultar(new Associacao(chave, null));
    return a == null ? null : a.getValor();
  }

  public int numElementos() {
    return numElementos;
  }

  public Iterador iterador() {
    return new IteradorTabelaHashListas(this);
  }

  public Iterador iteradorChaves() {
    return new IteradorChavesTabelaHashListas(this);
  }

  public Iterador iteradorValores() {
    return new IteradorValoresTabelaHashListas(this);
  }

  private int posicaoTabela(C chave) {
    return hashing.posicaoTabela(chave, tabela.length);
  }

  public void removerTodos() {
    for (int i = 0; i < tabela.length; i++) {
      tabela[i].removerTodos();
    }
    numElementos = 0;
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("Tabela = {\n");
    for (int i = 0; i < tabela.length; i++) {
      if (!tabela[i].vazia()) {
        s.append("Tabela[" + i + "]==> ");
        s.append(tabela[i]);
      }
    }
    s.append("}\n");
    return s.toString();
  }
}

/* Esta implementação dos iteradores não está
 * implementada utilizando genéricos.
 * Esta situação será revista no futuro.
 */
class IteradorTabelaHashListas implements Iterador {

  protected int indiceActual;
  protected TabelaHashListas tabelaHashListas;
  protected NoListaSimplesCircularBaseLimiteDistinto corrente, proximo;

  IteradorTabelaHashListas(TabelaHashListas tabelaHashListas) {
    this.tabelaHashListas = tabelaHashListas;
    reiniciar();
  }

  public boolean podeAvancar() {
    return proximo != null;
  }

  public Object avancar() {
    if (podeAvancar()) {
      corrente = proximo;
      proximo = proximoNoOcupado();
      return corrente.elemento;
    }
    throw ELEMENTO_INVALIDO;
  }

  public Object corrente() {
    if (corrente == null) {
      throw ELEMENTO_INVALIDO;
    }
    return corrente.elemento;
  }

  public void reiniciar() {
    if (tabelaHashListas.vazia()) {
      indiceActual = tabelaHashListas.tabela.length;
      corrente = proximo = null;
    } else {
      indiceActual = 0;
      corrente = null;
      proximo = tabelaHashListas.tabela[0].noInicial;
      proximo = proximoNoOcupado();
    }
  }

  private NoListaSimplesCircularBaseLimiteDistinto proximoNoOcupado() {
    if (indiceActual == tabelaHashListas.tabela.length) {
      return null;
    }
    if (proximo.seguinte
            != tabelaHashListas.tabela[indiceActual].noInicial) {
      return proximo.seguinte;
    }
    while (++indiceActual != tabelaHashListas.tabela.length
            && tabelaHashListas.tabela[indiceActual].noInicial.seguinte
            == tabelaHashListas.tabela[indiceActual].noInicial);
    if (indiceActual == tabelaHashListas.tabela.length) {
      return null;
    }
    return tabelaHashListas.tabela[indiceActual].noInicial.seguinte;
  }
}

class IteradorChavesTabelaHashListas extends IteradorTabelaHashListas {

  IteradorChavesTabelaHashListas(TabelaHashListas tabelaHashListas) {
    super(tabelaHashListas);
  }

  public Object avancar() {
    return ((Associacao) super.avancar()).getChave();
  }

  public Object corrente() {
    return ((Associacao) super.corrente()).getChave();
  }
}

class IteradorValoresTabelaHashListas extends IteradorTabelaHashListas {

  IteradorValoresTabelaHashListas(TabelaHashListas tabelaHashListas) {
    super(tabelaHashListas);
  }

  public Object avancar() {
    return ((Associacao) super.avancar()).getValor();
  }

  public Object corrente() {
    return ((Associacao) super.corrente()).getValor();
  }
}
