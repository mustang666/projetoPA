package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public class TabelaHashSondagemQuadratica<C,V> extends TabelaHashSondagem<C,V> {

  public TabelaHashSondagemQuadratica(int tamanho, Hashing<C> hashing) {
    super(tamanho, hashing);
  }

  protected int posicaoTabela(C chave) {
    int i = hashing.posicaoTabela(chave, tabela.length), pos = -1,
            inicial = i, inc = 1;
    while (tabela[i] != null && !tabela[i].getChave().equals(chave)) {
      if (!tabela[i].isActivo()) {
        pos = i;
        break;
      }
      i = (i + inc) % tabela.length;
      inc += 2;
      if (i == inicial) {
        throw SONDAGEM_CIRCULAR;
      }
    }
    if (pos != -1) {
      do {
        i = (i + inc) % tabela.length;
        inc += 2;
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
