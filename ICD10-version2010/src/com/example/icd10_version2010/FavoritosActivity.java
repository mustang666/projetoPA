package com.example.icd10_version2010;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.icd10_version2010.fragmento.BlocoFavorito;
import com.example.icd10_version2010.fragmento.CapituloFavorito;
import com.example.icd10_version2010.fragmento.CodigoFavorito;
import com.example.icd10_version2010.modelo.Favoritos;

public class FavoritosActivity extends Activity implements
		ActionBar.TabListener {

	public static final String BINARYFILE = "binary";
	private boolean isUploaded = false;

	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	private CapituloFavorito capFavorito;
	private BlocoFavorito blocoFavorito;
	private CodigoFavorito codFavorito;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favoritos);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		if (fragment instanceof CapituloFavorito && capFavorito == null) {
			capFavorito = ((CapituloFavorito) fragment);
		} else if (fragment instanceof BlocoFavorito && blocoFavorito == null) {
			blocoFavorito = ((BlocoFavorito) fragment);
		} else if (fragment instanceof CodigoFavorito && codFavorito == null) {
			codFavorito = ((CodigoFavorito) fragment);
		}
	}

	private boolean areFragmValid() {
		return (capFavorito != null && blocoFavorito != null && codFavorito != null);
	}

	private void notifyFragments() {
		if (areFragmValid()) {
			capFavorito.atualizarVista();
			blocoFavorito.atualizarVista();
			codFavorito.atualizarVista();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.favoritos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.persistenciaFavoritos:
			return true;

		case R.id.saveInt:
			if (Favoritos.getInstance().returnTotal() > 0) {
				guardarInternamente();
			} else {
				Toast.makeText(getApplicationContext(),
						"Não há favoritos a guardar!", Toast.LENGTH_SHORT)
						.show();
			}

			return true;

		case R.id.readInt:
			if (!isUploaded) {
				lerInternamente();
			} else {
				Toast.makeText(getApplicationContext(),
						"Favoritos já carregados!", Toast.LENGTH_SHORT).show();
			}

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {

	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new CapituloFavorito();
				break;
			case 1:
				fragment = new BlocoFavorito();
				break;
			case 2:
				fragment = new CodigoFavorito();
				break;

			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	private void guardarInternamente() {
		FileOutputStream output = null;
		ObjectOutputStream outStream = null;
		try {
			output = openFileOutput(BINARYFILE, Context.MODE_PRIVATE);
			outStream = new ObjectOutputStream(output);
			outStream.writeObject(Favoritos.getInstance());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				Toast.makeText(getApplicationContext(),
						"Favoritos guardados com sucesso!", Toast.LENGTH_SHORT)
						.show();
				if (outStream != null)
					outStream.close();
				if (output != null)
					output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void lerInternamente() {
		FileInputStream input = null;
		ObjectInputStream inpStream = null;
		try {
			input = openFileInput(BINARYFILE);
			inpStream = new ObjectInputStream(input);
			Favoritos.getInstance().setVerifyingIfAlreadyExists(
					(Favoritos) inpStream.readObject());
			notifyFragments();

		} catch (FileNotFoundException e) {
			Toast.makeText(getApplicationContext(),
					"Não existem favoritos guardados!", Toast.LENGTH_SHORT)
					.show();

			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				isUploaded = true;
				if (inpStream != null)
					inpStream.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
