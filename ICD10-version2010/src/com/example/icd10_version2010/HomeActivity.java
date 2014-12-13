package com.example.icd10_version2010;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.example.icd10_version2010.Adapters.CapituloLevel;
import com.example.icd10_version2010.modelo.Capitulo;
import com.example.icd10_version2010.modelo.Favoritos;
import com.example.icd10_version2010.modelo.ICD10;

public class HomeActivity extends Activity {
	private static final String BINARYFILE = null;
	ExpandableListView explvlist;
	Context context = this;
	CapituloLevel cl;
	private boolean isLongClick;
	private AlertDialog alert;
	private Capitulo cap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		explvlist = (ExpandableListView) findViewById(R.id.ParentLevel);
		cl = new CapituloLevel(context, ICD10.getInstance().getAll());
		explvlist.setAdapter(cl);
		explvlist.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				int len = cl.getGroupCount();
				for (int i = 0; i < len; i++) {
					if (i != groupPosition) {
						explvlist.collapseGroup(i);
					}
				}
				explvlist.setSelectedGroup(groupPosition);
			}

		});

		explvlist.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (!cl.isEmpty()) {
					isLongClick = true;

					cap = (Capitulo) explvlist.getAdapter().getItem(arg2);
					if (cap != null) {
						alert.show();
					}
				}

				return false;
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
		alert = builder
				.setMessage("Guardar capítulo como Favorito?")
				.setPositiveButton(R.string.ok_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								try {
									if (!Favoritos.getInstance()
											.existsCapitulo(cap)) {
										Favoritos.getInstance()
												.adicionarFavoritoCapitulo(cap);
										Toast.makeText(
												context,
												"Favorito guardado com sucesso!",
												Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(context,
												"Favorito já existe!",
												Toast.LENGTH_SHORT).show();
									}
								} catch (Exception e) {
									e.printStackTrace();
									Toast.makeText(
											context,
											"Não foi possivel guardar o favorito\nTente novamente pf.",
											Toast.LENGTH_SHORT).show();

								}

								isLongClick = false;
							}
						})
				.setNegativeButton(R.string.cancelar_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								isLongClick = false;
							}
						}).create();

	}

}
