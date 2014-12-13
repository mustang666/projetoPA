package com.example.icd10_version2010.fragmento;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.icd10_version2010.R;
import com.example.icd10_version2010.modelo.ICD10;

public class CapituloPesquisar extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private View rootView;
	private ArrayAdapter<String> adapter;
	private static Context context;
	String[] lista;
	private AutoCompleteTextView searchPesquisar;

	public static CapituloPesquisar newInstance(int sectionNumber,
			Context contextAct) {
		CapituloPesquisar fragment = new CapituloPesquisar();
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

		lista = new String[ICD10.getInstance().getAll().size()];
		for (int i = 0; i < ICD10.getInstance().getAll().size(); i++) {
			lista[i] = ICD10.getInstance().getAll().get(i).getCodigo() + " "
					+ ICD10.getInstance().getAll().get(i).getNome();
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
