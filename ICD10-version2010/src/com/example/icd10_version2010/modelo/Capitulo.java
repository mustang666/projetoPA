package com.example.icd10_version2010.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Capitulo extends ItemICD10 implements Serializable {

	private ArrayList<Bloco> listaBloco;

	public Capitulo() {
		this.listaBloco = new ArrayList<Bloco>();
	}

	public Capitulo(String nome, String cod) {
		super(nome, cod);
		this.listaBloco = new ArrayList<Bloco>();
	}


	public ArrayList<Bloco> getAll() {
		return new ArrayList<Bloco>(listaBloco);
	}

	public void addBloco(Bloco b) {
		listaBloco.add(b);
	}

}
