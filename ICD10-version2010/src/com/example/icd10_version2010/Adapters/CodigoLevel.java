package com.example.icd10_version2010.Adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.icd10_version2010.R;
import com.example.icd10_version2010.modelo.CodigoNaoTerminal;
import com.example.icd10_version2010.modelo.CodigoTerminal;

public class CodigoLevel extends BaseExpandableListAdapter {
	Context context;
	ArrayList<CodigoTerminal> listaCodigos;

	public CodigoLevel(Context context, ArrayList<CodigoTerminal> listaCodigos) {
		this.context = context;
		this.listaCodigos = listaCodigos;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if (listaCodigos.get(groupPosition) instanceof CodigoNaoTerminal) {
			ArrayList<CodigoTerminal> listaSubCodigos = ((CodigoNaoTerminal) listaCodigos
					.get(groupPosition)).getAll();
			return listaSubCodigos.get(childPosition);
		}

		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		CodigoTerminal subCodigo = (CodigoTerminal) getChild(groupPosition,
				childPosition);

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

	@Override
	public int getChildrenCount(int groupPosition) {
		if (listaCodigos.get(groupPosition) instanceof CodigoNaoTerminal) {
			return ((CodigoNaoTerminal) listaCodigos.get(groupPosition))
					.getAll().size();
		}
		return 0;
	}

	@Override
	public CodigoTerminal getGroup(int groupPosition) {
		return listaCodigos.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return listaCodigos.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (getGroup(groupPosition) instanceof CodigoNaoTerminal) {
		
			CodigoNaoTerminal cod = (CodigoNaoTerminal) getGroup(groupPosition);
			
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.codigopaiview, null);
			TextView cap = (TextView) convertView.findViewById(R.id.codigoPai);
			cap.setText(cod.getCodigo() + " " + cod.getNome());
		} else {
			CodigoTerminal cod = (CodigoTerminal) getGroup(groupPosition);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.codigoview, null);
			TextView cap = (TextView) convertView.findViewById(R.id.codigo);
			cap.setText(cod.getCodigo() + " " + cod.getNome());
			
		}

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