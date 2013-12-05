package com.manuel.personscrud;

import com.manuel.personscrud.bd.PersonaSqliteHelper;
import com.manuel.personscrud.model.Persona;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonaFormActivity extends Activity {

	protected TextView nombre, telefono;
	protected Persona persona;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.persona_form);
		
		nombre = (TextView) findViewById(R.id.txtName);
		telefono = (TextView) findViewById(R.id.txtPhone);
		
		Bundle bundle = getIntent().getExtras();
		
		if(bundle != null){
			int id = bundle.getInt("id");
			
			persona = new PersonaSqliteHelper(this).findPersona(id);
			
			nombre.setText(persona.getNombre());
			telefono.setText(persona.getTelefono());
			
			Log.d("FORM","Se va a editar la persona: " + persona);
		}else{
			persona = new Persona();
			Log.d("FORM","Se va a crear una nueva persona");
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.persona_form, menu);
		return true;
	}
	
	public void onClickAtras(View view)	{
		finish();
	}
	
	public void onClickGuardar(View view)	{
		persona.setNombre(nombre.getText().toString());
		persona.setTelefono(telefono.getText().toString());
		
		new PersonaSqliteHelper(this).guardarPersona(persona);
		
		finish();
		
		Toast.makeText(this, "Datos guardados!!!", Toast.LENGTH_SHORT).show();
	}

}
