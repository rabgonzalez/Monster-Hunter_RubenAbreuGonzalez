package ies.puerto;

import java.util.concurrent.Semaphore;
import java.util.Objects;

public class Cueva extends Personaje {

    public static final Semaphore SEMAPHORE = new Semaphore(2, true);

    public Cueva() {
    }

    public Cueva(String nombre) {
        super(nombre);
    }

    public Cueva(String nombre, Mapa mapa) {
        super(nombre, mapa);
    }

    public Cueva(String nombre, Mapa mapa, int[] posicion) {
        super(nombre, mapa, posicion);
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
}