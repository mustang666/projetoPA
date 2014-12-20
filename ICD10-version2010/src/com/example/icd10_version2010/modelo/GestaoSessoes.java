package com.example.icd10_version2010.modelo;

import java.io.Serializable;

import pt.ipleiria.estg.dei.is.pa.coleccoes.ArvoreBinaria;
import pt.ipleiria.estg.dei.is.pa.coleccoes.Iterador;

public class GestaoSessoes implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2749851857407162309L;

	ArvoreBinaria<Sessao> arvore = new ArvoreBinaria<Sessao>(new ComparacaoSessoesData());
	
	private static GestaoSessoes gestaoSessoes;

	public static GestaoSessoes getInstance() {
		if (GestaoSessoes.gestaoSessoes == null) {
			GestaoSessoes.gestaoSessoes = new GestaoSessoes();
		}
		return GestaoSessoes.gestaoSessoes;
	}

	public static void setInstance(GestaoSessoes gestaoSessoes) {

		GestaoSessoes.gestaoSessoes = gestaoSessoes;
	}
	
	public void inserirSessao(Sessao sessao){
		arvore.inserir(sessao);
		
	}
	
	public Iterador<Sessao> listaSessoes(){
		return arvore.iteradorEmOrdem();
	}
	
	
}
