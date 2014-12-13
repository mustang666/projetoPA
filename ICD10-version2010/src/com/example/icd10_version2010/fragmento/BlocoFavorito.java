package com.example.icd10_version2010.fragmento;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.icd10_version2010.FavoritosActivity;
import com.example.icd10_version2010.R;
import com.example.icd10_version2010.Adapters.CodigoLevel;
import com.example.icd10_version2010.modelo.Bloco;
import com.example.icd10_version2010.modelo.CodigoTerminal;
import com.example.icd10_version2010.modelo.Favoritos;

public class BlocoFavorito extends Fragment implements Atualizavel {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private View rootView;
	private TextView txtTitle;
	private ViewFlipper flip;
	private ListView listView;
	private ExpandableListView expListView;
	private Bloco bloco;
	private CodigoLevel codlev;
	private boolean isLongClick;
	private Dialog alert;
	private ArrayAdapter<Bloco> adaptador;

	public BlocoFavorito() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_blocofavorito, container,
				false);

		txtTitle = (TextView) rootView.findViewById(R.id.titleBloco);

		flip = (ViewFlipper) rootView.findViewById(R.id.viewFlipper2);

		flip.setDisplayedChild(flip.indexOfChild(rootView
				.findViewById(R.id.flipBloco1)));

		listView = (ListView) rootView
				.findViewById(R.id.listViewBlocoFavoritos);

		expListView = (ExpandableListView) rootView
				.findViewById(R.id.ParentLevelBloco);

		atualizarVista();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				bloco = (Bloco) listView.getAdapter().getItem(arg2);

				if (Favoritos.getInstance().existsBloco(bloco)) {
					flip.setDisplayedChild(flip.indexOfChild(rootView
							.findViewById(R.id.flipBloco2)));
					if (flip.getDisplayedChild() == flip.indexOfChild(rootView
							.findViewById(R.id.flipBloco2)) && bloco != null) {
						txtTitle.setText(bloco.getCodigo() + " "
								+ bloco.getNome());
					}
					codlev = new CodigoLevel((FavoritosActivity) getActivity(),
							bloco.getAll());
					expListView.setAdapter(codlev);

					expListView
							.setOnGroupExpandListener(new OnGroupExpandListener() {
								int len = codlev.getGroupCount();

								@Override
								public void onGroupExpand(int groupPosition) {

									for (int i = 0; i < len; i++) {
										if (i != groupPosition) {
											expListView.collapseGroup(i);
										}
									}
								}

							});
				}
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				isLongClick = true;
				bloco = (Bloco) listView.getAdapter().getItem(arg2);
				alert.show();
				return false;
			}
		});

		txtTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flip.setDisplayedChild(flip.indexOfChild(rootView
						.findViewById(R.id.flipBloco1)));
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(
				(FavoritosActivity) getActivity());
		alert = builder
				.setMessage("Remover Favorito?")
				.setPositiveButton(R.string.ok_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Favoritos.getInstance().removerFavoritoBloco(
										bloco);
								Toast.makeText(
										(FavoritosActivity) getActivity(),
										"Favorito removido com sucesso!",
										Toast.LENGTH_SHORT).show();
								atualizarVista();
								isLongClick = false;
							}
						})
				.setNegativeButton(R.string.cancelar_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								isLongClick = false;
							}
						}).create();

		return rootView;
	}

	@Override
	public void atualizarVista() {
		adaptador = new ArrayAdapter<Bloco>((FavoritosActivity) getActivity(),
				android.R.layout.simple_list_item_1, Favoritos.getInstance()
						.getAllBlocos());
		listView.setAdapter(adaptador);
	}

	@Override
	public ArrayAdapter<Bloco> getAdapter() {
		return adaptador;
	}
}
