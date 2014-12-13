package pt.ipleiria.estg.dei.is.pa.coleccoes;

/**
 * 
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public class ListaSimples<T> implements ListaSimplesADT<T>,
		java.io.Serializable {

	private Comparacao<T> criterio;
	private NoListaSimples<T> noInicial;

	public ListaSimples(Comparacao<T> criterio) {
		this.criterio = criterio;
		noInicial = null;
	}

	public void inserir(T elem) {
		NoListaSimples<T> ant = pesquisar(elem);
		if (ant == null) {
			noInicial = new NoListaSimples<T>(elem, noInicial);
		} else {
			ant.seguinte = new NoListaSimples<T>(elem, ant.seguinte);
		}
	}

	public boolean remover(T elem) {
		if (noInicial == null) {
			return false;
		}
		NoListaSimples<T> ant = pesquisar(elem), aux;
		if (ant == null) {
			if (criterio.comparar(elem, noInicial.elemento) != 0) {
				return false;
			}
			if (noInicial.elemento.equals(elem)) {
				noInicial = noInicial.seguinte;
				return true;
			}
			ant = noInicial;
		}
		while (ant.seguinte != null
				&& criterio.comparar(elem, ant.seguinte.elemento) == 0
				&& !ant.seguinte.elemento.equals(elem)) {
			ant = ant.seguinte;
		}
		if (ant.seguinte == null || !ant.seguinte.elemento.equals(elem)) {
			return false;
		}
		ant.seguinte = ant.seguinte.seguinte;
		return true;
	}

	public T consultar(T elem) {
		NoListaSimples<T> ant = pesquisar(elem);
		if (ant == null) {
			if (criterio.comparar(elem, noInicial.elemento) == 0) {
				return noInicial.elemento;
			}
			return null;
		}
		if (ant.seguinte != null
				&& criterio.comparar(elem, ant.seguinte.elemento) == 0) {
			return ant.seguinte.elemento;
		}
		return null;
	}

	public T consultar(int indice) {
		NoListaSimples<T> aux = noInicial;
		while (aux != null) {
			if (indice-- == 0) {
				return aux.elemento;
			}
			aux = aux.seguinte;
		}
		return null;
	}

	public int numElementos() {
		int i = 0;
		NoListaSimples<T> aux = noInicial;
		while (aux != null) {
			i++;
			aux = aux.seguinte;
		}
		return i;
	}

	private NoListaSimples<T> pesquisar(T elem) {
		NoListaSimples<T> ant;
		if (noInicial == null
				|| criterio.comparar(elem, noInicial.elemento) <= 0) {
			return null;
		}
		for (ant = noInicial; ant.seguinte != null
				&& criterio.comparar(elem, ant.seguinte.elemento) > 0; ant = ant.seguinte)
			;
		return ant;
	}

	private ListaSimples<T> devolverCopia() {
		ListaSimples<T> listaNova = new ListaSimples<T>(this.criterio);
		NoListaSimples<T> ant;
		if (noInicial != null) {
			for (ant = noInicial; ant.seguinte != null; ant = ant.seguinte) {
				listaNova.inserir((T) ant);
			}
			return listaNova;

		}

		return null;
	}

	public boolean vazia() {
		return noInicial == null;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Lista Simples = {\n");
		NoListaSimples<T> aux = noInicial;
		while (aux != null) {
			s.append(aux.elemento).append("\n");
			aux = aux.seguinte;
		}
		s.append("}\n");
		return s.toString();
	}
}

// /////////////////////////////////////////////////
class NoListaSimples<T> implements java.io.Serializable {

	T elemento;
	NoListaSimples<T> seguinte;

	NoListaSimples(T elem, NoListaSimples<T> seg) {
		elemento = elem;
		seguinte = seg;
	}

	protected void finalize() {
		elemento = null;
		seguinte = null;
	}
}
