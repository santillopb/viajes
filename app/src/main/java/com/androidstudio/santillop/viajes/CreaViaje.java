package com.androidstudio.santillop.viajes;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CreaViaje extends AppCompatActivity {
    DBHelper dbHelper;
    DBQueries dbQueries;

    EditText txfCantidad, txfFecha, txfOrden, txfConcepto, txfFabrica, txfPrecio;
    Button btnCrearViaje;
    ArrayList<Viaje> viajes = new ArrayList<>();
    String yearStr, monthStr, dayStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crea_viaje);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(getApplicationContext());
        dbQueries = new DBQueries(getApplicationContext());

        txfCantidad = (EditText) findViewById(R.id.txfCantidad);
        txfFecha = (EditText) findViewById(R.id.txfFecha);
        txfOrden = (EditText) findViewById(R.id.txfOrden);
        txfConcepto = (EditText) findViewById(R.id.txfConcepto);
        txfFabrica = (EditText) findViewById(R.id.txfFabrica);
        txfPrecio = (EditText) findViewById(R.id.txfPrecio);
        btnCrearViaje = (Button) findViewById(R.id.btnCrearViaje);

        txfFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnCrearViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate(txfCantidad) && validate(txfFecha) && validate(txfOrden) &&
                        validate(txfConcepto) && validate(txfFabrica) && validate(txfPrecio)) {
                    dbQueries.open();

                    String cantidadStr, fechaFormato, ordenStr, conceptoStr, fabricaStr, precioStr;
                    int cantidadInt;
                    double precioDouble;

                    cantidadStr = txfCantidad.getText().toString();
                    cantidadInt = Integer.parseInt(cantidadStr);
                    fechaFormato = yearStr + "-" + monthStr + "-" + dayStr;
                    ordenStr = txfOrden.getText().toString();
                    conceptoStr = txfConcepto.getText().toString();
                    fabricaStr = txfFabrica.getText().toString();
                    precioStr = txfPrecio.getText().toString();
                    precioDouble = Double.parseDouble(precioStr);
                    Viaje viaje = new Viaje(cantidadInt,fechaFormato,ordenStr,conceptoStr,fabricaStr,precioDouble);

                    dbQueries.insertViaje(viaje);
                    dbQueries.close();
                    Snackbar.make(view, "Viaje creado!", Snackbar.LENGTH_SHORT).show();

                    // Limpiamos los campos
                    txfCantidad.setText("");
                    txfFecha.setText("");
                    txfOrden.setText("");
                    txfConcepto.setText("");
                    txfFabrica.setText("");
                    txfPrecio.setText("");

                    // Reseteamos los focos
                    txfCantidad.clearFocus();
                    txfFecha.clearFocus();
                    txfOrden.clearFocus();
                    txfConcepto.clearFocus();
                    txfFabrica.clearFocus();
                    txfPrecio.clearFocus();
                }
            }
        });

    }
    boolean validate(EditText editText) {
        if (editText.getText().toString().length() == 0) {
            editText.setError("Campo requerido");
            editText.requestFocus();
        }
        return editText.getText().toString().length() > 0;
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                txfFecha.setText(selectedDate);
                yearStr = String.valueOf(year);
                monthStr = String.valueOf(month+1);
                // Si el mes es inferior a 10 le añadimos un cero delante
                if((month+1) < 10) {
                    monthStr = "0" + monthStr;
                }
                dayStr = String.valueOf(day);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }
}
