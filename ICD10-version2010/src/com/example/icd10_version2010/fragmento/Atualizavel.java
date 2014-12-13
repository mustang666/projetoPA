package com.example.icd10_version2010.fragmento;

import android.widget.ArrayAdapter;

public interface Atualizavel {

	public void atualizarVista();
	
	public ArrayAdapter<?> getAdapter();
}
