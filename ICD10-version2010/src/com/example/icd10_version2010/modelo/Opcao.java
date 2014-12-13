package com.example.icd10_version2010.modelo;

import java.util.Random;

public class Opcao {
	public static final String ERRO = "erro";
	private String campo;
	private boolean isCerto;

	private int indexCapitulo;
	private int indexBloco;
	private int indexCodigo;
	private ItemICD10 capitulo;

	public Opcao() {
		isCerto = false;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public boolean isCerto() {
		return isCerto;
	}

	public void setCerto(boolean isCerto) {
		this.isCerto = isCerto;
	}

	public String gerarOpcao(int isFirst, int tipoPergunta) {
		Random randPergunta = new Random();
		int op = randPergunta.nextInt(4);
		ItemICD10 i;
		switch (op) {
		case 0:// Capitulo
			i = randCapitulo();
			if (i != null) {
				return definirOpcao(i, tipoPergunta, isFirst);
			}
			break;

		case 1:// Bloco
			i = randCapitulo();
			if (i != null) {
				i = randBloco();
				if (i != null) {
					return definirOpcao(i, tipoPergunta, isFirst);
				}
			}

			break;

		case 2:// Codigo
			i = randCapitulo();
			if (i != null) {
				i = randBloco();
				if (i != null) {
					i = randCodigo();
					if (i != null) {
						return definirOpcao(i, tipoPergunta, isFirst);
					}

				}
			}
			break;

		case 3:// subcodigo
			i = randCapitulo();
			if (i != null) {
				i = randBloco();
				if (i != null) {
					i = randCodigo();
					if (i != null) {
						if(i instanceof CodigoTerminal && !(i instanceof CodigoNaoTerminal)){
						
								return ERRO;

							
						}
						 else {
							if (randSubCodigo() != null) {
								return definirOpcao(randSubCodigo(),
										tipoPergunta, isFirst);
							}
						}

					}

				}
			}
			break;

		}

		return null;
	}

	private String definirOpcao(ItemICD10 item, int tipoPergunta, int isFirst) {
		String pergunta = "";
		if (tipoPergunta == 0) {// tipo 0 pergunta codigo opcao nome
			this.campo = item.getNome();
			if (isFirst == 0) {
				isCerto = true;
				pergunta = item.getCodigo();
			}
		} else {// tipo 1 pergunta nome opcao codigo
			this.campo = item.getCodigo();
			if (isFirst == 0) {
				isCerto = true;
				pergunta = item.getNome();

			}
		}
		return pergunta;

	}

	public ItemICD10 randCapitulo() {
		int length = ICD10.getInstance().getAll().size();
		Random rCap = new Random();
		indexCapitulo = rCap.nextInt(length);
		ItemICD10 i = ICD10.getInstance().getAll().get(indexCapitulo);
		if (i != null) {
			return i;
		}
		return null;
	}

	public ItemICD10 randBloco() {

		Capitulo capitulo = ICD10.getInstance().getAll().get(indexCapitulo);
		int lengthBlocos = capitulo.getAll().size();
		Random rBloco = new Random();
		indexBloco = rBloco.nextInt(lengthBlocos);
		ItemICD10 i = capitulo.getAll().get(indexBloco);
		if (i != null) {
			return i;
		}
		return null;
	}

	private ItemICD10 randCodigo() {
		Bloco bloco = ICD10.getInstance().getAll().get(indexCapitulo).getAll()
				.get(indexBloco);
		int lengthCodigos = bloco.getAll().size();
		ItemICD10 i;
		// do {
		Random rCodigo = new Random();
		indexCodigo = rCodigo.nextInt(lengthCodigos);
		i = bloco.getAll().get(indexCodigo);
		// } while (i instanceof CodigoTerminal);

		if (i != null) {
			return i;
		}
		return null;
	}

	private ItemICD10 randSubCodigo() {
		CodigoNaoTerminal codigo = (CodigoNaoTerminal) ICD10.getInstance()
				.getAll().get(indexCapitulo).getAll().get(indexBloco).getAll()
				.get(indexCodigo);
		int lengthSubcodigos = codigo.getAll().size();
		Random rSubCodigo = new Random();
		int indexSubCodigo = rSubCodigo.nextInt(lengthSubcodigos);
		ItemICD10 i = codigo.getAll().get(indexSubCodigo);
		if (i != null) {
			return i;
		}
		return null;
	}

}
