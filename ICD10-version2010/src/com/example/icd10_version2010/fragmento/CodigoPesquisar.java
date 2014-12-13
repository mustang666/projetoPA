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
import com.example.icd10_version2010.modelo.CodigoTerminal;
import com.example.icd10_version2010.modelo.ICD10;

public class CodigoPesquisar extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	private View rootView;
	private ArrayAdapter<String> adapter;
	private static Context context;
	String[] lista;
	private AutoCompleteTextView searchPesquisar;
	private static ArrayList<CodigoTerminal> listaCodigos;

	public static CodigoPesquisar newInstance(int sectionNumber,
			Context contextAct) {
		CodigoPesquisar fragment = new CodigoPesquisar();
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

		return rootView;
	}

}
