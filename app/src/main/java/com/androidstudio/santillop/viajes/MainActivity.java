package com.androidstudio.santillop.viajes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button btnNuevoViaje, btnRevisarViajes,btnModificarViajes, btnExportarExcel;
    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";
    SQLiteToExcel sqliteToExcel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnNuevoViaje = (Button) findViewById(R.id.btnNuevoViaje);
        btnRevisarViajes = (Button) findViewById(R.id.btnRevisarViajes);
        btnModificarViajes = (Button) findViewById(R.id.btnModificarViajes);
        btnExportarExcel = (Button) findViewById(R.id.btnExportarExcel);

        btnNuevoViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreaViaje.class));
            }
        });
        btnRevisarViajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VistaViajes.class));
            }
        });

        btnModificarViajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditaViajes.class));
            }
        });
        File file = new File(directory_path);
        if (!file.exists()) {
            Log.v("File Created", String.valueOf(file.mkdirs()));
        }
        btnExportarExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        exportDatabase(view);
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }
                else {
                    exportDatabase(view);
                }
            }
        });
    }
    private void exportDatabase(final View view) {
        // Export SQLite DB as EXCEL FILE
        sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DBHelper.DB_NAME, directory_path);
        sqliteToExcel.exportAllTables("viajes.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                Snackbar.make(view,"Viajes exportados con éxito!",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
