package com.manuel.personscrud;

import java.util.List;

import com.manuel.personscrud.adapter.PersonaAdapter;
import com.manuel.personscrud.bd.PersonaSqliteHelper;
import com.manuel.personscrud.model.Persona;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ListActivity {

	protected ListView listaPersonas;
	protected PersonaSqliteHelper conexion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		conexion = new PersonaSqliteHelper(this);

		listaPersonas = getListView();
		
		listaPersonas.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long id) {
				// TODO Auto-generated method stub
				final Persona persona = (Persona) listaPersonas
						.getItemAtPosition(position);

				AlertDialog.Builder dialogo = new AlertDialog.Builder(
						MainActivity.this);

				OnClickListener dialogoListener = new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == DialogInterface.BUTTON_POSITIVE) {
							// eliminamos a la persona
							conexion.deletePersona(persona);
							
							consultar();
							
							Log.d("Borrado", persona.toString());

							Toast.makeText(
									MainActivity.this,
									"Verga que rata borraste a "
											+ persona.getNombre() + "!!!",
									Toast.LENGTH_LONG).show();

						}else{
							Toast.makeText(
									MainActivity.this,
									"Uff se salvó esa rata!!!",
									Toast.LENGTH_LONG).show();
						}
					}
				};

				dialogo.setTitle("Pregunta")
						.setMessage(
								"¿Realmente desea eliminar el registro "
										+ persona + "?")
						.setPositiveButton("Claro Menol!", dialogoListener)
						.setNegativeButton("Mejor no!", dialogoListener).show();
				
				return true;
			}
			
		});
	}

	protected void consultar() {
		List<Persona> personas = conexion.findAll();
		setListAdapter(new PersonaAdapter(this, personas));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClickAdd(View view) {
		Intent intent = new Intent(this, PersonaFormActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		consultar();
		super.onResume();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		verForm((Persona) listaPersonas.getItemAtPosition(position));
	}

	protected void verForm(Persona persona) {
		Intent intent = new Intent(this, PersonaFormActivity.class);

		Bundle bundle = new Bundle();

		bundle.putInt("id", persona.getId());

		intent.putExtras(bundle);

		this.startActivity(intent);
	}

}
