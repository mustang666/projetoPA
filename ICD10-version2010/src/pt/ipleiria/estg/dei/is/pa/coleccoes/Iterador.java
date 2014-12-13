package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 *
 * @author Actual code:   Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis <catarina.reis@ipleiria.pt>
 *         Original code: José Magno <jose.magno@ipleiria.pt>
 */
public interface Iterador<T> {

    /**	Excepção propagada quando se acede a elemento inválido.   */
    public static final java.util.NoSuchElementException ELEMENTO_INVALIDO = new java.util.NoSuchElementException();

    /**	Verifica se pode avancar para o proximo elemento.
    @return true caso ainda existam elementos a percorrer;
    false caso contrário	*/
    public boolean podeAvancar();

    /**	Devolve o próximo elemento.
    Caso não exista lança a excepção ELEMENTO_INVALIDO
    @return o próximo elemento   */
    public T avancar();

    /**	Coloca o iterador no nó inicial da lista   */
    public void reiniciar();

    /**	Devolve o elemento actual.
    Caso não exista lança a excepção ELEMENTO_INVALIDO
    @return o elemento actual   */
    public T corrente();
}
