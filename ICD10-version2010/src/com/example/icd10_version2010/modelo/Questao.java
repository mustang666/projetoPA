package com.example.icd10_version2010.modelo;

import java.util.HashMap;
import java.util.Random;

import android.util.SparseArray;

public class Questao {
	private int tipoQuestao;
	private String pergunta;
	private double tempoResposta;
	private String respostaCerta;

	private SparseArray<Opcao> opcoes;
	public static final int NROPCOES = 4;
	private int nrOpcoesExist = 0;

	public Questao() {
		this.opcoes = new SparseArray<Opcao>(NROPCOES);
	}

	public SparseArray<Opcao> getOpcoes() {
		return opcoes.clone();
	}

	public String getRespostaCerta() {
		return respostaCerta;
	}

	public void setRespostaCerta(String respostaCerta) {
		this.respostaCerta = respostaCerta;
	}

	public int getTipoQuestao() {
		return tipoQuestao;
	}

	public void setTipoQuestao(int tipoQuestao) {
		this.tipoQuestao = tipoQuestao;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public double getTempoResposta() {
		return tempoResposta;
	}

	public void setTempoResposta(double tempoResposta) {
		this.tempoResposta = tempoResposta;
	}

	public void gerarPergunta() {
		Random randTipo = new Random();
		tipoQuestao = randTipo.nextInt(2);

		while (nrOpcoesExist < NROPCOES) {
			Opcao opcao = new Opcao();
			String pergunta = opcao.gerarOpcao(nrOpcoesExist, tipoQuestao);
			if (pergunta != Opcao.ERRO) {
				if (pergunta != "") {
					this.pergunta = pergunta;
				}
				opcoes.append(nrOpcoesExist, opcao);
				nrOpcoesExist++;
			}
		}

	}
}
