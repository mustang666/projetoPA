package com.example.icd10_version2010.modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import pt.ipleiria.estg.dei.is.pa.coleccoes.ListaSimples;

public class Sessao {

	private Date dataSessao;
	private float score;
	private int nrPerguntasApresentadas;
	private int nrRespostasCorretas;
	private float tempoTotal;
	private float tempoMedioResposta;
	private float tempoMelhorResposta;

	private ListaSimples<Questao> listaQuestoes;

	public Sessao() {
		dataSessao = new Date();
		listaQuestoes = new ListaSimples<Questao>(
				new ComparacaoQuestoesTempoResp());
	}

	public void addQuestao(Questao q) {
		listaQuestoes.inserir(q);
	}

	public Date getDataSessao() {
		return dataSessao;
	}

	public void setDataSessao(Date dataSessao) {
		this.dataSessao = dataSessao;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getNrPerguntasApresentadas() {
		return nrPerguntasApresentadas;
	}

	public void setNrPerguntasApresentadas(int nrPerguntasApresentadas) {
		this.nrPerguntasApresentadas = nrPerguntasApresentadas;
	}

	public int getNrRespostasCorretas() {
		return nrRespostasCorretas;
	}

	public void setNrRespostasCorretas(int nrRespostasCorretas) {
		this.nrRespostasCorretas = nrRespostasCorretas;
	}

	public float getTempoTotal() {
		return tempoTotal;
	}

	public void setTempoTotal(float tempoTotal) {
		this.tempoTotal = tempoTotal;
	}

	public float getTempoMedioResposta() {
		return tempoMedioResposta;
	}

	public void setTempoMedioResposta(float tempoMedioResposta) {
		this.tempoMedioResposta = tempoMedioResposta;
	}

	public float getTempoMelhorResposta() {
		return tempoMelhorResposta;
	}

	public void setTempoMelhorResposta(float tempoMelhorResposta) {
		this.tempoMelhorResposta = tempoMelhorResposta;
	}

}
