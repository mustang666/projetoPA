package com.example.icd10_version2010.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Bloco extends ItemICD10 implements Serializable {

	private String codBlocoFin;
	private Capitulo capitulo;
	private String codCapitulo;

	private ArrayList<CodigoTerminal> listaCodigos;

	public Bloco(String codBlocoIni, String codBlocoFin, String nomeBloco,
			Capitulo capitulo) {
		super(nomeBloco, codBlocoIni);
		this.codBlocoFin = codBlocoFin;
		this.nome = nomeBloco;
		this.capitulo = capitulo;
		this.listaCodigos = new ArrayList<CodigoTerminal>();
	}

	public Bloco() {
		this.listaCodigos = new ArrayList<CodigoTerminal>();
	}

	public String getCodCapitulo() {
		return codCapitulo;
	}

	public void setCodCapitulo(String codCapitulo) {
		this.codCapitulo = codCapitulo;
	}

	public Capitulo getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(Capitulo capitulo) {
		this.capitulo = capitulo;
	}

	public String getCodBlocoFin() {
		return codBlocoFin;
	}

	public void setCodBlocoFin(String codBlocoFin) {
		this.codBlocoFin = codBlocoFin;
	}

	public ArrayList<CodigoTerminal> getAll() {
		return new ArrayList<CodigoTerminal>(listaCodigos);
	}

	public void addCodigo(CodigoTerminal c) {
		listaCodigos.add(c);
	}

}
