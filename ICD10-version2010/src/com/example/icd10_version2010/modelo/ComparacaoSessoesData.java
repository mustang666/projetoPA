package com.example.icd10_version2010.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.DateFormat;

import pt.ipleiria.estg.dei.is.pa.coleccoes.Comparacao;

public class ComparacaoSessoesData implements Comparacao<Sessao> {
	@Override
	public int comparar(Sessao s1, Sessao s2) {
		Date T1 = s1.getDataSessao();
		Date T2 = s2.getDataSessao();

		int res = (T1.before(T2) ? 1 : T1.after(T2) ? -1 : 0);

		if (res == 0) {
			float sc1 = s1.getScore();
			float sc2 = s2.getScore();

			res = (sc1 < sc2 ? 1 : sc1 > sc2 ? -1 : 0);
		}

		return res;
	}
}
