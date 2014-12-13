package com.example.icd10_version2010;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.icd10_version2010.modelo.Bloco;
import com.example.icd10_version2010.modelo.Capitulo;
import com.example.icd10_version2010.modelo.CodigoNaoTerminal;
import com.example.icd10_version2010.modelo.CodigoTerminal;
import com.example.icd10_version2010.modelo.Favoritos;
import com.example.icd10_version2010.modelo.ICD10;

public class MainActivity extends Activity {

	private static final String IS_FINISHED = "isFinished";
	private static final String ICDCAP = "icd102010enMetachapters.txt";
	private static final String ICDBLOCO = "icd102010enMetablocks.txt";
	private static final String ICDCOD = "icd102010enMetacodes.txt";
	private static final String[] NIVELARVORE = { "3", "4" };
	private static final String[] TERMINALSN = { "T", "N" };
	private ImageButton homeBtn;
	private ImageButton searchBtn;
	private ImageButton favoriteBtn;
	private ImageButton statisticsBtn;
	private ImageButton trainingBtn;

	private ArrayList<Capitulo> listaCapitulos = new ArrayList<Capitulo>();
	private ArrayList<Bloco> listaBlocos = new ArrayList<Bloco>();
	private ArrayList<CodigoTerminal> listaCodigosFim = new ArrayList<CodigoTerminal>();
	private ArrayList<CodigoNaoTerminal> listaCodigosPai = new ArrayList<CodigoNaoTerminal>();
	private ArrayList<CodigoTerminal> listaSubcodigos = new ArrayList<CodigoTerminal>();
	private ArrayList<CodigoTerminal> listaCodigos = new ArrayList<CodigoTerminal>();
	public static HashMap<String, Double> estatisticasHashMap = new HashMap<String, Double>();
	private ProgressDialog dialogue;
	private static boolean isFinished = false;
	private boolean slowWay;

	private double tempoInicialCapitulos = 0L;
	private double tempoFinalCapitulos = 0L;
	private double tempoInicialBlocos = 0L;
	private double tempoFinalBlocos = 0L;
	private double tempoInicialCodigos = 0L;
	private double tempoFinalCodigos = 0L;
	private double tempoTotalInicio = 0L;
	private double tempoTotalFim = 0L;
	private int countTotalItems = 0;
	private int countTotalCapitulos = 0;
	private int countTotalItemsBlocos = 0;
	private int countTotalItemsCodigos = 0;
	private AlertDialog alertFastWay;
	private AlertDialog alertFavNotSaved;
	private boolean isAlter = false;
	private int totalBinary = 0;
	private int totalFavoritos = 0;

	public static final String TOTAL_COD = "totalCod";
	public static final String TOTAL_BLOCO = "totalBloco";
	public static final String TOTAL_CAP = "totalCap";
	public static final String TOTAL_ITEMS = "totalItems";
	public static final String TEMPO_TOTAL_COD = "tempoTotalCod";
	public static final String TEMPO_TOTAL_BLOCO = "tempoTotalBloco";
	public static final String TEMPO_TOTAL_CAP = "tempoTotalCap";
	public static final String TEMPO_TOTAL = "tempoTotal";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dialogue = new ProgressDialog(MainActivity.this);
		dialogue.setProgressStyle(1);
		dialogue.setProgress(0);
		dialogue.setMax(100);
		dialogue.setTitle("A carregar ICD10..");

		homeBtn = (ImageButton) findViewById(R.id.imageHome);
		searchBtn = (ImageButton) findViewById(R.id.imageSearch);
		favoriteBtn = (ImageButton) findViewById(R.id.imageFavorite);
		statisticsBtn = (ImageButton) findViewById(R.id.imageChart);
		trainingBtn = (ImageButton) findViewById(R.id.imageQuestion);

		homeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isFinished) {

					Intent intentHome = new Intent(getApplicationContext(),
							HomeActivity.class);

					startActivity(intentHome);

				} else {
					Toast.makeText(getApplicationContext(),
							"Carregue primeiro os ficheiros pf",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentPesquisar = new Intent(getApplicationContext(),
						PesquisarActivity.class);

				startActivity(intentPesquisar);
			}
		});

		favoriteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentFavoritos = new Intent(getApplicationContext(),
						FavoritosActivity.class);

				startActivity(intentFavoritos);
			}
		});

		statisticsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isFinished) {
					Intent intentEstatisticas = new Intent(
							getApplicationContext(), EstatisticasActivity.class);
					intentEstatisticas.putExtra("estatisticas",
							estatisticasHashMap);
					startActivity(intentEstatisticas);
				} else {
					Toast.makeText(getApplicationContext(),
							"Carregue primeiro os ficheiros pf.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		trainingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					Intent intentTraining = new Intent(getApplicationContext(),
							QuestoesActivity.class);

					startActivity(intentTraining);
			}
		});	

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		alertFastWay = builder
				.setMessage(
						"Modo Rápido pressupõe formato correto do ficheiro.\nPretende continuar?")
				.setPositiveButton(R.string.ok_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								new myTask().execute();
							}
						})
				.setNegativeButton(R.string.cancelar_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
							}
						}).create();

		alertFavNotSaved = builder
				.setMessage(
						"Favoritos alterados.\nPretende guardar os favoritos?")
				.setPositiveButton(R.string.ok_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								guardarInternamente();
								MainActivity.super.onBackPressed();
								finish();
							}
						})
				.setNegativeButton(R.string.cancelar_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								MainActivity.super.onBackPressed();
							}

						}).create();
	}

	@Override
	public void onBackPressed() {
		lerInternamente();
		if (isAlter) {
			alertFavNotSaved.show();
		} else {
			super.onBackPressed();
		}

	}

	public boolean isExternalStorageWritable() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.upload:

			return true;

		case R.id.fastWay:
			if (!isFinished) {
				alertFastWay.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"O ICD10 já foi carregado!", Toast.LENGTH_SHORT).show();
			}
			return true;

		case R.id.slowWay:
			if (!isFinished) {
				slowWay = true;
				new myTask().execute();
			} else {
				Toast.makeText(getApplicationContext(),
						"O ICD10 já foi carregado!", Toast.LENGTH_SHORT).show();
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	class myTask extends AsyncTask<String, String, String> {
		private ProgressDialog dialogue;

		@Override
		protected void onPreExecute() {

			dialogue = new ProgressDialog(MainActivity.this);
			dialogue.setProgressStyle(1);
			dialogue.setProgress(0);
			dialogue.setMax(100);
			dialogue.setTitle("A carregar ICD10...");
			dialogue.setCancelable(false);
			dialogue.show();

			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			lerExternamente();
			dialogue.dismiss();
			return "ok";
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);

		}

		protected void onPostExecute(String result) {
			if (result.equals("ok")) {
				Toast.makeText(getApplicationContext(),
						"ICD10 carregado com sucesso!", Toast.LENGTH_SHORT)
						.show();
				isFinished = true;
			}
		}

		public void lerExternamente() {
			if (isExternalStorageWritable()) {
				Environment.getExternalStorageDirectory();
				File fileCapitulos = new File(
						Environment.getExternalStorageDirectory(), ICDCAP);
				File fileBlocos = new File(
						Environment.getExternalStorageDirectory(), ICDBLOCO);
				File fileCodigos = new File(
						Environment.getExternalStorageDirectory(), ICDCOD);

				FileReader fileReaderCapitulos = null;
				BufferedReader brCapitulos = null;
				FileReader fileReaderBlocos = null;
				BufferedReader brBlocos = null;
				FileReader fileReaderCodigos = null;
				BufferedReader brCodigos = null;

				String[] tokensCapitulo = null;
				String[] tokensBloco = null;
				String[] tokensCodigo = null;

				Capitulo cap = new Capitulo();
				Bloco bloco = new Bloco();
				CodigoTerminal codT = new CodigoTerminal();
				CodigoNaoTerminal codN = new CodigoNaoTerminal();

				try {
					dialogue.show();
					fileReaderCapitulos = new FileReader(fileCapitulos);
					brCapitulos = new BufferedReader(fileReaderCapitulos);
					fileReaderBlocos = new FileReader(fileBlocos);
					brBlocos = new BufferedReader(fileReaderBlocos);
					fileReaderCodigos = new FileReader(fileCodigos);
					brCodigos = new BufferedReader(fileReaderCodigos);
					String linha1;
					String linha2;
					String linha3;

					tempoTotalInicio = System.currentTimeMillis();
					tempoInicialCapitulos = System.currentTimeMillis();
					while ((linha1 = brCapitulos.readLine()) != null) {// CAPITULO
						tokensCapitulo = linha1.split(";");
						cap.setCodigo(tokensCapitulo[0]);
						cap.setNome(tokensCapitulo[1]);
						listaCapitulos.add(cap);
						cap = new Capitulo();
						countTotalCapitulos++;

					}
					tempoFinalCapitulos = System.currentTimeMillis();
					double tempoTotalCapitulos = (tempoFinalCapitulos - tempoInicialCapitulos) / 1000;
					dialogue.incrementProgressBy(10);

					tempoInicialBlocos = System.currentTimeMillis();
					while ((linha2 = brBlocos.readLine()) != null) {// BLOCO
						tokensBloco = linha2.split(";");

						bloco.setCodigo(tokensBloco[0]);
						bloco.setCodBlocoFin(tokensBloco[1]);
						bloco.setCodCapitulo(tokensBloco[2]);
						bloco.setNome(tokensBloco[3]);
						listaBlocos.add(bloco);
						bloco = new Bloco();
						countTotalItemsBlocos++;
					}
					tempoFinalBlocos = System.currentTimeMillis();
					double tempoTotalBlocos = (tempoFinalBlocos - tempoInicialBlocos) / 1000;
					dialogue.incrementProgressBy(20);

					fileReaderCodigos = new FileReader(fileCodigos);
					brCodigos = new BufferedReader(fileReaderCodigos);

					CodigoNaoTerminal aux = new CodigoNaoTerminal();
					tempoInicialCodigos = System.currentTimeMillis();
					while ((linha3 = brCodigos.readLine()) != null) {
						tokensCodigo = linha3.split(";");

						if (tokensCodigo[0].equals(NIVELARVORE[0])
								&& tokensCodigo[1].equals(TERMINALSN[0])) {
							codT.setNivelArvore(NIVELARVORE[0].charAt(0));
							codT.setTerminalSN(TERMINALSN[0].charAt(0));
							codT.setCodCapitulo(tokensCodigo[3]);
							codT.setCodBlocoIni(tokensCodigo[4]);
							codT.setCodigo(tokensCodigo[5]);
							codT.setNome(tokensCodigo[8]);
							listaCodigosFim.add(codT);
							listaCodigos.add(codT);
						}

						if (tokensCodigo[0].equals(NIVELARVORE[0])
								&& tokensCodigo[1].equals(TERMINALSN[1])) {
							codN.setNivelArvore(NIVELARVORE[0].charAt(0));
							codN.setTerminalSN(TERMINALSN[1].charAt(0));
							codN.setCodCapitulo(tokensCodigo[3]);
							codN.setCodBlocoIni(tokensCodigo[4]);
							codN.setCodigo(tokensCodigo[5]);
							codN.setNome(tokensCodigo[8]);
							listaCodigosPai.add(codN);
							listaCodigos.add(codN);
							aux = codN;
						}

						if (tokensCodigo[0].equals(NIVELARVORE[1])
								&& tokensCodigo[1].equals(TERMINALSN[0])) {
							codT.setNivelArvore(NIVELARVORE[1].charAt(0));
							codT.setTerminalSN(TERMINALSN[0].charAt(0));
							codT.setCodCapitulo(tokensCodigo[3]);
							codT.setCodBlocoIni(tokensCodigo[4]);
							codT.setCodigo(tokensCodigo[5]);
							codT.setNome(tokensCodigo[8]);
							listaSubcodigos.add(codT);
							aux.addSubcodigo(codT);

						}
						codT = new CodigoTerminal();
						codN = new CodigoNaoTerminal();
						countTotalItemsCodigos++;
					}
					tempoFinalCodigos = System.currentTimeMillis();
					double tempoTotalCodigos = (tempoFinalCodigos - tempoInicialCodigos) / 1000;

					dialogue.incrementProgressBy(20);

					estruturarICD10();

					tempoTotalFim = System.currentTimeMillis();

					double tempoTotal = (tempoTotalFim - tempoTotalInicio) / 1000;
					countTotalItems = (countTotalCapitulos
							+ countTotalItemsBlocos + countTotalItemsCodigos);
					estatisticasHashMap.put(TEMPO_TOTAL, tempoTotal);
					estatisticasHashMap.put(TEMPO_TOTAL_CAP,
							tempoTotalCapitulos);
					estatisticasHashMap
							.put(TEMPO_TOTAL_BLOCO, tempoTotalBlocos);
					estatisticasHashMap.put(TEMPO_TOTAL_COD, tempoTotalCodigos);
					estatisticasHashMap.put(TOTAL_ITEMS,
							(double) countTotalItems);
					estatisticasHashMap.put(TOTAL_CAP,
							(double) countTotalCapitulos);
					estatisticasHashMap.put(TOTAL_BLOCO,
							(double) countTotalItemsBlocos);
					estatisticasHashMap.put(TOTAL_COD,
							(double) countTotalItemsCodigos);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (brCapitulos != null || brBlocos != null
								|| brCodigos != null)
							brCapitulos.close();
						brBlocos.close();
						brCodigos.close();
						if (fileReaderCapitulos != null
								|| fileReaderBlocos != null
								|| fileReaderCodigos != null)
							fileReaderCapitulos.close();
						fileReaderBlocos.close();
						fileReaderCodigos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		public void estruturarICD10() {
			for (Capitulo capitulo : listaCapitulos) {
				for (Bloco bloco : listaBlocos) {
					if (capitulo.getCodigo().equals(bloco.getCodCapitulo())) {
						bloco.setCapitulo(capitulo);
						capitulo.addBloco(bloco);

					}
				}

			}

			dialogue.incrementProgressBy(5);

			if (slowWay) {

				for (Bloco bloco : listaBlocos) {
					for (CodigoTerminal codigo : listaCodigosFim) {
						if (codigo.getCodBlocoIni().equals(bloco.getCodigo())
								&& bloco.getCapitulo().getCodigo()
										.equals(codigo.getCodCapitulo())) {
							codigo.setBloco(bloco);
							bloco.addCodigo(codigo);
						}
					}

				}
				dialogue.incrementProgressBy(5);

				for (Bloco bloco : listaBlocos) {
					for (CodigoNaoTerminal codigo : listaCodigosPai) {
						if (codigo.getCodBlocoIni().equals(bloco.getCodigo())
								&& bloco.getCapitulo().getCodigo()
										.equals(codigo.getCodCapitulo())) {
							codigo.setBloco(bloco);
							bloco.addCodigo(codigo);
						}
					}

				}
				dialogue.incrementProgressBy(10);

				double d = listaCodigosPai.size() / 5;
				long i = (long) d;
				int contador = 1;
				for (CodigoNaoTerminal codigoPai : listaCodigosPai) {
					for (CodigoTerminal codigoFilho : listaSubcodigos) {
						if (codigoFilho.getCodigo().substring(0, 4)
								.equals(codigoPai.getCodigo().substring(0, 4))) {
							codigoPai.addSubcodigo(codigoFilho);

						}
					}
					if (i == contador) {
						dialogue.incrementProgressBy(5);
						i += i;
					}
					contador++;
				}

			} else {
				for (Bloco bloco : listaBlocos) {
					for (CodigoTerminal codigo : listaCodigos) {
						if (codigo.getCodBlocoIni().equals(bloco.getCodigo())
								&& bloco.getCapitulo().getCodigo()
										.equals(codigo.getCodCapitulo())) {
							codigo.setBloco(bloco);
							bloco.addCodigo(codigo);
						}

					}

				}
				dialogue.incrementProgressBy(40);

			}

			ICD10.getInstance().setICD10List(listaCapitulos);
			dialogue.incrementProgressBy(5);

			isFinished = true;
		}
	}

	private void lerInternamente() {
		FileInputStream input = null;
		ObjectInputStream inpStream = null;
		totalFavoritos = Favoritos.getInstance().returnTotal();
		try {
			input = openFileInput(FavoritosActivity.BINARYFILE);
			inpStream = new ObjectInputStream(input);

			totalBinary = Favoritos.returnTotalBinary((Favoritos) inpStream
					.readObject());

			if (totalBinary != totalFavoritos && totalFavoritos > 0) {
				isAlter = true;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			if (totalFavoritos > 0) {
				isAlter = true;
			}
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inpStream != null)
					inpStream.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void guardarInternamente() {
		FileOutputStream output = null;
		ObjectOutputStream outStream = null;
		try {
			output = openFileOutput(FavoritosActivity.BINARYFILE,
					Context.MODE_PRIVATE);
			outStream = new ObjectOutputStream(output);
			outStream.writeObject(Favoritos.getInstance());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				Toast.makeText(getApplicationContext(),
						"Favoritos guardados com sucesso!", Toast.LENGTH_SHORT)
						.show();
				if (outStream != null)
					outStream.close();
				if (output != null)
					output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
