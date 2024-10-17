package ies.puerto;

public class Monstruo extends Personaje {
    private boolean cazado;

    public boolean isCazado() {
        return this.cazado;
    }

    public void setCazado(boolean cazado) {
        this.cazado = cazado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Monstruo)) {
            return false;
        }
        Monstruo monstruo = (Monstruo) o;
        return cazado == monstruo.cazado;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "{" +
            " cazado='" + isCazado() + "'" +
            "}";
    }

    public Monstruo() {
    }

    public Monstruo(String nombre) {
        super(nombre);
    }

    public Monstruo(String nombre, Mapa mapa) {
        super(nombre, mapa);
        this.cazado = false;
    }
}