package com.alsiten.memoriaexterna;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void guardarDatos(View v){
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			File raiz =Environment.getExternalStorageDirectory();
			File dir = new File(raiz.getAbsolutePath()+"/datos/miapp/loquequieras");
			dir.mkdirs();
				try{
				   File Archivo = new File(dir,((EditText) findViewById(R.id.Nombre)).getText().toString());	 
				   FileOutputStream fos =new FileOutputStream(Archivo);
				   PrintWriter pw = new PrintWriter(fos);
				   pw.println(((EditText) findViewById(R.id.Texto)).getText().toString());
				   pw.flush();
				   pw.close();
				} catch (FileNotFoundException e) {
					Toast.makeText(this, "Error escribiendo en "+((EditText) findViewById(R.id.Nombre)).getText().toString(), Toast.LENGTH_LONG);
					//Toast.makeText(this, "Error:"+e.getMessage(), Toast.LENGTH_LONG);
				}
			
		}else
		{
			Toast.makeText(this, "No se puede guardar el archivo.", Toast.LENGTH_LONG);
		}
	}
	
	public void leerDatos(View v){
		File raiz= Environment.getExternalStorageDirectory();
		File dir = new File(raiz.getAbsolutePath()+"/datos/miapp/loquequieras");
		dir.mkdirs();
			try{
				 File Archivo = new File(dir,((EditText) findViewById(R.id.Nombre)).getText().toString());
				 FileInputStream fis = new FileInputStream(Archivo);
				 InputStreamReader isr = new InputStreamReader(fis);
				 BufferedReader buf = new BufferedReader(isr);
		//limpiar el contenido a presentar antes de agregar las lineas nuevas
				 ((EditText) findViewById(R.id.Texto)).setText("");
				 //leer la primera linea
				 String linea = buf.readLine();
				 while (linea!=null){  //y mientras haya lineas que leer, agregarlas
					 ((EditText) findViewById(R.id.Texto)).append(linea);
					 linea = buf.readLine();
				 }
				 isr.close();
			} catch(IOException e){
				Toast.makeText(this, "No se leer el archivo.", Toast.LENGTH_LONG);
			}
	}


}
