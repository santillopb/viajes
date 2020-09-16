package com.androidstudio.santillop.viajes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DBQueries {
    private Context context;
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public DBQueries(Context context) {
        this.context = context;
    }

    public DBQueries open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    // Viaje
    public boolean insertViaje(Viaje viaje) {
        ContentValues values = new ContentValues();
        values.put(DBConstants.CANTIDAD, viaje.getCantidad());
        values.put(DBConstants.FECHA, viaje.getFecha().toString());
        values.put(DBConstants.ORDEN, viaje.getOrden());
        values.put(DBConstants.CONCEPTO, viaje.getConcepto());
        values.put(DBConstants.FABRICA, viaje.getFabrica());
        values.put(DBConstants.PRECIO, viaje.getPrecio());
        return database.insert(DBConstants.VIAJE_TABLA, null, values) > -1;
    }


    public boolean updateViaje(Viaje viaje) {
        ContentValues values = new ContentValues();
        //values.put(DBConstants.ID_VIAJE, viaje.getIdViaje());
        values.put(DBConstants.CANTIDAD, viaje.getCantidad());
        values.put(DBConstants.FECHA, viaje.getFecha());
        values.put(DBConstants.ORDEN, viaje.getOrden());
        values.put(DBConstants.CONCEPTO, viaje.getConcepto());
        values.put(DBConstants.FABRICA, viaje.getFabrica());
        values.put(DBConstants.PRECIO, viaje.getPrecio());
        return database.update(DBConstants.VIAJE_TABLA,values,DBConstants.ID_VIAJE + " = ?",
                new String[] {String.valueOf(viaje.getIdViaje())}) > -1;
    }

    public ArrayList<Viaje> readViajes() {
        ArrayList<Viaje> list = new ArrayList<>();
        try {
            Cursor cursor;
            database = dbHelper.getReadableDatabase();
            cursor = database.rawQuery(DBConstants.SELECT_QUERY, null);
            list.clear();
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        int idViaje = cursor.getInt(cursor.getColumnIndex(DBConstants.ID_VIAJE));
                        int cantidad = cursor.getInt(cursor.getColumnIndex(DBConstants.CANTIDAD));
                        String fecha = cursor.getString(cursor.getColumnIndex(DBConstants.FECHA));
                        String orden = cursor.getString(cursor.getColumnIndex(DBConstants.ORDEN));
                        String concepto = cursor.getString(cursor.getColumnIndex(DBConstants.CONCEPTO));
                        String fabrica = cursor.getString(cursor.getColumnIndex(DBConstants.FABRICA));
                        double precio = cursor.getDouble(cursor.getColumnIndex(DBConstants.PRECIO));

                        Viaje viaje = new Viaje(idViaje,cantidad,fecha,orden,concepto,fabrica,precio);
                        list.add(viaje);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.v("Exception", e.getMessage());
        }
        return list;
    }
}
