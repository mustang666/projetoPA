package com.example.icd10_version2010.Adapters;

import java.util.ArrayList;

import com.example.icd10_version2010.FavoritosActivity;
import com.example.icd10_version2010.R;
import com.example.icd10_version2010.modelo.CodigoTerminal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SubCodigosAdapterList extends BaseAdapter {

	private ArrayList<CodigoTerminal> listaSubCodigos;
	private Context context;

	public SubCodigosAdapterList(Context context,
			ArrayList<CodigoTerminal> listaSubCodigos) {
		this.listaSubCodigos = listaSubCodigos;
		this.context = context;
	}

	@Override
	public int getCount() {

		return listaSubCodigos.size();
	}

	@Override
	public Object getItem(int position) {
		return listaSubCodigos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CodigoTerminal subCodigo = (CodigoTerminal) getItem(position);

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.subcodigoview, null);
		}

		TextView txtSubCod = (TextView) convertView
				.findViewById(R.id.subCodigo);
		txtSubCod.setText(subCodigo.getCodigo() + " " + subCodigo.getNome());

		return convertView;
	}

}
