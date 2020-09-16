package com.androidstudio.santillop.viajes;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class EditaViajes extends AppCompatActivity {
    DBHelper dbHelper;
    DBQueries dbQueries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edita_viajes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Tabla tabla = new Tabla(this, (TableLayout) findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_tabla_modificar);

        dbHelper = new DBHelper(getApplicationContext());
        dbQueries = new DBQueries(getApplicationContext());

        // Declaramos la lista de viajes
        ArrayList<Viaje> viajes = new ArrayList<>();
        // Abrimos conexión
        dbQueries.open();
        // Llenamos la lista de viajes haciendo la consulta a la tabla viaje
        viajes = dbQueries.readViajes();
        // Cerramos la conexión
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
            tabla.agregarFilaTablaEditText(elementos);
        }

        // Obtenemos lista filas de la tabla
        final ArrayList<TableRow> filas = tabla.getFilas();

        // Recorremos filas
        for (int i = 0; i < filas.size(); i++) {
            // Declaramos objeto clase TableRow
            TableRow fila = filas.get(i);
            // Declaramos las vistas
            final View vCantidad, vFecha, vOrden, vConcepto, vFabrica, vPrecio, vModificar;
            vCantidad = (View) fila.getChildAt(0);
            vFecha = (View) fila.getChildAt(1);
            vOrden = (View) fila.getChildAt(2);
            vConcepto = (View) fila.getChildAt(3);
            vFabrica = (View) fila.getChildAt(4);
            vPrecio = (View) fila.getChildAt(5);
            vModificar = (View) fila.getChildAt(6);

            final int finalI = i;

            // Cuando pulsamos un boton de la fila modificamos el contenido de ese viaje que se encuentre en dicha fila
            vModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Declaramos los EditText (campos de texto)
                    EditText txfCantidad, txfFecha, txfOrden, txfConcepto, txfFabrica, txfPrecio;
                    // Declaramos el contenido de los EditText objeto String
                    String cantidadStr, fechaStr, ordenStr, conceptoStr, fabricaStr, precioStr;
                    int cantidad;
                    double precio;
                    // Aplicamos un casteo a los view y lo asignamos a los EditText
                    txfCantidad = (EditText) vCantidad;
                    txfFecha = (EditText) vFecha;
                    txfOrden = (EditText) vOrden;
                    txfConcepto = (EditText) vConcepto;
                    txfFabrica = (EditText) vFabrica;
                    txfPrecio = (EditText) vPrecio;

                    // Extraemos el contenido de los EditText y lo asignamos al objeto String
                    cantidadStr = txfCantidad.getText().toString();
                    fechaStr = txfFecha.getText().toString();
                    ordenStr = txfOrden.getText().toString();
                    conceptoStr = txfConcepto.getText().toString();
                    fabricaStr = txfFabrica.getText().toString();
                    precioStr = txfPrecio.getText().toString();

                    // Si cantidadStr = "" cantidad = 0
                    if (cantidadStr.isEmpty()) {
                        cantidad = 0;
                    } else {
                        cantidad = Integer.parseInt(cantidadStr);
                    }

                    // Si precioStr = "" precio = 0
                    if (precioStr.isEmpty()) {
                        precio = 0;
                    } else {
                        precio = Double.parseDouble(precioStr);
                    }

                    // Declaramos objeto viaje
                    Viaje viaje = new Viaje(finalI, cantidad, fechaStr, ordenStr, conceptoStr, fabricaStr, precio);
                    // Abrimos la base de datos y realizamos la modificación del viaje
                    dbQueries.open();
                    dbQueries.updateViaje(viaje);
                    // Cerramos la conexion
                    dbQueries.close();
                    // Mostramos un mensaje informativo
                    Snackbar.make(view, "Viaje " + finalI + " modificado!", Snackbar.LENGTH_SHORT).show();

                }
            });


        }


    }
}
