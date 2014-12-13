package com.example.icd10_version2010.modelo;

import java.util.ArrayList;

public class CodigoNaoTerminal extends CodigoTerminal {
	private ArrayList<CodigoTerminal> listaCodigos;

	public CodigoNaoTerminal(char nivelArvore, char terminalSN, Bloco bloco,
			String codCodigo, String nomeCodigo) {
		super(nivelArvore, terminalSN, bloco, codCodigo, nomeCodigo);

		this.listaCodigos = new ArrayList<CodigoTerminal>();
	}

	public CodigoNaoTerminal() {
		listaCodigos = new ArrayList<CodigoTerminal>();
	}

	public ArrayList<CodigoTerminal> getAll() {
		return new ArrayList<CodigoTerminal>(listaCodigos);
	}

	public void addSubcodigo(CodigoTerminal s) {
		listaCodigos.add(s);
	}
}
