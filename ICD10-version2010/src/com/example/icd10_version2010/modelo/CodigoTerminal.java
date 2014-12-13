package com.example.icd10_version2010.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class CodigoTerminal extends ItemICD10 implements Serializable {

	private char nivelArvore;
	private char terminalSN;
	private String codBlocoIni;
	private String codCapitulo;

	public String getCodBlocoIni() {
		return codBlocoIni;
	}

	public void setCodBlocoIni(String codBlocoIni) {
		this.codBlocoIni = codBlocoIni;
	}

	public String getCodCapitulo() {
		return codCapitulo;
	}

	public void setCodCapitulo(String codCapitulo) {
		this.codCapitulo = codCapitulo;
	}

	private Bloco bloco;

	public CodigoTerminal(char nivelArvore, char terminalSN, Bloco bloco,
			String codCodigo, String nomeCodigo) {
		super(nomeCodigo, codCodigo);
		this.nivelArvore = nivelArvore;
		this.terminalSN = terminalSN;
		this.bloco = bloco;
	}

	public CodigoTerminal() {
	}

	public int getNivelArvore() {
		return nivelArvore;
	}

	public void setNivelArvore(char nivelArvore) {
		this.nivelArvore = nivelArvore;
	}

	public char getTerminalSN() {
		return terminalSN;
	}

	public void setTerminalSN(char terminalSN) {
		this.terminalSN = terminalSN;
	}

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

}
