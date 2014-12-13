package com.example.icd10_version2010.Adapters;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.example.icd10_version2010.ExpListView;
import com.example.icd10_version2010.R;
import com.example.icd10_version2010.modelo.Bloco;
import com.example.icd10_version2010.modelo.CodigoTerminal;
import com.example.icd10_version2010.modelo.Favoritos;

public class BlocoLevel extends BaseExpandableListAdapter {
	private Context context;
	private ArrayList<Bloco> listaBlocos;
	private CodigoLevel codlev;
	private ExpListView thirdLevelExpList;
	private AlertDialog alert;
	private boolean isLongClick;
	private CodigoTerminal codigo;
	private int anterior = -1;

	public BlocoLevel(Context context, ArrayList<Bloco> listaBlocos) {
		this.context = context;
		this.listaBlocos = listaBlocos;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<CodigoTerminal> listaCodigos = ((Bloco) listaBlocos
				.get(groupPosition)).getAll();
		return listaCodigos.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		Bloco bloco = (Bloco) getGroup(groupPosition);

		thirdLevelExpList = new ExpListView(context);
		codlev = new CodigoLevel(context, bloco.getAll());
		thirdLevelExpList.setAdapter(codlev);

		thirdLevelExpList.setGroupIndicator(null);

		thirdLevelExpList.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				int len = codlev.getGroupCount();

				for (int i = 0; i < len; i++) {
					if (i != groupPosition) {
						parent.collapseGroup(i);
					}
				}

				if (groupPosition > anterior) {
					parent.collapseGroup(anterior);
					parent.expandGroup(groupPosition);
					anterior = groupPosition;
				}
				
				parent.collapseGroup(groupPosition);
				
				if (groupPosition < anterior) {
					parent.collapseGroup(anterior);
					anterior = groupPosition;
				}

				return false;
			}
		});

		thirdLevelExpList
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						if (!codlev.isEmpty()) {
							isLongClick = true;
							codigo = (CodigoTerminal) thirdLevelExpList.getAdapter()
									.getItem(arg2);
							if (codigo != null) {
								alert.show();
								return true;
							}
						}
						return false;
					}
				});

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		alert = builder
				.setMessage("Guardar código como Favorito?")
				.setPositiveButton(R.string.ok_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								if (!Favoritos.getInstance().existsCodigo(
										codigo)) {
									Favoritos.getInstance()
											.adicionarFavoritoCodigo(codigo);
									Toast.makeText(context,
											"Favorito guardado com sucesso!",
											Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(context,
											"Favorito já existe!",
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

		return thirdLevelExpList;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return listaBlocos.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return listaBlocos.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		Bloco b = (Bloco) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.blocoview, null);
		}

		TextView txtBloco = (TextView) convertView.findViewById(R.id.nomeBloco);
		txtBloco.setText(b.getCodigo() + " " + b.getNome());

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
