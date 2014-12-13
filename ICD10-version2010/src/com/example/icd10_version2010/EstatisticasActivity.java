package com.example.icd10_version2010;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class EstatisticasActivity extends Activity {

	private TextView totalTempoLeitura;
	private TextView totalLeituraCapitulos;
	private TextView totalLeituraBlocos;
	private TextView totalLeituraCodigos;
	private TextView totalItems;
	private TextView totalItemsCapitulo;
	private TextView totalItemsBloco;
	private TextView totalItemsCodigo;
	HashMap<String, Double> estatisticasHashMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estatisticas);
		estatisticasHashMap = (HashMap<String, Double>) getIntent()
				.getSerializableExtra("estatisticas");

		totalTempoLeitura = (TextView) findViewById(R.id.txtTempoTotalValue);
		totalLeituraCapitulos = (TextView) findViewById(R.id.txtLeituraCapValue);
		totalLeituraBlocos = (TextView) findViewById(R.id.txtLeituraBlocoValue);
		totalLeituraCodigos = (TextView) findViewById(R.id.txtLeituraCodValue);
		totalItems = (TextView) findViewById(R.id.totalItemsValue);
		totalItemsCapitulo = (TextView) findViewById(R.id.totalCapitulosValue);
		totalItemsBloco = (TextView) findViewById(R.id.totalBlocosValue);
		totalItemsCodigo = (TextView) findViewById(R.id.totalCodigosValue);

		totalTempoLeitura.setText(Double.toString(Math
				.round(estatisticasHashMap.get(MainActivity.TEMPO_TOTAL)))
				+ " (s)");
		totalLeituraCapitulos.setText(Double.toString(estatisticasHashMap
				.get(MainActivity.TEMPO_TOTAL_CAP)) + " (s)");
		totalLeituraBlocos.setText(Double.toString(estatisticasHashMap
				.get(MainActivity.TEMPO_TOTAL_BLOCO)) + " (s)");
		totalLeituraCodigos.setText(Double.toString(estatisticasHashMap
				.get(MainActivity.TEMPO_TOTAL_COD)) + " (s)");
		totalItems.setText(Double.toString(estatisticasHashMap
				.get(MainActivity.TOTAL_ITEMS)) + " (itens)");
		totalItemsCapitulo.setText(Double.toString(estatisticasHashMap
				.get(MainActivity.TOTAL_CAP)) + " (itens)");
		totalItemsBloco.setText(Double.toString(estatisticasHashMap
				.get(MainActivity.TOTAL_BLOCO)) + " (itens)");
		totalItemsCodigo.setText(Double.toString(estatisticasHashMap
				.get(MainActivity.TOTAL_COD)) + " (itens)");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.estatisticas, menu);
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
}
