package com.AgroVeterinaria.DTO;

public class ResumenVisitasProductos {
    private int global;
    private int escritorio;
    private int movil;
    private int tablet;
    private int otros;
    
    public ResumenVisitasProductos() {}

    public ResumenVisitasProductos(int global, int escritorio, int movil, int tablet, int otros) {
        this.global = global;
        this.escritorio = escritorio;
        this.movil = movil;
        this.tablet = tablet;
        this.otros = otros;
    }

    public int getGlobal() {
        return global;
    }

    public void setGlobal(int global) {
        this.global = global;
    }

    public int getEscritorio() {
        return escritorio;
    }

    public void setEscritorio(int escritorio) {
        this.escritorio = escritorio;
    }

    public int getMovil() {
        return movil;
    }

    public void setMovil(int movil) {
        this.movil = movil;
    }

    public int getTablet() {
        return tablet;
    }

    public void setTablet(int tablet) {
        this.tablet = tablet;
    }

    public int getOtros() {
        return otros;
    }

    public void setOtros(int otros) {
        this.otros = otros;
    }

    @Override
    public String toString() {
        return "ResumenVisitasProductos [global=" + global + ", escritorio=" + escritorio + ", movil=" + movil
                + ", tablet=" + tablet + ", otros=" + otros + "]";
    }
}