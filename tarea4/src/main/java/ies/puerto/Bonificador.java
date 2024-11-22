package ies.puerto;

public class Bonificador extends Personaje{
    private int bonificacion;

    public Bonificador() {
    }

    public Bonificador(String nombre, Mapa mapa) {
        super(nombre, mapa);
        this.bonificacion = 50;
    }

    public int getBonificacion() {
        return this.bonificacion;
    }

    public void setBonificacion(int bonificacion) {
        this.bonificacion = bonificacion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Bonificador)) {
            return false;
        }
        Bonificador bonificador = (Bonificador) o;
        return bonificacion == bonificador.bonificacion;
    }

    @Override
    public String toString() {
        return "{" +
            " bonificacion='" + getBonificacion() + "'" +
            "}";
    }
}
