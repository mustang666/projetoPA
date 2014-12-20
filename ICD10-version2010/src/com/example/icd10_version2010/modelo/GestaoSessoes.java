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
	
//	SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
//	
//	
//	try {
//		Sessao s1 = new Sessao(new Date(), 35, 5, 2, 55, 10, 3);
//		Sessao s2 = new Sessao(dt.parse("16-12-2014"), 50, 5, 2, 55, 10, 3);
//		Sessao s3 = new Sessao(dt.parse("15-12-2014"), 90, 5, 2, 55, 10, 3);
//		Sessao s4 = new Sessao(new Date(), 80, 5, 2, 55, 10, 3);
//
//		GestaoSessoes.getInstance().inserirSessao(s1);
//		GestaoSessoes.getInstance().inserirSessao(s2);
//		GestaoSessoes.getInstance().inserirSessao(s3);
//		GestaoSessoes.getInstance().inserirSessao(s4);
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//	
//	Sessao aux;
//	
//	 Iterador<Sessao> iteradorArvore =
//			 GestaoSessoes.getInstance().listaSessoes();
//	    while (iteradorArvore.podeAvancar()) {
//	      aux = iteradorArvore.avancar();
//	     
//	        System.out.println(aux);
//	      
//	    }
	
	
}
