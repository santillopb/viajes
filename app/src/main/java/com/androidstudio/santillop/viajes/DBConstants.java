package com.androidstudio.santillop.viajes;

public class DBConstants {
    static final String VIAJE_TABLA = "viaje";
    static final String ID_VIAJE = "id_viaje";
    static final String CANTIDAD = "cantidad";
    static final String FECHA = "fecha";
    static final String ORDEN = "orden";
    static final String CONCEPTO = "concepto";
    static final String FABRICA = "fabrica";
    static final String PRECIO = "precio";

    static final String CREATE_VIAJE_TABLA = "CREATE TABLE IF NOT EXISTS " + VIAJE_TABLA + " ("
            + ID_VIAJE + " INTEGER PRIMARY KEY,"
            + CANTIDAD + " INTEGER,"
            + FECHA + " DATE,"
            + ORDEN + " TEXT,"
            + CONCEPTO + " TEXT,"
            + FABRICA + " TEXT,"
            + PRECIO + " REAL)";

    static final String SELECT_QUERY = "SELECT * FROM " + VIAJE_TABLA;

    static final String UPDATE_TABLE = "UPDATE " + VIAJE_TABLA + " SET " + CANTIDAD + "= ";
}
