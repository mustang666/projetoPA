package com.example.icd10_version2010.modelo;

import java.util.ArrayList;
import java.util.Iterator;

public class ICD10 {
	private ArrayList<Capitulo> listaCapitulos;

	private static ICD10 icd10;

	public static ICD10 getInstance() {
		if (ICD10.icd10 == null) {
			ICD10.icd10 = new ICD10();
		}
		return ICD10.icd10;
	}

	public void setICD10List(ArrayList<Capitulo> listaCapitulos) {
		this.listaCapitulos = listaCapitulos;
	}

	private ICD10() {
		listaCapitulos = new ArrayList<Capitulo>();
	}

	public void adicionarCapitulo(Capitulo c) {
		listaCapitulos.add(c);
	}

	public Iterator<Capitulo> consultar() {
		return listaCapitulos.iterator();
	}

	public ArrayList<Capitulo> getAll() {
		return new ArrayList<Capitulo>(listaCapitulos);
	}

}
