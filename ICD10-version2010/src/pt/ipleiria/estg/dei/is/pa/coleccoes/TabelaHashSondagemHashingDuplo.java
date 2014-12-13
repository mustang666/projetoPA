package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public class TabelaHashSondagemHashingDuplo<C,V>
        extends TabelaHashSondagem<C,V> {

  Hashing<C> hashingIncrem;

  public TabelaHashSondagemHashingDuplo(int tamanho, Hashing<C> hashing,
          Hashing<C> hashingIncrem) {
    super(tamanho, hashing);
    this.hashingIncrem = hashingIncrem;
  }

  protected int posicaoTabela(C chave) {
    int i = hashing.posicaoTabela(chave, tabela.length), pos = -1, inicial = i,
            inc = hashingIncrem.posicaoTabela(chave, tabela.length);
    while (tabela[i] != null && !tabela[i].getChave().equals(chave)) {
      if (!tabela[i].isActivo()) {
        pos = i;
        break;
      }
      i = (i + inc) % tabela.length;
      if (i == inicial) {
        throw SONDAGEM_CIRCULAR;
      }
    }
    if (pos != -1) {
      do {
        i = (i + inc) % tabela.length;
        if (i == inicial) {
          throw SONDAGEM_CIRCULAR;
        }
      } while (tabela[i] != null
              && !tabela[i].getChave().equals(chave));
    }
    if (tabela[i] == null && pos != -1) {
      return pos;
    }
    return i;
  }
}
