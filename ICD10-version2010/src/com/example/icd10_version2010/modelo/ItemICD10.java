package com.example.icd10_version2010.modelo;

import java.io.Serializable;

public abstract class ItemICD10 implements Serializable {
	protected String nome;
	protected String codigo;

	public ItemICD10() {
	}

	public ItemICD10(String nome, String codigo) {
		this.nome = nome;
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String toString() {
		return codigo + " " + nome;
	}

}
