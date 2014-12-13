package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public class Fila<T> implements FilaADT<T>, java.io.Serializable {

    private NoListaDuplaCircularBase<T> fila;

    public Fila() {
        fila = new NoListaDuplaCircularBase<T>(null);
    }

    public void inserir(T elem) {
        new NoListaDuplaCircularBase<T>(elem, fila); //sempre no fim da fila
    }

    public T remover() {
        if (vazia()) {
            return null;
        }
        NoListaDuplaCircularBase<T> aux = fila.seguinte;
        aux.seguinte.anterior = fila;
        fila.seguinte = aux.seguinte;
        return aux.elemento;
    }

    public boolean vazia() {
        return fila.seguinte == fila;
    }

    public T consultar() {
        if (vazia()) {
            return null;
        }
        return fila.seguinte.elemento;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Fila = {\n");
        NoListaDuplaCircularBase<T> aux = fila.seguinte;
        while (aux != fila) {
            s.append(aux.elemento).append("\n");
            aux = aux.seguinte;
        }
        s.append("}\n");
        return s.toString();
    }
}

class NoListaDuplaCircularBase<T> implements java.io.Serializable {

    T elemento;
    NoListaDuplaCircularBase<T> anterior, seguinte;

    NoListaDuplaCircularBase(T limite) {  // Criação do nó base
        elemento = limite;
        anterior = this;
        seguinte = this;
    }

    NoListaDuplaCircularBase(T elem,
            NoListaDuplaCircularBase<T> seg) {
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
