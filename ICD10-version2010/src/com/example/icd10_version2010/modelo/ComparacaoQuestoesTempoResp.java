package com.example.icd10_version2010.modelo;

import pt.ipleiria.estg.dei.is.pa.coleccoes.Comparacao;

public class ComparacaoQuestoesTempoResp implements Comparacao<Questao> {
	@Override
	public int comparar(Questao q1, Questao q2) {
		double T1 = q1.getTempoResposta();
		double T2 = q2.getTempoResposta();
		return (T1 > T2 ? 1 : T1 < T2 ? -1 : 0);
	}

}
