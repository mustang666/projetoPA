package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public class TabelaHashSondagemLinear<C,V> extends TabelaHashSondagem<C,V> {

  public TabelaHashSondagemLinear(int tamanho, Hashing<C> hashing) {
    super(tamanho, hashing);
  }

  protected int posicaoTabela(C chave) {
    int i = hashing.posicaoTabela(chave, tabela.length), pos = -1,
            inicial = i;
    while (tabela[i] != null && !tabela[i].getChave().equals(chave)) {
      if (!tabela[i].isActivo()) {
        pos = i;
        break;
      }
      i = (i + 1) % tabela.length;
      if (i == inicial) {
        throw SONDAGEM_CIRCULAR;
      }
    }
    if (pos != -1) {
      do {
        i = (i + 1) % tabela.length;
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
