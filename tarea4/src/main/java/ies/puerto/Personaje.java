package ies.puerto;
import java.util.Objects;

public class Personaje {
    private String nombre;
    private Mapa mapa;
    private int[] posicion;

    public Personaje() {
    }

    public Personaje(String nombre) {
        this.nombre = nombre;
    }

    public Personaje(String nombre, Mapa mapa) {
        this.nombre = nombre;
        this.mapa = mapa;
    }

    public Personaje(String nombre, Mapa mapa, int[] posicion) {
        this.nombre = nombre;
        this.mapa = mapa;
        this.posicion = posicion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Mapa getMapa() {
        return this.mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    public int[] getPosicion() {
        return this.posicion;
    }

    public void setPosicion(int[] posicion) {
        this.posicion = posicion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Personaje)) {
            return false;
        }
        Personaje personaje = (Personaje) o;
        return Objects.equals(nombre, personaje.nombre) && Objects.equals(mapa, personaje.mapa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, mapa);
    }

    @Override
    public String toString() {
        return "{" +
            " nombre='" + getNombre() + "'" +
            ", mapa='" + getMapa() + "'" +
            "}";
    }    
}