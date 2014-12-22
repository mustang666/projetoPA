package com.example.icd10_version2010;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EstatQuestActivity extends Activity {

	private TextView NRespostasCorretas;
	private TextView NRespostasApresentadas;
	private TextView TMelhorResposta;
	private TextView TMedioResposta;
	private TextView TtotalSessao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estat_quest);
		
		NRespostasCorretas = (TextView)findViewById(R.id.txtNRespC);
		NRespostasApresentadas = (TextView) findViewById(R.id.txtNRespAp);
		TMelhorResposta = (TextView) findViewById(R.id.txtTMResp);
		TMedioResposta = (TextView) findViewById(R.id.txtTMedioR);
		TtotalSessao = (TextView) findViewById(R.id.txtTtotalS);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estat_quest, menu);
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
