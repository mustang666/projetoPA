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
import com.example.icd10_version2010.Adapters.SubCodigosAdapterList;
import com.example.icd10_version2010.modelo.Bloco;
import com.example.icd10_version2010.modelo.CodigoNaoTerminal;
import com.example.icd10_version2010.modelo.CodigoTerminal;
import com.example.icd10_version2010.modelo.Favoritos;

public class CodigoFavorito extends Fragment implements Atualizavel {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private View rootView;
	private TextView txtTitle;
	private ViewFlipper flip;
	private ListView listView;
	private ListView list;
	private ExpandableListView expListView;
	private CodigoTerminal codigo;
	private CodigoLevel subcodlev;
	private boolean isLongClick;
	private Dialog alert;
	private ArrayAdapter<CodigoTerminal> adaptador;
	private SubCodigosAdapterList adaptadorSubcodigos;

	public CodigoFavorito() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_codigofavorito,
				container, false);

		txtTitle = (TextView) rootView.findViewById(R.id.titleCodigo);

		flip = (ViewFlipper) rootView.findViewById(R.id.viewFlipper3);

		flip.setDisplayedChild(flip.indexOfChild(rootView
				.findViewById(R.id.flipCodigo1)));

		listView = (ListView) rootView
				.findViewById(R.id.listViewCodigoFavoritos);
		list = (ListView) rootView.findViewById(R.id.ParentLevelCodigo);

		atualizarVista();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				codigo = (CodigoTerminal) listView.getAdapter().getItem(arg2);

				if (Favoritos.getInstance().existsCodigo(codigo)) {
					if (codigo instanceof CodigoNaoTerminal) {
						flip.setDisplayedChild(flip.indexOfChild(rootView
								.findViewById(R.id.flipCodigo2)));
						if (flip.getDisplayedChild() == flip
								.indexOfChild(rootView
										.findViewById(R.id.flipCodigo2))
								&& codigo != null) {
							txtTitle.setText(codigo.getCodigo() + " "
									+ codigo.getNome());
						}

						adaptadorSubcodigos = new SubCodigosAdapterList(
								(FavoritosActivity) getActivity(), ((CodigoNaoTerminal)codigo)
										.getAll());

						list.setAdapter(adaptadorSubcodigos);
					} else {
						Toast.makeText((FavoritosActivity) getActivity(),
								"Este código é terminal!", Toast.LENGTH_SHORT)
								.show();
					}
				}
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				isLongClick = true;
				codigo = (CodigoTerminal) listView.getAdapter().getItem(arg2);
				alert.show();
				return false;
			}
		});

		txtTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flip.setDisplayedChild(flip.indexOfChild(rootView
						.findViewById(R.id.flipCodigo1)));
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(
				(FavoritosActivity) getActivity());
		alert = builder
				.setMessage("Remover Favorito?")
				.setPositiveButton(R.string.ok_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Favoritos.getInstance().removerFavoritoCodigo(
										codigo);
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
		adaptador = new ArrayAdapter<CodigoTerminal>((FavoritosActivity) getActivity(),
				android.R.layout.simple_list_item_1, Favoritos.getInstance()
						.getAllCodigos());
		listView.setAdapter(adaptador);
	}

	@Override
	public ArrayAdapter<CodigoTerminal> getAdapter() {
		return adaptador;
	}
}
