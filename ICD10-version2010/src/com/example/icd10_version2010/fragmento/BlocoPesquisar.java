package com.example.icd10_version2010.fragmento;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.icd10_version2010.R;
import com.example.icd10_version2010.modelo.Bloco;
import com.example.icd10_version2010.modelo.Capitulo;
import com.example.icd10_version2010.modelo.ICD10;

public class BlocoPesquisar extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	private View rootView;
	private ArrayAdapter<String> adapter;
	private static Context context;
	String[] lista;
	private static ArrayList<Bloco> listaBlocos;
	private AutoCompleteTextView searchPesquisar;

	public static BlocoPesquisar newInstance(int sectionNumber,
			Context contextAct) {
		BlocoPesquisar fragment = new BlocoPesquisar();
		listaBlocos = new ArrayList<Bloco>();
		context = contextAct;
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_pesquisar, container,
				false);
		for (Capitulo capitulo : ICD10.getInstance().getAll()) {
			for (Bloco b : capitulo.getAll()) {
				listaBlocos.add(b);
			}
		}

		lista = new String[listaBlocos.size()];
		for (int i = 0; i < listaBlocos.size(); i++) {

			lista[i] = listaBlocos.get(i).getCodigo() + " "
					+ listaBlocos.get(i).getNome();
		}
		adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_dropdown_item_1line, lista);
		searchPesquisar = (AutoCompleteTextView) rootView
				.findViewById(R.id.searchPesquisar);
		searchPesquisar.setThreshold(1);

		searchPesquisar.setAdapter(adapter);

		return rootView;
	}

}
