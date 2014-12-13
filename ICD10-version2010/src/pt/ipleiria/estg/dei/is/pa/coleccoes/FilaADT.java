package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public interface FilaADT<T> {

    /** Coloca um objecto no fim da fila.
    @param o objecto a colocar numa fila   */
    void inserir(T elem);

    /** Retira o objecto que está no início da fila e devolve-o.
    @return o objecto retirado do início da fila
    ou null caso da fila vazia */
    T remover();

    /** Devolve objecto que está no início da fila sem o retirar.
    @return o objecto retirado do início da fila
    ou null caso da fila vazia */
    T consultar();

    /** Verifica se a fila está vazia.
    @return true caso a fila se encontre vazia
    ou false no caso contrário */
    boolean vazia();
}
