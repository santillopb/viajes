package com.androidstudio.santillop.viajes;

public class Viaje {
    private int idViaje;
    private int cantidad;
    private String fecha;
    private String orden;
    private String concepto;
    private String fabrica;
    private double precio;

    public Viaje(int idViaje, int cantidad, String fecha, String orden, String concepto, String fabrica, double precio) {
        this.idViaje = idViaje;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.orden = orden;
        this.concepto = concepto;
        this.fabrica = fabrica;
        this.precio = precio;
    }

    public Viaje(int cantidad, String fecha, String orden, String concepto, String fabrica, double precio) {
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.orden = orden;
        this.concepto = concepto;
        this.fabrica = fabrica;
        this.precio = precio;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFabrica() {
        return fabrica;
    }

    public void setFabrica(String fabrica) {
        this.fabrica = fabrica;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
