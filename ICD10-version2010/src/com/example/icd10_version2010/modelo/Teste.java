package com.example.icd10_version2010.modelo;

import java.util.Date;

import android.text.method.DateTimeKeyListener;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		Sessao s1 = new Sessao(new Date(), 35, 5, 2, 55, 10, 3);
		Sessao s2 = new Sessao(new Date(), 50, 5, 2, 55, 10, 3);
		Sessao s3 = new Sessao(new Date(), 90, 5, 2, 55, 10, 3);
		Sessao s4 = new Sessao(new Date(), 80, 5, 2, 55, 10, 3);
		
		GestaoSessoes.getInstance().inserirSessao(s1);
		GestaoSessoes.getInstance().inserirSessao(s2);
		GestaoSessoes.getInstance().inserirSessao(s3);
		GestaoSessoes.getInstance().inserirSessao(s4);
		
		System.out.println(GestaoSessoes.getInstance().listaSessoes());
		

	}

}
