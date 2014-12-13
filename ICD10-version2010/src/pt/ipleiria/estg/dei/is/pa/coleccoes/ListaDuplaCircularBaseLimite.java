package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public class ListaDuplaCircularBaseLimite<T> implements
        ListaDuplaADT<T>, java.io.Serializable {

    private ComparacaoLimite criterio;
    private NoListaDuplaCircularBaseLimite<T> noInicial;

    public ListaDuplaCircularBaseLimite(ComparacaoLimite<T> criterio) {
        this.criterio = criterio;
        noInicial = new NoListaDuplaCircularBaseLimite<T>(criterio.limite());
    }

    public void inserir(T elem) {
        new NoListaDuplaCircularBaseLimite<T>(elem, pesquisar(elem));
    }

    /** Remove após consultar */
    public boolean remover(T elem) {
        NoListaDuplaCircularBaseLimite<T> aux = pesquisar(elem);
        while (criterio.comparar(elem, aux.elemento) == 0
                && !aux.elemento.equals(elem)) {
            aux = aux.seguinte;
        }
        if (!aux.elemento.equals(elem)) {
            return false;
        }
        aux.anterior.seguinte = aux.seguinte;
        aux.seguinte.anterior = aux.anterior;
        return true;
    }

    public boolean vazia() {
        return noInicial.seguinte == noInicial;
    }

    public T consultar(int indice) {
        NoListaDuplaCircularBaseLimite<T> aux = noInicial.seguinte;
        while (aux != noInicial) {
            if (indice-- == 0) {
                return aux.elemento;
            }
            aux = aux.seguinte;
        }
        return null;
    }

    public Iterador<T> consultar(T elem) {
        NoListaDuplaCircularBaseLimite<T> aux = pesquisar(elem);
        ListaDuplaCircularBaseLimite<T> lc =
                new ListaDuplaCircularBaseLimite<T>(criterio);
        while (criterio.comparar(elem, aux.elemento) == 0) {
            lc.inserir(aux.elemento);
            aux = aux.seguinte;
        }
        return lc.iteradorLista();
    }

    public int numElementos() {
        int i = 0;
        NoListaDuplaCircularBaseLimite<T> aux = noInicial.seguinte;
        while (aux != noInicial) {
            i++;
            aux = aux.seguinte;
        }
        return i;
    }

    private NoListaDuplaCircularBaseLimite<T> pesquisar(T elem) {
        NoListaDuplaCircularBaseLimite<T> aux = noInicial.seguinte;
        while (criterio.comparar(elem, aux.elemento) > 0) {
            aux = aux.seguinte;
        }
        return aux;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Lista DCBLim = {\n");
        NoListaDuplaCircularBaseLimite<T> aux = noInicial.seguinte;
        while (aux != noInicial) {
            s.append(aux.elemento).append("\n");
            aux = aux.seguinte;
        }
        s.append("}\n");
        return s.toString();
    }

    public IteradorListaDupla<T> iteradorLista() {
        return new IteradorListaDuplaCircularBaseLimite<T>(noInicial);
    }
}

class NoListaDuplaCircularBaseLimite<T> implements java.io.Serializable {

    T elemento;
    NoListaDuplaCircularBaseLimite<T> anterior, seguinte;

    NoListaDuplaCircularBaseLimite(T limite) {  // Criação do nó base
        elemento = limite;
        anterior = this;
        seguinte = this;
    }

    NoListaDuplaCircularBaseLimite(T elem,
            NoListaDuplaCircularBaseLimite<T> seg) {
        elemento = elem;
        anterior = seg.anterior;
        seguinte = seg;
        seg.anterior = this;
        anterior.seguinte = this;
    }

    protected void finalize() {
        elemento = null;
        anterior = null;
        seguinte = null;
    }
}

class IteradorListaDuplaCircularBaseLimite<T> implements IteradorListaDupla<T> {

    private NoListaDuplaCircularBaseLimite<T> noInicial, corrente;

    IteradorListaDuplaCircularBaseLimite(
            NoListaDuplaCircularBaseLimite<T> noInicial) {
        this.noInicial = corrente = noInicial;
    }

    public boolean podeAvancar() {
        return corrente.seguinte != noInicial;
    }

    public boolean podeRecuar() {
        return corrente.anterior != noInicial;
    }

    public T avancar() {
        if (podeAvancar()) {
            corrente = corrente.seguinte;
            return corrente.elemento;
        }
        throw ELEMENTO_INVALIDO;
    }

    public T recuar() {
        if (podeRecuar()) {
            corrente = corrente.anterior;
            return corrente.elemento;
        }
        throw ELEMENTO_INVALIDO;
    }

    public T corrente() {
        if (corrente == noInicial) {
            throw ELEMENTO_INVALIDO;
        }
        return corrente.elemento;
    }

    public void reiniciar() {
        corrente = noInicial;
    }
}
