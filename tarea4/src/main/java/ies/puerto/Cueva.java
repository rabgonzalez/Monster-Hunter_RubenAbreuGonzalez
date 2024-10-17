package ies.puerto;

import java.util.concurrent.Semaphore;
import java.util.Objects;

public class Cueva extends Personaje {

    private static final Semaphore semaphore = new Semaphore(1);

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
