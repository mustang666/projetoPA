package com.example.icd10_version2010;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.icd10_version2010.modelo.Questao;
import com.example.icd10_version2010.modelo.Sessao;

public class TrainingActivity extends Activity {

	private Button iniciar;
	private ViewFlipper flipTrain;
	private Sessao sessao;
	private RadioButton radio0;
	private RadioButton radio1;
	private RadioButton radio2;
	private RadioButton radio3;
	private TextView pergunta;
	private Button terminar;
	private Button submeter;
	private RadioGroup radioGroup;
	private int posicaoCerta;
	private Questao questao;
	private double startTime;
	private double endTime;
	private AlertDialog alertPerguntaCerta;
	private String tempo;
	private View alert;
	final Context context = this;
	private boolean acertou = false;
	private TextView nrRespostasCorretas;
	private TextView nrRespostasApresentadas;
	private TextView nrTMelhorResposta;
	private TextView nrTMedioResposta;
	private TextView nrTtotalSessao;
	private TextView perguntaCerta;
	private TextView respostaCerta;
	private TextView tresposta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training);

		iniciar = (Button) findViewById(R.id.btn_Iniciar);
		terminar = (Button) findViewById(R.id.btn_terminarTraining);
		submeter = (Button) findViewById(R.id.btn_pergSeguinte);
		flipTrain = (ViewFlipper) findViewById(R.id.viewFlipperTraining);
		radio0 = (RadioButton) findViewById(R.id.radio0);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		radio2 = (RadioButton) findViewById(R.id.radio2);
		radio3 = (RadioButton) findViewById(R.id.radio3);
		pergunta = (TextView) findViewById(R.id.txt_Pergunta);
		radioGroup = (RadioGroup) findViewById(R.id.radioQuestoes);
		perguntaCerta = (TextView) findViewById(R.id.txt_pergunta_alert_errou);

		nrRespostasCorretas = (TextView) findViewById(R.id.txtNRespC_value);
		nrRespostasApresentadas = (TextView) findViewById(R.id.txtNRespAp_value);
		nrTMelhorResposta = (TextView) findViewById(R.id.txtTMResp_value);
		nrTMedioResposta = (TextView) findViewById(R.id.txtTMedioR_value);
		nrTtotalSessao = (TextView) findViewById(R.id.txtTtotalS_value);

		flipTrain.setDisplayedChild(flipTrain
				.indexOfChild(findViewById(R.id.flipComecar)));

		iniciar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flipTrain.setDisplayedChild(flipTrain
						.indexOfChild(findViewById(R.id.flipQuestoes)));

				if (flipTrain.getDisplayedChild() == flipTrain
						.indexOfChild(findViewById(R.id.flipQuestoes))) {
					sessao = new Sessao();

					fazerPergunta();

				}

			}
		});

		submeter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				endTime = System.currentTimeMillis() - startTime;
				questao.setTempoResposta(endTime);
				sessao.addQuestao(questao);
				sessao.setNrPerguntasApresentadas(sessao
						.getNrPerguntasApresentadas() + 1);
				sessao.setTempoTotal(sessao.getTempoTotal()
						+ questao.getTempoResposta());
				tempo = Double.toString((questao.getTempoResposta() / 1000));

				if (radioGroup.getCheckedRadioButtonId() == ((RadioButton) radioGroup
						.getChildAt(posicaoCerta)).getId()) {
					sessao.setNrRespostasCorretas(sessao
							.getNrRespostasCorretas() + 1);
					acertou = true;

				} else {
					acertou = false;
				}
				displayAlertDialog();

			}
		});

		terminar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				terminarSessao();
			}
		});

	}

	public void displayAlertDialog() {
		if (tempo != null) {
			LayoutInflater inflater = getLayoutInflater();
			View alertLayout;

			if (acertou) {
				alertLayout = inflater.inflate(R.layout.alert_acertou, null);
				tresposta = (TextView) alertLayout
						.findViewById(R.id.txtTempoResp_value);
			} else {
				alertLayout = inflater.inflate(R.layout.alert_errou, null);
				tresposta = (TextView) alertLayout
						.findViewById(R.id.txtTempoResp_value_alert_errou);
				perguntaCerta = (TextView) alertLayout
						.findViewById(R.id.txt_pergunta_alert_errou);
				respostaCerta = (TextView) alertLayout
						.findViewById(R.id.txt_resposta_alert_errou);
				perguntaCerta.setText(questao.getPergunta() + " �:");
				respostaCerta.setText(questao.getRespostaCerta());
			}

			tresposta.setText(tempo + "s");

			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setView(alertLayout);
			alert.setCancelable(false);
			alert.setNegativeButton("Terminar",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							terminarSessao();
						}
					});

			alert.setPositiveButton("Seguinte",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							limparQuizz();
							fazerPergunta();
						}
					});
			AlertDialog dialog = alert.create();
			dialog.show();
		}

	}

	protected void limparQuizz() {

		if (!acertou) {
			perguntaCerta.setText("");
			respostaCerta.setText("");
		}

		pergunta.setText("");
		tresposta.setText("");

		for (int i = 0; i < questao.NROPCOES; i++) {
			((RadioButton) radioGroup.getChildAt(i)).setText("");

		}

		radioGroup.clearCheck();
	}

	protected void terminarSessao() {
		sessao.setScore(sessao.getNrRespostasCorretas()
				/ (float) sessao.getNrPerguntasApresentadas());
		sessao.setTempoMedioResposta(sessao.getTempoTotal()
				/ (float) sessao.getNrPerguntasApresentadas());
		sessao.setTempoMelhorResposta(sessao.getTempoMelhorResposta());
		flipTrain.setDisplayedChild(flipTrain
				.indexOfChild(findViewById(R.id.flipFeedback)));
		nrRespostasCorretas.setText(sessao.getNrRespostasCorretas()+"");
		nrRespostasApresentadas.setText(sessao.getNrPerguntasApresentadas()+"");
		nrTMelhorResposta.setText(sessao.getTempoMelhorResposta() + "s");
		nrTMedioResposta.setText(sessao.getTempoMedioResposta() + "s");
		nrTtotalSessao.setText(sessao.getTempoTotal() + "s");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.training, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void fazerPergunta() {
		questao = new Questao();
		questao.gerarPergunta();

		pergunta.setText((questao.getTipoQuestao() == 0 ? "Qual a descri��o para este c�digo ICD10"
				: "Qual o c�digo ICD10 que identifica esta descri��o")
				+ questao.getPergunta() + "?");
		Random r = new Random();
		posicaoCerta = r.nextInt(4);

		((RadioButton) radioGroup.getChildAt(posicaoCerta)).setText(questao
				.getOpcoes().get(0).getCampo());

		questao.setRespostaCerta(questao.getOpcoes().get(0).getCampo());

		for (int i = 1; i < questao.NROPCOES; i++) {
			for (int j = 0; j < 4; j++) {
				if (j == posicaoCerta) {
					continue;
				} else {
					RadioButton rb = (RadioButton) radioGroup.getChildAt(j);
					String campo = rb.getText().toString();
					if (campo.equals("")) {
						((RadioButton) radioGroup.getChildAt(j))
								.setText(questao.getOpcoes().get(i).getCampo());
						break;
					} else {
						continue;
					}

				}
			}
		}
		startTime = System.currentTimeMillis();
	}

}
