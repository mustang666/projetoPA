package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public class ListaSimplesCircularBaseLimite<T>implements ListaSimplesADT2<T>, java.io.Serializable {
    private ComparacaoLimite criterio;
    private NoListaSimplesCircularBaseLimite<T> noInicial;

    public ListaSimplesCircularBaseLimite(ComparacaoLimite<T> criterio) {
        this.criterio = criterio;
        noInicial =
                new NoListaSimplesCircularBaseLimite<T>(criterio.limite());
    }

    public void inserir(T elem) {
        NoListaSimplesCircularBaseLimite<T> ant = pesquisar(elem);
        ant.seguinte =
                new NoListaSimplesCircularBaseLimite<T>(elem, ant.seguinte);
    }

    /** Remove após consultar */
    public boolean remover(T elem) {
        NoListaSimplesCircularBaseLimite<T> ant = pesquisar(elem);
        while (criterio.comparar(elem, ant.seguinte.elemento) == 0
                && !ant.seguinte.elemento.equals(elem)) {
            ant = ant.seguinte;
        }
        if (!ant.seguinte.elemento.equals(elem)) {
            return false;
        }
        ant.seguinte = ant.seguinte.seguinte;
        return true;
    }

    public boolean vazia() {
        return noInicial.seguinte == noInicial;
    }

    public T consultar(int indice) {
        NoListaSimplesCircularBaseLimite<T> aux = noInicial.seguinte;
        while (aux != noInicial) {
            if (indice-- == 0) {
                return aux.elemento;
            }
            aux = aux.seguinte;
        }
        return null;
    }

    public Iterador<T> consultar(T elem) {
        NoListaSimplesCircularBaseLimite<T> ant = pesquisar(elem);
        ListaSimplesCircularBaseLimite<T> lc =
                new ListaSimplesCircularBaseLimite<T>(criterio);
        while (criterio.comparar(elem, ant.seguinte.elemento) == 0) {
            lc.inserir(ant.seguinte.elemento);
            ant = ant.seguinte;
        }
        return new IteradorListaSimplesCircularBaseLimite<T>(lc.noInicial);
    }

    public int numElementos() {
        int i = 0;
        NoListaSimplesCircularBaseLimite<T> aux = noInicial.seguinte;
        while (aux != noInicial) {
            i++;
            aux = aux.seguinte;
        }
        return i;
    }

    private NoListaSimplesCircularBaseLimite<T> pesquisar(T elem) {
        NoListaSimplesCircularBaseLimite<T> ant = noInicial;
        while (criterio.comparar(elem, ant.seguinte.elemento) > 0) {
            ant = ant.seguinte;
        }
        return ant;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Lista SCBLim = {\n");
        NoListaSimplesCircularBaseLimite<T> aux = noInicial.seguinte;
        while (aux != noInicial) {
            s.append(aux.elemento).append("\n");
            aux = aux.seguinte;
        }
        s.append("}\n");
        return s.toString();
    }

    public Iterador<T> iteradorLista() {
        return new IteradorListaSimplesCircularBaseLimite<T>(noInicial);
    }
}

class NoListaSimplesCircularBaseLimite<T> implements java.io.Serializable {

    T elemento;
    NoListaSimplesCircularBaseLimite<T> seguinte;

    NoListaSimplesCircularBaseLimite(T limite) {  // Criação do nó base
        elemento = limite;
        seguinte = this;
    }

    NoListaSimplesCircularBaseLimite(T elem, NoListaSimplesCircularBaseLimite<T> seg) {
        elemento = elem;
        seguinte = seg;
    }

    protected void finalize() {
        elemento = null;
        seguinte = null;
    }
}

class IteradorListaSimplesCircularBaseLimite<T> implements Iterador<T> {

    private NoListaSimplesCircularBaseLimite<T> noInicial,
            corrente;

    IteradorListaSimplesCircularBaseLimite(NoListaSimplesCircularBaseLimite<T> noInicial) {
        this.noInicial = corrente = noInicial;
    }

    public boolean podeAvancar() {
        return corrente.seguinte != noInicial;
    }

    public T avancar() {
        if (podeAvancar()) {
            corrente = corrente.seguinte;
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
