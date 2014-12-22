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
	private double tempoTotal;
	private double tempoMedioResposta;
	private double tempoMelhorResposta;

	private ListaSimples<Questao> listaQuestoes;

	public Sessao() {
		dataSessao = new Date();
		listaQuestoes = new ListaSimples<Questao>(
				new ComparacaoQuestoesTempoResp());
	}
	
	

	public Sessao(Date dataSessao, float score, int nrPerguntasApresentadas,
			int nrRespostasCorretas, double tempoTotal,
			double tempoMedioResposta, double tempoMelhorResposta) {
		super();
		this.dataSessao = dataSessao;
		this.score = score;
		this.nrPerguntasApresentadas = nrPerguntasApresentadas;
		this.nrRespostasCorretas = nrRespostasCorretas;
		this.tempoTotal = tempoTotal;
		this.tempoMedioResposta = tempoMedioResposta;
		this.tempoMelhorResposta = tempoMelhorResposta;
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

	public double getTempoTotal() {
		return tempoTotal;
	}

	public void setTempoTotal(double tempoTotal) {
		this.tempoTotal = tempoTotal;
	}

	public double getTempoMedioResposta() {
		return tempoMedioResposta;
	}

	public void setTempoMedioResposta(double tempoMedioResposta) {
		this.tempoMedioResposta = tempoMedioResposta;
	}

	public double getTempoMelhorResposta() {
		return tempoMelhorResposta;
	}

	public void setTempoMelhorResposta(double tempoMelhorResposta) {
		this.tempoMelhorResposta = tempoMelhorResposta;
	}

}
