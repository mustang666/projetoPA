package com.example.icd10_version2010.modelo;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class Favoritos implements Serializable {

	private static final long serialVersionUID = 7104135962905279470L;
	private LinkedList<Capitulo> listaFavoritosCapitulos;
	private LinkedList<Bloco> listaFavoritosBlocos;
	private LinkedList<CodigoTerminal> listaFavoritosCodigos;

	private static Favoritos favoritos;

	public static Favoritos getInstance() {
		if (Favoritos.favoritos == null) {
			Favoritos.favoritos = new Favoritos();
		}
		return Favoritos.favoritos;
	}

	public static void setInstance(Favoritos favoritos) {

		Favoritos.favoritos = favoritos;
	}

	public int returnTotal() {
		return getInstance().getAllCapitulos().size()
				+ getInstance().getAllBlocos().size()
				+ getInstance().getAllCodigos().size();
	}

	public static int returnTotalBinary(Favoritos favoritos) {
		return favoritos.getAllCapitulos().size()
				+ favoritos.getAllBlocos().size()
				+ favoritos.getAllCodigos().size();
	}

	public void setVerifyingIfAlreadyExists(Favoritos favoritos) {
		if (Favoritos.getInstance().listaFavoritosCapitulos.size() == 0) {
			Favoritos.getInstance().listaFavoritosCapitulos = favoritos
					.getAllCapitulos();
		} else {
			for (Capitulo capitulo : favoritos.getAllCapitulos()) {
				try {
					if (!existsCapitulo(capitulo)) {
						Favoritos.getInstance().listaFavoritosCapitulos
								.add(capitulo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (Favoritos.getInstance().listaFavoritosBlocos.size() == 0) {
			Favoritos.getInstance().listaFavoritosBlocos = favoritos
					.getAllBlocos();
		} else {
			for (Bloco bloco : favoritos.getAllBlocos()) {
				if (!existsBloco(bloco)) {
					Favoritos.getInstance().listaFavoritosBlocos.add(bloco);
				}
			}
		}
		if (Favoritos.getInstance().listaFavoritosCodigos.size() == 0) {
			Favoritos.getInstance().listaFavoritosCodigos = favoritos
					.getAllCodigos();
		} else {
			for (CodigoTerminal codigo : favoritos.getAllCodigos()) {
				if (!existsCodigo(codigo)) {
					Favoritos.getInstance().listaFavoritosCodigos.add(codigo);
				}
			}
		}
	}

	private Favoritos() {
		listaFavoritosCapitulos = new LinkedList<Capitulo>();
		listaFavoritosBlocos = new LinkedList<Bloco>();
		listaFavoritosCodigos = new LinkedList<CodigoTerminal>();
	}

	public void adicionarFavoritoCapitulo(Capitulo capitulo) {
		listaFavoritosCapitulos.add(capitulo);
	}

	public void adicionarFavoritoBloco(Bloco bloco) {
		listaFavoritosBlocos.add(bloco);
	}

	public void adicionarFavoritoCodigo(CodigoTerminal codigo) {
		listaFavoritosCodigos.add(codigo);
	}

	public void removerFavoritoCapitulo(Capitulo capitulo) {
		listaFavoritosCapitulos.remove(capitulo);
	}

	public void removerFavoritoBloco(Bloco bloco) {
		listaFavoritosBlocos.remove(bloco);
	}

	public void removerFavoritoCodigo(CodigoTerminal codigo) {
		listaFavoritosCodigos.remove(codigo);
	}

	public Iterator<Capitulo> consultarCapitulos() {
		return listaFavoritosCapitulos.iterator();
	}

	public Iterator<Bloco> consultarBlocos() {
		return listaFavoritosBlocos.iterator();
	}

	public Iterator<CodigoTerminal> consultarCodigos() {
		return listaFavoritosCodigos.iterator();
	}

	public LinkedList<Capitulo> getAllCapitulos() {
		return new LinkedList<Capitulo>(listaFavoritosCapitulos);
	}

	public LinkedList<Bloco> getAllBlocos() {
		return new LinkedList<Bloco>(listaFavoritosBlocos);
	}

	public LinkedList<CodigoTerminal> getAllCodigos() {
		return new LinkedList<CodigoTerminal>(listaFavoritosCodigos);
	}

	public boolean existsCapitulo(Capitulo capitulo) throws Exception {

		try {
			for (Capitulo cap : Favoritos.getInstance().listaFavoritosCapitulos) {
				if (cap.getCodigo().equals(capitulo.getCodigo())) {
					return true;
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return false;
	}

	public boolean existsBloco(Bloco bloco) {
		for (Bloco b : Favoritos.getInstance().listaFavoritosBlocos) {
			if (b.getCodigo().equals(bloco.getCodigo())) {
				return true;
			}
		}

		return false;
	}

	public boolean existsCodigo(CodigoTerminal codigo) {
		for (CodigoTerminal cod : Favoritos.getInstance().listaFavoritosCodigos) {
			if (cod.getCodigo().equals(codigo.getCodigo())) {
				return true;
			}
		}
		return false;
	}

}
