package ies.puerto;

import java.util.concurrent.Semaphore;
import java.util.Objects;

public class Cueva extends Personaje {
    private int cantidad;
    public static Semaphore semaphore;

    public Cueva() {
    }

    public Cueva(String nombre) {
        super(nombre);
    }

    public Cueva(String nombre, Mapa mapa) {
        super(nombre, mapa);
    }

    public Cueva(String nombre, Mapa mapa, int[] posicion, int cantidad) {
        super(nombre, mapa, posicion);
        semaphore = new Semaphore(cantidad/2, true);
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cueva)) {
            return false;
        }
        Cueva cueva = (Cueva) o;
        return Objects.equals(this, cueva);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "{" +
            " cantidad='" + getCantidad() + "'" +
            "}";
    }
}