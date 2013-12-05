package com.manuel.personscrud.bd;

import java.util.LinkedList;
import java.util.List;

import com.manuel.personscrud.model.Persona;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PersonaSqliteHelper extends SQLiteOpenHelper {

	public static final String BD_NAME = "personas";
	public static final int VERSION = 5;

	public static final String[] columnas = { "id", "nombre", "telefono" };

	public PersonaSqliteHelper(Context context) {
		super(context, BD_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {

		Log.i("onCreate()", "Creando la Base de datos");

		arg0.execSQL("CREATE TABLE personas(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, telefono TEXT)");

		Log.i("onCreate()", "La tabla persona fué creada");

		arg0.execSQL("INSERT INTO personas(nombre, telefono) VALUES (?, ?)",
				new String[] { "Manuel Aguirre", "04262326948" });
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

		arg0.execSQL("DROP TABLE IF EXISTS persona");

		this.onCreate(arg0);

	}

	public void addPersona(Persona persona) {
		Log.d("Agregando un persona:", "addPersona(" + persona + ")");

		ContentValues vals = new ContentValues();

		vals.put("nombre", persona.getNombre());
		vals.put("telefono", persona.getTelefono());

		SQLiteDatabase db = this.getWritableDatabase();

		db.insert("personas", null, vals);

		db.close();
	}

	public void updatePersona(Persona persona) {
		Log.d("Actualizando una persona:", "updatePersona(" + persona + ")");

		ContentValues vals = new ContentValues();

		vals.put("nombre", persona.getNombre());
		vals.put("telefono", persona.getTelefono());

		SQLiteDatabase db = this.getWritableDatabase();

		db.update("personas", vals, "id=?",
				new String[] { String.valueOf(persona.getId()) });

		db.close();
	}
	
	public void guardarPersona(Persona persona) {
		if(persona.getId() == 0){
			addPersona(persona);
		}else{
			updatePersona(persona);
		}
	}

	public Persona findPersona(long id) {
		Log.d("BUscando una persona:", "findPersona(" + id + ")");

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query("personas", columnas, "id=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		Persona persona = null;

		if (cursor.moveToFirst()) {
			persona = new Persona();

			persona.setId(cursor.getInt(cursor.getColumnIndex("id")));
			persona.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
			persona.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
		}

		db.close();

		return persona;
	}

	public List<Persona> findAll() {
		Log.d("Buscando a todas las personas:", "findAll()");

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM personas", null);

		Persona persona = null;
		
		List<Persona> lista = new LinkedList<Persona>();

		if (cursor.moveToFirst()) {
			do {
				persona = new Persona();
				persona.setId(cursor.getInt(0));
				persona.setNombre(cursor.getString(1));
				persona.setTelefono(cursor.getString(2));
				lista.add(persona);
			} while (cursor.moveToNext());
		}

		db.close();

		return lista;
	}
	
	public void deletePersona(Persona persona){
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete("personas", "id=?", new String[]{String.valueOf(persona.getId())});
		
		db.close();
	}
}
