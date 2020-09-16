package com.androidstudio.santillop.viajes;

import android.os.Bundle;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class VistaViajes extends AppCompatActivity {
    DBHelper dbHelper;
    DBQueries dbQueries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_viajes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Tabla tabla = new Tabla(this, (TableLayout) findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_tabla);

        dbHelper = new DBHelper(getApplicationContext());
        dbQueries = new DBQueries(getApplicationContext());

        ArrayList<Viaje> viajes = new ArrayList<>();
        dbQueries.open();
        viajes = dbQueries.readViajes();
        dbQueries.close();

        // Recorremos la lista de objetos y añadimos filas a la tabla
        for (int i = 0; i < viajes.size(); i++) {
            ArrayList<String> elementos = new ArrayList<>();
            elementos.add(String.valueOf(viajes.get(i).getCantidad()));
            elementos.add(String.valueOf(viajes.get(i).getFecha()));
            elementos.add(viajes.get(i).getOrden());
            elementos.add(viajes.get(i).getConcepto());
            elementos.add(viajes.get(i).getFabrica());
            elementos.add(String.valueOf(viajes.get(i).getPrecio()));
            tabla.agregarFilaTabla(elementos);
        }
    }
}
