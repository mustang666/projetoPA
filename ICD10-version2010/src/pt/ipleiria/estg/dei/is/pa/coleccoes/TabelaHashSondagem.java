package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
abstract public class TabelaHashSondagem<C,V> implements TabelaHashADT<C,V>, java.io.Serializable {

  RuntimeException SONDAGEM_CIRCULAR =
          new RuntimeException("Sondagem circular de toda a tabela");
  protected Entrada<C,V>[] tabela;
  protected Hashing<C> hashing;
  protected int numElementos, inactivos;

  public TabelaHashSondagem(int tamanho, Hashing<C> hashing) {
    this.hashing = hashing;
    tabela = new Entrada[proximoPrimo(tamanho)];
    numElementos = inactivos = 0;
  }

  public boolean inserir(C chave, V valor) {
    int i = posicaoTabela(chave);
    if (tabela[i] != null) {
      if (tabela[i].getChave().equals(chave) && tabela[i].isActivo()) {
        return false;
      }
      inactivos--;
    }
    tabela[i] = new Entrada<C,V>(chave, valor);
    numElementos++;
    if (factorCarga() >= 0.5) {
      reHash();
    }
    return true;
  }

  public boolean remover(C chave) {
    int i = posicaoTabela(chave);
    if (tabela[i] != null && tabela[i].getChave().equals(chave)
            && tabela[i].isActivo()) {
      tabela[i].setActivo(false);
      inactivos++;
      numElementos--;
      return true;
    }
    return false;
  }

  public boolean vazia() {
    return numElementos == 0;
  }

  public V consultar(C chave) {
    int i = posicaoTabela(chave);
    if (tabela[i] != null && tabela[i].getChave().equals(chave)
            && tabela[i].isActivo()) {
      return tabela[i].getValor();
    }
    return null;
  }

  public int numElementos() {
    return numElementos;
  }

  public void removerTodos() {
    for (int i = 0; i < tabela.length; i++) {
      tabela[i] = null;
    }
    numElementos = inactivos = 0;
  }

  public Iterador iterador() {
    return new IteradorTabelaHashSondagem(this);
  }

  public Iterador iteradorChaves() {
    return new IteradorChavesTabelaHashSondagem(this);
  }

  public Iterador iteradorValores() {
    return new IteradorValoresTabelaHashSondagem(this);
  }

  // devolve a primeira posição livre ou a posição da chave
  protected abstract int posicaoTabela(C chave);

  private float factorCarga() {
    return (numElementos + inactivos) / (float) tabela.length;
  }

  private void reHash() {
    int tam = proximoPrimo(tabela.length * 2);
    Entrada<C,V>[] tabelaAntiga = tabela;
    tabela = new Entrada[tam];
    numElementos = inactivos = 0;
    for (int i = 0; i < tabelaAntiga.length; i++) {
      if (tabelaAntiga[i] != null && tabelaAntiga[i].isActivo()) {
        inserir(tabelaAntiga[i].getChave(),
                tabelaAntiga[i].getValor());
      }
    }
  }

  public static int proximoPrimo(int n) {
    if (n < 2) {
      return 2;
    }
    if (n % 2 == 0) {
      ++n;
    }
    for (int i;; n += 2) {
      for (i = 3; i * i <= n && n % i != 0; i += 2);
      if (i * i > n) {
        return n;
      }
    }
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("Tabela de tamanho ");
    s.append(tabela.length);
    s.append(" = {\n");
    for (int i = 0; i < tabela.length; i++) {
      if (tabela[i] != null) {
        s.append("Tabela[" + i + "]==> ");
        s.append(tabela[i]);
        s.append("\n");
      }
    }
    s.append("}\n");
    return s.toString();
  }
}

class Entrada<C,V> implements java.io.Serializable {

  private Associacao<C,V> associacao;
  private boolean activo;

  public Entrada(C chave, V valor) {
    associacao = new Associacao<C,V>(chave, valor);
    activo = true;
  }

  public C getChave() {
    return associacao.getChave();
  }

  public V getValor() {
    return associacao.getValor();
  }

  public Associacao<C,V> getAssociacao() {
    return associacao;
  }

  public void setValor(V valor) {
    associacao.setValor(valor);
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean estado) {
    activo = estado;
  }

  public String toString() {
    return "Chave: " + getChave() + " Valor: " + getValor()
            + (isActivo() ? " - Activo" : " - Inactivo");
  }
}

/* Esta implementação dos iteradores não está
 * implementada utilizando genéricos.
 * Esta situação será revista no futuro.
 */
class IteradorTabelaHashSondagem implements Iterador {

  protected TabelaHashSondagem tabelaHashSondagem;
  protected int corrente, proximo;

  IteradorTabelaHashSondagem(TabelaHashSondagem tabelaHashSondagem) {
    this.tabelaHashSondagem = tabelaHashSondagem;
    reiniciar();
  }

  public boolean podeAvancar() {
    return proximo != tabelaHashSondagem.tabela.length;
  }

  public Object avancar() {
    if (podeAvancar()) {
      corrente = proximo;
      proximo = proximoActivo();
      return tabelaHashSondagem.tabela[corrente].getAssociacao();
    }
    throw ELEMENTO_INVALIDO;
  }

  public Object corrente() {
    if (corrente == tabelaHashSondagem.tabela.length) {
      throw ELEMENTO_INVALIDO;
    }
    return tabelaHashSondagem.tabela[corrente].getAssociacao();
  }

  public void reiniciar() {
    if (tabelaHashSondagem.vazia()) {
      corrente = proximo = tabelaHashSondagem.tabela.length;
    } else {
      corrente = tabelaHashSondagem.tabela.length;
      proximo = -1;
      proximo = proximoActivo();
    }
  }

  private int proximoActivo() {
    if (proximo == tabelaHashSondagem.tabela.length) {
      return proximo;
    }
    while (++proximo != tabelaHashSondagem.tabela.length
            && (tabelaHashSondagem.tabela[proximo] == null
            || !tabelaHashSondagem.tabela[proximo].isActivo()));
    return proximo;
  }
}

class IteradorChavesTabelaHashSondagem
        extends IteradorTabelaHashSondagem {

  IteradorChavesTabelaHashSondagem(TabelaHashSondagem tabelaHashSondagem) {
    super(tabelaHashSondagem);
  }

  public Object avancar() {
    return ((Associacao) super.avancar()).getChave();
  }

  public Object corrente() {
    return ((Associacao) super.corrente()).getChave();
  }
}

class IteradorValoresTabelaHashSondagem
        extends IteradorTabelaHashSondagem {

  IteradorValoresTabelaHashSondagem(TabelaHashSondagem tabelaHashSondagem) {
    super(tabelaHashSondagem);
  }

  public Object avancar() {
    return ((Associacao) super.avancar()).getValor();
  }

  public Object corrente() {
    return ((Associacao) super.corrente()).getValor();
  }
}
