package com.manuel.personscrud.adapter;

import java.util.List;

import com.manuel.personscrud.model.Persona;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PersonaAdapter extends ArrayAdapter<Persona> {

	public PersonaAdapter(Context context, List<Persona> objects) {
		super(context, android.R.layout.simple_dropdown_item_1line, objects);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = super.getView(position, convertView, parent);
		
		TextView text = (TextView) view;

		Persona persona = getItem(position);

		Spanned texto = Html.fromHtml("<b>" + persona.getNombre() + "</b> (<i>" + persona.getTelefono()
				+ "</i>)");

		text.setText(texto);

		return view;
	}
}
