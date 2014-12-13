package com.example.icd10_version2010.fragmento;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.example.icd10_version2010.Adapters.BlocoLevel;
import com.example.icd10_version2010.modelo.Capitulo;
import com.example.icd10_version2010.modelo.CodigoTerminal;
import com.example.icd10_version2010.modelo.Favoritos;

public class CapituloFavorito extends Fragment implements Atualizavel {
	private static final String TAGCAP = "capituloFrag";
	private View rootView;
	private ListView listView;
	private ArrayAdapter<Capitulo> adaptador;
	private boolean isLongClick;
	private AlertDialog alert;
	private Capitulo cap;
	private BlocoLevel bl;
	private ViewFlipper flip;
	private ExpandableListView expListView;
	private TextView txtTitle;

	public CapituloFavorito() {
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_capitulofavorito,
				container, false);

		txtTitle = (TextView) rootView.findViewById(R.id.titleCap);

		flip = (ViewFlipper) rootView.findViewById(R.id.viewFlipper1);

		flip.setDisplayedChild(flip.indexOfChild(rootView
				.findViewById(R.id.flip1)));

		listView = (ListView) rootView.findViewById(R.id.listViewCapFavoritos);
		expListView = (ExpandableListView) rootView
				.findViewById(R.id.ParentLevelCapitulo);

		atualizarVista();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				cap = (Capitulo) listView.getAdapter().getItem(arg2);

				try {
					if (Favoritos.getInstance().existsCapitulo(cap)) {
						flip.setDisplayedChild(flip.indexOfChild(rootView
								.findViewById(R.id.flip2)));
						if (flip.getDisplayedChild() == flip
								.indexOfChild(rootView.findViewById(R.id.flip2))
								&& cap != null) {
							txtTitle.setText(cap.getCodigo() + " "
									+ cap.getNome());
						}
						bl = new BlocoLevel((FavoritosActivity) getActivity(),
								cap.getAll());
						expListView.setAdapter(bl);

						expListView
								.setOnGroupExpandListener(new OnGroupExpandListener() {
									int len = bl.getGroupCount();

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
				} catch (Exception e) {
					Toast.makeText((FavoritosActivity) getActivity(),
							"Ocorreu um erro interno.", Toast.LENGTH_SHORT)
							.show();
					e.printStackTrace();
				}
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				isLongClick = true;
				cap = (Capitulo) listView.getAdapter().getItem(arg2);
				alert.show();
				return false;
			}
		});

		txtTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flip.setDisplayedChild(flip.indexOfChild(rootView
						.findViewById(R.id.flip1)));
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(
				(FavoritosActivity) getActivity());
		alert = builder
				.setMessage("Remover Favorito?")
				.setPositiveButton(R.string.ok_alert,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Favoritos.getInstance()
										.removerFavoritoCapitulo(cap);
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
		adaptador = new ArrayAdapter<Capitulo>(
				(FavoritosActivity) getActivity(),
				android.R.layout.simple_list_item_1, Favoritos.getInstance()
						.getAllCapitulos());
		listView.setAdapter(adaptador);
	}

	@Override
	public ArrayAdapter<Capitulo> getAdapter() {
		return adaptador;
	}
}
