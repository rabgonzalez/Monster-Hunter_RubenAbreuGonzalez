package ies.puerto;

import java.util.Objects;

public class Cazador extends Personaje implements Runnable{
    public static int kills = 0;
    private int damage;

    public Cazador() {
    }

    public Cazador(String nombre) {
        super(nombre);
    }

    public Cazador(String nombre, Mapa mapa) {
        super(nombre, mapa);
        damage = 50;
    }

    public Cazador(String nombre, Mapa mapa, int[] posicion) {
        super(nombre, mapa, posicion);
        damage = 50;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cazador)) {
            return false;
        }
        Cazador cazador = (Cazador) o;
        return Objects.equals(this, cazador);
    }

    @Override
    public String toString() {
        return "{" +
            "}";
    }

    @Override
    public void run() {
        int[] nuevaPosicion = null;
        int contador = 0;

        while(true){
            nuevaPosicion = getMapa().generarUbicacionAleatoria();
            getMapa().moverCazador(this, nuevaPosicion);

            if(kills > contador){
                contador = kills;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getMapa().getUbicaciones()[getPosicion()[0]][getPosicion()[1]] = null;
        }
    }
}