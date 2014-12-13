package com.example.icd10_version2010;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.icd10_version2010.modelo.Bloco;
import com.example.icd10_version2010.modelo.Capitulo;
import com.example.icd10_version2010.modelo.CodigoNaoTerminal;
import com.example.icd10_version2010.modelo.CodigoTerminal;
import com.example.icd10_version2010.modelo.ICD10;
import com.example.icd10_version2010.modelo.ItemICD10;

public class QuestoesActivity extends Activity {

	private Button trainingBtn;
	private Button estatisticasBtn;
	private Button historicoBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questoes);
		trainingBtn = (Button) findViewById(R.id.btn_training);
		estatisticasBtn = (Button) findViewById(R.id.btn_estatisticas);
		historicoBtn = (Button) findViewById(R.id.btn_historico);

		trainingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentT = new Intent(getApplicationContext(),
						TrainingActivity.class);

				startActivity(intentT);
			}
		});

		estatisticasBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentE = new Intent(getApplicationContext(),
						EstatQuestActivity.class);

				startActivity(intentE);

			}
		});

		historicoBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentH = new Intent(getApplicationContext(),
						HistoricoQuestActivity.class);

				startActivity(intentH);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questoes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
