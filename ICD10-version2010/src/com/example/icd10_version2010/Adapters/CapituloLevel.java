package com.example.icd10_version2010.Adapters;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icd10_version2010.ExpListView;
import com.example.icd10_version2010.R;
import com.example.icd10_version2010.modelo.Bloco;
import com.example.icd10_version2010.modelo.Capitulo;
import com.example.icd10_version2010.modelo.Favoritos;

public class CapituloLevel extends BaseExpandableListAdapter {
	private Context context;
	private ArrayList<Capitulo> listaCapitulos;
	private BlocoLevel bl;
	private ExpListView secondLevelExpList;
	private boolean isLongClick;
	private AlertDialog alert;
	private Bloco bloco;

	public CapituloLevel(Context context, ArrayList<Capitulo> listaCapitulos) {
		this.context = context;
		this.listaCapitulos = listaCapitulos;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<Bloco> listaBlocos = ((Capitulo) listaCapitulos
				.get(groupPosition)).getAll();
		return listaBlocos.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		Capitulo cap = (Capitulo) getGroup(groupPosition);

		secondLevelExpList = new ExpListView(context);

		bl = new BlocoLevel(context, cap.getAll());
		secondLevelExpList.setAdapter(bl);

		secondLevelExpList.setGroupIndicator(null);

		secondLevelExpList
				.setOnGroupExpandListener(new OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int groupPosition) {
						int len = bl.getGroupCount();
						for (int i = 0; i < len; i++) {
							if (i != groupPosition) {
								secondLevelExpList.collapseGroup(i);

							}
						}
					}
				});

		secondLevelExpList
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						if (!bl.isEmpty()) {
							isLongClick = true;
							bloco = (Bloco) secondLevelExpList.getAdapter()
									.getItem(arg2);
							if (bloco != null) {
								alert.show();
								return true;
							}
						}

						return false;
					}
				});

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		alert = builder
				.setMessage("Guardar bloco como Favorito?")
				.setPositiveButton(R.string.ok_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								if (!Favoritos.getInstance().existsBloco(bloco)) {
									Favoritos.getInstance()
											.adicionarFavoritoBloco(bloco);
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

		return secondLevelExpList;

	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Capitulo getGroup(int groupPosition) {
		return listaCapitulos.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return listaCapitulos.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		Capitulo cap = (Capitulo) getGroup(groupPosition);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.capituloview, null);

		}

		TextView capTextView = (TextView) convertView
				.findViewById(R.id.capitulo);
		capTextView.setText(cap.getCodigo() + " " + cap.getNome());
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
